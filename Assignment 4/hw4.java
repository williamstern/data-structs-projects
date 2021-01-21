/*

  Author: William Stern
  Email: wstern2019@my.fit.edu
  Course: CSE2010
  Section: 03

  Description of this file:
    Recomends music to people using hash and priority queue

 */

import java.io.*; // For file io
import java.lang.Math; // for abs

  
public class hw4 {
    public static void main(String args[]) throws Exception { // Excption for file io
        String fileName = args[0];
        File input = new File(fileName); // Make filetype from file name entered
        BufferedReader inputReader = new BufferedReader(new FileReader(input)); 
        String line; // Each line wil l be stored in this var

        String targetCust = inputReader.readLine(); // reads the first

        Customer[] custArray = new Customer[100]; // makes any customer array that is 100 long

        while((line = inputReader.readLine()) != null) { // reads through all the files in the setup file
            //System.out.println(line);
            String[] splitString = line.split(" "); // splits at spaces
            //System.out.println(splitString);
            int[] ratings = new int[10]; // makes and array for the ratings
            //System.out.println(splitString.length);
            int j = 0;
            for(int i = 0; i < splitString.length-1; i++) { // goes through file
                
                if ((splitString[i+1].equals("")) || (splitString[i+1].equals(" "))) { // removes empty sections from array
                    continue; // skip rest of lop
                }
                
                ratings[j] = Integer.parseInt(splitString[i+1]); //conver to int and add to rating array
                j += 1;
            }

            String custName = splitString[0]; // setes first as name

            insertCustomer(custArray, custName, ratings); // inserts into array
        }
        int namesLen = getCustArrayLen(custArray); // get the 

        Customer[] subArray = new Customer[namesLen-1]; // create sub array that has non null values to sort

        for(int i = 0; i < namesLen-1; i++) {
            subArray[i] = custArray[i]; // add em in
        }

        selectionSort(subArray); // sort
        custArray = subArray; // set cust array to subarray
        inputReader.close(); // clsoe da reader

        for (int i = 0; i < custArray.length; i++) {
            if (custArray[i] != null) {
                //System.out.println(custArray[i].getName());
            }
        }

        int targetIndex = findCustomer(custArray, targetCust); // finds the index of target customer
        Customer target = custArray[targetIndex]; // gets the customer from the index

        fileName = args[1]; // gets file name or second file
        input = new File(fileName); // Make filetype from file name entered
        inputReader = new BufferedReader(new FileReader(input)); // sets up new input reader

        while((line = inputReader.readLine()) != null) { // reads through all the lines
            String[] splitString = line.split(" "); // splits line at spcace
            if (splitString[0].equals("RecommendSongs")) { // if command is Recomended songs

                Customer[] pQueue = new Customer[100]; // Sets up queue
                int numAdded = 0;
                // load everything into the new array exacet the target cust
                for (int foo = 0; foo < custArray.length; foo++) {
                    custArray[foo].setDistance(getDistance(target, custArray[foo]));
                    if (!(custArray[foo].equals(target))) {
                        pQueue[numAdded] = custArray[foo];
                        numAdded += 1;
                    }
                }
                
                initalize(pQueue, custArray.length-1); // turns aray into a heap
                
                int size = getCustArrayLen(pQueue); // get size
                //System.out.println(size);
                size -=2;
                Customer top = popTop(pQueue, size); // pops top from pqueue
                size -= 1;

                boolean match = false;
                // while match 
                while (!match && !isEmpty(pQueue)) { // checks if q is empty and sise > 0 for backup
                    
                    // goes through array and checks if it is a valid match
                    for (int o = 0; o < 10; o++) {
                        if((top.ratings[o] >= 4 && target.ratings[o] == 0) &&  getDistance(top, target) != -1) { // target didnt rate any song edge case
                            match = true;
                        }
                    }
                    if (match) { // if match is found
                        System.out.print("RecommendSongs "+ top.getName()+ " ");
                        for (int o = 0; o < 10; o++) {
                            if(top.ratings[o] >= 4 && target.ratings[o] == 0) {
                                System.out.print("song" + o + " "+ top.ratings[o] + " ");
    
                            }
                        }
                        System.out.print("\n");
                    }
                    else {
                        // if no match get next
                        top = popTop(pQueue, size);
                        size -= 1;
                    }
                }
                if (!match && (isEmpty(pQueue))) { // if no matches found
                    System.out.println("RecommendSongs "+ "none"); // prints none according to directions
                }
            }

            else if (splitString[0].equals("PrintCustomerDistanceRatings")){ // else if imput says print customer ratings
                System.out.print("PrintCustomerDistanceRatings");
                System.out.print("\n");
                System.out.printf("      %-11s",targetCust); // prints 11 spaces and target cust
                for (int p = 0; p < 10; p++) {
                    System.out.printf("%d ", (target.getRating())[p]); // prints ratings
                }
                System.out.printf("\n");
                for(int z=0; z<subArray.length; z++){
                    if (!(subArray[z].getName().equals(targetCust))) {
                            if(subArray[z].getDistance() != -1.0){ // if they have songs in common
                                System.out.printf("%.3f %-11s",subArray[z].distance, subArray[z].getName());
                            }
                            else{
                                System.out.printf("----- %-11s",subArray[z].getName());
                            }
                            for(int q = 0; q<10; q++) { // prints ratings
                                System.out.printf("%d ",subArray[z].getRating()[q]);
                            }
                            System.out.printf("\n");       
                    }
                }
            }

            else if (splitString[0].equals("AddCustomer")) { // if it sasy to addCust
                custArray = new Customer[100]; // remakes custArray to be size 100
                int[] ratings = new int[10]; // new rating array
                String custName = splitString[1]; 

                for(int b =2; b<12; b++) { // adds ratings from imput to rating array
                    ratings[b-2] = Integer.parseInt(splitString[b]);
                }

                System.out.printf("AddCustomer %s ",splitString[1]);

                for(int b =0; b<10; b++) {
                    System.out.printf("%d ",ratings[b]); // prints rating
                }

                System.out.printf("\n");
                for (int e = 0; e < subArray.length; e++) {
                    custArray[e] = subArray[e];
                }

                insertCustomer(custArray, custName, ratings); // Inserts the cusstomer into custArray

                subArray = new Customer[subArray.length +1]; // make a new subArray that is one bigger for sorting

                for (int e = 0; e < subArray.length; e++) {
                    subArray[e] = custArray[e];
                    subArray[e].setDistance(getDistance(target, subArray[e]));
                }
                selectionSort(subArray); // sorts array
                
                custArray = subArray; // sets resets Cust to have no nulls
                
            }
        }

    }

