
public class Event { // this event class is the data part of the node
    String date;
    String name;
    public Event(String date, String name) {
        this.date = date;
        this.name = name;
    }

    // following return strings and take nothing
    // gets the month part of key
    public String getMonth() {
        return this.date.substring(0, 2);
    }

    // gets the day part of key
    public String getDay() {
        return this.date.substring(2, 4);
    }

    public String getKey() { // get entire key
        return date;
    }

    public String getName() { // the the name of the event as a string
        return name;
    }

    // This will camare two keys to eachother
    // return 1 of this is greater, -1 is this is less then and 0 if they are the same
    public int compare(Event other) {
        if (this.getMonth().compareTo(other.getMonth()) > 0) {
            return 1;
        }

        else if (this.getMonth().compareTo(other.getMonth())  < 0) {
            return -1;
        }

        else {
            if (this.getDay().compareTo(other.getDay()) > 0) {
                return 1;
            }

            else if (this.getDay().compareTo(other.getDay()) < 0) {
                return -1;
            }

            else {
                return 0;
            }
        }
    }
}