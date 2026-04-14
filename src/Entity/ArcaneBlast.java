package Entity;

import java.util.List;

public class ArcaneBlast extends SpecialSkill {

  // Constructor
  public ArcaneBlast(Player player) {
    super(player);
  }

  // return skill name
  @Override
  public String getName() {
    return "Arcane Blast";
  }

  // return skill description
  @Override
  public String getDesc(Combatant target) {
    return target.getAtk() + "dmg dealt to all enemies and gain 10 Attack for each enemy defeated by Arcane Blast. (Cooldown: 3 turns)";
  }

  @Override
  public void execute(Combatant player, List<Combatant> targets) {
    for (Combatant target : targets) {
      // check alive before dealing dmg
      boolean targetWasAlive = target.isAlive(); // store state before dmg
      target.takeDmg(player.getAtk());

      if (targetWasAlive && !target.isAlive()) { // False: no kill
        caster.onKill(); // wiz gains +10 ATK for each kill
      }
    }
    triggerCooldown(); // Put skill on cooldown after execution
  }

  @Override
  public boolean targetSelection() { // No manual target selection (hits all enemies automatically)
    return false;
  }

  @Override
  public boolean isAOE() { // This skill is an AOE skill
    return true;
  }
}
