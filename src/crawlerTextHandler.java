import java.io.*;
import java.util.*;

public class crawlerTextHandler {

    public static void main(String[] args) throws IOException {
        File rawFile=new File("/Users/brayb/Downloads/andorid_java_work/java-workspace/test_check/ReferenceFiles/commonPhrases.txt");
        Set<String> collectStr=new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rawFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(""))
                    continue;
                collectStr.add(line);
            }
        }
        Object[] arr=collectStr.toArray();
        Arrays.sort(arr);

        File txtFile=new File("/Users/brayb/Downloads/andorid_java_work/java-workspace/test_check/ReferenceFiles/Phrases.txt");
        try {
            FileWriter fileWriter=new FileWriter(txtFile);
            for (int i=0;i<arr.length;i++){
                String str=(String) arr[i];
                fileWriter.write(str+"\n");
            }
        }catch (Exception e){
            System.out.println("error");
        }

    }

}


