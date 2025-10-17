import java.util.Scanner;
public class Account {
    // 帳戶號碼
    private String accountNumber;
    // 帳戶餘額（以 TWD 計算）
    private double balance;
    // 初始餘額
    private double initialBalance;

    /**
     * 建構子，初始化帳戶號碼與初始餘額
     */
    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        try {
            setBalance(initialBalance);
            this.initialBalance = initialBalance;
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤: " + e.getMessage() + "，餘額設為0");
            this.balance = 0;
            this.initialBalance = 0;
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new IllegalArgumentException("帳戶餘額必須為正數");
        }
    }

    // ===== 存款 =====
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("存款金額必須為正數");
        }
    }

    // 多筆存款
    public void deposit(double... amounts) {
        for (double amount : amounts) {
            deposit(amount);
        }
    }

    // 存款（指定幣別）
    public void deposit(double amount, String currency) {
        switch (currency.toUpperCase()) {
            case "TWD":
                deposit(amount);
                break;
            case "USD":
                deposit(amount * 30); // 假設 1 USD = 30 TWD
                break;
            case "EUR":
                deposit(amount * 35); // 假設 1 EUR = 35 TWD
                break;
            case "JPY":
                deposit(amount * 0.22); // 假設 1 JPY = 0.22 TWD
                break;
            default:
                throw new IllegalArgumentException("不支援的貨幣: " + currency);
        }
    }

    // 整數版本
    public void deposit(int amount) {
        deposit((double) amount);
    }

    // ===== 提款 =====
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("提款金額不合法或餘額不足");
        }
    }

    public void withdraw(double... amounts) {
        double total = 0;
        for (double amount : amounts) {
            if (amount <= 0) {
                throw new IllegalArgumentException("提款金額必須為正數: " + amount);
            }
            total += amount;
        }

        if (total > balance) {
            throw new IllegalArgumentException("提款總額超過餘額，交易失敗。可用餘額：" + balance + "，欲提取：" + total);
        }

        for (double amount : amounts) {
            balance -= amount;
        }
    }

    public void withdraw(int amount) {
        withdraw((double) amount);
    }

    // ===== 顯示帳戶資訊 =====
    public void display() {
        System.out.println("帳戶號碼: " + accountNumber);
        System.out.println("初始餘額: " + initialBalance + " TWD");
        System.out.println("存款後餘額: " + balance + " TWD");
        System.out.println("---------------------------");
    }
}
