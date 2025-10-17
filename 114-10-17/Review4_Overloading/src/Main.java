public class Main {
    public static void main(String[] args) {
        // 建立兩個帳戶
        Account acc1 = new Account("A001", 1000);
        Account acc2 = new Account("A002", 2000);

        // 帳戶 1 存款
        acc1.deposit(500);               // 單筆存款
        acc1.deposit(100, 200);          // 多筆存款
        acc1.deposit(20, "USD");         // 美金存款
        acc1.deposit(10, "EUR");         // 歐元存款
        acc1.deposit(5000, "JPY");       // 日圓存款

        // 帳戶 2 存款
        acc2.deposit(300);               // 單筆存款
        acc2.deposit(50, 70);            // 多筆存款
        acc2.deposit(100, "USD");        // 美金存款

        // 顯示帳戶資訊
        System.out.println("帳戶資料:");
        acc1.display();
        acc2.display();
    }
}
