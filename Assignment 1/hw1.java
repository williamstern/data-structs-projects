/*

  Author: William Stern
  Email: wstern2019@my.fit.edu
  Course: CSE2010
  Section: 03

  Description of this file:
    THis softwre is for a call centre to magage calls and hold line
    It also finds the max wait time 


 */
// This is the main file
// I recomend reding comments in this order: Representitive, customer, chat, node, LinkedList, and then this since that kinda how i wrote it
import java.io.*; // For file io

public class hw1 {
    public static void main(String[] args) throws Exception { // Excpetion is so computer dosent explode if file is not found

        String fileName = args[0]; // Gets the file name from command line arguments
        int maxWaitTime = 0; // This is only one int since MaxWait time return just minutes
        String chatEndTime = ""; // This is where I store the chat end time as a string
        

        // Sets up the LL and initalizes it
        LinkedList<Representitive> repsAvil = new LinkedList<Representitive>();
        LinkedList<Customer> custOnHold = new LinkedList<Customer>();
        LinkedList<Chat> chatSessions = new LinkedList<Chat>();

        // Adds the reps in order to the LL
        initRepList(repsAvil);

        File input = new File(fileName); // Create file object of location fileName
        
        // Im using bufferedreader since it is th best way to read line by line imo
        BufferedReader inputReader = new BufferedReader(new FileReader(input)); 
        String line; // Each line will be stored in this var

        while ((line = inputReader.readLine()) != null) { // Just read til end of txt file
            

            String[] lineArr = line.split(" "); // I split each line into words at space
            String command = lineArr[0]; // The first word is the command word

            // Ends a chat and sends rep back to available rep list
            // actualy i just send entire chat to shadow rehlm and make new rep but it should be the same 
            if (command.equals("ChatEnded")) {
                //int startTime = chatSessions.RemoveChat(lineArr[2]);
                //int endHour = Integer.parseInt(lineArr[1].substring(0, 3)
                chatSessions.RemoveChat(lineArr[1]);
                System.out.println(line);
                chatEndTime = lineArr[3];
                Representitive newRep = new Representitive(lineArr[2]);
                repsAvil.addLast(newRep);
            }

            // This one if if not else if because it can happen inbetween input commands
            // Activates if there are people on hold and reps avalible
            if (!(repsAvil.isEmpty()) && !(custOnHold.isEmpty())) {
                Customer firstInLine = custOnHold.getFirst();
                Representitive firstAvail = repsAvil.getFirst();
                Chat newChat = new Chat(firstAvail, firstInLine);
                chatSessions.addLast(newChat);

                // parses and converts time the last chat ended into ints
                String endString = chatEndTime.substring(0, 2);

                int endHour = Integer.parseInt(endString);
                int endMin = Integer.parseInt(chatEndTime.substring(2, 4));

                // convert to minutes
                int endTime = (60 *endHour) + endMin;

                int initTime = (60 * firstInLine.getHour()) + firstInLine.getMinute();

                // calculate how much time they waited
                int waitTime = endTime - initTime;

                if (waitTime > maxWaitTime) { // Replace maxWaitTime if new waitTime is bigger than it
                    maxWaitTime = waitTime;
                }
                System.out.println("RepAssignment " +  firstInLine.getName() + " " + firstAvail.getName()  + " " + chatEndTime); // XD
            }

            else if (command.equals("ChatRequest")) {
                System.out.println(line);
                // lineArr[2] = Name
                // LineArr[1] = Time

                // Create new 
                // public Customer(String name, int hour, int minute)
                // I take name from input line, i split the time into hour, and minute
                Customer cust = new Customer(lineArr[2], Integer.parseInt(lineArr[1].substring(0, 2)), Integer.parseInt(lineArr[1].substring(2, 4)));
                
                // If there are reps avail u just make new chat session using first available rep
                if (!(repsAvil.isEmpty())) {
                    Representitive rep = repsAvil.getFirst();
                    System.out.println("RepAssignment " + lineArr[2] + " " + rep.getName() + " " + lineArr[1]);

                    Chat newChat = new Chat(rep, cust);
                    chatSessions.addLast(newChat);
                }

                // If there are no reps you only addd customer if they decided to wait
                // also u add them to hold ll not chat one
                else if (lineArr[3].equals("wait")) {
                    custOnHold.addLast(cust);
                    System.out.println("PutOnHold " + lineArr[2] + " " + lineArr[1]);
                }

                // If there are no
                else if (lineArr[3].equals("later")) {
                    System.out.println("TryLater " + lineArr[2] + " " + lineArr[1]);
                }

            }

            // pretty self explanary u just display the mex wait time and the time called
            else if (command.equals("PrintMaxWaitTime")) {
                System.out.println("MaxWaitTime " + lineArr[1] + " " + maxWaitTime);
            }

            // Prints Reps that are not in chat sesh
            else if (command.equals("PrintAvailableRepList")) {
                System.out.print("AvailableRepList " + lineArr[1] + " " + repsAvil.getRepsAvil() + "\n");
            }

            // remove the person from the custsOnHold linkedlist
            else if (command.equals("QuitOnHold")) {
                System.out.println(line);
                custOnHold.RemoveHold(lineArr[2]);
            }

            

            
        }

        inputReader.close(); // Close da  reader so java dosent freak out on me
        
        }

    // This func sets up the initial ll of representatives
    // it takes a linked list of type representitives
    // it does not return anything but the data becomes accessable in main
    // kind like pointers
    public static void initRepList(LinkedList<Representitive> list) {
        Representitive rep1 = new Representitive("Alice");
        Representitive rep2 = new Representitive("Bob");
        Representitive rep3 = new Representitive("Carol");
        Representitive rep4 = new Representitive("David");
        Representitive rep5 = new Representitive("Emily");

    
        list.addLast(rep1);
        list.addLast(rep2);
        list.addLast(rep3);
        list.addLast(rep4);
        list.addLast(rep5);   
    }

}