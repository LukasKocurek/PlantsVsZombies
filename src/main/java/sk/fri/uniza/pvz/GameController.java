package sk.fri.uniza.pvz;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sk.fri.uniza.pvz.gamelogic.EntityManager;
import sk.fri.uniza.pvz.gamelogic.GameState;
import sk.fri.uniza.pvz.gamelogic.WaveManager;
import sk.fri.uniza.pvz.gamelogic.GameStateManager;
import sk.fri.uniza.pvz.inputs.InputHandler;
import sk.fri.uniza.pvz.map.Map;
import sk.fri.uniza.pvz.menu.MainMenu;
import sk.fri.uniza.pvz.player.Inventory;
import sk.fri.uniza.pvz.player.SunCount;
import sk.fri.uniza.pvz.renderers.DynamicRenderer;
import sk.fri.uniza.pvz.renderers.HudRenderer;
import sk.fri.uniza.pvz.renderers.StaticRenderer;
import sk.fri.uniza.pvz.utils.CollisionManager;
import sk.fri.uniza.pvz.utils.Constants;

/**
 * Vstupny bod aplikacie a koren hry.
 * Spravuje prechod medzi hlavnym menu a samotnou hrou:
 * - pri spusteni aplikacie sa zobrazi hlavne menu
 * - po stlaceni tlacidla hrat sa zacne nova hra
 * - po skonceni hry (vyhra alebo prehra) sa hrac vracia do menu so spravou o vysledku
 * Trieda vytvara a prepaja vsetky herne komponenty (mapa, manageri,
 * renderery, vstupne udalosti) a riadi hlavnu hernu slucku cez AnimationTimer
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class GameController extends Application {
    private Stage stage;
    private AnimationTimer animationTimer;


    /**
     * Inicializuje hlavne okno a zobrazi hlavne menu. Zvysok hry sa spusta az po stlaceni tlacidla
     * hrat v menu.
     * @param stage hlavne okno aplikacie poskytnute JavaFX-om
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setResizable(false);
        this.showMenu(null);
        this.stage.show();
    }

    private void showMenu(GameState previousResult) {
        if (this.animationTimer != null) {
            this.animationTimer.stop();
            this.animationTimer = null;
        }

        MainMenu menu = new MainMenu();
        menu.setResult(previousResult);

        menu.setOnPlay(new Runnable() {
            @Override
            public void run() {
                GameController.this.startGame();
            }
        });

        this.stage.setScene(menu.getScene());
    }

    private void startGame() {
        Pane root = new Pane();
        Map gameMap = new Map(5, 9);
        SunCount sunCount = new SunCount();
        EntityManager entityManager = new EntityManager(gameMap, sunCount);
        Inventory inventory = new Inventory();
        WaveManager waveManager = new WaveManager(entityManager, gameMap.getNumberOfRows(), gameMap.getNumberOfCols());

        StaticRenderer staticRenderer = new StaticRenderer(gameMap, root, inventory);
        DynamicRenderer dynamicRenderer = new DynamicRenderer(root, entityManager);
        CollisionManager collisionManager = new CollisionManager();

        staticRenderer.renderMap();
        staticRenderer.renderInventory();

        HudRenderer hudRenderer = new HudRenderer(sunCount, waveManager, root);
        InputHandler inputHandler = new InputHandler(inventory, sunCount, gameMap, entityManager, hudRenderer);
        GameStateManager gameStateManager = new GameStateManager(entityManager, waveManager);

        root.setOnMouseClicked(e -> {
            if (gameStateManager.isRunning()) {
                inputHandler.handleClick(e.getX(), e.getY());
            }
        });

        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameStateManager.isRunning()) {
                    waveManager.update();
                    entityManager.updateAll();
                    collisionManager.checkCollisions(entityManager);
                    sunCount.update();
                    inventory.update();
                }
                gameStateManager.update();
                hudRenderer.update();
                dynamicRenderer.render();
                entityManager.removeAllDead();
                if (!gameStateManager.isRunning()) {
                    GameController.this.showMenu(gameStateManager.getState());
                }
            }
        };
        this.animationTimer.start();

        this.stage.setScene(new Scene(root, Constants.getResolutionX(), Constants.getResolutionY()));
    }
}