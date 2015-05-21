package Mainichi;

import General.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Daniel on 5/20/2015.
 */

public class MainichiParser implements Parser {
    private static final String selector = "div.NewsBody";
    private int docnum;

    public MainichiParser() {
        docnum = 0;
    }

    public void parsePage(URLWrapper page) {
        try {
            System.out.println(page.getFullURL());
            Document doc = Jsoup.connect(page.getFullURL()).get();
            Element story = doc.select(selector).first();

            //List sentences = story.select("p");
            System.out.println(docnum);
            String s = story.text().trim();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(""+(docnum++)), "UTF-8"));
            try {
                out.write(s);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch "+page.getFullURL());
            e.printStackTrace();
        }
    }
}
