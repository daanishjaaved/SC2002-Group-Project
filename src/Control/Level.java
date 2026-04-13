package Control;

import Entity.Enemy;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Level {
    private Difficulty diff;  // Difficulty of level
    private String desc;      // Description of level
    private List<Enemy> initialSpawn; // List of initial wave of enemies
    private List<Enemy> backupSpawn; // List of backup wave of enemies
    private boolean backupSpawned; // Tracks if backup wave has already spawned

    public Level(Difficulty diff, String desc, List<Enemy> initialSpawn, List<Enemy> backupSpawn) {
        this.diff = diff; 
        this.desc = desc;
        this.initialSpawn = initialSpawn;
        this.backupSpawn = Objects.requireNonNullElse(backupSpawn, Collections.emptyList()); //If backupSpawn is not null, use it. Else replace it with empty list
        this.backupSpawned = false;
    }

    
    public Difficulty getDiff() {   // Returns difficulty of level
        return diff; 
    }
    
    public String getDesc() {   // Returns description of level
        return desc; 
    }
    
    public List<Enemy> getInitialSpawn() {  // Returns list of enemies in initial wave, but as an unmodifiable list
        return Collections.unmodifiableList(initialSpawn); 
    }
    
    public List<Enemy> getBackupSpawn() {   // Returns list of enemies in backup wave, but as an unmodifiable list
        return Collections.unmodifiableList(backupSpawn); 
    }
    
    public boolean isBackupSpawned() {  // Returns boolean value to check if backup wave has already been triggered
        return backupSpawned; 
    }

    public boolean hasBackupSpawn() {  // Returns boolean value to check if the level has backup wave
        return !backupSpawn.isEmpty();    
    }

    public void triggerBackupSpawn() {  // Updates "backupSpawned" to "true" to indicate that enemies in backup wave have already been spawned
        this.backupSpawned = true;
    }
}
