import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class SentimentAnalysis {
    public static void main(String args[]) throws IOException {
        HashTable words = new HashTable(3000,0);

        FileInputStream fileIn = new FileInputStream("sentiments.txt");
        Scanner input = new Scanner(fileIn);

        //Reads in the file and puts it into a string.
        while (input.hasNextLine()){
            String value = input.nextLine();
            String key = value;
            int indexOfVal = value.indexOf(',') + 1;
            key = key.substring(0,indexOfVal - 1);
            value = value.substring(indexOfVal);
            int val = Integer.parseInt(value);
            words.insert(key,val);
        }
        fileIn.close();

        input = new Scanner(System.in);
        System.out.println("Enter text: ");
        String userInput = "";

        while (!userInput.contains("END")) {
            userInput = userInput + " " + input.nextLine();
        }


        //Removes leading spaces
        userInput = userInput.replaceAll("^\\s+", "");

        //Removes Trailing spaces
        userInput = userInput.replaceAll("\\s+$", "");

        //MAKE THIS NOT LOWERCASE END.
        userInput = userInput.toLowerCase(Locale.ROOT);
        userInput = userInput.substring(0, userInput.length() - 3);
        userInput = userInput + "END";
        System.out.println("");
        analysis(userInput, words);

    }
    //MEthod carries out the sentiment analysis.
    public static void analysis(String userInput, HashTable words){
        int sent = 0;
        String temp = userInput;
        String currentWord = "";
        String previousWord = "";

        while (!temp.equals("END")){
            int index = temp.indexOf(" ");

            currentWord = temp.substring(0,index);

            //removing punctuation,
            char punc = currentWord.charAt(currentWord.length() - 1);
            if(Character.isAlphabetic(punc) == false){
                currentWord = currentWord.substring(0, currentWord.length() - 1);
            }
            //Checks for double words and gets that value.
            String doubleWord = previousWord + " " + currentWord;
            if(words.lookup(doubleWord) != null){
                int a = (int) words.lookup(doubleWord);
                sent = sent + a;
            } else if(words.lookup(currentWord) != null){
                int a = (int) words.lookup(currentWord);
                sent = sent + a;
            }



            temp = temp.substring(index);
            temp = temp.replaceAll("^\\s+", "");
            previousWord = currentWord;
        }
        int numOfWords = countWords(userInput);
        float num = numOfWords;
        float sum = sent;
        float overall = sum/num;
        System.out.println("Words: " + numOfWords);
        System.out.println("Sentiment: " + sent);
        System.out.printf("Overall: %.2f\n", overall);


    }

    public static int countWords(String userInput){
        String temp = userInput;
        String currentWord = "";
        int words = 0;
        while (!temp.equals("END")){
            int index = temp.indexOf(" ");
            currentWord = temp.substring(0,index);
            //System.out.println(currentWord);
            temp = temp.substring(index);
            temp = temp.replaceAll("^\\s+", "");
            words++;
        }


        return words;
    }
}
