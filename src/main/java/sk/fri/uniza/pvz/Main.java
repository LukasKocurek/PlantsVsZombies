package sk.fri.uniza.pvz;

import javafx.application.Application;
import sk.fri.uniza.pvz.utils.Constants;

/**
 * Main, spusti hru, vypne vsync, zamkne FPS na urcitu hodnotu
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class Main {
    /**
     * Stara sa o spustenie hry a zamknute FPS
     * @param args argumenty
     */
    public static void main(String[] args) {
        System.setProperty("prism.vsync", "false");
        System.setProperty("javafx.animation.framerate", String.valueOf(Constants.getFps()));

        Application.launch(GameController.class, args);
    }
}
