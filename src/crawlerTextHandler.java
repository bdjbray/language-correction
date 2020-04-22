import java.io.*;
import java.util.*;

public class crawlerTextHandler {

    public static void main(String[] args) throws IOException {
        String dir1="";
        String dir2="";
        FileReader fileReader = new FileReader(dir1);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String inputLine;
        Set<String> lineList = new HashSet<>();
        while ((inputLine = bufferedReader.readLine()) != null) {
            lineList.add(inputLine);
        }
        fileReader.close();
        Object[] arr=lineList.toArray();

        Arrays.sort(arr);

        FileWriter fileWriter = new FileWriter(dir2);
        PrintWriter out = new PrintWriter(fileWriter);
        for (Object outputLine : arr) {
            String str=(String) outputLine;
            out.println(str);
        }
        out.flush();
        out.close();
        fileWriter.close();

    }

}


