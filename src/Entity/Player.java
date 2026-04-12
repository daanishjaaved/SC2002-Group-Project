package Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the user combatant
 * Manages an inventory of items and a cooldown-based special skill
 */
public abstract class Player extends Combatant {
    private List<Item> inventory;
    private int specialSkillCooldown; // Number of turns until the special skill can be used again

    public Player(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
        this.inventory = new ArrayList<>();
        this.specialSkillCooldown = 0;
    }

    // returns a read-only view of the inventory to prevent external modification
    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    // Checks if the player has any usable (unconsumed) items left.
    public boolean hasItems() {
        for (Item item : inventory) {
            if (!item.isConsumed())
                return true;
        }
        return false;
    }

    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    public void setSpecialSkillCooldown(int cooldown) {
        this.specialSkillCooldown = cooldown;
    }

    // Progresses the skill cooldown timer
    public void reduceCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    // each player subclass must define its own unique special skill
    public abstract Action getSpecialSkill();

    // for arcane blast
    public void onKill() {} 
}
