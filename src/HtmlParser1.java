import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser1 {

    String htmlUrl;
    ArrayList<String> hrefList = new ArrayList();

    public HtmlParser1(String htmlUrl) {
        // TODO Automated Generation of constructor stub
        this.htmlUrl = htmlUrl;
    }

    public ArrayList<String> parser() throws IOException { //obtain hyperlink
        URL url = new URL(htmlUrl); //URL object initialization
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "gb2312"); //Input Stream Initialization
        BufferedReader br = new BufferedReader(isr);
        String str = null, rs = null;
        while ((str = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("<a href=(.*?)>"); //pattern web-page format checking
            Matcher matcher = pattern.matcher(str);

            while (matcher.find()) {
                Pattern pattern1 = Pattern.compile("\"(.*?)\"");
                Matcher matcher1 = pattern1.matcher(matcher.group(1));
                if (matcher1.find()) {
                    rs = matcher1.group(1);
                }
                if (rs.indexOf("http") != -1) {  //Standard URL
                    if (rs != null)
                        hrefList.add(rs);
                }
            }
        }
        return hrefList;
    }
}







