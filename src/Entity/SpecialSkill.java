package Entity;

public abstract class SpecialSkill implements Action {

    // The player who owns and uses this special skill
    protected final Player caster;

    // Number of turns required before the skill can be used again
    protected static final int DEFAULT_COOLDOWN = 3;

    protected SpecialSkill(Player caster) {
        this.caster = caster;
    }

    @Override
    public boolean isAvail(Combatant target) {
        // Skill is available only when cooldown reaches 0
        return caster.getSpecialSkillCooldown() == 0;
    }

    protected void triggerCooldown() {
        // Reset cooldown after skill is used
        caster.setSpecialSkillCooldown(DEFAULT_COOLDOWN);
    }
}