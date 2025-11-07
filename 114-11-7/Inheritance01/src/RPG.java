public class RPG {
    public static void main(String[] args) {
        SwordsMan hero = new SwordsMan("勇者", 30, 100);
        SwordsMan monster = new SwordsMan("怪物", 20, 80);
        Magician mage = new Magician("魔法師", 25, 70);
        mage.setHealPower(15); // 設定魔法師的治癒力為15
        System.out.println("戰鬥開始！");

        //戰鬥過程
        System.out.println("戰鬥開始!");
        hero.attack(monster);
        Magician allyMage = mage; // 魔法師支援勇者
        allyMage.attack(hero); // 魔法師治癒勇者
        mage.attack(hero);

    }//End of main
}//End of RPG
