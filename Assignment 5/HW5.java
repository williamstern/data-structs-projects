/*

  Author: William Stern
  Email: wstern2019@my.fit.edu
  Course: CSE2010
  Section: 03

  Description of this file:
    Tif software manages and sorts events using a skip list to make it more effecient


 */

import java.io.*; // For file io
class HW5 {
    static FakeRandHeight rand = new FakeRandHeight();

    public static void main(String args[]) throws Exception { // Excpetion is so computer dosent explode if file is not
                                                              // // found
        LinkedList[] skipList = new LinkedList[10]; // creates an array of linked lists for the skiplist
        String fileName = args[0]; // Gets the file name from command line arguments

        File input = new File(fileName); // Create file object of location fileName

        // Im using bufferedreader since it is th best way to read line by line imo
        BufferedReader inputReader = new BufferedReader(new FileReader(input));
        String line; // Each line will be stored in this var
        // sl = skiplist
        // initialize the lest and right limits of the sl
        Event first = new Event("!!!!", "Start");
        Event last = new Event("9999", "End");
        skipList[9] = new LinkedList();
        skipList[9].addLast(first, null, null, null, null);
        skipList[9].addLast(last, null, skipList[9].getFirst(), null, null);
        skipList[9].getFirst().setNext(skipList[9].getLast());
        skipList[9].getLast().setPrev(skipList[9].getFirst());
        for (int i = 8; i >= 0; i--) {
            // goes through array and initalizes stuff
            skipList[i] = new LinkedList();
            first = new Event("!!!!", "Start");
            last = new Event("9999", "End");
            skipList[i].addLast(first, null, null, null, skipList[i + 1].getFirst());
            skipList[i].addLast(last, null, skipList[i].getFirst(), null, skipList[i + 1].getLast());
            skipList[i].getFirst().setNext(skipList[i].getLast());
            skipList[i].getLast().setPrev(skipList[i].getFirst());
        }
        // addLast(data, initialNext, initialPrev, initialDown, initialUp);
        for (int i = 1; i <= 9; i++) {
            // more initalization to make sure all pointer are set up
            skipList[i].getFirst().setDown(skipList[i - 1].getFirst());
            skipList[i].getLast().setDown(skipList[i - 1].getLast());
        }

        // start reading input
        while ((line = inputReader.readLine()) != null) { // Just read til end of txt file

            String[] lineArr = line.split(" "); // I split each line into words at space
            String command = lineArr[0];

            if (command.equals("DisplayEvent")) {
                Event current = get(skipList, lineArr[1]); // try and find event using key
                if (current == null) {
                    System.out.println(command + " " + lineArr[1] + " " + "none"); // if not found print none
                } else {
                    System.out.println(command + " " + lineArr[1] + " " + current.getName()); // else print name
                }
            }

            else if (command.equals("AddEvent")) {
                Event replace = put(skipList, lineArr[1], lineArr[2]); // add event
                if (replace == null) {
                    System.out.println("AddEvent " + lineArr[1] + " " + lineArr[2] + " success"); // if not replacing event
                }

                else {
                    // if it is replacing an event
                    System.out.println("AddEvent " + lineArr[1] + " " + lineArr[2] + " replacingExistingEvent");
                }
            }

            else if (command.equals("DeleteEvent")) {
                Event result = remove(skipList, lineArr[1]); // remove an event
                if (result == null) {
                    System.out.println("DeleteEvent " + lineArr[1] + " noDateError"); // event not found
                } else {
                    System.out.println("DeleteEvent " + lineArr[1] + " success"); // successfully remoed

                }
            }

            else if (command.equals("DisplayEventsBetweenDates")) {
                System.out.print("DisplayEventsBetweenDates " + lineArr[1] + " " + lineArr[2] + " ");
                subMap(skipList, lineArr[1], lineArr[2]); // displays events between the two dates
            }

            else if (command.equals("DisplayEventsFromStartDate")) {
                System.out.print("DisplayEventsFromStartDate " + lineArr[1] + " ");
                subMap(skipList, lineArr[1], "8888"); // displays events from the input to the end
            }

            else if (command.equals("DisplayEventsToEndDate")) {
                System.out.print("DisplayEventsToEndDate " + lineArr[1] + " ");
                subMap(skipList, "0000", lineArr[1]); // displays events from the start to the input
            }

            else if (command.equals("DisplayAllEvents")) {
                System.out.print("DisplayAllEvents ");
                subMap(skipList, "0000", "8888"); // prints all events
            }

            else if (command.equals("PrintSkipList")) { // uhhhhhhhhhhhhhhhhhh
                String printlst = "";
                String sublist = "";
                boolean found = false; // checks is layer is empty
                for (int i = 0; i < 10; i ++) {
                    found = false;
                    sublist = "(S" + i + ") ";
                    Node current = skipList[i].getFirst().getNext();
                    while(current.getNext() != null) {
                        sublist = sublist + current.getData().getKey() + ":" + current.getData().getName() + " ";
                        current = current.getNext();
                        found = true;
                    }
                    if (found == false) { // if layer was empty
                        sublist = sublist + "empty";
                    }
                    
                    printlst =  sublist + "\n" +  printlst;

                    if (found == false) { // if layer was empty stops loop
                        break;
                    }
                }
                printlst = printlst.trim(); // cleans up string
                System.out.println(printlst);
            }

            else {
                System.out.println("Something gon wrong");
            }
        }
        inputReader.close(); // Close da reader so java dosent freak out on me

    }

