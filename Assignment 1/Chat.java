public class Chat {
    // Since a chat session has both a rep and a customer this takes a object of both
    Representitive rep;
    Customer cust;

    // Constructor
    // This takes Representitve object and a customer object
    public Chat(Representitive rep, Customer cust) {
        this.rep = rep;
        this.cust = cust;
    }

    // Takes notheing as params
    // Returns the Repressentitive as an object
    public Representitive getRep() {
        return rep;
    }

    // Takes notheing as params
    // Returns the Customer as an object
    public Customer getCust() {
        return cust;
    }
}

//  its 6 am and im like :)))))))))))
// Ive written representitive and customes so much they dont seem like real words anymore