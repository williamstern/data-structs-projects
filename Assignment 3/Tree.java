// Tree class contains root node and methods that perate over entire tree
public class Tree {
    private Node root;

    public Tree() { // auto set to null
        this.root = null;
    }

    //i: String for name of root node
    //O: nothing
    // sets up root node with custom name allong with base depth of 0
    public void setRoot(String name) { 
        this.root = new Node(name, null, 0);
    }

    //I; A base node to start looking at and a String name to find name you are looking for
    public Node findNode(Node node, String name) {
        if(node != null){ // if node exists
            if(node.getName().equals(name)){ // check name
               return node;
            } 
            
            else {
                Node found = findNode(node.getSubordinate(), name); // Go down recursively
                if(found == null) { // check if node was found down
                    found = findNode(node.getNextSibling(), name); // go sideways recursively
                }
                return found;
            }
        }
        else {
            return null;
        }
    }

    // I; 2 strings one for supervisor to find and one for name of subordinate
    // O; zip nada none
    // This method adds a new node onto tree by finding supervisor and adding subordiante bellon in alphbetical order
    public void addNode(String supervisor, String subordinate) {
        Node upper = findNode(this.root, supervisor); // finds supervisor

        Node lower = new Node(subordinate, upper, upper.getDepth()+1); // create subordinate
        Node head = upper.getSubordinate(); // go down one
        // some linked list stuff
        if (head == null || head.getName().compareTo(lower.getName())  > 1) { // if first in node or greater alphabeticly
            lower.setNextSibling(head);
            upper.setSubordinate(lower);
        }
        else {
            Node current = head;
            //check for end or alphabetical order
            while (current.getNextSibling() != null && current.getNextSibling().getName().compareTo(lower.getName()) > 1) { 
                current = current.getNextSibling(); 
            }
            lower.setNextSibling(current.getNextSibling());
            current.setNextSibling(lower);
        } 
    }

    //I: a string name
    // O; the supervisor of name of input
    // finds node and gets supervisor
    public Node printDirectSupervisor(String name) {
        Node current = findNode(this.root, name);
        System.out.print(current.getName() + " ");
        current = current.getSupervisor();
        System.out.println(current.getName());
        return current;
    }

    //i; a name of node
    // o the name of all of the direct subordinates
    // prints all direct subordinates of node named entered
    public void printDirectSubordinates(String name) {
        Node current = findNode(this.root, name);
        System.out.print(current.getName() + " ");
        // goes through linked list printing
        Node subordinate = current.getSubordinate();
        while(subordinate.getNextSibling() != null) {
            System.out.print(subordinate.getName() + " ");
            subordinate = subordinate.getNextSibling();
        }
        System.out.print(subordinate.getName() + "\n");
    }
    //i; a name of node
    // o the names of all of the supervisors of a node
    // This method finds the node of string entered and then prints all the supervisors of that node
    public void printAllSupervisors(String name) {
        Node current = findNode(this.root, name);
        System.out.print(current.getName() + " ");
        // This keeps getting supervisor until next is null aka the root node
        while(current.getSupervisor() != null) {
            current = current.getSupervisor();
            System.out.print(current.getName() + " ");
        }
        System.out.print("\n");
    }
    
    //i; a name of node
    // o the names of all of the subordinates of a node
    // This method finds the node of string entered and then prints all the subordinates of that node
    public void printAllSubordinates(String name) {
        Node current = findNode(this.root, name);
        System.out.print(current.getName() + " ");
        preOrderPrint(current.getSubordinate()); // calls recursive function
        System.out.print("\n");
    }

    // i; a node
    // O it prints all the nodes using preorder

    // prints current node and then recurses down aka left and sideways aka right
    private void preOrderPrint(Node current) {
        System.out.print(current.getName() + " ");
        if (current.getSubordinate() != null) {
            preOrderPrint(current.getSubordinate());
        }
        if(current.getNextSibling() != null) {
            preOrderPrint(current.getNextSibling());
        }
    }
    
