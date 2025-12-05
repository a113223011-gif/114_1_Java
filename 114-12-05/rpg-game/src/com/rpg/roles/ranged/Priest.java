package com.rpg.roles.ranged;
import com.rpg.interfaces.Healable;
public abstract class Priest extends RangedRole implements Healable {
    private int blessPower;

    public Priest(String name, int health, int attackPower,
                  int blessPower, int range, int maxEnergy) {
        super(name, health, attackPower, range, maxEnergy);
        this.blessPower = blessPower;
    }

    @Override
    public String getRangedAttackType() {
        return "神聖之光";
    }
}
