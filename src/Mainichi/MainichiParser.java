package Mainichi;

import General.*;
import com.google.gson.stream.JsonWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

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
            JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(new FileOutputStream((docnum++)+".json"),"UTF-8"));

            System.out.println(page.getFullURL());
            try {
                Document doc = Jsoup.connect(page.getFullURL()).get();
                Element story = doc.select(selector).first();
                Elements pars = story.select("p");

                String s = story.text().trim();

                jsonWriter.beginObject();
                jsonWriter.name(page.getFullURL());
                jsonWriter.beginArray();

                //jsonWriter.value(s);
                for(Element e : pars) {
                    jsonWriter.value(e.text().trim());
                }

                jsonWriter.endArray();
                jsonWriter.endObject();
                jsonWriter.close();
            } catch (Exception e) {
                System.out.println("Failed to fetch "+page.getFullURL());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Failed to create json file to write to");
        }
    }
}