    // i; a name of a node
    // o  prints a count of all supervisors
    // this method finds a node and then counts all the supervisors in the tree above it
    public void printNumberOfAllSupervisors(String name) {
        Node current = findNode(this.root, name);
        System.out.print(current.getName() + " ");
        int counter = 0;
        //
        while (current.getSupervisor() != null) {
            current = current.getSupervisor();
            counter += 1;       
        }
        System.out.print(counter + "\n");
    }

    // i; a name of a node
    // o the number of all the subordinates
    // finds a node and prints a coundt of all of its subordinates
    public void printNumberOfAllSubordinates(String name) {
        System.out.print(name + " ");
        Node current = findNode(this.root, name).getSubordinate();
        int num = countTree(current); // calls recursive function
        System.out.println(num);

    }

    // i; a node
    // o an int of num of the number of subordinates of a node
    // recursises tree and counts all the nodes
    private int countTree(Node current) {
        //System.out.println(current.getName());
        int counter = 1; // sets value of all nodes to one
        Node sibl = current.getNextSibling();
        Node child = current.getSubordinate();

        if (current.getNextSibling() != null) {
            counter += countTree(sibl); // will add the one each time
        }
        if (current.getSubordinate() != null) {
            counter += countTree(child); // will add the one each time
        }
        return counter; 
    }

    // i a name of a node and a name of a supervisor
    // o print yes if second is a supervisor of the first else no
    // finds entitiy in tree and then sees if supervisor is above it
    public void printIsSupervisor(String entity, String supervisor) {
        System.out.print(entity + " " + supervisor + " ");
        Node current = findNode(this.root, entity);
        Node subordinate = current;
        boolean found = false;

        while(current.getSupervisor() != null && !found) {
            if (current.getName().equals(subordinate.getName())) { // check if they match
                System.out.print("yes\n");
                return;
            }

            else {
                current = current.getSupervisor(); // go up 1 level
            }
        }
        System.out.println("no\n"); // else no

    }

    // I: the name of an node and the name of a subordinate to chekc
    // o: prints yes if entity is a subordinate of subordinate else no
    // finds entity and then sees if subordinates can be found when search starts at entity node
    public void printIsSubordinate(String entity, String subordinate) {
        System.out.print(entity + " " + subordinate + " ");
        Node superNode = findNode(this.root, entity).getSubordinate();

        if (findNode(superNode, subordinate) == null) {
            System.out.print("no\n");
        }

        else {
            System.out.print("yes\n");
        }
    }

    // I: two string that are the names of two ranks
    // o: print higher if person1 is a higher rank then person 2, lower if person one is lower and same if they are teh same
    public void printCompareRank(String entity1, String entity2) {
        System.out.print(entity1 + " " + entity2 + " ");
        Node person1 = findNode(this.root, entity1);
        Node person2 = findNode(this.root, entity2);

        // a lower depth means a higher rank

        if (person1.getDepth() < person2.getDepth()) { 
            System.out.print("higher\n");
        }

        else if (person1.getDepth() > person2.getDepth()) {
            System.out.print("lower\n");    
        }

        else {
            System.out.print("same\n");
        }
    }

    // I; the names of two nodes
    // O prints the closets common supervisor of two nodes
    // finds lower rand one and moves it to be equal to higher and then moves them both up until they are the same
    public void printClosestCommonSupervisor(String entity1, String entity2) {
        System.out.print(entity1 + " " + entity2 + " ");
        Node person1 = findNode(this.root, entity1);
        Node person2 = findNode(this.root, entity2);
        Node higher;
        Node lower;

        // finds lower initial rand
        if (person1.getDepth() < person2.getDepth()) { 
            higher = person1;
            lower = person2;
        }

        else {
            higher = person2;
            lower = person1;
        }

        // moves lower one up to be same rank as higher
        while (lower.getDepth() > higher.getDepth()) {
            lower = lower.getSupervisor();
        }

        // while names dont match move both up
        while (!(lower.getName().equals(higher.getName()))) {
            lower = lower.getSupervisor();
            higher = higher.getSupervisor();
        }

        System.out.print(lower.getName() + "\n");
    }
    
}
