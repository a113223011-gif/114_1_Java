public class AccountTest {
    public static void main(String[] args) {
        // 建立兩個帳戶
        Account acc1 = new Account("A001", 1000);
        Account acc2 = new Account("A002", 2000);
        Account acc3 = new Account("000000", 5000); // 測試初始餘額為負數
        // 單筆存款
        acc1.deposit(500);
        // 多筆存款
        acc1.deposit(100, 200);
        // 存入不同幣別
        acc1.deposit(10, "USD");
        acc1.deposit(20, "EUR");
        acc1.deposit(1000, "JPY");

        acc2.deposit(500);
        acc2.deposit(300, "USD");
        acc2.deposit(50, "EUR");
        acc2.deposit(5000, "JPY");

        acc3.deposit(1000); // 測試對初始負餘額帳戶存款


        // 顯示帳戶資訊
        System.out.println("=== 帳戶資料 ===");
        acc1.display();
        acc2.display();
        acc3.display();
    }
}
