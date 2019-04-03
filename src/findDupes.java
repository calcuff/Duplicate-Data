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

        //Prints list of data
        display(entriesList);

        //Testing with metaphone
        Metaphone meta =  new Metaphone();
        System.out.println(meta.isMetaphoneEqual(entriesList.get(4).get(1),entriesList.get(5).get(1)));

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

}
