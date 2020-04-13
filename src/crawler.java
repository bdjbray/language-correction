import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class crawler {


    public static void main(String[] args) throws IOException {
        System.out.println("beging...");

        String StartLink = "https://www.boston.gov/departments/parking-clerk/how-get-resident-parking-permit"; // user-defined link
        String SaveLocation_html = "C:/Users/36806/Desktop/crawl_/www.txt"; // local html file
        String SaveLocation_output = "C:/Users/36806/Desktop/crawl_/output.txt"; //txt file before the "20" filter for tracing error
        String SaveLocation_clause = "C:/Users/36806/Desktop/crawl_/clause.txt"; //txt file for checker to use

        HashSet<String> url_set = new HashSet<>();
        url_set.add(StartLink);

//        DownLoadPages(StartLink, SaveLocation_html);
        String X = html_extract(readTxt(SaveLocation_html));
//        X= CUT_Sentence(X);
//        TextToFile(SaveLocation_output, X,0);
//        X = filter(SaveLocation_output,20);
//        TextToFile(SaveLocation_clause, X,1);


        for(int n=0; n<30; n++) { // To check whether link goes to linking
            HtmlParser1 HP = new HtmlParser1(StartLink);
            ArrayList<String> hrefList = HP.parser();
            System.out.println("number link:"+hrefList.size());
            int num_link = hrefList.size();
            for (int i = 0; i < num_link; i++)
                System.out.println(hrefList.get(i));
            System.out.println("\n");

            Boolean N=true;
            while(N) {
                int iRandom = (int) (Math.random() * (num_link-1));
                StartLink = hrefList.get(iRandom);

                if(url_set.contains(StartLink) == false) {
                    url_set.add(StartLink);
                    N = false;
                    System.out.println("find");
                }

            }

            System.out.println("link now:  " + StartLink);
            DownLoadPages(StartLink, SaveLocation_html);
            X = html_extract(readTxt(SaveLocation_html));
            X= CUT_Sentence(X);
            TextToFile(SaveLocation_output, X,0);
            X = filter(SaveLocation_output,20);
            TextToFile(SaveLocation_clause, X,1);
        }
        System.out.println("end.");
    }



    public static void DownLoadPages(String urlStr, String outPath)
    {
        /** Byte length of Input Stram */
        int chByte = 0;
        /** URL Address */
        URL url = null;
        /** http link */
        HttpURLConnection httpConn = null;
        /** Input Stream */
        InputStream in = null;
        /** File Output Stream */
        FileOutputStream out = null;
        try
        {
            url = new URL(urlStr);
            httpConn = (HttpURLConnection) url.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");

            in = httpConn.getInputStream();
            out = new FileOutputStream(new File(outPath));

            chByte = in.read();
            while (chByte != -1)
            {
                out.write(chByte);
                chByte = in.read();
            }
        }
        //URL Invalid error handling
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
                in.close();
                httpConn.disconnect();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    ////////////////
    // read txt file to string
    ////////////////
    public static String readTxt(String filePath) {// D:\\a.txt
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));
            String lineTxt = null;
            while ((lineTxt = bfr.readLine()) != null) {
                result.append(lineTxt).append("\n");
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    ////////////////
    // Website Content extraction
    ////////////////
    public static String html_extract(String inputString) {

        String htmlStr = inputString; // String with html label
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // regularization for script
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // regularization for style
            String regEx_html = "<[^>]+>"; //  re. for html label
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // script label filter
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // style label filter
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // html label filter
            textStr = htmlStr;
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        //Extra space elimination
        textStr = textStr.replaceAll("[ ]+", " ");
        textStr = textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", " ");
        textStr= textStr.replaceAll("\\d+"," ");
        textStr= textStr.replaceAll("[^0-9a-zA-Z]J*"," ");
        //  textStr = textStr.replaceAll("\\d+","");
        return textStr;
    }

////////////
// information stores to txt file as string
/////////////

    public static void TextToFile(final String strFilename, final String strBuffer, int cover_or_not)
    {
        try
        {
            if(cover_or_not==1) { // 1 for create
                FileWriter writer = new FileWriter(strFilename, true);
                writer.write(strBuffer);
                writer.close();}//
            else { // 0 for overwrite
                FileWriter writer = new FileWriter(strFilename);
                writer.write(strBuffer);
                writer.close();
            }

        }
        catch (IOException e)
        {
            //
            e.printStackTrace();
        }
    }

    ///////////////
///Cut raw paragraph contents into sentences.
///////////////
    public static String CUT_Sentence(String X)
    {
        char[] x = X.toCharArray();

        try {
            for(int i=1;i<X.length();i++)
            {

                if( x[i] >='A' && x[i]<='Z') //Enter a new line when Capital character appear
                {
                    x[i-1] = '\n';
                }
            }

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        //剔除空格行 extra space elimination
        String cut_text = String.valueOf(x);;
        //  textStr = textStr.replaceAll("\\d+","");
        System.out.println("cut");
        StringTokenizer pas = new StringTokenizer(cut_text," ");//Empty Line Elimination
        cut_text="";
        while (pas.hasMoreTokens())
        {
            String s = pas.nextToken();
            cut_text = cut_text+s+" ";
        }


        return cut_text;
    }

    ////////////
//一行一行读，大于20 删除
///////
    public static String filter(String filePath,int max) {
        StringBuilder result = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(filePath)),"UTF-8");
            BufferedReader bfr = new BufferedReader(isr);
            String lineTxt = null;
            while ((lineTxt = bfr.readLine()) != null) {
                if (lineTxt.length()>max)
                {result.append(lineTxt).append("\n");}
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}