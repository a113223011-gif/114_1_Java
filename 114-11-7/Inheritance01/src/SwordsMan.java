public class SwordsMan extends Role {

    public SwordsMan(String name, int attackPower, int health) {
        super(name, health, attackPower);
    }

    //攻擊對手
    public void attack(SwordsMan opponent) {
        System.out.println(this.getName() +"攻擊" +opponent.getName() + "，造成" + this.getAttackPower() + "點傷害");
         opponent.setHealth(opponent.getHealth() - this.getAttackPower());
    }
}//End of SwordsMan
