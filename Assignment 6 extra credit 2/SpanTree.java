// this function makes a spanning tree for the search
public class SpanTree {
    private Node head = null;
    private Node current = null;
    Node[] queue = new Node[100];
    int qStart = 0;
    int qEnd = 0;
    private char[][] grid;

    // The constructor for the class
    public SpanTree(char[][] grid) {
        this.head = null;
        this.grid = grid;
    }

    // This is the search for the mosters 
    // it takes a character, and the cords of the player
    // it returns a node
    public Node bSearch(CharacterPlayer monster, int playerX, int playerY) {
        head = new Node(monster.xCord, monster.yCord, null, 0, '0'); // set the head node
        enQueue(head); // adds it to the queue
        this.grid[head.getX()][head.getY()] = '#'; // marks current value as visited
        while(isQueueEmpty() == false) { // keep going until q is empyt
            //displayBoard(grid);
            current = deQueue(); // dequeue one thing
            // if it is the right one return it
            //System.out.println(playerX + " "+ playerY);
            if (current.getX() == playerX && current.getY() == playerY) {
                return current;
            }

            // looks at surounding nodes
            Node up = current.makeUp();
            Node down = current.makeDown();
            Node left = current.makeLeft();
            Node right = current.makeRight();

            

            // if they are valid add them to the queue and mark them as visited
            if (isValid(up, grid)) {
                enQueue(up);
                grid[up.getX()][up.getY()] = '#';
            }

            if (isValid(down, grid)) {
                enQueue(down);
                grid[down.getX()][down.getY()] = '#';
            }

            if (isValid(left, grid)) {
                enQueue(left);
                grid[left.getX()][left.getY()] = '#';
            }

            if (isValid(right, grid)) {
                enQueue(right);
                grid[right.getX()][right.getY()] = '#';
            }
        }

        
        return null;
    }


    // This function reutnrs true if a move is valid
    // false if not
    // takes node and 2darray of chars
    // returns bool
    private boolean isValid(Node current, char grid[][]) {
        //System.out.println(current.getX() + " "+ current.getY());
        if (current.getY() >= grid[0].length || current.getX() >= grid.length  ) {
            return false;
        }
        if (grid[current.getX()][current.getY()] == ' ' || grid[current.getX()][current.getY()] == '*' || grid[current.getX()][current.getY()] == '0') {
            return true;
        }

        return false;
    }

    // this fuct adds a node to the q
    // increaces end of q by 1
    private void enQueue(Node node) {
        queue[qEnd] = node;
        qEnd +=1;
    }

    // this fuct says if the q is empty
    // true if its is
    // false if not
    private boolean isQueueEmpty() {
        if (qEnd == qStart) {
            return true;
        }

        else {
            return false;
        }
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

    // Returns first thign from the q
    // Returns a NOde
    private Node deQueue() {
       Node node =  queue[qStart];
       queue[qStart] = null;
       qStart += 1;
        return node;
    }

    

}