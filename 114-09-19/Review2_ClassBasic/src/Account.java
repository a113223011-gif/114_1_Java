public class Account {
    private String accountNumber; // instance variable
    private double balance;


    // constructor initializes name with parameter name
    public Account(String accountNumber, double initialBalance) {
        this.balance = initialBalance; // assign name to instance variable name
    }

    // method to set the name
    public String getAccountNumber() {
        return accountNumber; // store the name
    }

    // method to retrieve the name
    public double getBalance() {
        return balance; // return value of name to caller
    }

}
