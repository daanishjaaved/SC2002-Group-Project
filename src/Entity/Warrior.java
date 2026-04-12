package Entity;

/**
 * Warrior Player
 */
public class Warrior extends Player {
    private Action specialSkill;

    public Warrior() {
        // Stats: Name, HP, ATK, DEF, SPD
        super("Warrior", 260, 40, 20, 30);
        this.specialSkill = new ShieldBash(this);
    }

    @Override
    public Action getSpecialSkill() {
        return specialSkill;
    }
}
