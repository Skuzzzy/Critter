package General;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by Daniel on 5/20/2015.
 */
public class Crawler {
    private Queue links; // General.URLWrapper objects
    private HashSet<String> alreadyTouched;
    private HashSet<String> alreadyProcessed;
    private CrawlStrategy crawlInfo;
    private Parser parser;

    public Crawler(CrawlStrategy cInfo) {
        alreadyTouched = new HashSet<String>();
        alreadyProcessed = new HashSet<String>();

        crawlInfo = cInfo;
        parser = crawlInfo.getMatchingParser();

        links = new LinkedList();
        links.addAll(crawlInfo.getSeedURLS());
    }

    public void go() {

        // While there are more links to be processed
        while( ! links.isEmpty() ) {

            URLWrapper currentURL = (URLWrapper)links.remove();

            // Verify this has not been crawled
            if( alreadyTouched.contains(currentURL.getFullURL()) ) {
                skip(currentURL, "Link has already been crawled");
                continue;
            }

            // Check URL Protocol
            if( ! Arrays.asList(crawlInfo.acceptedProtocols()).contains(currentURL.getProtocol()) ) {
                skip(currentURL, "Unaccepted protocol");
                continue;
            }

            // Check robots.txt
            if( ! robotsAllowed(currentURL) ) {
                skip(currentURL,"Robots not allowed");
                continue;
            }

            ArrayList<URLWrapper> documentsToParse = getLinksFromURL(currentURL);


            // Another horrible hack
            for(int i=0; i<documentsToParse.size(); i++) {
                documentsToParse.set(i,new URLWrapper(documentsToParse.get(i).getFullURL().replaceFirst("/news/m","/news/")));
            }

            for(URLWrapper document : documentsToParse) {
                if( ! alreadyProcessed.contains(document.getFullURL()) ) {
                    parser.parsePage(document);
                    alreadyProcessed.add(document.getFullURL());
                }
            }

            alreadyTouched.add(currentURL.getFullURL());

        }
    }

    public void skip(URLWrapper url, String reason) {
        System.out.printf("Skipping (%s) (%s)\n",reason,url.getFullURL());
    }

    public boolean robotsAllowed(URLWrapper url) {
        // Todo implement .Disallow.
        return true;
    }

    public ArrayList<URLWrapper> getLinksFromURL(URLWrapper url) {
        ArrayList<URLWrapper> links = new ArrayList<URLWrapper>();
        try {
            Document doc = Jsoup.connect(url.getFullURL()).get();
            Elements linkBlocks = doc.select(crawlInfo.getJsoupLinkSelector());
            for(Element linkB : linkBlocks) {
                String extractedURL = linkB.attr("href");
                if(extractedURL.matches(crawlInfo.getRegexSelector()) && extractedURL.endsWith(".html") && extractedURL.contains("/news/")) { // Todo remove horrible hacks
                    links.add(new URLWrapper(extractedURL));
                }

            }
        } catch (IOException e) {
            System.out.println("Failed to fetch "+url);
            //e.printStackTrace();
        }
        return links;
    }

}
