public abstract class RangedRole extends Role {
    private int range;      // 攻擊範圍
    private int energy;     // 能量值
    private int maxEnergy;

    // 具體方法：檢查射程
    public boolean isInRange(int distance) {
        return distance <= range;
    }

    // 具體方法：能量管理
    public boolean consumeEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            return true;
        }
        return false;
    }

    // 抽象方法：取得攻擊類型
    public abstract String getRangedAttackType();
}
