package citiaps.twitter_api;

import java.io.FileWriter;
import java.io.IOException;

public class Util {
    public static void writeStringToFile(String filePathAndName, String stringToBeWritten) throws IOException {
        try
        {
            String filename= filePathAndName;
            boolean append = true;
            FileWriter fw = new FileWriter(filename,append);

            fw.write(stringToBeWritten);//appends the string to the file
            fw.write("\n");
            fw.close();
        }

        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }

    }
}
