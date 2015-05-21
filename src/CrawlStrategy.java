import java.util.ArrayList;

/**
 * Created by Daniel on 5/20/2015.
 */
public interface CrawlStrategy {
    public String[] acceptedProtocols();
    public ArrayList<URLWrapper> getSeedURLS();
    public String getJsoupLinkSelector();
    public String getRegexSelector();
}
