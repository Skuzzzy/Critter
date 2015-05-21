import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel on 5/20/2015.
 */
public class ExciteParser {
    private static final String selector = "div.story";
    private int docnum;

    public ExciteParser() {
        docnum = 0;
    }

    public ArrayList<String> ParsePage(URLWrapper page) {

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
        return new ArrayList<String>();
    }
}
