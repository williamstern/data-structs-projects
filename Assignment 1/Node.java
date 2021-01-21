// Another generic type so this can be any object
// Has to be declared at compile time tho
public class Node<T> {

    private Node<T> next; // ref to next node
    private T data; // Generic data so it can be any class
    // Class<t> classtype;

    // Constructor for this class
    // Takes a referecne to next node and data of any type
    public Node(Node<T> initialNext, T data) {
        this.next = initialNext;
        this.data = data;
            
    }   

    // This methdod sets data
    // It takes object of any type as a param
	public void setData(T dataRep) {
        data = dataRep;
    }

    // This gets the generic data of any type
    // MAKE SHURE U KNOW WHAT TYPE OF DATA U R GETTING
    public T getData() {
        return data;
    }

    // THis will get the next Node in the LL
    // It return another generic type node
    public Node<T> getNext() {
        return next;    
    }

    // This will set the next node
    // takes gener node as a param
    // make sure u know the type again
    public void setNext(Node<T> node) {
        this.next = node;
    }

}