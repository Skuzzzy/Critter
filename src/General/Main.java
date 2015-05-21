package General;

import Mainichi.MainichiCrawl;

/**
 * Created by Daniel on 5/20/2015.
 */
public class Main {
    public static void main(String[] args) {
        Crawler a = new Crawler(new MainichiCrawl());
        a.go();
    }
}
