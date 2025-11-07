public class Magician extends Role {
    private int healPower;

    public Magician(String name, int magicPower, int health) {
        super(name, health, magicPower);
        this.healPower = healPower; // 預設治癒力為20
    }

    //取得治癒力
    public int getHealPower() {
        return healPower;
    }

    //設定治癒力
    public void setHealPower(int healPower) {
        this.healPower = healPower;
    }

    //治癒自己
    public void attack(SwordsMan ally) {
        System.out.println(this.getName() + "治癒" + ally.getName() + "，恢復" + this.getHealPower() + "點生命值");
        ally.setHealth(ally.getHealth() + this.getHealPower());
    }

    //攻擊對手
    public void attack(Magician opponent) {
        System.out.println(this.getName() + "攻擊" + opponent.getName() + "，造成" + this.getAttackPower() + "點傷害");
        opponent.setHealth(opponent.getHealth() - this.getAttackPower());
    }

    public String toString() {
        return super.toString() + "，治癒力: " + healPower;
    }
}//End of Magician
