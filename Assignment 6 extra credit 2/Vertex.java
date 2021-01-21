// This file is the node or vertexes that are used in the Pathfind class
import java.util.ArrayList;
import java.util.Objects;

public class Vertex implements Comparable<Vertex>{
    // coordnates
    public int xCord;
    public int yCord;

    // prev node
    Vertex prev;

    // some data points
    public int distance;
    public char direction;
    public int length;

    // initalizer
    public Vertex(int x, int y, int len, int distance) {
        this.length = len;
        this.xCord = x;
        this.yCord = y;
        this.distance = 99999999; // sets max distance
    }

    // returns the x value as an int
	public int getX() {
        return xCord;
    }

    // sets the direction
    public void setDirection(char dir) {
        this.direction = dir;
    }

    // set distance for the node
    public void setDistance(int distance) {
        this.distance = distance;
    }

// returns the y value as an int
    public int getY() {
        return yCord;
    }

    // returns the direction as a char
    public char getDirection() {
        return direction;
    }
    
    public int getLength(){
        return length;
    }

    //returns the distance as an int
    public int getDistance() {
        return distance;
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }


    // returns the parent of the current node
    public Vertex getPrev() {
        return this.prev;
    }

    // Gets the up node if it is avaliable
    public Vertex getUp(char[][] grid) {
        int xMax = grid.length;
        int yMax = grid[0].length;
        if (yCord < yMax && xCord-1 < xMax && yCord >=0 && xCord-1 >=0) {
            if ((grid[xCord-1][yCord])>= '0' && grid[xCord-1][yCord] <='9') {
                Vertex newV =  new Vertex(xCord-1, yCord, Character.getNumericValue(grid[xCord-1][yCord]), distance);
                newV.setDirection('u');
                return newV;
            }
        }
        return null;
    }

    // Gets the Down node if it is avaliable
    public Vertex getDown(char[][] grid) {
        int xMax = grid.length;
        int yMax = grid[0].length;
        if (yCord < yMax && xCord+1 < xMax && yCord >=0 && xCord+1 >=0) {
            if ((grid[xCord+1][yCord])>= '0' && grid[xCord+1][yCord] <='9') {
                Vertex newV =  new Vertex(xCord+1, yCord, Character.getNumericValue(grid[xCord+1][yCord]),0 );
                newV.setDirection('d');
                return newV;
            }
        }
        return null;
    }

    // Gets the left node if it is avaliable
    public Vertex getLeft(char[][] grid) {
        int xMax = grid.length;
        int yMax = grid[0].length;
        if (yCord-1 < yMax && xCord < xMax && yCord-1 >=0 && xCord >=0) {
            if ((grid[xCord][yCord-1])>= '0' && grid[xCord][yCord-1] <='9') {
                Vertex newV =  new Vertex(xCord, yCord-1, Character.getNumericValue(grid[xCord][yCord-1]), 0);
                newV.setDirection('l');
                return newV;
            }
        }
        return null;
    }

    // Gets the right node if it is avaliable
    public Vertex getRight(char[][] grid) {
        int xMax = grid.length;
        int yMax = grid[0].length;
        if (yCord+1 < yMax && xCord < xMax && yCord+1 >=0 && xCord >=0) {
            if ((grid[xCord][yCord+1])>= '0' && grid[xCord][yCord+1] <='9') {
                Vertex newV =  new Vertex(xCord, yCord+1, Character.getNumericValue(grid[xCord][yCord+1]), 0);
                newV.setDirection('R');
                return newV;
            }
        }
        return null;
    }
    
    //Gets all the avalible nodes and puts them and in list and return it
    public ArrayList<Vertex> getNeighbors(char[][] grid) {
        ArrayList<Vertex> result = new ArrayList<Vertex>();
        Vertex down = this.getDown(grid);
        Vertex up = this.getUp(grid);
        Vertex left = this.getLeft(grid);
        Vertex right = this.getRight(grid);

        if (right != null) {
            result.add(right);
        }
        if (up != null) {
            result.add(up);
        }
        if (down != null) {
            result.add(down);
        }
        if (left != null) {
            result.add(left);
        }
        return result;
    }

    // This compares this object against a character object to see if the destiantion hass been reached
    public boolean equalsCharacter(CharacterPlayer chara) {
        //int th = this.xCord;
        //int ch =chara.xCord;
        if(this.xCord == chara.xCord && this.yCord == chara.yCord) {
            return true;
        }

        else {
            return false;
        }
    }

    // This overrides the CompareTo function for sorting
    @Override
    public int compareTo(Vertex p) {
        if(this.distance < p.distance) return -1;
        if(this.distance == p.distance) return 0;
        else return 1;
    }

    // THis overrides the equals for seeing if something is in the queue
    @Override
    public boolean equals(Object o) {
        if (o == this) { 
            return true; 
        } 
        if (!(o instanceof Vertex)) { 
            return false; 
        } 
        Vertex other = (Vertex) o;

        if(this.xCord == other.xCord && this.yCord == other.yCord && this.length == other.length) {
            return true;
        }

        return false;
    }

    // Overides the hashcode since that needs to be done when you reset the equals
    @Override public int hashCode() {
        return Objects.hash(xCord, yCord);
    }
}