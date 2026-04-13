package Control;

import Boundary.GameUI;
import Entity.Action;
import Entity.BasicAttack;
import Entity.Combatant;
import Entity.Defend;
import Entity.Enemy;
import Entity.Item;
import Entity.ItemAction;
import Entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Controls battle logic
public class BattleEngine {
    private GameUI gameUI;
    private Player player;
    private List<Enemy> enemies;
    private Map<String, Integer> enemyCount = new HashMap<>();   // Used to label enemies uniquely (e.g Goblin A, Goblin B)
    private Level level;
    private TurnOrderStrategy turnOrder;
    private int currentRound;
    private boolean isBattleOver;    

    /*
        Constructor 
     */
    public BattleEngine(Player player, Level level, TurnOrderStrategy turnOrder, GameUI gameUI) {
        this.player = player;
        this.level = level;
        this.turnOrder = turnOrder;
        this.gameUI = gameUI;
        this.enemies = new ArrayList<>(level.getInitialSpawn());    // Copies initial enemy from level into battle's own enemy list, Battle only starts with initial wave
        labelEnemy(this.enemies);                                   // Renames to distinguish duplicates
        this.currentRound = 0;
        this.isBattleOver = false;
    }

    /*
        As long as battle not over, start another round
     */
    public void startBattle() {
        while (!isBattleOver) {
            startRound();
        }
    }

    /*
        * Runs 1 full round
    */
    public void startRound() {
        currentRound++;                                                                 
        gameUI.displayRoundStart(currentRound);                                         
        List<Combatant> aliveCombatants = getAliveCombatants();                         
        List<Combatant> turnSequence = turnOrder.determineTurnOrder(aliveCombatants);   
        gameUI.displayTurnOrder(turnSequence);                                          

        // For each combatant based on turn order, run their turn, check if battle ended. set isBattleOver to true and return early. If not, display end of round message
        for (Combatant activeCombatant : turnSequence) {                                                                                                     
            startTurn(activeCombatant);                                                 
            if (checkWinLoseCondition()) {                                              
                isBattleOver = true;                                                    
                return;                                                                 
            }
        }

        gameUI.displayRoundEnd(player, enemies, currentRound);                          
    }
    
    // Logic for start of turn
    private void startTurn(Combatant activeCombatant) {
        activeCombatant.applyStatusEffects();                                                        

        if (!activeCombatant.isAlive()) {                                                            
            return;
        }

        if (activeCombatant.isStunned()) {                                                             
            gameUI.displayActionResult(activeCombatant.getName() + " is STUNNED -> turn skipped");
            return;
        }

        // If combatant is player, reduce cooldown, show display for player's turn then let game flow depending on player's choice
        if (activeCombatant instanceof Player) {                                              
            ((Player) activeCombatant).reduceCooldown();                                              
            gameUI.displayPlayerTurn((Player) activeCombatant);                                       
            startPlayerTurn((Player) activeCombatant);                                                 
        } 
        else if (activeCombatant instanceof Enemy) {                                                 
            startEnemyTurn((Enemy) activeCombatant);                                                   
        }
    }

    /*
    Logic for player's turn
    */
    private void startPlayerTurn(Player player) {                                                  
        while (true) {                                                                              
            List<Action> availableActions = availableActions(player);                              
            int choice = gameUI.getActionChoice(availableActions);                                   
            Action selectedAction = availableActions.get(choice);                                     

            if (selectedAction.getName().equals("Use Item")) {                              
                selectedAction = selectItem(player);                                                    
                if (selectedAction == null) {
                    continue;
                }
            }

            List<Combatant> selectedTarget = chooseActionTarget(selectedAction);                      // Target selection. If cancelled, display turn menu again
            if (selectedTarget == null) {
                continue;
            }

            String targetNames = selectedAction.isAOE() ? "All Enemies" :                                                 // Chooses how to display name of target(s)
                               (selectedTarget.isEmpty() ? player.getName() : selectedTarget.get(0).getName());
            
            String actionResult = String.format("%-10s -> %-12s -> %-10s", 
                               player.getName(), selectedAction.getName(), targetNames);

            Map<Combatant, Integer> preHp = new HashMap<>();                               

            preHp.put(player, player.getHp());

            for (Enemy e : enemies) {
                preHp.put(e, e.getHp());
            }

            selectedAction.execute(player, selectedTarget);

            if (selectedAction.isAOE()) {                    
                gameUI.displayActionResult(actionResult + " | All Targets Hit");                  // Display message For AOE action
            } 
            else if (!selectedTarget.isEmpty()) {                                                 // Display message For single-target action
                Combatant t = selectedTarget.get(0);
                int dmg = preHp.get(t) - t.getHp();
                String hpChange = preHp.get(t) + " -> " + (t.isAlive() ? t.getHp() : "X");
                gameUI.displayActionResult(actionResult + " | dmg: " + (player.getAtk() - t.getDef()) + " = " + dmg + " | HP: " + hpChange);
            } 
            else {                                                 // Self/no-target action (e.g healing, defend)
                int heal = player.getHp() - preHp.get(player);
                String result = (heal > 0) ? "+" + heal + " HP" : "Potion Used";
                gameUI.displayActionResult(actionResult + " | " + result + " | HP: " + preHp.get(player) + " -> " + player.getHp());
            }
            break;
        }
    }

