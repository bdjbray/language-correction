import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import dataStructure.AVLTree;
public class Checker {
    protected static String userInput, userInNoPunc;
    protected static int arraylength1, arraylength2;
    protected static int score;
    protected AVLTree nounList = new AVLTree();
    protected AVLTree verbList = new AVLTree();
    protected AVLTree phraseList = new AVLTree();
    public Checker() {
        this.userInput = "";
        this.arraylength1 = 0;
        this.arraylength2 = 0;
        this.score = 0;
    }


    static class simpleGUI extends JFrame {  //a simple java GUI
        private JButton normal;
        private JButton normal2;
        private JTextField textField1;
        private JTextArea resultArea;
        JFrame frame = new JFrame("Warning");
        JFrame frame2 = new JFrame("Error");

        public simpleGUI(){
            super("checker");
            setLayout(new FlowLayout());

            textField1=new JTextField("please type or paste the path to the file",23); //the textfield
            add(textField1);

            normal=new JButton("confirm");  //the button to confirm
            add(normal);
            normal2=new JButton("show result");  //the button to show result
            add(normal2);

            resultArea= new JTextArea(40, 25);
            JScrollPane scroll = new JScrollPane ( resultArea );
            scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
            add(scroll);

            normal.addActionListener(new ActionListener(){   // if 'confirm' clicked
                public void actionPerformed(ActionEvent ae){
                    directory= textField1.getText();
                    try{
                        Scanner testScanner = new Scanner(new File(directory)); // see if it is a right path
                        String testStr=testScanner.nextLine();
                        signal=1;
                        signal2=0;
                        resultArea.setText("");
                        collect.setLength(0);
                    }catch (Exception e){         // error handling
                        JOptionPane.showMessageDialog(frame2,
                                "Please type or paste the path to the file!\nYou may use the test file that I provided.\nExample" +
                                        " path (Mac):/Users/Brian/Downloads/group11/TestFiles/simpleTest.txt",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            normal2.addActionListener(new ActionListener(){  // if 'show result' clicked
                public void actionPerformed(ActionEvent ae){
                    if (signal2==1) {
                        resultArea.setText(collect.toString());
                        textField1.setText("");
                    }
                    else {
                        JOptionPane.showMessageDialog(frame,
                                "The checker is still working, please try again later!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
        }

    }

    public void init_checker(String dirName) throws IOException {
        // init nounList
        File nounFile = new File(dirName + "nounList.txt"); //assign the noun file
        BufferedReader in = new BufferedReader(new FileReader(nounFile));
        String line;
        while ((line = in.readLine()) != null){
            nounList.root = nounList.insert(nounList.root, line);
        }
        in.close();

        //init verbList
        File verbFile = new File(dirName + "verbList.txt"); //assign the noun file
        in = new BufferedReader(new FileReader(verbFile));
        while ((line = in.readLine()) != null){
            verbList.root = verbList.insert(verbList.root, line);
        }
        in.close();

        //init phraseList
        File phraseFile = new File(dirName + "Phrases.txt");
        in = new BufferedReader(new FileReader(phraseFile));
        while ((line = in.readLine()) != null){
            phraseList.root = phraseList.insert(phraseList.root, line);
        }
        in.close();

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

    public void checkDoubleUpper(String currentSentence){
        for (int i=0;i<currentSentence.length()-1;i++){
            if (Character.isUpperCase(currentSentence.charAt(i))&&(Character.isUpperCase(currentSentence.charAt(i+1))))
                score+=10;  // to or more continuous uppercase characters
        }
    }

    public void CheckSpace(String userInArray) {
        for (int i = 0; i < userInArray.length()-1; i++){
            if (userInArray.charAt(i)==' ' && userInArray.charAt(i)==userInArray.charAt(i+1)) {
                score+=10; //too much space between two words,-10
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

    public Map<String,Integer> CheckCountriesPeople(String[] arr,String dirName,Map<String,Integer> phrases) throws IOException {
        for (String item:arr){
            File countryFile = new File(dirName + "Countries.txt");    //check whether the sentence contain country name
            BufferedReader in = new BufferedReader(new FileReader(countryFile));  //or people's name
            String line;
            while ((line = in.readLine()) != null){
                item=item.replaceAll("[.,?!]","");
                if (line.equalsIgnoreCase(item)){
                    phrases=HandleCountryName(line,item,arr,phrases);
                    break;
                }
            }
            in.close();

            File phraseFile = new File(dirName + "names.txt");    //check whether the sentence contain country name
            BufferedReader in2 = new BufferedReader(new FileReader(phraseFile));
            while ((line = in2.readLine()) != null){
                item=item.replaceAll("[.,?!]","");
                if (line.equalsIgnoreCase(item)){
                    phrases=HandleCountryName(line,item,arr,phrases);
                    break;
                }
            }
            in2.close();
        }
        return phrases;
    }

    public Map<String,Integer> HandleCountryName(String countryName,String nameInInput,String[] arr,Map<String,Integer> phrases){
        LinkedList<String> containCountry=new LinkedList<>();
        if (!Character.isUpperCase(nameInInput.charAt(0))){
            score+=10;       //the first character of a Country or people's name should be upper case
        }
        for (String thePhrase:phrases.keySet()){
            if (thePhrase.toLowerCase().contains(countryName.toLowerCase())) {
                containCountry.add(thePhrase);
            }
        }
        for (String item:containCountry){
            phrases.remove(item);        // remove the phrases that contain country name or people's name
        }
        return phrases;
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
            item=item.replaceAll("[.,?!]","");
            if(phraseList.find(phraseList.root,item)){
                phrases.replace(item,0);
            }
        }
        return phrases;
    }


    public void CheckNounVerb(Object[] userInArray, String dirName) throws IOException {
        int nounPlace = 0, verbPlace = 0;
        boolean nounFound = false, verbFound = false;
        for (int i = 0; i < userInArray.length; i++){
            String currentNoun = (String) userInArray[i];
            if (nounList.find(nounList.root, currentNoun)) {
                nounFound=true;
                break;
            }
        }

        for (int i = 0; i < userInArray.length; i++){
            String currentVerb = (String) userInArray[i];
            if (verbList.find(verbList.root, currentVerb)) {
                verbFound=true;
                break;
            }
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
    static int signal=0;  //set to 1, after we get the directory
    static int signal2=0;  //set to 1,after we get the full result
    static String directory="";
    static Checker c1 = new Checker();
    static String[] sentenceCollection;
    static StringBuilder collect=new StringBuilder();  //collect the output
    public static void main(String[] args) throws IOException, InterruptedException {
        simpleGUI myGUI = new simpleGUI();
        myGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myGUI.setSize(400,800);
        myGUI.setVisible(true);
        while (true) {   // first time we received input or not first time
            while (signal==0) {
                Thread.sleep(1000);
            }

            Scanner sentence = new Scanner(new File(directory));
            ArrayList<String> sentenceList = new ArrayList<String>();
            while (sentence.hasNextLine()) {
                sentenceList.add(sentence.nextLine());
            }
            sentence.close();
            String[] sentenceArray = sentenceList.toArray(new String[sentenceList.size()]);
            for (int r = 0; r < sentenceArray.length; r++) {
                sentenceCollection = sentenceArray[r].split("(?<=[.!?])\\s*");
            }

            for (int index = 0; index < sentenceCollection.length; index++) {  //check the whole file
                userInput = sentenceCollection[index];
                userInNoPunc = userInput.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
                Map<String, Integer> phrases = new HashMap<>();  //used to store different phrases of a sentence
                String[] userInArray = userInNoPunc.split(" "); //each word WITHOUT PUNCTUATION is an element
                String[] userInArray2 = userInput.split(""); //each character is an element
                String[] userInArray3 = userInput.split(" "); //each word is an element
                String userInArray4 = userInput; //the raw input string
                for (int i = 0, l = userInArray3.length; i + 1 < l; i++)
                    phrases.put(userInArray[i] + " " + userInArray[i + 1], 100);  //add the phrases with length==2
                for (int i = 0, l = userInArray3.length; i + 3 < l; i++)
                    phrases.put(userInArray[i] + " " + userInArray[i + 1] + " " + userInArray[i + 2], 100); //add the phrases with length==3
                arraylength1 = userInArray.length;
                arraylength2 = userInArray2.length;

                final String dir = System.getProperty("user.dir");
                String dirName = dir + "/ReferenceFiles/";
                File dirObj = new File(dirName);
                if (!dirObj.exists()) {
                    dirObj.mkdir();
                }
                c1.init_checker(dirName);
                c1.CheckNounVerb(userInArray, dirName);
                c1.CheckCompound(userInArray);
                if (!(userInArray.length == 0)) {
                    String firstWord = userInArray[0];
                    c1.FirstChar(firstWord, arraylength1);

                    String lastWord = userInArray3[userInArray3.length - 1];
                    c1.LastChar(lastWord);
                } else {
                    System.out.println("The sentence has " + userInArray.length + " words, which isn't accepted.");
                }

                for (int i = 0; i < userInArray.length; i++) {
                    String reallyVery = userInArray[i];
                    c1.WeakWord(reallyVery, arraylength1);
                }
                c1.CheckLength(arraylength1);
                c1.sameWord(userInArray3);
                c1.CheckSpace(userInArray4);
                c1.checkDoubleUpper(userInput);
                phrases = c1.CheckCountriesPeople(userInArray3, dirName, phrases); // check whether the sentence contain a Country name or people's name
                phrases = c1.CheckPhrases(phrases, dirName);  //check whether the phrase is in the commonPhrase list

                int quoteCount = 0;
                for (int i = 0; i < userInArray2.length; i++) {
                    String quote = userInArray2[i];
                    c1.CheckQuote(quote, quoteCount);
                }

                for (int i = 0; i < userInArray.length; i++) {
                    String wordNum = userInArray[i];
                    c1.CheckNums(wordNum);
                }

                for (int i = 0; i < userInArray.length; i++) {
                    String aAn = userInArray[i];
                    c1.CheckAandAN(aAn, userInArray, i);
                }
                int phraseAverage = 0;
                for (int value : phrases.values()) {
                    phraseAverage += value;
                }
                phraseAverage = phraseAverage / (2 * phrases.size());
                score += phraseAverage;
                if (score > 100)  //the suspicious should no larger than 100
                    score = 100;
                collect.append("{\n");
                collect.append("  sentences: {\n    ").append(userInput).append(":").append(score).append("\n");
                collect.append("  },\n");
                collect.append("  phrases : {\n");
                for (Map.Entry<String, Integer> entry : phrases.entrySet())
                    collect.append("   ").append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
                collect.append("  }\n");
                collect.append("}\n");
                score = 0;   //update the score
                phrases.clear();
            }
            signal2 = 1;  //set to 1 when we already get result
            signal=0;
            System.out.println(collect.toString());
        }
    }
}