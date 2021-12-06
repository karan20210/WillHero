package com.example.willherogui;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GUIController {

    @FXML
    private Pane game;
    @FXML
    private ImageView hero;
    @FXML
    private Pane pausemenu;
    @FXML
    private Label score;

    TranslateTransition jump, moveRight;
    boolean firstJump = true;
    boolean pauseMenuActive = false;


    @FXML
    protected void heroJump() {
        if (firstJump && !pauseMenuActive) {
            firstJump = false;
            hero.requestFocus();
            jump = new TranslateTransition();
            jump.setDuration(Duration.millis(1000));
            jump.setNode(hero);
            jump.setByY(-100);
            jump.setCycleCount(Animation.INDEFINITE);
            jump.setAutoReverse(true);
            jump.play();
        }

    }

    @FXML
    protected void onSpace(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE && !pauseMenuActive) {
            moveRight = new TranslateTransition();
            moveRight.setDuration(Duration.millis(100));
            moveRight.setNode(hero);
            moveRight.setByX(+100);
            moveRight.setCycleCount(1);
            moveRight.play();

            int s = Integer.valueOf(score.getText());
            s++;
            score.setText(Integer.toString(s));
        }
    }

    @FXML
    protected void pauseGame(ActionEvent event) {
        pauseMenuActive = true;
        pausemenu.requestFocus();
        pausemenu.setOpacity(1);
        pausemenu.setDisable(false);

        if (jump != null)
            jump.pause();
        if (moveRight != null)
            moveRight.pause();
    }

    @FXML
    protected void resumeGame(ActionEvent event) {
        pauseMenuActive = false;
        game.requestFocus();
        pausemenu.setOpacity(0);
        pausemenu.setDisable(true);

        if (jump != null)
            jump.play();
    }

    @FXML
    protected void restartGame(ActionEvent event) {
        game.requestFocus();
        pausemenu.setOpacity(0);
        pausemenu.setDisable(true);
        score.setText("0");

        if (jump != null)
            jump.stop();
        if (moveRight != null)
            moveRight.stop();

        hero.setTranslateX(0.0);
        hero.setTranslateY(0.0);
        firstJump = true;
    }

    @FXML
    protected void saveGame(ActionEvent event) {
        System.out.println("Saving...");
    }

    @FXML
    protected void returnToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}