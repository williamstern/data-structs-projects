// This is a generic type class which i thought would be cool and save time
// It turned out to be a hege headach insted
// Its still kinda cool tho imo since you only need one LinkedList and Node class
// This class can take any type of data however some of the functions will not work with every type 
public class LinkedList<T> {

    // Initalize head and tail
    private Node<T> head = null;
    private Node<T> tail = null;

    // This function takes no params
    // It will return true if LL is empty and False if it is not empty
    public boolean isEmpty(){
        return head == null;
    }

    // This will add a node of any type to end of linked list
    // it takes an object as a param
    // it returns nothing
    public void addLast(T data) {
        Node<T> newNode = new Node<T>(null, data);
        if (isEmpty()) { // if its empty the head and tail r the same
            head =  newNode;
            tail =  newNode;
        }
        else { // if not connect tail to new obj and make the new obj the new tail
                tail.setNext(newNode);
                tail = newNode;
        } 
    }

    // This funct will remove head from ll and return it
    // no params
    // The return can be any type so make sure you know what type u r expecting when u use it
    public T getFirst() {
        Node<T> currentNode = head;

        head = currentNode.getNext();
        return currentNode.getData();
    }

    // This function will print the the names of the reps available
    // Only works on liked lists of type Represenative
    // Returns the names as a string
    public String getRepsAvil() {
        Node<T> current = head;
        String names = "";

        // If the ll is empty return enpty string
        if (current == null) {
            return "";
        }

        // else go through whole ll and add their names to the string
        while (current.getNext() != null) {
            Representitive data = (Representitive)current.getData();
            names = names + data.getName() + " ";
            current = current.getNext();
        }
        Representitive data = (Representitive)current.getData();
        names = names + data.getName() + " ";
        return names;
    }

    // This fuction will remove a chat sesh by matching it with the name of a customer
    // Takes a string that is the name of the customer
    // returns notherng
    // ONLY WORKS WITH CHAT SESHIONS
    public void RemoveChat(String nameOfCust) {
        Node<T> current = head;
        boolean notFound = true;
        Node<T> leading = null;
        if (current.getNext() != null) {
            leading = current.getNext().getNext();
        }
        //int result = -1;
        Chat currentChat = (Chat)(current.getData());
        // go through ll until its found or u r at the end
        while (current.getNext() != null && notFound) {
            // if u find a matching remove it and quit funct
            if (currentChat.getCust().getName().equals(nameOfCust)){
                notFound = false;
                current.setNext(leading);
                return;
                //result = (((currentChat.getCust().getHour()) * 60) + currentChat.getCust().getMinute());
            }
            // else get next elem in the ll
            else {
                current = current.getNext();
                if (leading != null) { // This is so it doesnt break if Leading is alread null. 
                    leading = leading.getNext();
                }
                currentChat = (Chat)(current.getData());
            }
        }
        //return result;  
    }

    // This one is pretty much the same as the one above excpte it only works with A ll of representives instead of chat sheshions
    // This fuction will remove a cust on hold by matching it with the name of a customer
    // Takes a string that is the name of the customer
    // returns notherng
    public void RemoveHold(String nameOfCust) {
        Node<T> current = head;
        boolean notFound = true;
        Node<T> leading = null;
        if (current.getNext() != null) {
            leading = current.getNext().getNext();
        }
        //int result = -1;
        Customer currentHold = (Customer)(current.getData());
        while (current.getNext() != null && notFound) {
            if (currentHold.getName().equals(nameOfCust)){
                notFound = false;
                current.setNext(leading);
                return;
                //result = (((currentChat.getCust().getHour()) * 60) + currentChat.getCust().getMinute());
            }
            else {
                current = current.getNext();
                if (leading != null) { // This is so it doesnt break if Leading is alread null. 
                    leading = leading.getNext();
                }
                currentHold = (Customer)(current.getData());
            }
        }
        //return result;  
    }

}
