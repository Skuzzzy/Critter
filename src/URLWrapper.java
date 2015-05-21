import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Daniel on 5/20/2015.
 */
public class URLWrapper {
    private URL javaURL;

    public URLWrapper(String fullURL) {

        try {
            javaURL = new URL(fullURL);
        } catch (MalformedURLException e){
            e.printStackTrace(); // Todo fix this up
        }
    }

    public URLWrapper(String baseURL, String relativeURL) {
        try {
            URL base = new URL(baseURL);
            javaURL = new URL(base, relativeURL);
        } catch (MalformedURLException e){
            e.printStackTrace(); // Todo fix this up
        }
    }

    public URLWrapper(URLWrapper context, String url) {

    }

    public String getArticleInfoString(){
        return javaURL.getPath().replace("/News/","").replace("/","-").replace(".html","");
    }

    public String getFullURL() {
        return javaURL.getProtocol()+"://"+javaURL.getHost()+javaURL.getPath();
    }

    public String getProtocol() {
        return javaURL.getProtocol();
    }
}
