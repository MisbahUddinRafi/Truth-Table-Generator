package controllers;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.util.Duration;
import utilities.Main;

public class AboutUsController {

    public void goToMain() {
        Parent root = Main.primaryStage.getScene().getRoot();

        TranslateTransition slide = new TranslateTransition(Duration.millis(300), root);
        slide.setFromY(0);
        slide.setToY(600);   // slide downward

        slide.setOnFinished(e -> Main.goBack());
        slide.play();
    }

}
