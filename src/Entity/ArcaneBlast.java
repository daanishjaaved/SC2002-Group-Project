package Entity;

import java.util.List;

// Skill: Wizard
// Special Skill: deal BasicAttack dmg to all enemies
// grant 10 ATK for rest of battle after each kill
// Cooldown: 3 turns

public class ArcaneBlast extends SpecialSkill {

  public ArcaneBlast(Player owner) {
    super(owner);
  }

  @Override
  public String getName() {
    return "Arcane Blast";
  }

  @Override
  public String getDesc(Combatant performer) {
    return performer.getAtk() + "dmg is dealt to all enemies! 10 ATk is granted! (Cooldown: 3 turns)";
  }

  @Override
  public void execute(Combatant performer, List<Combatant> targets) {
    for (Combatant target : targets) {
      // check alive before dealing dmg
      boolean targetWasAlive = target.isAlive(); // store state before dmg
      target.takeDmg(performer.getAtk());

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
  public boolean isAreaOfEffect() { // This skill is an AOE skill
    return true;
  }
}
