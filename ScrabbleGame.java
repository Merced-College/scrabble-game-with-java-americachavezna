//Improvement: double-check that the user only used the letters provided before the binary search is initiated

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class ScrabbleGame {

    //a data structure to hold the dictionary of words
    private static List<Word> dictionary = new ArrayList<Word>();

    public static void main(String[] args) {

        //read in text file of words and defintions into my ArrayList
        Scanner in = null;
        try {
            in = new Scanner(new File("definitions.txt"));
            while (in.hasNextLine()) {
                String word = in.next().trim().toLowerCase();
                String def = in.nextLine().trim();
                dictionary.add(new Word(word, def));
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println ("File not found: " + e.getMessage());
        }

        //for (Word w: dictionary) {
          //  System.out.println(w);
        //}

        //generate random letters
        char[] letters = generateRandomLetters(4);
        System.out.print("Your letters are: ");
        for (char c : letters) System.out.print(c + " ");
        System.out.println();

        //user input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a word using the provided letters: ");
        String target = input.nextLine().toLowerCase();
        input.close();

        //ensure only those letters are used (ADDED CODE) 
        if (!isValidWord(target, letters)) {
            System.out.println("Invalid word. You must use only the given letters.");
            return;
        }
        //end portion of added code

        //initiate binary search
        int index = binarySearch(dictionary, target);
        if (index != -1) {
            Word found = dictionary.get(index);
            System.out.println("Word found: " + found.getWord());
            System.out.println("Definition: " + found.getDefinition());
        } else {
            System.out.println("Word not found in dictionary.");
        }
    //}
}

    //randomize 4 letters (vowel necessary) and output them to user
    public static char[] generateRandomLetters(int n) {
        Random random = new Random();
        char[] letters = new char[n];
        String vowels = "aeiou";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        //ADDED CODE
        //ensure at least one vowel chosen
        letters[0] = vowels.charAt(random.nextInt(vowels.length()));
        //END ADDED PORTION

        //find other letters
        for (int i = 1; i < n; i++) {
            letters[i] = alphabet.charAt(random.nextInt(alphabet.length()));
        }

        return letters;
    }

    //ADDED CODE
    //read user input, compare user input to the four letters provided to ensure they only use those letters
    public static boolean isValidWord(String word, char[] letters) {
        int[] valid = new int [26];
        for (char c : letters) {
            valid[c- 'a']++;
        }

        for (char c : word.toCharArray()) {
            if (c < 'a' || c > 'z') return false;
            if (valid[c - 'a'] == 0) return false;
            valid[c - 'a']--;
        }

        return true;
    }
    //END ADDED PORTION

    //binary search for a word in the dictionary
    public static int binarySearch(List<Word> list, String target) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right-left) / 2;
            int cmp = list.get(mid).compareTo(new Word (target, ""));
            if(cmp ==0) {
                return mid; //found it
            } else if(cmp < 0) {
                left = mid + 1; //search right half
            } else {
                right = mid - 1; //search left half
            }

        }
        return -1; //not found
    }

}