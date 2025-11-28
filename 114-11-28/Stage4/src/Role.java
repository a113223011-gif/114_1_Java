// 第二階段和第三階段完全相同
public abstract class Role {
    private String name;
    private int health;
    private int attackPower;
    private int maxHealth; // 新增：最大生命值

    public Role(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.maxHealth = health; // 初始化最大生命值為當前生命值
    }

    // getter/setter 方法
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getAttackPower() { return attackPower; }
    public void setHealth(int health) { this.health = health; }
    public boolean isAlive() { return health > 0; }

    // 新增：取得最大生命值
    public int getMaxHealth() { return maxHealth; }

    // 具體方法
    public void takeDamage(int damage) {
        this.health -= damage;
        System.out.println("💥 " + name + " 受到 " + damage + " 點傷害！目前生命值：" + health);
        if (!isAlive()) {
            onDeath();
        }
    }

    // 抽象方法
    public abstract void attack(Role opponent);
    public abstract void showSpecialSkill();
    public abstract void onDeath();
    public abstract void prepareBattle();
    public abstract void afterBattle();

    @Override
    public String toString() {
        return "角色名稱: " + name + ", 生命值: " + health;
    }
}