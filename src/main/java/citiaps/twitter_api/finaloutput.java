package citiaps.twitter_api;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class finaloutput {

        private static final Integer ONE = 1;

        public static void main(String[] args) {
            Map<String, Integer> map = new HashMap<String, Integer>();

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:\\bitbucket\\group11\\infectiousKeywords.txt")),
                        "UTF-8"));
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    String[] names = lineTxt.split("\\s");
                    for (String name : names) {
                        if (map.keySet().contains(name)) {
                            map.put(name, (map.get(name) + ONE));
                        } else {
                            map.put(name, ONE);
                        }
                    }
                }
                br.close();
            } catch (Exception e) {
                System.err.println("read errors :" + e);
            }

            
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("final.txt")),
                        "UTF-8"));
                int count=0;
                for (String name : map.keySet()) {
                    if(!isContainNoEnglish(name)) {
                        bw.write(name + " ");
                        count++;
                    }
                    if (count%2 == 0){
                        bw.newLine();
                    }
                }
                bw.close();
            } catch (Exception e) {
                System.err.println("write errors :" + e);
            }
        }
        public static boolean isContainNoEnglish(String str) {

            Pattern p = Pattern.compile("[^a-zA-Z\\s]");
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
            return false;
        }
    }

