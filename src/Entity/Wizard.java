package Entity;

/**
 * Wizard Player
 */
public class Wizard extends Player {
    private Action specialSkill;
    private int bonusAtk; // Accumulated attack power from kills

    public Wizard() {
        // Stats: Name, HP, ATK, DEF, SPD
        super("Wizard", 200, 50, 10, 20);
        this.specialSkill = new ArcaneBlast(this);
        this.bonusAtk = 0;

    }

    public int getBonusAtk() {
        return bonusAtk;
    }

    public void addBonusAtk(int amount) {
        bonusAtk += amount;
    }

    public void resetBonusAtk() {
        bonusAtk = 0;
    }

    // Overrides the base attack to include the kill bonus
    @Override
    public int getAtk() {
        return super.getAtk() + bonusAtk;
    }

    @Override
    public Action getSpecialSkill() {
        return specialSkill;
    }

    // increases total attack power when an enemy is killed
    @Override
    public void onKill() {
        addBonusAtk(10);
    }
}
