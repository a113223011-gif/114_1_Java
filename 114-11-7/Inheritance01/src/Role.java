public class Role extends Object {
    //角色名稱
    private String name;
    //生命值
    private int health;
    //攻擊力
    private int attackPower;

    public Role(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    //設定生命值
    public void setHealth(int health) {
        this.health = health;
    }

    //設定攻擊力
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    //判斷是否存活
    public boolean isAlive() {
        return health > 0;
    }
    @Override
    public String toString() {
        return "角色名稱: " + name + "，生命值: " + health + "，攻擊力: " + attackPower;
    }
}
