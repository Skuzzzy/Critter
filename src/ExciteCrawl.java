import java.util.ArrayList;

/**
 * Created by Daniel on 5/20/2015.
 */
public class ExciteCrawl implements CrawlStrategy {
    private String[] acceptedProtocols = {"http","https"};
    private String[] seedLinks = {
        "http://www.excite.co.jp/News/society_g/",
        "http://www.excite.co.jp/News/politics_g/",
        "http://www.excite.co.jp/News/health/",
        "http://www.excite.co.jp/News/science/",
        "http://www.excite.co.jp/News/local/",
        "http://www.excite.co.jp/News/society_clm/",
        "http://www.excite.co.jp/News/weather/"
    };

    public ExciteCrawl() {

    }

    public String[] acceptedProtocols() {
        return acceptedProtocols;
    }

    public ArrayList<URLWrapper> getSeedURLS() {
        ArrayList<URLWrapper> seeds = new ArrayList<URLWrapper>();
        for(String seed : seedLinks) {
            seeds.add(new URLWrapper(seed));
        }
        return seeds;
    }

    public String getJsoupLinkSelector() {
        return "body div a";
    }

    public String getRegexSelector() {
        return "http://www.excite.co.jp/News/.++";
    }

}
