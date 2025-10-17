public class AccountTest {
    private static int customerCount;
    public static void main(String[] args) {
        Account[] customers = new Account[10];
        Account acc1 = new Account("A001", 1000);
        addCustomer(customers, acc1);
        Account acc2 = new Account("A002", 2000);
        addCustomer(customers, acc2);
        Account acc3 = new Account("000000", 5000); // 測試初始餘額為負數
        addCustomer(customers, acc3);
    }

    public static void addCustomer(Account[] customers, Account newAccount) {
        if (customerCount < customers.length) {
            customers[customerCount] = newAccount;
            customerCount++;
        } else {
            System.out.println("無法新增更多客戶，已達上限");
        }

    }
}
