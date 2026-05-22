package sk.fri.uniza.pvz.menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sk.fri.uniza.pvz.gamelogic.GameState;
import sk.fri.uniza.pvz.utils.Constants;
import sk.fri.uniza.pvz.utils.ImageLoader;

/**
 * Trieda predstavuje hlavne menu, ktore sa otvori po spusteni hry
 * Taktiez sa otvori pri ukonceni hry s danym vypisom
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class MainMenu {
    private final Scene scene;
    private final Button playButton;
    private final Text resultText;

    /**
     * Inicializuje sa a nastavi si potrebne hodnoty
     */
    public MainMenu() {
        Pane root = new Pane();

        ImageView background = ImageLoader.getImageView("/menu/menupicture.jpg");

        this.resultText = new Text();
        this.resultText.setFont(Font.font("Algerian", 40));
        this.resultText.setX(350);
        this.resultText.setY(200);

        this.playButton = new Button("Play");
        this.playButton.setLayoutX(400);
        this.playButton.setLayoutY(400);
        this.playButton.setPrefSize(120, 60);

        root.getChildren().addAll(background, this.resultText, this.playButton);

        this.scene = new Scene(root, Constants.getResolutionX(), Constants.getResolutionY());
    }

    /**
     * Nastavi vysledok podla stavu hry, ktory dostane do parametra
     * Ak ziadny vylsedok neni nastavene (na zaciatku spustenia hry), nastavi sa ako text prazdny retazec
     * @param previousResult vysledok hry
     */
    public void setResult(GameState previousResult) {
        if (previousResult == GameState.WON) {
            this.resultText.setText("YOU WON!");
            this.resultText.setFill(Color.GOLD);
        } else if (previousResult == GameState.LOST) {
            this.resultText.setText("YOU LOST!");
            this.resultText.setFill(Color.RED);
        } else {
            this.resultText.setText("");
        }
    }

    /**
     * Nastavi akciu, ktora sa vykona po stlaceni tlacidla "Play".
     * Pouziva Runnable ako callback
     * @param play akcia, ktora sa vykona pri kliknuti na tlacidlo
     */
    public void setOnPlay(Runnable play) {
        this.playButton.setOnAction(e -> play.run());
    }

    /**
     * Vrati aktualnu scenu
     * @return scena typu Scene
     */
    public Scene getScene() {
        return this.scene;
    }
}