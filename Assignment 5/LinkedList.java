// This is a generic type class which i thought would be cool and save time
// It turned out to be a hege headach insted
// Its still kinda cool tho imo since you only need one LinkedList and Node class
// This class can take any type of data however some of the functions will not work with every type 
public class LinkedList {

    // Initalize head and tail
    private Node head = null;
    private Node tail = null;

    // This function takes no params
    // It will return true if LL is empty and False if it is not empty
    public boolean isEmpty(){
        return head == null;
    }

    // This will add a node of any type to end of linked list
    // it takes an object as a param
    // it returns nothing
    public void addLast(Event data, Node initialNext, Node initialPrev, Node initialDown, Node initialUp) {
        Node newNode = new Node(initialNext, initialPrev, initialUp, initialDown, data);
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
    public Node getFirst() {
        return head;
    }

    // this takes no imput and return the last node in the liked list
    public Node getLast() {
        return tail;
    }
}
