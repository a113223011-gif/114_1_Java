public class MagicSwordsman extends MeleeRole {
    private MagicAbility magic;  // 組合魔法能力

    public void castSpell(Role target) {
        magic.cast(target);
    }
}