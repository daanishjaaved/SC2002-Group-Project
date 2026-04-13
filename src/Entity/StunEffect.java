package Entity;

public class StunEffect extends StatusEffect {

  /**
     * Creates a new Stun effect.
     * duration is the number of turns the entity will be unable to act.
     */
  public StunEffect(int duration) {
    super("Stun", duration);
  }

  /**
     * Overrides the base behaviour to stop the entity from acting.
     * The game loop should check this method before allowing a Combatant to choose a move.
     * return true, as the nature of being stunned is to lose a turn.
     */
  @Override
  public boolean preventAction() {
    return true;
  }
}
