import java.io.*;
import java.util.Set;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.HttpURLConnection;



public class crawler {


    public static void main(String[] args) throws IOException {
        System.out.println("beging...");

        String StartLink = "https://en.wikipedia.org/wiki/Wi-Fi";
        String SaveLocation_html = "C:/Users/36806/Desktop/crawl_/www.txt";
        String SaveLocation_output = "C:/Users/36806/Desktop/crawl_/output.txt";

        DownLoadPages(StartLink, SaveLocation_html);
        System.out.println("end.");
        String X = html_extract(readTxt(SaveLocation_html));
        TextToFile(SaveLocation_output, X);

        for(int n=0; n<5; n++) {
            HtmlParser1 HP = new HtmlParser1(StartLink);
            ArrayList<String> hrefList = HP.parser();
            for (int i = 0; i < 10; i++)
                System.out.println(hrefList.get(i));
            System.out.println("\n");

            int iRandom = (int)(Math.random()*9); // 0-9随机数
            StartLink = hrefList.get(iRandom);
            System.out.println("link now:  " + StartLink);
            DownLoadPages(StartLink, SaveLocation_html);
            X = html_extract(readTxt(SaveLocation_html));
            TextToFile(SaveLocation_output, X);
        }
  //
    }
    public static void DownLoadPages(String urlStr, String outPath)
    {
        /** 读入输入流的数据长度 */
        int chByte = 0;
        /** 网络的url地址 */
        URL url = null;
        /** http连接 */
        HttpURLConnection httpConn = null;
        /** 输入流 */
        InputStream in = null;
        /** 文件输出流 */
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
    // 读txt to string
    ////////////////
    public static String readTxt(String filePath) {// D:\\a.txt
        StringBuilder result = new StringBuilder();
        try {
//          BufferedReader bfr = new BufferedReader(new FileReader(new File(filePath)));
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
    // 提取内容
    ////////////////
    public static String html_extract(String inputString) {

        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        //剔除空格行
        textStr = textStr.replaceAll("[ ]+", " ");
        textStr = textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        return textStr;// 返回文本字符串
    }
////////////
// 写进txt
/////////////


public static void TextToFile(final String strFilename, final String strBuffer)
{
    try
    {
        FileWriter writer = new FileWriter(strFilename, true);
        writer.write(strBuffer);
        writer.close();


    }
    catch (IOException e)
    {
        //
        e.printStackTrace();
    }
}






}
