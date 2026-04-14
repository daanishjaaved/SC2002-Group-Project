import Boundary.CLIView;
import Boundary.GameUI;
import Control.GameController;

public class Main {
  public static void main(String[] args) {
    GameUI ui = new CLIView();
    GameController controller = new GameController(ui);
    controller.startGame();
  }
}
