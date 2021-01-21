// this class if for the nodes for both tree and linked list stuff
public class Node {
    private int depth;
    private String name;
    private Node supervisor;
    private Node subordinate;
    private Node sibling;

    // Initilizer with all required node params
    public Node(String name, Node supervisor, int depth) {
        this.name = name;
        this.depth = depth;
        this.supervisor = supervisor;
        this.subordinate = null;
        this.sibling = null;
    }

    // returns int of depth from root
    public int getDepth() {
        return depth;
    }

    // return node
    // gets the supervisor of a node
    public Node getSupervisor() {
        return supervisor;
    }

    // returns node
    // gets the subordnte of current node
    public Node getSubordinate() {
        return subordinate;
    }

    // I: a node 
    // out: none
    // sets the subordinate of current node to input
    public void setSubordinate(Node subordinate) {
        this.subordinate = subordinate;
    }

    // input none
    // output string
    // returns string of name of current node
    public String getName() {
        return name;
    }

    // I; none
    // outputs node
    // this gets the next sibiling of current node
    public Node getNextSibling() {
        return sibling;
    }

    // i ; a node to set as sibiling
    // out: none
    // this method sets the next subiling of current node to input
    public void setNextSibling(Node sibling) {
        this.sibling = sibling;
    }
}
