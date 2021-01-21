/*

  Author: William Stern
  Email: wstern2019@my.fit.edu
  Course: CSE2010
  Section: 03

  Description of this file:
    This file creates a tree from an input file and can read querys describing the orginazational structure


 */
import java.io.*; // For file io

public class hw3 {
    public static void main (String args[]) throws Exception { // Excption for file io
        String fileName = args[0];
        File input = new File(fileName); // Make filetype from file name entered
        BufferedReader inputReader = new BufferedReader(new FileReader(input)); 
        String line; // Each line will be stored in this var
        
        // This read the first line and sets the first line as root of tree
        line = inputReader.readLine(); 
        Tree org = new Tree(); // org for orginization
        org.setRoot(line); 

        // Reads the rest of the lines and adds them to the tree
        while((line = inputReader.readLine()) != null) {
            String[] splitString = line.split(" ");
            org.addNode(splitString[0], splitString[1]);
        }

        inputReader.close(); // closes file io
        fileName = args[1]; // Opens query file
        input = new File(fileName); // Make filetype from file name entered
        // This first pass of file just counts # number of lines so it knows how long to make the array
        inputReader = new BufferedReader(new FileReader(input));

        while((line = inputReader.readLine()) != null) { // reads all lines
            String[] splitString = line.split(" "); // splits line into words
            // 0th word = command
            // 1st = 1st name
            // second == 2 name

            System.out.print(splitString[0] + " "); // print command

            // detect command and run function

            if (splitString[0].equals("DirectSupervisor")) {
                org.printDirectSupervisor(splitString[1]);
            } 

            else if (splitString[0].equals("DirectSubordinates")){
                org.printDirectSubordinates(splitString[1]);
            }

            else if (splitString[0].equals("AllSupervisors")) {
                org.printAllSupervisors(splitString[1]);
            }

            else if (splitString[0].equals("AllSubordinates")) {
                org.printAllSubordinates(splitString[1]);
            }

            else if (splitString[0].equals("NumberOfAllSubordinates")) {
                org.printNumberOfAllSubordinates(splitString[1]);
            }

            else if (splitString[0].equals("NumberOfAllSupervisors")) {
                org.printNumberOfAllSupervisors(splitString[1]);
            }

            else if (splitString[0].equals("IsSubordinate")) {
                org.printIsSubordinate(splitString[1], splitString[2]);
            }

            else if (splitString[0].equals("IsSupervisor")) {
                org.printIsSupervisor(splitString[1], splitString[2]);
            }

            else if (splitString[0].equals("CompareRank")) {
                org.printCompareRank(splitString[1], splitString[2]);
            }

            else if (splitString[0].equals("ClosestCommonSupervisor")) {
                org.printClosestCommonSupervisor(splitString[1], splitString[2]);
            }
        }
        inputReader.close(); // closes file io
    }
}