    // Takes two strings
    // Compares then to see if key1 > key2
    // returns 1 if key1 larger, -1 is key1 smaller and 0 if they are the same
    public static int compareKeys(String key1, String key2) {
        String month1 = key1.substring(0, 2);
        String month2 = key2.substring(0, 2);
        String day1 = key1.substring(2, 4);
        String day2 = key2.substring(2, 4);

        if (month1.compareTo(month2) > 0) {
            return 1;
        }

        else if (month1.compareTo(month2) < 0) {
            return -1;
        }

        else {
            if (day1.compareTo(day2) > 0) {
                return 1;
            }

            else if (day1.compareTo(day2) < 0) {
                return -1;
            }

            else {
                return 0;
            }
        }
    }

    // This function takes an array of linkedlists and a key
    // return an event if it is found else it reutrns null
    public static Event get(LinkedList[] skiplist, String key) {
        Event result = null; // this is is no match is foudn
        boolean found = false; // not sure if this does anything but it works mostly atm
        Node current = skiplist[9].getFirst();
        String currentKey = current.getData().getKey();
        while (!found && current != null) { // while current is not null
            currentKey = current.getData().getKey(); // get current key
            // if a match is found returns it
            if (compareKeys(key, currentKey) == 0) {
                found = true;
                return current.getData();
            }
            if (current.getNext() != null) { // eeeee
                if (compareKeys(key, current.getNext().getData().getKey()) < 0) { // if next is less than key
                    current = current.getDown(); // goes down a level
                }

                else if (compareKeys(key, current.getNext().getData().getKey()) >= 0) { // if key is greater than or equal to the next 
                    current = current.getNext(); // moves over one
                }
            }
        }

        return result; // else return null
    }

    // this one is pretty much the same as the get function except if the event is not found it rounds down
    public static Node floor(LinkedList[] skiplist, String key) {
        Node result = null;
        Node current = skiplist[9].getFirst();
        String currentKey = current.getData().getKey();
        while (current != null) {
            currentKey = current.getData().getKey();
            if (compareKeys(key, currentKey) == 0) {
                return current;
            }

            if (current.getDown() == null && (compareKeys(key, current.getNext().getData().getKey()) < 0)) {
                return current; // this will be less than if its not found
            }

            else if (compareKeys(key, current.getNext().getData().getKey()) < 0) {
                current = current.getDown();
            }

            else if (compareKeys(key, current.getNext().getData().getKey()) >= 0) {
                current = current.getNext();
            }
        }

        return result;
    }

