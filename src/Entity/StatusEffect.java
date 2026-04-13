package Entity;

public abstract class StatusEffect {
  private String name; // The display name of the effect
  private int duration; // How many turns/actions the effect remains active

  /**
     * Constructor for a StatusEffect.
     * name: The name of the status.
     * duration: The number of turns the effect should last.
     */
  public StatusEffect(String name, int duration) {
    this.name = name; 
    this.duration = duration; 
  }

  /**
     * return display name of this status effect.
     */
  public String getName() {
    return name;
  }

  /**
     * return The current number of turns remaining for this effect.
     */
  public int getDuration() {
    return duration;
  }

  /**
     * Decrements the duration by 1. Usually called at the end of a turn.
     * Ensures duration does not drop below zero.
     */
  public void reduceDuration() {
    if (duration > 0) {
      duration--;
    }
  }

  /**
     * Checks if the effect has run its course.
     * return true if duration is 0 or less, false otherwise.
     */
  public boolean isExpired() {
    return duration <= 0;
  }

  /**
     * Logic hook to determine if this status stops the entity from acting.
     * Overridden by effects like "Stun".
     * return Default is false (action not prevented).
     */
  public boolean preventAction() {
    return false;
  }

  /**
     * Logic hook to determine if this status prevents incoming damage.
     * Overridden by effects like "SmokeBombInvulnerability" or "Defend."
     * return Default is false (damage is not negated).
     */
  public boolean negateDmg() {
    return false;
  }

  /**
     * Triggered immediately when the effect is added to a Combatant.
     * target The Combatant receiving the status.
     */
  public void onApply(Combatant target) {}

  /**
     * Triggered when the effect expires or is cleared.
     * Used to revert stat changes or clean up logic.
     * target The Combatant losing the status.
     */
  public void onRemove(Combatant target) {}
}
