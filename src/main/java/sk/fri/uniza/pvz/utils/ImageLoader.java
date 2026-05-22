package sk.fri.uniza.pvz.utils;

import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.Objects;

/**
 * Pomocna trieda na nacitavanie obrazkov zo zdrojov projektu.
 * @author (Lukas Kocurek)
 * @version (17.05.2026)
 */
public class ImageLoader {
    private ImageLoader() {

    }

    /**
     * @param path cesta k obrazku
     * @return vrati obrazok podla adresy typu ImageView
     */
    public static ImageView getImageView(String path) {
        String imagePath = url(path);
        return new ImageView(imagePath);
    }

    /**
     * Touto metodou som si POMOHOL StackOverflowom, NIE JE 100% MOJA!
     * <a href="https://stackoverflow.com/questions/2593154/get-a-resource-using-getresource">...</a>
     * @param path cesta k obrazku
     * @return url typu String
     */
    private static String url(String path) {
        URL resource = ImageLoader.class.getResource(path);
        return Objects.requireNonNull(resource, "Image not found at path " + path).toExternalForm();
    }
}
