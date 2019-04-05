// This program provides methods to parse a CSV file and identify possible
// duplicate records in the data set. Implementation of the Metaphone phonetic
// algorithm is used to identify possible mispellings or errors in entries. The
// Levenshtein distance algorithm is used to calculate the number of character
// changes needed to match strings. The implementations I used require a phonetic
// match of first and last names, or a Levenshtein distance of 1 or less for
// email matches to identify duplicates. Once a list of duplicates is identified
// methods are supplied for removing those duplicates from the original list of entries.

import java.util.*;
import java.util.List;
import org.apache.commons.codec.language.Metaphone;
import com.opencsv.CSVReader;
import java.io.FileReader;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class FindDupes {

    //Reads in .csv file and fills "records"
    public List<List<String>> fill(List<List<String>> records, String myFile) throws Exception {
        try (CSVReader csvReader = new CSVReader(new FileReader(myFile))) {
            String[] values;
            while ((values = csvReader.readNext()) != null)
                records.add(Arrays.asList(values));
        }
        return records;
    }

    // Method to print list of entries
    public void display(List<List<String>> listOfRecords){
        for ( int i = 0; i < listOfRecords.size(); i++)
            System.out.println(listOfRecords.get(i));
        System.out.println();
    }

    // Boolean method to evaluate phonetic equality between 2 strings
    public boolean soundsSame(String str1, String str2){
        Metaphone meta = new Metaphone();
        return (meta.isMetaphoneEqual(str1, str2));
    }

    // Returns first name of a data entry
    public String getFname(List<List<String>> listOfRecords, int i){
        return listOfRecords.get(i).get(1);
    }

    // Returns last name of a data entry
    public String getLname(List<List<String>> listOfRecords, int i){
        return listOfRecords.get(i).get(2);
    }

    //Returns email of a data entry
    public String getEmail(List<List<String>> listOfRecords, int i){
        return listOfRecords.get(i).get(4);
    }

    // Phonetically compares first and last names, and email similarity, adds to list of duplicates
    public void findDuplicates(List<List<String>> listOfRecords, List<List<String>> listofDupes){
        LevenshteinDistance leva = new LevenshteinDistance();
        listOfRecords.remove(0);

        for (int i = 1 ; i < listOfRecords.size(); i++) {
            for (int j = i + 1; j < listOfRecords.size(); j++) {
                if ((soundsSame(getFname(listOfRecords, i), getFname(listOfRecords, j)) &&
                        soundsSame(getLname(listOfRecords, i), getLname(listOfRecords, j))) ||
                        leva.apply(getEmail(listOfRecords, i), getEmail(listOfRecords, j)) < 2) {

                    listofDupes.add(listOfRecords.get(i));
                    listofDupes.add(listOfRecords.get(j));
               }
            }
        }
    }

    // Removes any duplicates from original list of entries
    public void removeDupes(List<List<String>> listOfRecords, List<List<String>> listofDupes){
        for ( int i = 0 ; i < listOfRecords.size(); i++){
            for (int j = 0; j < listofDupes.size(); j++)
                if (listOfRecords.get(i) == listofDupes.get(j))
                    listOfRecords.remove(i);
        }

    }
}
