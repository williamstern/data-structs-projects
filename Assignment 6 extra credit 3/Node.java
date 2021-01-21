// this is the class that is used for the nodes in the spanning tree

public class Node {
    private int xCord;
    private int yCord;
    private Node parent;
    private int distance;
    private char direction;
    //private Node[] children = new Node[4];

    // constructor for node
    public Node(int xCord, int yCord, Node parent, int distance, char direction) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.parent = parent;
        this.distance = distance;
        this.direction = direction;
    }


    // returns the x value as an int
	public int getX() {
        return xCord;
    }

    // returns the y value as an int
    public int getY() {
        return yCord;
    }

    // returns the direction as a char
    public char getDirection() {
        return direction;
    }

    //returns the distance as an int
    public int getDistance() {
        return distance;
    }

    // All theses are kinda messed up but they mostly work
    // i think

    // Makes a new node above
    public Node makeUp() {
        Node newNode = new Node(xCord-1, yCord, this, distance+1, 'u');
        return newNode;
    }

    //makes a new node below
    public Node makeDown() {
        Node newNode = new Node(xCord+1, yCord, this, distance+1, 'd');
        return newNode;
    }

    // makes a new node to the right
    public Node makeRight() {
        Node newNode = new Node(xCord, yCord+1, this, distance+1, 'l');
        return newNode;
    }

    // makes a new node to the left
    public Node makeLeft() {
        Node newNode = new Node(xCord, yCord-1, this, distance+1, 'r');
        return newNode;
    }

    // returns the parent of the current node
    public Node getParent() {
        return this.parent;
    }

    public static void displayBoard(char[][] grid) {
        for(int x = -1; x < grid.length; x++) {
            for (int y = -1; y < grid[0].length +1; y++) {
                if (y < 0 && x >= 0) {
                    System.out.print(x);
                }

                else if (x < 0 && y == 0) {
                    System.out.print(" ");
                }
                else if (x < 0 && y > 0) {
                    System.out.print(y-1); 
                }

                else if (y >= 0 && x >= 0 && x <grid.length && y < grid[0].length) {
                    System.out.print(grid[x][y]);
                }
            }
            System.out.print("\n");
        }
    }

    /* public void setChild(Node child) {
        for (int i = 0; i < 4; i ++) {
            if (children[i] != null) {
                children[i] = child;
            }
        }
    } */
}