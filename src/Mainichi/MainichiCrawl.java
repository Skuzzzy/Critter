package Mainichi;

import General.*;
import java.util.ArrayList;

/**
 * Created by Daniel on 5/20/2015.
 */
public class MainichiCrawl implements CrawlStrategy {
    private String[] acceptedProtocols = {"http","https"};
    private String[] seedLinks = {
            "http://mainichi.jp/select/shakai/",
            "http://mainichi.jp/select/seiji/",
            "http://mainichi.jp/select/biz/",
            "http://mainichi.jp/select/world/",
            "http://mainichi.jp/select/science/"
    };

    public MainichiCrawl() {

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
        return "body li a";
    }

    public String getRegexSelector() {
        return "http://mainichi.jp/.++";
    }

    public Parser getMatchingParser() {
        return new MainichiParser();
    }

}
