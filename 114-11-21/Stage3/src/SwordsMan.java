public  class SwordsMan extends Role{
    // 建構子：初始化劍士的名稱、生命值和攻擊力
    public SwordsMan(String name, int health, int attackPower) {
        super(name, health, attackPower);
    }

    // 攻擊對手(劍客/魔法師)，父類別的參考指到子類別物件
    @Override
    public void attack(Role opponent) {
        System.out.println("✈️" + this.getName() + " 揮劍攻擊 " + opponent.getName() + " ! " );
        opponent.takeDamage(this.getAttackPower());;
    }
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能        ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 技能名稱：連續斬擊          ║");
        System.out.println("║ 技能描述：快速揮劍三次      ║");
        System.out.println("║ 技能效果:造成 150% 傷害       ║");
        System.out.println("╚═════════════════════════════╝");
    }
    /**
     * 劍士的死亡效果
     * 劍士倒下時，劍會掉落在地上
     */
    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 倒下了...");
        System.out.println("⚔️  " + this.getName() + " 的劍掉落在地上，發出清脆的聲響。");
        System.out.println("---");
    }
    /**
     * 劍士的戰前準備
     * 擦拭劍刃，做好戰鬥準備
     */
    @Override
    public void prepareBattle() {
        System.out.println("🗡️  " + this.getName() + " 擦拭劍刃，劍身反射出凜冽的寒光...");
    }

    /**
     * 劍士的戰後行為
     * 將劍收入劍鞘
     */
    @Override
    public void afterBattle() {
        System.out.println("🗡️  " + this.getName() + " 將劍收入劍鞘。");
    }

    // SwordsMan.java
    @Override
    public void onLevelUp() {
        System.out.println("⚔️  劍技更加精湛！");
    }
}
