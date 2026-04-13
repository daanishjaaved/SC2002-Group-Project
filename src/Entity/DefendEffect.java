package Entity;

public class DefendEffect extends StatusEffect {
  private int defBonus;  // Amount of defence temporarily added to target

  /*
    * Constructor for DefendEffect
    * name: Name of effect (Defend)
    * duration: Number of turns effect lasts
    * set defBonus to 10
  */
  public DefendEffect(int duration) {
    super("Defend", duration);
    this.defBonus = 10;
  }

  /**
     * return defence bonus gained.
     */
  public int getDefBonus() {
    return defBonus;
  }

  /**
     * Increase target's defence by defBonus.
     */
  @Override
  public void onApply(Combatant target) {
    target.setDef(target.getDef() + defBonus);
  }

  /**
     * Removes temporarily gained defence from target
     */
  @Override
  public void onRemove(Combatant target) {
    target.setDef(target.getDef() - defBonus);
  }
}
