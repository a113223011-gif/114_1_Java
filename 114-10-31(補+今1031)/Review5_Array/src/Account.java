public class Account{

    private static  int accountCount=0; //帳戶數量統計
    //帳戶號碼，唯一識別每個帳戶
    private String accountNumber;
    //帳戶擁有者姓名
    private String ownerName;
    //帳戶餘額
    private double balance;
    /**
     * 建構子，初始化帳戶號碼與初始餘額
     * @param accountNumber 帳戶號碼
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber,String ownerName,double initialBalance){
        this.setAccountNumber(accountNumber);
        this.ownerName=ownerName;
        try {
            this.setBalance(initialBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("初始化餘額錯誤: " + e.getMessage()+". 將餘額設為0.");
        }

        accountCount++;
    }
    public Account(String accountNumber,double initialBalance){
        this(accountNumber,"未知",initialBalance);

    }
    public Account(){
        this("未知帳號","未知",0);
    }
    public Account(String accountNumber){
        this(accountNumber,"未知",0);
    }
    /** 取得目前帳戶數量
     * @return 帳戶數量
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    /** 取得帳戶餘額
     * @return 帳戶餘額
     */
    public double getBalance() {
        return balance;
    }
    /** 取得帳戶擁有者姓名
     * @return 帳戶擁有者姓名
     */
    public String getOwnerName() {
        return ownerName;
    }
    /** 設定帳戶餘額
     * @param balance 帳戶餘額
     * @throws    IllegalArgumentException 當餘額為負數時拋出例外
     */
    public void setBalance( double balance) {
        if (balance > 0) {
            this.balance = balance;
        }else {
            throw new IllegalArgumentException("餘額不能為負數");
        }

    }
    /** 設定帳戶號碼
     * @param accountNumber 帳戶號碼
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    /** 存款方法，將指定金額存入帳戶
     * @param amount 存款金額
     * @throws    IllegalArgumentException 當存款金額小於等於零時拋出例外
     */
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException("存款金額必須大於零");
        }
    }
    /** 提款方法，從帳戶中提取指定金額
     * @param amount 提款金額
     * @throws    IllegalArgumentException 當提款金額無效或超過餘額時拋出例外
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("提款金額無效或超過餘額");
        }
    }
}








