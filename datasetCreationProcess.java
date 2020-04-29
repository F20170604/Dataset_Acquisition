import java.io.*;
public class datasetCreationProcess {
    public static void addEntry(String englishWord, String secondLanguageWord) throws IOException {
        File tempFile = new File("dataset.csv");

        boolean exists = tempFile.exists(); // check if file exists
        if(!exists) // if file doesn't exists
            File.createTempFile("dataset", ".csv");   // create file

        String textToAppend = englishWord+","+secondLanguageWord; // data in the from of englishWord,secondLanguageWord as is found in csv files
        FileWriter fileWriter = new FileWriter("dataset.csv", true); //Set true for append mode
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(textToAppend);  //New line
        printWriter.close();
    }
}