    // player available actions
    private List<Action> availableActions(Player player) {
        List<Action> actionOptions = new ArrayList<>();
        actionOptions.add(new BasicAttack());
        actionOptions.add(new Defend());

        if (player.hasItems()) {
            actionOptions.add(new ItemAction(null));
        }

        Action specialSkill = player.getSpecialSkill();
        if (specialSkill.isAvail(player)) {
            actionOptions.add(specialSkill);
        }

        return actionOptions;
    }

    // user items 
    private ItemAction selectItem(Player player) {
        List<Item> usableItems = new ArrayList<>();

        for (Item inventoryItem : player.getInventory()) {
            if (!inventoryItem.isConsumed()) {
                usableItems.add(inventoryItem);
            }
        }

        int selectedItem = gameUI.getItemUseChoice(usableItems);

        if (selectedItem == -1) {
            return null;
        }

        return new ItemAction(usableItems.get(selectedItem));
    }

    // choose target 
    private List<Combatant> chooseActionTarget(Action selectedAction) {
        // if item
        if (selectedAction instanceof ItemAction) {
            Action item = ((ItemAction) selectedAction).getTarget(player);
            if (item != null) {
                return chooseActionTarget(item);
            }
        }

        List<Combatant> chosenTargets = new ArrayList<>();

        // multiple target
        if (selectedAction.isAOE()) {
            chosenTargets.addAll(getAliveEnemies());
        } 
        // single target
        else if (selectedAction.targetSelection()) {
            List<Enemy> aliveEnemies = getAliveEnemies();
            int targetChoice = gameUI.getTargetChoice(aliveEnemies);
            
            if (targetChoice == -1) {
                return null;
            }

            chosenTargets.add(aliveEnemies.get(targetChoice));
        }
        // Default Case (Empty list means self targeting or no target actions like Defend)
        return chosenTargets;
    }

    private void startEnemyTurn(Enemy enemy) {
        int initialPlayerHp = player.getHp();
        boolean isDmgNegated = player.hasDmgNegation();
        
        enemy.doAction(player);

        int dmg = initialPlayerHp - player.getHp();
        gameUI.displayEnemyAttack(enemy, player, dmg, isDmgNegated);
    }

    // for labelling enemies A,B,C etc
    private void labelEnemy(List<Enemy> enemies) {
        for (Enemy e : enemies) {
            String name = e.getName();
            int currentCount = enemyCount.getOrDefault(name, 0);
            e.setName(name + " " + (char) ('A' + currentCount));
            enemyCount.put(name, currentCount + 1);
        }
    }

    private void spawnBackupEnemies() {
        level.triggerBackupSpawn();
        List<Enemy> backupEnemies = level.getBackupSpawn();
        enemies.addAll(backupEnemies);
        labelEnemy(backupEnemies);
        gameUI.displayActionResult("Backup Spawn Triggered!");
    }

    private List<Combatant> getAliveCombatants() {
        List<Combatant> all = new ArrayList<>();
        if (player.isAlive()) {
            all.add(player);
        }
        all.addAll(getAliveEnemies());
        return all;
    }
    
    private List<Enemy> getAliveEnemies() {
        return enemies.stream().filter(Enemy::isAlive).toList();
    }

    private boolean enemiesDefeated() {
        return getAliveEnemies().isEmpty();
    }

    public Player getPlayer() { 
        return player; 
    }

    public List<Enemy> getEnemies() { 
        return Collections.unmodifiableList(enemies); 
    }

    public boolean isPlayerVictory() {
        return player.isAlive() && enemiesDefeated();
    }

    public int getRoundCount() { 
        return currentRound; 
    }

    private boolean checkWinLoseCondition() {
        if (!player.isAlive()) {
            return true;
        }

        if (enemiesDefeated()) {
            if (level.hasBackupSpawn() && !level.isBackupSpawned()) {
                spawnBackupEnemies();
                return false;
            }
            return true;
        }

        return false;
    }
}
