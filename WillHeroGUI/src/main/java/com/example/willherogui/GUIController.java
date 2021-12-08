package com.example.willherogui;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    @FXML
    private Pane game;
    @FXML
    private ImageView hero;
    @FXML
    private Pane pausemenu;
    @FXML
    private Pane saveMenu;
    @FXML
    private Label score;
    @FXML
    private Label tapToStart;
    @FXML
    private Button pauseButton;
    @FXML
    private HBox coins_1, coins_2, coins_3, coins_4;
    @FXML
    private ImageView orc_1, orc_2, orc_3;
    @FXML
    private Text coinsCollected;
    @FXML
    private ImageView coinsCollectedImg;
    @FXML
    private Pane weaponTab;

    private ArrayList<HBox> coins;
    private ArrayList<ImageView> orcs;

    TranslateTransition jump, moveRight, sceneMove, pauseMenuMove, pauseButtonMove, scoreMove, orcJump, coinsCollectedMove, coinsCollectedImgMove, saveMenuMove, weaponTabMove;
    ArrayList<TranslateTransition> orcJumps = new ArrayList<TranslateTransition>();
    RotateTransition rotate;
    FadeTransition ft;
    boolean firstJump = true;
    boolean pauseMenuActive = false;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        ft = new FadeTransition(Duration.millis(1000), tapToStart);
        ft.setFromValue(1);
        ft.setToValue(0.6);
        ft.setCycleCount(Animation.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        coins = new ArrayList<HBox>();
        Collections.addAll(coins, coins_1, coins_2, coins_3, coins_4);

        orcs = new ArrayList<ImageView>();
        Collections.addAll(orcs, orc_1, orc_2, orc_3);
        pauseButton.setFocusTraversable(false);
    }

    @FXML
    protected void heroJump() {
        if (firstJump && !pauseMenuActive) {
            ft.stop();
            tapToStart.setOpacity(0);

            firstJump = false;
            hero.requestFocus();
            jump = new TranslateTransition();
            jump.setDuration(Duration.millis(1000));
            jump.setNode(hero);
            jump.setByY(-110);
            jump.setCycleCount(Animation.INDEFINITE);
            jump.setAutoReverse(true);
            jump.setInterpolator(Interpolator.LINEAR);
            jump.play();
        }
        coinRotate();
        orcJump();
    }

    @FXML
    protected void orcJump()
    {
        for(ImageView i: orcs)
        {
            orcJump = new TranslateTransition();
            orcJump.setDuration(Duration.millis(1000));
            orcJump.setNode(i);
            orcJump.setByY(-40);
            orcJump.setCycleCount(Animation.INDEFINITE);
            orcJump.setAutoReverse(true);
            orcJump.setInterpolator(Interpolator.LINEAR);
            orcJump.play();
            orcJumps.add(orcJump);
        }
    }

    @FXML
    protected void coinRotate()
    {
        for(HBox h: coins)
        {
            for(Node node: h.getChildren())
            {
                rotate = new RotateTransition(Duration.millis(4000), node);
                rotate.setByAngle(360);
                rotate.setCycleCount(Animation.INDEFINITE);
                rotate.setInterpolator(Interpolator.LINEAR);
                rotate.play();
            }
        }
    }

    @FXML
    protected void onSpace(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE && !pauseMenuActive && !firstJump) {
            moveRight = new TranslateTransition();
            moveRight.setDuration(Duration.millis(100));
            moveRight.setNode(hero);
            moveRight.setByX(+50);
            moveRight.setCycleCount(1);
            moveRight.play();

            int s = Integer.valueOf(score.getText());
            s++;
            score.setText(Integer.toString(s));

            pauseButtonMove = new TranslateTransition();
            pauseButtonMove.setDuration(Duration.millis(250));
            pauseButtonMove.setNode(pauseButton);
            pauseButtonMove.setByX(50);
            pauseButtonMove.setCycleCount(1);
            pauseButtonMove.play();

            sceneMove = new TranslateTransition();
            sceneMove.setDuration(Duration.millis(250));
            sceneMove.setNode(game);
            sceneMove.setByX(-50);
            sceneMove.setCycleCount(1);
            sceneMove.play();

            pauseMenuMove = new TranslateTransition();
            pauseMenuMove.setDuration(Duration.millis(250));
            pauseMenuMove.setNode(pausemenu);
            pauseMenuMove.setByX(50);
            pauseMenuMove.setCycleCount(1);
            pauseMenuMove.play();

            scoreMove = new TranslateTransition();
            scoreMove.setDuration(Duration.millis(250));
            scoreMove.setNode(score);
            scoreMove.setByX(50);
            scoreMove.setCycleCount(1);
            scoreMove.play();

            coinsCollectedMove = new TranslateTransition();
            coinsCollectedMove.setDuration(Duration.millis(250));
            coinsCollectedMove.setNode(coinsCollected);
            coinsCollectedMove.setByX(50);
            coinsCollectedMove.setCycleCount(1);
            coinsCollectedMove.play();

            coinsCollectedImgMove = new TranslateTransition();
            coinsCollectedImgMove.setDuration(Duration.millis(250));
            coinsCollectedImgMove.setNode(coinsCollectedImg);
            coinsCollectedImgMove.setByX(50);
            coinsCollectedImgMove.setCycleCount(1);
            coinsCollectedImgMove.play();

            saveMenuMove = new TranslateTransition();
            saveMenuMove.setDuration(Duration.millis(250));
            saveMenuMove.setNode(saveMenu);
            saveMenuMove.setByX(50);
            saveMenuMove.setCycleCount(1);
            saveMenuMove.play();

            weaponTabMove = new TranslateTransition();
            weaponTabMove.setDuration(Duration.millis(250));
            weaponTabMove.setNode(weaponTab);
            weaponTabMove.setByX(50);
            weaponTabMove.setCycleCount(1);
            weaponTabMove.play();
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

        for(TranslateTransition orcJump: orcJumps)
        {
            if(orcJump!=null)
                orcJump.pause();
        }
    }

    @FXML
    protected void resumeGame(ActionEvent event) {
        pauseMenuActive = false;
        game.requestFocus();
        pausemenu.setOpacity(0);
        pausemenu.setDisable(true);

        if (jump != null)
            jump.play();
        for(TranslateTransition orcJump: orcJumps)
        {
            if(orcJump!=null)
                orcJump.play();
        }
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
        score.setTranslateX(0.0);
        saveMenu.setTranslateX(0.0);
        coinsCollectedImg.setTranslateX(0.0);
        coinsCollected.setTranslateX(0.0);

        for(ImageView i: orcs)
        {
            i.setTranslateY(0.0);
        }

        firstJump = true;
        pauseMenuActive = false;

        if(orcJump!=null)
            orcJump.stop();
        tapToStart.setOpacity(1);
        ft.play();
    }

    @FXML
    protected void saveGame(ActionEvent event) {
        System.out.println("Saving...");
        pausemenu.setOpacity(0);
        pausemenu.setDisable(true);
        saveMenu.setDisable(false);
        saveMenu.setOpacity(1);
    }

    @FXML
    protected void saveGameButton(ActionEvent event)
    {
        pausemenu.setOpacity(1);
        pausemenu.setDisable(false);
        saveMenu.setDisable(true);
        saveMenu.setOpacity(0);
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