    // same as floor excepts rounds up
    public static Node ceil(LinkedList[] skiplist, String key) {
        Node result = null;
        Node current = skiplist[9].getFirst();
        String currentKey = current.getData().getKey();
        while (current != null) {
            currentKey = current.getData().getKey();
            if (compareKeys(key, currentKey) == 0) {
                return current;
            }

            if (current.getDown() == null && compareKeys(key, current.getNext().getData().getKey()) < 0) {
                return current.getNext(); // return one greater than the one youre looking for
            }

            else if (compareKeys(key, current.getNext().getData().getKey()) < 0) {
                current = current.getDown();
            }

            else if (compareKeys(key, current.getNext().getData().getKey()) >= 0) {
                current = current.getNext();
            }
        }

        return result;
    }

    // This will insert a event into sl
    // takes a sl, key, and an event name
    public static Event put(LinkedList[] skiplist, String date, String name) {
        Event result = null;
        int randNum = rand.get(); // get random num
        Event newEvent = new Event(date, name); // creates the event
        Node prev = floor(skiplist, date);
        if (prev.getData().compare(newEvent) == 0) {
            result = prev.getData();
            prev = prev.getPrev();
        }
        Node next = prev.getNext();
        Node newNode = new Node(next, prev, null, null, newEvent);
        prev.setNext(newNode);
        next.setPrev(newNode);
        for(int i = 0; i < randNum; i ++) { // goes up based on random num
            // goes through sourounding events until if finds on that goes up so it can connect
            while (prev.getUp() == null) {
                prev = prev.getPrev();
            }

            while (next.getUp() == null) {
                next = next.getNext();
            }
            // connects
            next = next.getUp();
            prev = prev.getUp();

            Node upNode = new Node(next, prev, null, newNode, newEvent);
            next.setPrev(upNode);
            prev.setNext(upNode);

            upNode.getDown().setUp(upNode);
            newNode = upNode;
        }

        return result;
    }
    

    // this function removes from the sl
    // imput is a sl and a key
    // kout put is an event if it is found and null if it is not
    public static Event remove(LinkedList[] skiplist, String key) {
        Event result = null;
        boolean found = false;
        Node current = skiplist[9].getFirst();
        String currentKey = current.getData().getKey();
        while (!found && current != null) {
            currentKey = current.getData().getKey();
            if (compareKeys(key, currentKey) == 0 && current.getDown() == null) { // finds element and goes down all the way
                found = true;
                // removes node and connects suruonding nodes
                Node prev = current.getPrev();
                Node next = current.getNext();
                prev.setNext(next);
                next.setPrev(prev);

                // goes up removing all the nodes above it
                while (current.getUp() != null) {
                    current = current.getUp();
                    prev = current.getPrev();
                    next = current.getNext();
                    prev.setNext(next);
                    next.setPrev(prev);
                }

                return current.getData();
            }

            if (compareKeys(key, current.getNext().getData().getKey()) < 0) { // goes down a level
                current = current.getDown();     
            }

            else if (compareKeys(key, current.getNext().getData().getKey()) >= 0) { // goes to next node
                current = current.getNext();
            }
        }
        return result; // reurn null if not found
    }

    // prints event bween two wkeys
    // takes an array of LL, and two string keys
    // returns nothing just prints to stdio
    public static void subMap(LinkedList skiplist[], String key1, String key2) {
        Node left = ceil(skiplist, key1); // gets leftmost node of limit
        Node right = left;
        if (left.getData().getKey().equals("!!!!") || left == null) { // if there is none  in between
            System.out.print("none\n");
        }
        System.out.print(right.getData().getKey() + ":" + right.getData().getName() + " ");
        while (compareKeys(right.getNext().getData().getKey(), key2) <= 0) { // prints all the events until rightmost limit
            right = right.getNext();
            System.out.print(right.getData().getKey() + ":" + right.getData().getName() + " ");
        }
        System.out.print("\n");
    }
}