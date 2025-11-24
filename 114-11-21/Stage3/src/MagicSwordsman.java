public abstract class MagicSwordsman extends MeleeRole {
    private MagicAbility magic;  // 組合魔法能力

    public MagicSwordsman(String name, int health, int attackPower) {
        super(name, health, attackPower);
    }

    public void castSpell(Role target) {
        magic.cast(target);
    }
}