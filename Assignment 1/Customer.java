// this one extends rep since they also have a name but adds hour and minute
public class Customer extends Representitive {
    // Time the person called
    private int requestHour;
    private int requestMinute;

    // Constructor for class
    // takes a string for the name and two ints for hour and min respetibly
    public Customer(String name, int hour, int minute) {
        super(name); // sets name 
        this.requestHour = hour;
        this.requestMinute = minute;
    }

    // Made this but never used em
    // public void setMinute(int minute){
    //     this.requestMinute = minute;
    // }

    // public void setHour(int hour) {
    //     this.requestHour = hour;
    // }

    // NO params
    // returns minute called as an int
    public int getMinute() {
        return requestMinute;
    }

    // NO params
    // returns hour called as an int
    public int getHour() {
        return requestHour;
    }

} 