// The purpose of this program is to parse a CSV file and identify possible
// duplicate records in the data set. Implementation of the Metaphone phonetic
// algorithm is used to identify possible mispellings or errors in entries.
// Duplicates will be printed out separately as well as a set of non-duplicate
// entries.
//

import java.util.*;
import java.util.List;
import org.apache.commons.codec.language.Metaphone;
import com.opencsv.CSVReader;
import java.io.FileReader;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class findDupes {

    public static void main(String[] args) throws Exception {
        //Data structures
        List<List<String>> entriesList = new ArrayList<List<String>>();
        List<List<String>> dupeNamesList = new ArrayList<List<String>>();
        List<List<String>> dupeEmailsList = new ArrayList<List<String>>();

        //Parses .csv file into "entriesList"
        fill(entriesList, "Normal.csv");

        // Finds potential duplicates according to first and last name
        findDupeNames(entriesList, dupeNamesList);

        //Prints list of potential dupes via names
        //display(dupeNamesList);

        //Finds potential duplicates according to email
        findDupeEmails(entriesList, dupeEmailsList);
        display(dupeEmailsList);

    }

    //Reads in .csv file and filld "records"
    public static List<List<String>> fill(List<List<String>> records, String myFile) throws Exception {
        try (CSVReader csvReader = new CSVReader(new FileReader(myFile))) {
            String[] values;
            while ((values = csvReader.readNext()) != null)
                records.add(Arrays.asList(values));
        }
        return records;
    }

    // Method to print list of entries
    public static void display(List<List<String>> listOfRecords){
        for ( int i = 0; i < listOfRecords.size(); i++)
            System.out.println(listOfRecords.get(i));
    }

    // Boolean method to evaluate phonetic equality between 2 strings
    public static boolean soundsSame(String str1, String str2){
        Metaphone meta = new Metaphone();
        return (meta.isMetaphoneEqual(str1, str2));
    }

    // Returns first name of a data entry
    public static String getFname(List<List<String>> listOfRecords, int i){
        return listOfRecords.get(i).get(1);
    }

    // Returns last name of a data entry
    public static String getLname(List<List<String>> listOfRecords, int i){
        return listOfRecords.get(i).get(2);
    }

    //Returns email of a data entry
    public static String getEmail(List<List<String>> listOfRecords, int i){
        return listOfRecords.get(i).get(5);
    }

    // Phonetically compares first and last names, adds to list of duplicates
    public static void findDupeNames(List<List<String>> listOfRecords, List<List<String>> listofDupes){
        for (int i = 0 ; i < listOfRecords.size(); i++)
            for (int j = i +1; j < listOfRecords.size(); j++){
                if (soundsSame(getFname(listOfRecords, i),getFname(listOfRecords, j))&&
                        soundsSame(getLname(listOfRecords, i),getLname(listOfRecords, j))){
                    listofDupes.add(listOfRecords.get(i));
                    listofDupes.add(listOfRecords.get(j));
                }
            }
    }

    // Compares Levenshtein distance among emails, adds to duplicates if difference is 1 character or less
    public static void findDupeEmails(List<List<String>> listOfRecords, List<List<String>> listofDupes) {
        LevenshteinDistance leva = new LevenshteinDistance();

        for (int i = 0; i < listOfRecords.size(); i++)
            for (int j = i + 1; j < listOfRecords.size(); j++) {
                if (leva.apply(getEmail(listOfRecords, i), getEmail(listOfRecords, j)) < 2) {
                    listofDupes.add(listOfRecords.get(i));
                    listofDupes.add(listOfRecords.get(j));
                }
            }
    }
}
