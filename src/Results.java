// This program parses in a "Normal.csv" data file and identifies
// potential duplicates. Entries are flagged according to name similarity
// based on Metaphone phonetics and emails according to the Levenshtein
// distance between 2 emails. After duplicate entries are found they
// are then removed from the original list of records. The corresponding
// lists of duplicates and non-duplicates are then printed to output.

import java.util.ArrayList;
import java.util.List;

public class Results {
    public static void main(String[] args)  throws Exception{
        FindDupes finder = new FindDupes();

        //Data structures
        List<List<String>> entriesList = new ArrayList<List<String>>();
        List<List<String>> dupesList = new ArrayList<List<String>>();

        //Parses .csv file into "entriesList"
        finder.fill(entriesList, "Normal.csv");

        // Finds potential duplicates according to first and last name or email
        finder.findDuplicates(entriesList, dupesList);

        // Removes duplicates from original list
        finder.removeDupes(entriesList, dupesList);

        //Prints list of potential duplicates
        System.out.println("Potential duplicates: ");
        finder.display(dupesList);

        //Prints list of non-duplicates
        System.out.println("Non-duplicates: ");
        finder.display(entriesList);
    }
}
