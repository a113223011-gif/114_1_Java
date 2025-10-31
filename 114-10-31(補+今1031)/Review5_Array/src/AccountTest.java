public class AccountTest {
    public static  int CustomerCount; //帳戶數量統計
    public static  void main(String[] args) {
        Account[] customers = new Account[10];
        Account acc1 = new Account("A001", "Alice", 5000);
        addCustomer(customers, acc1);
        Account acc2 = new Account("A002", "Bob", 3000); // 測試負初始餘額
        addCustomer(customers, acc2);
        Account acc3 = new Account("A003", "Charlie", -100);// 使用預設建構子
        addCustomer(customers, acc3);
        opperation(customers);
        // 顯示所有帳戶資訊
        //System.out.println("\n所有客戶帳戶資訊:");
        //printCustomerAccounts(customers);


    }
    public static void opperation(Account[] customers) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Account selectAccount = null;
        int choice;
        do {
            menu();
            System.out.print("請選擇操作 (1-3): ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("輸入帳戶號碼: ");
                    String accNum = scanner.next();
                    System.out.print("輸入擁有者姓名: ");
                    String ownerName = scanner.next();
                    System.out.print("輸入初始餘額: ");
                    double initialBalance = scanner.nextDouble();
                    Account newAccount = new Account(accNum, ownerName, initialBalance);
                    addCustomer(customers, newAccount);
                    break;
                case 2:
                    System.out.print("輸入要查詢的帳戶號碼: ");
                    String searchAccNum = scanner.nextLine();
                selectAccount = customerInaction(customers, searchAccNum);
                    if (selectAccount == null) {
                        System.out.println("找不到該帳戶號碼的客戶。");
                        break;
                    }
                    System.out.println("\n指定客戶帳戶資訊:");
                printCustomerInfo(selectAccount);
                    break;
                case 3:
                    System.out.println("\n所有客戶帳戶資訊:");
                    printCustomerAccounts(customers);
                    break;
                case 4:
                    System.out.println("離開系統，謝謝使用!");
                    return;
                default:
                    System.out.println("無效選項，請重新選擇。");
            }
        } while (choice != 3);
        scanner.close();
    }
    public static Account customerInaction(Account[] customers, String accountNumber) {
        for (int i = 0; i < CustomerCount; i++) {
            if (customers[i].getAccountNumber().equals(accountNumber)) {
                return customers[i];
            }
        }
        return null;
    }
    public static void addCustomer(Account[] customers, Account newAccount) {
        if (CustomerCount < customers.length) {
            customers[CustomerCount] = newAccount;
            CustomerCount++;
            System.out.println("成功新增客戶帳戶: " + newAccount.getAccountNumber());
            return;
        }
        System.out.println("無法新增更多客戶，已達上限。");

    }
    public static void printCustomerAccounts(Account[] customers) {
        for (int i = 0; i < CustomerCount; i++) {
            printCustomerInfo(customers[i]);
        }
    }
    public static void printCustomerInfo(Account account) {
        if(account==null){
            System.out.println("無此帳戶資料");
            return;
        }
        System.out.println("帳戶號碼: " + account.getAccountNumber() +
                ", 擁有者: " + account.getOwnerName() +
                ", 餘額: " + account.getBalance());
    }
    //功能選單 (1.新增客戶帳戶 2.列印指定客戶帳戶資訊 3.顯示所有客戶帳戶資訊 4.離開系統)
    public static  void menu() {
        System.out.println("功能選單");
        System.out.println("1. 新增客戶帳戶");
        System.out.println("2. 列印指定客戶帳戶資訊");
        System.out.println("3. 顯示所有客戶帳戶資訊");
        System.out.println("4. 離開系統");

    }
}
