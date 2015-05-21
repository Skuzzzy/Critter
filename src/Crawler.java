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
    private Queue links; // URL objects
    private HashSet<String> alreadyTouched;
    private CrawlStrategy crawlInfo;
    private Parser parser;

    public Crawler() {
        alreadyTouched = new HashSet<String>();
        crawlInfo = new ExciteCrawl();
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

            for(URLWrapper document : documentsToParse) {
                parser.parsePage(document);
                alreadyTouched.add(currentURL.getFullURL());
            }


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
                if(extractedURL.matches(crawlInfo.getRegexSelector())) {
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