    // This func takes a cust array and an entry
    // this is also the extra credit i think
    public static void insert(Customer pQueue[], Customer entry) {
        // adds entry to last avalible array and them remakes queue 
        for (int i = 0; i < pQueue.length; i ++) {
            if (pQueue[i] == null) {
                pQueue[i] = entry;
                initalize(pQueue, i);
                return;
            }
        }
        
    }

    // is last index is not null retuns true else false
    public static boolean isFull(Customer pQueue[]) {
        if (pQueue[99] != null) {
            return true;
        }
        return false;
    }

    // checks is firts entry is null and if it is retuns true
    public static boolean isEmpty(Customer pQueue[]) {
        if (pQueue[0] == null) {
            return true;
        }
        return false;
    }

     // This will take the top node off hean and then reheapify it
    public static Customer popTop(Customer pQueue[], int size) {
        Customer result = pQueue[0];
        int lastIndex = size;
        pQueue[0] = pQueue[lastIndex];
        pQueue[size] = null;
        initalize(pQueue, size-1);
        return result;
    }

    // This moves switches nodes to be in heap order
    public static void heapify(Customer pQueue[], int rootIndex, int size) {
        int smallestDistIndex = rootIndex;
        int leftIndex = (2*rootIndex+1);
        int rightIndex = 2* rootIndex + 2;

        // if left smaller than small swap
        if (leftIndex < size && (pQueue[leftIndex].getDistance() <pQueue[smallestDistIndex].getDistance())) { 
            smallestDistIndex = leftIndex;
        }

        // if right smaller than right swap
        if (rightIndex < size && pQueue[rightIndex].getDistance() < pQueue[smallestDistIndex].getDistance()) {
            smallestDistIndex = rightIndex;
        }

        // checks for alphabetical
        if (rightIndex < size && compareDouble(pQueue[rightIndex].getDistance(), pQueue[smallestDistIndex].getDistance()) == 0) {
            if (pQueue[rightIndex].getName().compareTo(pQueue[smallestDistIndex].getName()) < 0) {
                //System.out.println(pQueue[rightIndex].getName() + " " + pQueue[smallestDistIndex].getName() + " " + pQueue[rightIndex].getName().compareTo(pQueue[smallestDistIndex].getName()));
                smallestDistIndex = rightIndex;
            }
        }

        // checks for alphabetical
        if (leftIndex < size && compareDouble(pQueue[leftIndex].getDistance(), pQueue[smallestDistIndex].getDistance()) == 0) {
            if (pQueue[leftIndex].getName().compareTo(pQueue[smallestDistIndex].getName()) < 0) {
                smallestDistIndex = leftIndex;
            }
        }
        if (rootIndex != smallestDistIndex) {
            Customer temp = pQueue[rootIndex];
            pQueue[rootIndex] = pQueue[smallestDistIndex];
            pQueue[smallestDistIndex] = temp;
            heapify(pQueue, smallestDistIndex, size); // recurses
        }
    }

