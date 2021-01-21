
public class Node {

    private Node next; // ref to next node
    private Node prev; // ref to next node
    private Node up; // ref to next node
    private Node down; // ref to next node
    private Event data; 
    // Class<t> classtype;

    // Constructor for this class
    // Takes a referecne to next node and data of any type
    public Node(Node initialNext, Node initialPrev, Node initialUp, Node initialDown, Event data) {
        this.next = initialNext;
        this.data = data;
        this.down = initialDown;
        this.up = initialUp;
        this.prev = initialPrev;
    }   

    // This methdod sets data
    // It takes object of any type as a param
	public void setData(Event dataRep) {
        data = dataRep;
    }

    // This gets the generic data of any type
    // MAKE SHURE U KNOW WHAT TYPE OF DATA U R GETTING
    public Event getData() {
        return data;
    }

    // THis will get the next Node in the LL    
    public Node getNext() {
        return next;    
    }

    // gets the node below
    public Node getDown() {
        return down;    
    }

    // gets node above
    public Node getUp() {
        return up;    
    }

     //gets node to the left
    public Node getPrev() {
        return prev;    
    }
    

// The folowing fucntions are to set the surounding nodes

    // This will set the next node
    // takes gener node as a param
    // make sure u know the type again
    public void setNext(Node node) {
        this.next = node;
    }

    public void setUp(Node node) {
        this.up = node;
    }

    public void setDown(Node node) {
        this.down = node;
    }

    public void setPrev(Node node) {
        this.prev = node;
    }

}