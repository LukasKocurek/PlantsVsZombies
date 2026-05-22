package sk.fri.uniza.pvz.renderers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sk.fri.uniza.pvz.player.SunCount;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sk.fri.uniza.pvz.gamelogic.WaveManager;
import sk.fri.uniza.pvz.utils.Constants;
import sk.fri.uniza.pvz.utils.ImageLoader;


/**
 * Zobrazuje hracske rozhranie - pocet slnka, aktualnu vlnu zombikov
 * a zostavajuci cas
 * Okrem trvalych ukazovatelov dokaze zobrazit aj docasne upozornenie
 * napr. pri pokuse o zasadenie rastliny, ktoru si hrac nemoze dovolit kupit.
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class HudRenderer {
    private static final double START_X = 700;
    private static final double LABEL_Y_OFFSET = 530;
    private static final double VALUE_SPACING = 30;

    private final SunCount sunCount;
    private final WaveManager waveManager;

    private final Text sunText;
    private final Text waveText;
    private final Text timeText;
    private final Text warningText;
    private int warningTextFramesLeft;

    /**
     * Vytvori HUD a prida vsetky textove komponenty do zadaneho okna.
     *
     * @param sunCount zdroj informacii o pocte slnka
     * @param waveManager zdroj informacii o aktualnej vlne a zostavajucom case
     * @param root JavaFX kontajner, do ktoreho sa pridaju textove komponenty
     */
    public HudRenderer(SunCount sunCount, WaveManager waveManager, Pane root) {
        this.sunCount = sunCount;
        this.waveManager = waveManager;

        ImageView sunSprite = ImageLoader.getImageView("/others/sun.png");
        sunSprite.setFitWidth(20);
        sunSprite.setFitHeight(20);
        sunSprite.setY(LABEL_Y_OFFSET - 15);
        sunSprite.setX(START_X + 110);

        ImageView pvzLogo = ImageLoader.getImageView("/others/pvzLogo.png");
        pvzLogo.setFitWidth(100);
        pvzLogo.setFitHeight(100);
        pvzLogo.setY(LABEL_Y_OFFSET - 32);
        pvzLogo.setX(550);

        ImageView deskBoard = ImageLoader.getImageView("/others/deskBoard.png");
        deskBoard.setFitWidth(200);
        deskBoard.setFitHeight(100);
        deskBoard.setY(LABEL_Y_OFFSET - 30);
        deskBoard.setX(670);


        Font font = Font.font("Berlin Sans FB Demi", 18);

        this.sunText = new Text();
        this.sunText.setFont(font);
        this.sunText.setFill(Color.BLACK);
        this.sunText.setX(START_X);
        this.sunText.setY(LABEL_Y_OFFSET);

        this.waveText = new Text();
        this.waveText.setFont(font);
        this.waveText.setFill(Color.BLACK);
        this.waveText.setX(START_X);
        this.waveText.setY(LABEL_Y_OFFSET + VALUE_SPACING);

        this.timeText = new Text();
        this.timeText.setFont(font);
        this.timeText.setFill(Color.BLACK);
        this.timeText.setX(START_X);
        this.timeText.setY(LABEL_Y_OFFSET + VALUE_SPACING * 2);

        root.getChildren().add(deskBoard);
        root.getChildren().add(this.sunText);
        root.getChildren().add(this.waveText);
        root.getChildren().add(this.timeText);
        root.getChildren().add(sunSprite);
        root.getChildren().add(pvzLogo);

        this.warningText = new Text();
        this.warningText.setFont(Font.font("Arial", 24));
        this.warningText.setFill(Color.RED);
        this.warningText.setX(350);
        this.warningText.setY(50);
        root.getChildren().add(this.warningText);

        this.warningTextFramesLeft = 0;
    }

    /**
     * Nastavuje text varovani na zadanu hodnotu
     * Nastavi si cas, ako dlho bude viditelna
     * @param warning varovacia sprava, ktoru si nastavi atribut warningText
     */
    public void showWarning(String warning) {
        this.warningText.setText(warning);
        this.warningTextFramesLeft = Constants.countTime(1) / 2;
    }

    /**
     * Aktualizuje zobrazene hodnoty (pocet slniecok, vlna, cas) na zaklade aktualneho
     * Stavu hry. Tiez zabezpecuje zmiznutie docasneho upozornenia po uplynuti
     * jeho casu.
     */
    public void update() {
        this.sunText.setText("Sun count: " + this.sunCount.getCurrent());
        this.waveText.setText("Wave: " + this.waveManager.getCurrentWave() + "/" + this.waveManager.getNumberOfWaves());
        this.timeText.setText("Time: " + this.waveManager.getRemainingSeconds() + " s");

        if (this.warningTextFramesLeft > 0) {
            this.warningTextFramesLeft--;
            if (this.warningTextFramesLeft == 0) {
                this.warningText.setText("");
            }
        }
    }
}
