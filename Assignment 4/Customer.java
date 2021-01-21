
public class Customer {

    int[] ratings = new int[10];
    String name = new String();
    double distance;

    // Constructor for class Customer
    // Takes a name and an int array for ratings
    public Customer(String name, int[] ratings) {
        this.name = name;
        this.ratings = ratings;
    }

    // Returns the name as a string
    public String getName() {
        return name;
    }

    // Returns an int array of ratigns
    public int[] getRating() {
        return ratings;
    }

    // Sets the distance by inserting a type double
    public void setDistance(double distance) {
        this.distance = distance;
    }

    // retuns a double of the distance
    public double getDistance() {
        return distance;
    }   
}   