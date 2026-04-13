package Control;

import Boundary.GameUI;
import Entity.Player;
import Entity.Warrior;
import Entity.Wizard;
import Entity.Enemy;
import Entity.Goblin;
import Entity.Wolf;

import java.util.Arrays;
import java.util.List;

public class GameController {
    private GameUI UI;                     // UI: User interface object
    private BattleEngine battleEngine;     // BattleEngine object
    private int playerChoice;              // Stores Player's chosen class
    private List<Integer> itemChoice;      // Stores chosen items
    private Difficulty difficultyChoice;   // Stores chosen difficulty level

    /*
        Contructor for GameController
        Stores GameUI object as UI attribute
    */
    public GameController(GameUI UI) {
        this.UI = UI;
    }

    /*
        * Starts the game
        * As long as start = true, game will keep running
    */
    public void startGame() {
        boolean start = true;
        while (start) {
            
            UI.displayLoadingScreen(                                    // Displays loading screen which shows list of players and enemies
                Arrays.asList(new Warrior(), new Wizard()),
                Arrays.asList((Enemy) new Goblin(), new Wolf())
            );

            playerChoice = choosePlayer();                              // Allows player to choose class
            Player player = PlayerManager.choosePlayer(playerChoice);  

            itemChoice = chooseItems();                                 // Allows player to choose 2 items
            for (int index : itemChoice) {
                player.addItem(ItemManager.chooseItem(index));
            }

            difficultyChoice = chooseDifficulty();                      // Allows player to choose difficulty
            startRound(player, difficultyChoice);
            start = handleReplay();
        }
    }

    /*
        *Begins battle using chosen Player class and difficulty
    */
    private void startRound(Player player, Difficulty difficulty) {
        Level level = LevelManager.chooseLevel(difficulty);                // Set difficulty level
        TurnOrderStrategy strategy = new SpeedTurnOrder();                 // Set turn order based on speed
        battleEngine = new BattleEngine(player, level, strategy, UI);      
        battleEngine.startBattle();                                        // Start battle sequence
        showResults();
    }

    /*
        * Shows all playable classes and lets player choose
    */
    private int choosePlayer() {                                                  
        List<String> playerTypes = Arrays.asList("Warrior", "Wizard");
        return UI.getPlayerChoice(playerTypes);
    }

    /*
        * Shows all usable items and lets player choose
    */
    private List<Integer> chooseItems() {
        List<String> items = Arrays.asList("Potion", "Power Stone", "Smoke Bomb");
        
        UI.chooseInitialItems(1, 2);
        int choice1 = UI.getItemChoice(items);

        UI.chooseInitialItems(2, 2);
        int choice2 = UI.getItemChoice(items);
        
        return Arrays.asList(choice1, choice2);
    }

    /*
        * Shows all difficulty levels and lets player choose
    */
    private Difficulty chooseDifficulty() {
        Difficulty[] values = Difficulty.values();
        
        List<String> descriptions = Arrays.stream(values)
                                        .map(Difficulty::getDesc)
                                        .toList();

        int choice = UI.getDifficultyChoice(descriptions);
        
        return (choice >= 0 && choice < values.length) ? values[choice] : Difficulty.EASY;
    }

    /*
        * Show victory/loss text
    */
    private void showResults() {
        if (battleEngine.isPlayerVictory()) {
            UI.displayVictory(battleEngine.getPlayer(), battleEngine.getRoundCount());
        } else {
            UI.displayDefeat(battleEngine.getEnemies(), battleEngine.getRoundCount());
        }
    }

    /*
        * After game ended, allow player to choose to replay same setup, start a new game or exit
        * 1: Replay game with same class, difficulty and items
        * 2: Start a new game and able to choose class, difficulty and items again
        * 3: Exit game
    */
    private boolean handleReplay() {
        while (true) {
            int choice = UI.getReplayChoice();
            return switch (choice) {
                case 1 -> {
                    Player player = PlayerManager.choosePlayer(playerChoice);
                    for (int index : itemChoice) {
                        player.addItem(ItemManager.chooseItem(index));
                    }
                    startRound(player, difficultyChoice);
                    yield true;
                }
                case 2 -> true;
                default -> {
                    UI.displayExitMessage();
                    yield false;
                }
            };
        }
    }
}
