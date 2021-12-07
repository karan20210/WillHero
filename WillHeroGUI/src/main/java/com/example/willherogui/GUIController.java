package com.example.willherogui;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    @FXML
    private Button pauseButton;

    TranslateTransition jump, moveRight, sceneMove, pauseMenuMove, pauseButtonMove;
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
            moveRight.setByX(+50);
            moveRight.setCycleCount(1);
            moveRight.play();

            int s = Integer.valueOf(score.getText());
            s++;
            score.setText(Integer.toString(s));

//            game.setTranslateX(-1*hero.getTranslateX());
//            pausemenu.setTranslateX(hero.getTranslateX());
//            pauseButton.setTranslateX(hero.getTranslateX());

            pauseButtonMove = new TranslateTransition();
            pauseButtonMove.setDuration(Duration.millis(500));
            pauseButtonMove.setNode(pauseButton);
            pauseButtonMove.setByX(50);
            pauseButtonMove.setCycleCount(1);
            pauseButtonMove.play();

            sceneMove = new TranslateTransition();
            sceneMove.setDuration(Duration.millis(500));
            sceneMove.setNode(game);
            sceneMove.setByX(-50);
            sceneMove.setCycleCount(1);
            sceneMove.play();

            pauseMenuMove = new TranslateTransition();
            pauseMenuMove.setDuration(Duration.millis(500));
            pauseMenuMove.setNode(pausemenu);
            pauseMenuMove.setByX(50);
            pauseMenuMove.setCycleCount(1);
            pauseMenuMove.play();
        }
    }

    @FXML
    protected void pauseGame(ActionEvent event) {
        pauseMenuActive = true;
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
        game.setTranslateX(0.0);
        pauseButton.setTranslateX(0.0);
        pausemenu.setTranslateX(0.0);
        firstJump = true;
        pauseMenuActive = false;
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