import java.util.*;
import java.io.*;
public class Checker {
    protected static String userInput, userInNoPunc;
    protected static int arraylength1, arraylength2;
    protected static int score;
    public Checker() {
        this.userInput = "";
        this.arraylength1 = 0;
        this.arraylength2 = 0;
        this.score = 0;
    }
    public void FirstChar(String firstWord, int length) {
        if (firstWord.equals("")){
            score=100;             //empty word sentence zero point
        }
        else if (firstWord.equals(" ")){
            score+=10;      //first char is " ",-10 points
        }
        else {
            char firstChar = firstWord.charAt(0);
            if (!Character.isUpperCase(firstChar)){
                score+=10;   //did not capitalize, -10 points
            }
        }
    }

    public void LastChar(String lastWord) {
        if (!lastWord.endsWith("?") && !lastWord.endsWith("!") && !lastWord.endsWith(".")){
            score+=20;  //no proper punctuation,-20
        }
    }

    public void CheckLength(int length) {
        if (length >= 35) {
            score+=20; //sentence too long(over 35 words),-20 points
        }
    }

    public void WeakWord(String reallyVery, int length) {
        if (reallyVery.equalsIgnoreCase("very")) {
            score+=5;  //use weak word "very",-5
        }
        else if (reallyVery.equalsIgnoreCase("really")){
            score+=5;  //use weak word "really",-5
        }
    }

    public void CheckNums(String wordNum) {
        if (wordNum.equalsIgnoreCase("0") || wordNum.equalsIgnoreCase("1") || wordNum.equalsIgnoreCase("2") || wordNum.equalsIgnoreCase("3") || wordNum.equalsIgnoreCase("4") || wordNum.equalsIgnoreCase("5") || wordNum.equalsIgnoreCase("6") || wordNum.equalsIgnoreCase("7") || wordNum.equalsIgnoreCase("8") || wordNum.equalsIgnoreCase("9")){
            score+=5; //did not spell numbers,-5
        }
    }

    public void CheckQuote(String quote, int quoteCount) {
        if (quote.equalsIgnoreCase("\"")){
            quoteCount++;
        }
        if (quoteCount % 2 != 0){
            score+=5;  //quote use error,-5
        } //else its even, which is fine.
    }

    public void sameWord(Object[] userInArray) {
        for (int i = 1; i < userInArray.length; i++){
            if (( (String) userInArray[i]).equalsIgnoreCase((String) userInArray[i - 1])) {
                score+=30; //repeat a word twice,-30
            }
        }
    }

    public void CheckSpace(String userInArray) {
        for (int i = 0; i < userInArray.length()-1; i++){
            if (userInArray.charAt(i)==' ' && userInArray.charAt(i)==userInArray.charAt(i+1)) {
                score+=10; //two much space between two words,-10
            }
        }
    }

    public void CheckAandAN(String aAn, Object[] userInArray, int i){
        if (aAn.equalsIgnoreCase("a")) {
            String aAfter = (String) (userInArray[i + 1]);
            String a2 = aAfter.substring(0, 1);
            if (a2.equalsIgnoreCase("a") || a2.equalsIgnoreCase("e") || a2.equalsIgnoreCase("i") || a2.equalsIgnoreCase("o") || a2.equalsIgnoreCase("u")){
                score+=10; //should use "an",-10
            }
        }
        else if (aAn.equalsIgnoreCase("an")){ //a,e,i,o,u (vowel)
            String anAfter = (String) (userInArray[i + 1]);
            String an2 = anAfter.substring(0,1);
            if (!(an2.equalsIgnoreCase("a") || an2.equalsIgnoreCase("e") || an2.equalsIgnoreCase("i") || an2.equalsIgnoreCase("o") || an2.equalsIgnoreCase("u"))){
                score+=10; //should use "a",-10
            }
        }
    }

    public void CheckCompound(Object[] userInArray) {
        if (userInArray.length >= 15){
            int notAOBcount = 0;
            for (int i = 0; i < userInArray.length; i++){
                String aob = (String) userInArray[i];
                if (!(aob.equalsIgnoreCase("and") || aob.equalsIgnoreCase("or") || aob.equalsIgnoreCase("but"))){
                    notAOBcount++;
                }
            }
            if (notAOBcount == userInArray.length){
                score+=10; //should better add "and","but","or"
            }
        }
    }
    public Map<String,Integer> CheckPhrases(Map<String,Integer> phrases,String dirName)throws IOException{ //check whether the phrase is in the phrase list
        for (String item:phrases.keySet()){
            File phraseFile = new File(dirName + "commonPhrases.txt");
            BufferedReader in = new BufferedReader(new FileReader(phraseFile));
            String line;
            while ((line = in.readLine()) != null){
                item=item.replaceAll("[.,?!]","");
                if (line.equalsIgnoreCase(item)){
                    phrases.replace(item,0);
                    break;
                }
            }
            in.close();
        }
        return phrases;
    }

