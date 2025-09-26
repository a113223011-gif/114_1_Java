public class Account {
    //帳戶號碼，唯一識別每個帳戶
    private String accountNumber;
    //帳戶餘額
    private double balance;

    /**
     * *建構子，初始化帳戶號碼與初始餘額
     * @param accountNumber 帳戶號碼
     * @param initialBalance 初始餘額
     */

    // constructor initializes name with parameter name
    public Account(String accountNumber, double initialBalance) {
        this.setAccountNumber(accountNumber);
        try{
            this.setBalance(initialBalance);
        }catch (IllegalArgumentException e){
            System.out.println("初始餘額錯誤:" + e.getMessage() + "，餘額設為0");
        }
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    /**
     * * 取得帳戶號碼
     * @return 帳戶餘額
     */

    // method to set the name
    public String getAccountNumber() {return accountNumber;}

    public void setBalance(double balance) {
        if (balance > 0) {
            this.balance = balance;
        }else{
            throw new IllegalArgumentException("帳戶餘額必須為正數");
        }
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * * 取得帳戶餘額
     * @return 帳戶餘額
     */
    // method to retrieve the name
    public double getBalance() {return balance; }

    /**
     * * 存款
     * @param amount 存入金額，必須為正數
     * @throws IllegalArgumentException 若金額非正數則拋出例外
     */

    public void deposit(double amount) {
        if (amount <= 0) {
            balance += amount; //增加餘額
        }else{
            throw new IllegalArgumentException("存款金額必須為正數");
        }
    }
    /**
     * * 提款
     * @param amount 提款金額，必須為正數且不得超過餘額
     * @throws IllegalArgumentException 若金額非正數或餘額不足則拋出例外
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount; //減少餘額
        } else {
            throw new IllegalArgumentException("提款金額不合法");
        }
    }
}
