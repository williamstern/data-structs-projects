import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathFind {

    char[][] grid; // this is the transformed grid
    CharacterPlayer player;
    CharacterPlayer cureature; // this is misspeld llol
    Vertex start;
    Vertex end;
    private PriorityQueue<Vertex> set = new PriorityQueue<Vertex>(); // this is the priorority q
    // initakazer
    public PathFind(char[][] grid, CharacterPlayer player, CharacterPlayer creature) {
        this.start = new Vertex(creature.xCord, creature.yCord, 0, 0);
        start.setDistance(0); // sets the start distance to 0
        set.add(start); // adds it to q
        this.grid = grid;
        this.player = player;
        //dijksta();
    }
    // u = curprent
    // v = neighbor
    public Vertex dijksta() {
        //int shortest = 9999999;
        //Vertex path = new Vertex(0,0,0,0);
        while (set.size() > 0) { // while there are things in the q
            
            //Collections.sort(set);
            Vertex current = set.poll(); // pop the top
            grid[current.xCord][current.yCord] = 'v'; // mark as visitied
            //System.out.println(Arrays.deepToString(grid).replace("],", "]\n"));
            //set.remove(0);

            // end condition
            if (current.equalsCharacter(player)) {
                //shortest = current.distance;  
                return current;
            }


            ArrayList<Vertex> neighbors = current.getNeighbors(grid); // gets the current vertexs neighbors

            for (int i = 0; i < neighbors.size(); i++) { // goes through all th neighbors
                Vertex neighborFocus = neighbors.get(i);
                //boolean die = !set.contains(neighborFocus);
                if (!set.contains(neighborFocus)) { // if they are not in the set
                    //int dis = current.getDistance();
                    //int leng = neighborFocus.getLength();
                    int alt = current.getDistance() + neighborFocus.getLength(); // tenative new distance
                    if (alt < neighborFocus.getDistance()) { // if its larger
                        // set the new distance and set prev
                        neighborFocus.setDistance(alt);
                        neighborFocus.setPrev(current);
                        // reset position in the queue
                        set.remove(neighborFocus);
                        set.add(neighborFocus);
                        //leng = neighborFocus.getLength();
                    }
                }
            }
        }
        return null;
    }
    


}

    