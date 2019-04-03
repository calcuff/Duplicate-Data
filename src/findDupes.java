// The purpose of this program is to parse a CSV file and identify possible
// duplicate records in the data set. Implementation of the Metaphone phonetic
// algorithm is used to identify possible mispellings or errors in entries.
// Duplicates will be printed out separately as well as a set of non-duplicate
// entries.
//
import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
import java.util.List;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.apache.commons.codec.language.Metaphone;

public class findDupes {

    public static void main(String[] args) throws FileNotFoundException {
        List<List<String>> entriesList = new ArrayList<List<String>>();
        File myFile = new File("Normal.csv");

        fillList(entriesList, myFile);

        // Prints potential duplicates according to names
        findDupeNames(entriesList);

    }

    // Retrieves all comma separated strings of a single line as
    public static List<String> getLineEntry(String record){
        List<String> data = new ArrayList<String>();
        Scanner rows = new Scanner(record);
        rows.useDelimiter(",");
        while(rows.hasNext()) {
            data.add(rows.next());
        }
        return data;
    }

    // Fills list of lists taking input one line at a time via helper method, from data file
    public static void fillList(List<List<String>> listOfRecords, File myFile) throws FileNotFoundException{
        Scanner in = new Scanner(myFile);
        while (in.hasNextLine()) {
            listOfRecords.add(getLineEntry(in.nextLine()));
        }
        in.close();
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

    // Phonetically compares first and last names, prints potential dupes
    public static void findDupeNames(List<List<String>> listOfRecords){
        System.out.println("Potential duplicates: ");
        for (int i = 0 ; i < listOfRecords.size(); i++)
            for (int j = i +1; j < listOfRecords.size(); j++){
                if (soundsSame(getFname(listOfRecords, i),getFname(listOfRecords, j))&&
                        soundsSame(getLname(listOfRecords, i),getLname(listOfRecords, j)))
                    System.out.println(listOfRecords.get(i) + "\n" + listOfRecords.get(j));
            }
    }

}