    static void initalize(Customer pQueue[], int size) { // Creats heap from an array
        // start at last index
        int startIndex = (size - 2) / 2; // calculation for last non leaf node

        // peform heapify for each node
        for (int i = startIndex; i >= 0; i--) { 
            heapify(pQueue, i, size); 
        } 
    }

    // This is just a mathod that sorts an array of strings
    public static void selectionSort( Customer[] array){

        for ( int j=0; j < array.length-1 && array[j +1] != null; j++ )
        {
            int min = j;
            for ( int k=j+1; k < array.length && array[j +1] != null; k++ )
            if ( array[k].getName().compareTo( array[min].getName() ) < 0 ) min = k;  

            Customer temp = array[j];
            array[j] = array[min];
            array[min] = temp;
        }

    }

    // Takes two customer arrays
    // Returns a double using the distance formula
    public static double getDistance(Customer cust1, Customer cust2) {
        int songsBothRated = 0;
        int[] cust1Ratings = cust1.getRating();
        int[] cust2Ratings = cust2.getRating();
        int[] diffofSongs = new int[10];
        for (int i = 0; i < 10; i++) {
            if (cust1Ratings[i] != 0 && cust2Ratings[i] != 0) {
                diffofSongs[songsBothRated] = Math.abs(cust1Ratings[i] - cust2Ratings[i]);
                songsBothRated += 1;
            }
        }

        // IF THEY RATED NO SONGS IN SIMILAR
        if (songsBothRated == 0) {
            return -1;
        }
        int sumDiff = 0;
        for (int i = 0; i < songsBothRated; i++) {
            sumDiff += diffofSongs[i];
        }
        
        double distance = (1.0 / songsBothRated) + (1.0 / songsBothRated)*sumDiff; // calc

        return distance;
    }

    // Gets the length kinda of a customer array
    // this is kina broken
    public static int getCustArrayLen(Customer custArray[]) {
        for(int i = 0; i < 100; i ++ ) {
            if(custArray[i] == null) {
                return (i+1);
            }
            
        }
        return -1;
    }

    // When given an customer array, anem and rating then it creats a cust ojjcet and adds it to the first free spot in array
    public static void insertCustomer(Customer custArray[], String name, int ratings[]) {
        Customer newCust = new Customer(name, ratings);
        for (int i = 0; i < 100; i++) {
            if (custArray[i] == null) {
                custArray[i] = newCust;
                break;
            }
        }
    }

    // this will retun the index of a custom when givven the name
    // uses bianarys search
    public static int findCustomer(Customer custArray[], String name) {
        int start = 0;
        int end = custArray.length-1;
        int middle;
        while (start <= end) {
            middle = (start + (end-1)) /2;
            int result = name.compareTo(custArray[middle].getName());

            if (result == 0) {
                return middle;
            }

            else if (result < 0) {
                end = middle+1;
            }

            else {
                start = middle -1;
            }
        }
        return -1;
    }


    // compares two doubles to see if they are equal
    // return int takes two doubles
    public static int compareDouble(Double d1, Double d2) {
        double threshold = 0.0009;

        if (Math.abs(d1 - d2) <= threshold) { // says it wqual if they are wihin threshold
            return 0;
        }

        else if (Math.abs(d1-d2) > 0) { // else if d1 is greater than d2
            return 1;
        }

        else { // else d2 > d1  
            return -1;
        }
    }   
}