    public void CheckNounVerb(Object[] userInArray, String dirName) throws IOException {
        int nounPlace = 0, verbPlace = 0;
        boolean nounFound = false, verbFound = false;
        nounLoop:
        for (int i = 0; i < userInArray.length; i++){
            String currentNoun = (String) userInArray[i];

            File nounFile = new File(dirName + "nounList.txt"); //assign the noun file
            BufferedReader in = new BufferedReader(new FileReader(nounFile));
            String line;
            while ((line = in.readLine()) != null){
                if (line.equalsIgnoreCase(currentNoun)){
                    nounFound = true;
                    nounPlace = (i);
                    break nounLoop; //break out of for loop
                }
            }
            in.close();
        }
        verbLoop:
        for (int i = 0; i < userInArray.length; i++){
            String currentVerb = (String) userInArray[i];
            File verbFile = new File(dirName + "verbList.txt");//Assigns verb file
            BufferedReader in = new BufferedReader(new FileReader(verbFile));
            String line;
            while ((line = in.readLine()) != null){
                if (line.equalsIgnoreCase(currentVerb)){
                    verbFound = true;
                    verbPlace = (i);
                    break verbLoop; //break out of for loop
                }
            }
            in.close();
        }
        if ((nounFound) && (verbFound)){ //if both were found:
            if (nounPlace > verbPlace){//check if noun didn't come first
                score+=20; // should have verb before a noun
            }
        }
        else if (nounFound == false){//otherwise one is missing
            score+=30; //no noun found
            if (verbFound == false){
                score+=30; //no verb found
            }
        }
        else if (verbFound == false){//otherwise one is missing
            score+=30; //no verb found
        }
    }
    static Checker c1 = new Checker();
    static String[] sentenceCollection;
    public static void main(String[] args) throws IOException {
        if (args.length<1)
            System.out.println("Please choose a file in the program argument!");
        Scanner sentence = new Scanner(new File(args[0]));
        ArrayList<String> sentenceList = new ArrayList<String>();
        while (sentence.hasNextLine()) {
            sentenceList.add(sentence.nextLine());
        }
        sentence.close();
        String[] sentenceArray = sentenceList.toArray(new String[sentenceList.size()]);
        for (int r=0;r<sentenceArray.length;r++) {
            sentenceCollection = sentenceArray[r].split("(?<=[.!?])\\s*");
        }

        for (int index=0;index<sentenceCollection.length;index++) {  //check the whole file
            userInput=sentenceCollection[index];
            userInNoPunc = userInput.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
            Map<String,Integer> phrases=new HashMap<>();  //used to store different phrases of a sentence
            String[] userInArray = userInNoPunc.split(" "); //each word WITHOUT PUNCTUATION is an element
            String[] userInArray2 = userInput.split(""); //each character is an element
            String[] userInArray3 = userInput.split(" "); //each word is an element
            String userInArray4=userInput; //the raw input string
            for (int i = 0, l = userInArray.length; i + 1 < l; i++)
                phrases.put(userInArray[i] + " " + userInArray[i + 1], 100);  //add the phrases with length==2
            for (int i = 0, l = userInArray.length; i + 3 < l; i++)
                phrases.put(userInArray[i] + " " + userInArray[i + 1]+" "+userInArray[i+2],100); //add the phrases with length==3
            arraylength1 = userInArray.length;
            arraylength2 = userInArray2.length;

            final String dir = System.getProperty("user.dir");
            String dirName = dir + "/ReferenceFiles/";
            File dirObj = new File(dirName);
            if (!dirObj.exists()){
                dirObj.mkdir();
            }
            c1.CheckNounVerb(userInArray, dirName);
            c1.CheckCompound(userInArray);
            if (!(userInArray.length == 0)){
                String firstWord = userInArray[0];
                c1.FirstChar(firstWord, arraylength1);

                String lastWord = userInArray3[userInArray3.length - 1];
                c1.LastChar(lastWord);
            }
            else {
                System.out.println("The sentence has " + userInArray.length + " words, which isn't accepted.");
            }

            for (int i = 0; i < userInArray.length; i++){
                String reallyVery = userInArray[i];
                c1.WeakWord(reallyVery,arraylength1);
            }
            c1.CheckLength(arraylength1);
            c1.sameWord(userInArray3);
            c1.CheckSpace(userInArray4);
            phrases=c1.CheckPhrases(phrases,dirName);  //check whether the phrase is in the commonPhrase list

            int quoteCount = 0;
            for (int i = 0; i < userInArray2.length; i++){
                String quote = userInArray2[i];
                c1.CheckQuote(quote, quoteCount);
            }

            for (int i = 0; i < userInArray.length; i++){
                String wordNum = userInArray[i];
                c1.CheckNums(wordNum);
            }

            for (int i = 0; i < userInArray.length; i++){
                String aAn = userInArray[i];
                c1.CheckAandAN(aAn, userInArray, i);
            }
            int phraseAverage=0;
            for (int value:phrases.values()){
                phraseAverage+=value;
            }
            phraseAverage=phraseAverage/phrases.size();
            score+=phraseAverage;
            System.out.println("{");
            System.out.println("  sentences: {\n    "+userInput+":"+score);
            System.out.println("  },");
            System.out.println("  phrases : {");
            for (Map.Entry<String,Integer> entry:phrases.entrySet())
                System.out.println("   "+entry.getKey()+":"+entry.getValue());
            System.out.println("  }");
            System.out.println("}");
            score=0;   //update the score
            phrases.clear();


        }
    }
}