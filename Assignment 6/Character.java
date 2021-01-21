// This is the calss that sis used for all npcs as well as the player
public class Character implements Comparable<Character> {
    public int xCord;
    public int yCord;
    public char name;

    public Character(char name, int xCord, int yCord) {
        this.name = name;
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public int getUp(){
        return yCord+1;
    }

    public int getDown(){
        return yCord-1;
    }

    public int getLeft(){
        return xCord-1;
    }

    public int getRight(){
        return xCord+1;
    }

    @Override // this is so you can sort an array of Characters by the char name
    public int compareTo(Character p) {
        if(this.name < p.name) return -1;
        if(this.name == p.name) return 0;
        else return 1;
    }

}