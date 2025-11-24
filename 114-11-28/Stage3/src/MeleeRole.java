public abstract class MeleeRole extends Role {
        private int armor;  // 護甲值：近戰角色特有

        // 具體方法：計算防禦
        public int calculateDefense(int incomingDamage) {
            return Math.max(0, incomingDamage - armor);
        }

        // 抽象方法：取得武器類型
        public abstract String getWeaponType();

        // 抽象方法：近戰特殊準備
        protected abstract void onMeleePrepare();
    // Role 是抽象類別
    public abstract class Role {
        public abstract void attack(Role opponent);
        public abstract void onDeath();
    }

 }

