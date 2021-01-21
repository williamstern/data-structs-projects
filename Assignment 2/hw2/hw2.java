/*

  Author: William Stern
  Email: wstern2019@my.fit.edu
  Course: CSE2010
  Section: 03

  Description of this file:
    This file finds palendrones of # of words entered of from the imput file entered
 */
import java.io.*; // For file io
import java.util.Arrays;

public class hw2 {
    public static void main(String[] args) throws Exception {
        String fileName = args[0];
        File input = new File(fileName); // Make filetype from file name entered
        // This first pass of file just counts # number of lines so it knows how long to make the array
        BufferedReader inputReader = new BufferedReader(new FileReader(input)); 
        String line; // Each line will be stored in this var
        int count = 0;
        
        while ((line = inputReader.readLine()) != null) {
            count +=1;
        }
        inputReader.close(); // Close da first reader

        // This pass enters the words into the array
        inputReader = new BufferedReader(new FileReader(input));
        String[] words = new String[count];
        count = 0;
        while ((line = inputReader.readLine()) != null) {
            words[count] = line;
            count +=1;
        }

        Arrays.sort(words); // Sorts to be in alphebetical order

        // Run fuction
        getPermutes(0, words.length-1, words, Integer.parseInt(args[1])); // The last converts commnd line arg to int
        inputReader.close(); // Close da  reader so java dosent freak out on me 
    }

    // This function takes a list a string a start and end param and a permNUm
    // It will permute the number permNum times and print all permutations that are palendrones
    // Return nothing but prints to io stream
    public static void getPermutes(int start, int end, String list[], int permNum) { 
        String temp;

        if (start == permNum) { // checks the end conditions
            String palChk = new String(); // This one is used to check if it is a palendrone 
            String displayString = new String(); // This one is the ones that is printed

            for (int i = 0; i < permNum; i++) {
                palChk = palChk + (list[i]); // prepare string to check if it is pal
            }
            
            if (checkPal(palChk, 0, palChk.length()-1)) { // check if palendrone
                // if it is make the string that will be displayed
                for (int i = 0; i < permNum-1; i++) {
                    displayString = displayString + (list[i]);
                    displayString = displayString + " ";
                }
                displayString = displayString + (list[permNum-1
                ]);
                System.out.println(displayString); // print the string
            }
        }

        else {
            // doin yo mom
            // start == depth
            // Swaps first elem with i, calls permutes again with shifted starting position, and swaps it back
            for (int i = start; i<= end; i++) {
                temp = list[start]; // swap start with i
                list[start] = list[i];
                list[i] = temp;
                getPermutes(start+1, end, list, permNum); // Lock in start by recursing and go one "deeper" in recursive "tree"
                temp = list[start]; // swap back to reset string for next swap
                list[start] = list[i];
                list[i] = temp;
            }

        }

    }

    // For this function you input in a string, the start intex, and the len or end 
    // It will return a bool thats true if string is a palendrome and false if not
    public static Boolean checkPal(String str, int start, int end) {
        if (start == end) { // one char remaining
            return true;
        }

        if (str.charAt(start) != str.charAt(end)) { // Oposites dont match
            return false;
        }

        else if (start <= end) { // If there are still chars left
            return checkPal(str, start+1, end-1);
        }
        return true;
    }

}