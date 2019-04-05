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

public class findDupes {

    public static void main(String[] args) throws Exception {
        //Data structures
        List<List<String>> entriesList = new ArrayList<List<String>>();
        List<List<String>> dupeNamesList = new ArrayList<List<String>>();

        //Parses .csv file into "entriesList"
        fill(entriesList, "Normal.csv");

        // Finds potential duplicates according to first and last name
        findDupeNames(entriesList, dupeNamesList);

        //Prints list of potential dupes
        display(dupeNamesList);

    }

    //Reads in .csv file and filld "records"
    public static List<List<String>> fill(List<List<String>> records, String myFile) throws Exception {
        try (CSVReader csvReader = new CSVReader(new FileReader(myFile))) {
            String[] values = null;
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
}
