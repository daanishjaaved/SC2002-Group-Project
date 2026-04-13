package Control;
/**
 * Difficulty defines the preset difficulty levels for the game.
 * Each level contains a description that outlines the initial 
 * enemies and potential backup spawns.
 */
public enum Difficulty {
    /** Easy difficulty: Straightforward encounter with 3 basic enemies. */
    EASY("Easy - 3 Goblins"),
    /** Medium difficulty: Mixed enemy types and a moderate backup wave. */
    MEDIUM("Medium - 1 Goblin + 1 Wolf | Backup Spawn: 2 Wolves"),
    /** Hard difficulty: Stronger initial force and a large backup wave. */
    HARD("Hard - 2 Goblins | Backup Spawn: 1 Goblin + 2 Wolves");

    // The display string describing the encounter details
    private final String desc;

    /**
     * Internal constructor for the enum constants.
     * desc is the text associated with the difficulty level.
     */
    Difficulty(String desc) {
        this.desc = desc;
    }

    /**
     * return the formatted description of the encounter for UI display.
     */
    public String getDesc() {
        return desc;
    }
}