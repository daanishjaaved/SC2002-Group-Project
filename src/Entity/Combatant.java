package Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for all entities in battle
 * Handles core stats, life cycle, and status effect management
 */
public abstract class Combatant {
    private String name;
    private int hp;
    private int maxHp;
    private int atk;
    private int def;
    private int spd;
    private List<StatusEffect> statusEffects;
    private boolean alive;

    public Combatant(String name, int hp, int atk, int def, int spd) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
        this.statusEffects = new ArrayList<>();
        this.alive = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    // Set HP between 0 and maxHp, then updates alive status
    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp)); 
        this.alive = this.hp > 0; 
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public boolean isAlive() {
        return alive;
    }

    public List<StatusEffect> getStatusEffects() {
        return Collections.unmodifiableList(statusEffects);
    }

    public void takeDmg(int atk) {
        // Smoke Bomb Invul
        if (hasDmgNegation()){
            return;
        }
        // Calculate final damage after defense reduction minimum 0
        int dmg = Math.max(0, atk - this.def); 
        setHp(this.hp - dmg);
    }

    public void heal(int amount) {
        setHp(this.hp + amount);
    }

    // for effects like smoke bomb
    public boolean hasDmgNegation() {
        for (StatusEffect effect : statusEffects) {
            if (effect.negateDmg()) { 
                return true;
            }
        }
        return false;
    }

    public void addStatusEffect(StatusEffect effect) {
        this.statusEffects.add(effect); 
        effect.onApply(this);
    }

    public void removeStatusEffect(StatusEffect effect) {
        this.statusEffects.remove(effect);
    }

    public void applyStatusEffects() {
        // Iterate backwards to safely remove expired effects without shifting indices
        for (int i = statusEffects.size() - 1; i >= 0; i--) {
            StatusEffect effect = statusEffects.get(i);
            effect.reduceDuration();
            if (effect.isExpired()) {
                effect.onRemove(this); 
                statusEffects.remove(i);
            }
        }
    }

    // for shield bash
    public boolean isStunned() {
        for (StatusEffect effect : statusEffects) {
            if (effect.preventAction()) { 
                return true;
            }
        }
        return false;
    }
}