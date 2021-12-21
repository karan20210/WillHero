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
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class GUIController implements Initializable, Serializable {

    private String gameName;
    private ArrayList<GUIController> savedGames = new ArrayList<>();

    // Game elements
    @FXML
    private Pane game;
    @FXML
    private ImageView hero;
    @FXML
    private Pane pausemenu;
    @FXML
    private VBox saveMenu;
    @FXML
    private Pane resurrectMenu;
    @FXML
    private Pane gameOverMenu;
    @FXML
    private Label score;
    @FXML
    private Label tapToStart;
    @FXML
    private Button pauseButton;
    @FXML
    private ImageView orc_1, orc_2, orc_3, orc_4, orc_5, orc_6;
    @FXML
    private Text coinsCollected;
    @FXML
    private ImageView coinsCollectedImg;
    @FXML
    private Pane weaponTab;
    @FXML
    private ImageView AxeTab;
    @FXML
    private ImageView KnifeTab;
    @FXML
    private Text instruction;
    @FXML
    private Button soundOn;
    @FXML
    private Button soundOff;
    @FXML
    private Pane volumeButtons;
    @FXML
    private ImageView boss;
    @FXML
    private ImageView c1, c2, c3;
    @FXML
    private ImageView coinChest, coinChest1, coinChest11, coinChest111;
    @FXML
    private ImageView weaponChest, weaponChest1, weaponChest11;
    @FXML
    private ImageView p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20;
    @FXML
    private Pane a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14;
    @FXML
    private TextField saveGameName;
    @FXML
    private Pane orcBox_1, orcBox_2, orcBox_3, orcBox_4, orcBox_5, orcBox_6;
    @FXML
    private ImageView tnt1, tnt2, tnt3;
    @FXML
    private ImageView Axe, Knife;

    @FXML
    private Pane heroBox;


    //Transitions
    TranslateTransition hJump, jump,fall, hmoveRight, moveRight, sceneMove, pauseMenuMove, pauseButtonMove, scoreMove, orcJump, coinsCollectedMove, coinsCollectedImgMove, saveMenuMove, weaponTabMove, volOnMove, gameOverMenuMove, resurrectMenuMove, axeMove, knifeMove, axeJump, knifeJump;
    HashMap<Orcs, TranslateTransition> orcJumps = new HashMap<>();
    ArrayList<TranslateTransition> orcBoxJumps = new ArrayList<TranslateTransition>();
    RotateTransition rotate;
    FadeTransition ft;

    //Useful boolean values
    boolean firstJump = true;
    boolean pauseMenuActive = false;
    boolean over = false;

    private double heroXbeforeFalling, heroBoxXbeforeFalling;

    // Collections
    HashMap<ImageView, Coin> coinsInGame = new HashMap<>();
    HashMap<ImageView, CoinChest> coinChestsInGame = new HashMap<>();
    HashMap<ImageView, WeaponChest> weaponChestsInGame = new HashMap<>();
    HashMap<ImageView, Island> islandsInGame = new HashMap<>();
    HashMap<Pane, Abyss> abyssInGame = new HashMap<>();
    HashMap<ImageView, Orcs> orcsInGame = new HashMap<>();
    HashMap<ImageView, Pane> orcBoxesInGame = new HashMap<>();
    HashMap<ImageView, Hero> heroInGame = new HashMap<>();
    HashMap<ImageView, Weapon> weaponsInGame = new HashMap<>();
    HashMap<ImageView, Obstacles> obstaclesInGame = new HashMap<>();

    // Collisions
    AnimationTimer chestCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for(ImageView i: coinChestsInGame.keySet())
            {
                if(i.getBoundsInParent().intersects(heroBox.getBoundsInParent()))
                {
                    if(!coinChestsInGame.get(i).isOpen())
                    {
                        coinChestsInGame.get(i).setOpen(true);
                        int c = coinChestsInGame.get(i).getAmount() + Integer.valueOf(coinsCollected.getText());
                        coinsCollected.setText(Integer.toString(c));
                        i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/OpenCoinChest.png").toUri().toString()));
                        Glow glow = new Glow();
                        glow.setLevel(1);
                        i.setEffect(glow);
                    }
                }
            }

            for(ImageView i: weaponChestsInGame.keySet())
            {
                if(i.getBoundsInParent().intersects(heroBox.getBoundsInParent()))
                {
                    if(!weaponChestsInGame.get(i).isOpen())
                    {
                        weaponChestsInGame.get(i).setOpen(true);
                        System.out.println(weaponChestsInGame.get(i).getProvidedWeapon().getClass().toString());
                        if(weaponChestsInGame.get(i).getProvidedWeapon().getClass().toString().equals("class com.example.willherogui.Axe"))
                        {
                            AxeTab.setOpacity(1);
                            KnifeTab.setOpacity(0.38);
                            Axe.setOpacity(1);
                            Axe.setDisable(false);
                            Knife.setOpacity(0);
                            Knife.setDisable(true);
                        }

                        else if(weaponChestsInGame.get(i).getProvidedWeapon().getClass().toString().equals("class com.example.willherogui.Knife"))
                        {
                            AxeTab.setOpacity(0.38);
                            KnifeTab.setOpacity(1);
                            Axe.setOpacity(0);
                            Axe.setDisable(true);
                            Knife.setOpacity(1);
                            Knife.setDisable(false);
                        }
                    }
                    i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/OpenWeaponChest.png").toUri().toString()));
                }
            }
        }
    };


    AnimationTimer abyssCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for (Pane i : abyssInGame.keySet()) {
                if (i.getBoundsInParent().intersects(heroBox.getBoundsInParent())) {
                    if(heroInGame.get(hero).isAlive())
                    {
                        heroInGame.get(hero).setAlive(false);
                        hJump.stop();
                        fall = new TranslateTransition();
                        fall.setDuration(Duration.millis(200));
                        fall.setNode(hero);
                        fall.setByY(400);
                        fall.setCycleCount(1);
                        fall.setInterpolator(Interpolator.LINEAR);
                        fall.play();

                        TranslateTransition fallBox = new TranslateTransition();
                        jump.stop();
                        fallBox.setDuration(Duration.millis(100));
                        fallBox.setNode(heroBox);
                        fallBox.setByY(500);
                        fallBox.setCycleCount(1);
                        fallBox.setInterpolator(Interpolator.LINEAR);
                        fallBox.play();
                        gameOver();
                    }

                }

                for(ImageView o: orcsInGame.keySet())
                {
                    if (i.getBoundsInParent().intersects(o.getBoundsInParent())) {
                        if(orcsInGame.get(o).isAlive())
                        {
                            orcsInGame.get(o).setAlive(false);
                            for(Orcs a: orcJumps.keySet()) {
                                if(!a.isAlive())
                                    orcJumps.get(a).pause();
                            }
//                            orcJump();
                            fall = new TranslateTransition();
                            fall.setDuration(Duration.millis(400));
                            fall.setNode(o);
                            fall.setByY(400);
                            fall.setCycleCount(1);
                            fall.setInterpolator(Interpolator.LINEAR);
                            fall.play();

                        }
                    }
                }
            }
        }
    };

    AnimationTimer islandCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for(ImageView i: islandsInGame.keySet())
            {
                if(i.getBoundsInParent().intersects(heroBox.getBoundsInParent()))
                {
                    heroXbeforeFalling = hero.getTranslateX();
                    heroBoxXbeforeFalling = heroBox.getTranslateX();
                }

            }
        }
    };

    AnimationTimer orcCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for(ImageView i: orcsInGame.keySet())
            {
                if (orcBoxesInGame.get(i).getBoundsInParent().intersects(heroBox.getBoundsInParent())) {
                    TranslateTransition orcmoveRight = new TranslateTransition();
                    orcmoveRight.setDuration(Duration.millis(50));
                    orcmoveRight.setNode(i);
                    orcmoveRight.setByX(+30);
                    orcmoveRight.setCycleCount(1);
                    orcmoveRight.play();

                    TranslateTransition orcBoxmoveRight = new TranslateTransition();
                    orcBoxmoveRight.setDuration(Duration.millis(50));
                    orcBoxmoveRight.setNode(orcBoxesInGame.get(i));
                    orcBoxmoveRight.setByX(+30);
                    orcBoxmoveRight.setCycleCount(1);
                    orcBoxmoveRight.play();
                }

                for(ImageView iv: orcsInGame.keySet())
                {
                    if (orcBoxesInGame.get(i).getBoundsInParent().intersects(orcBoxesInGame.get(iv).getBoundsInParent()))
                    {
                        if(orcBoxesInGame.get(i) != orcBoxesInGame.get(iv))
                        {
                            TranslateTransition orcmoveRight = new TranslateTransition();
                            orcmoveRight.setDuration(Duration.millis(50));
                            orcmoveRight.setNode(iv);
                            orcmoveRight.setByX(+30);
                            orcmoveRight.setCycleCount(1);
                            orcmoveRight.play();
                        }
                    }
                }
            }
        }
    };

    AnimationTimer obstacleCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for(ImageView i: obstaclesInGame.keySet())
            {
                if(i.getBoundsInParent().intersects(heroBox.getBoundsInParent()))
                {
                    TnT t = (TnT)obstaclesInGame.get(i);
                    if(!t.isBlast())
                    {
                        FadeTransition blast = new FadeTransition(Duration.millis(500), i);
                        blast.setCycleCount(4);
                        blast.setFromValue(1);
                        blast.setToValue(0.5);
                        blast.setAutoReverse(true);
                        blast.play();
                        t.setBlast(true);

                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        System.out.println("Blast");
                                        i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/blast.png").toUri().toString()));
                                        i.setScaleX(3);
                                        i.setScaleY(1);

                                        if(hero.getTranslateX() - i.getLayoutX() < 100)
                                        {
//                                            System.out.println("BOOM");
//                                            System.out.println(hero.getTranslateX() + " " + i.getLayoutX());
                                            gameOver();
                                        }

                                    }
                                },
                                2000
                        );

//                        System.out.println(hero.getTranslateX() + " " + i.getLayoutX() + " " + i.getTranslateX());
                    }
                }
            }
        }
    };

    AnimationTimer coinCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for(ImageView i: coinsInGame.keySet())
            {
                if(i.getBoundsInParent().intersects(heroBox.getBoundsInParent()))
                {
                    if(!coinsInGame.get(i).isCollected())
                    {
                        int c = 1 + Integer.valueOf(coinsCollected.getText());
                        coinsCollected.setText(Integer.toString(c));
                        coinsInGame.get(i).setCollected(true);
                        i.setImage(null);
                    }
                }
            }
        }
    };

    private static URL u;
    private static ResourceBundle r;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        u = url;
        r = rb;
        ft = new FadeTransition(Duration.millis(1000), tapToStart);
        ft.setFromValue(1);
        ft.setToValue(0.5);
        ft.setCycleCount(Animation.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        heroBox.setOpacity(0);
        heroInGame.put(hero, new Hero(hero.getLayoutX(), hero.getLayoutY()));

        orcsInGame.put(orc_1, new GreenOrc(orc_1.getTranslateX(), orc_1.getLayoutY()));
        orcsInGame.put(orc_2, new RedOrc(orc_2.getLayoutX(), orc_2.getLayoutY()));
        orcsInGame.put(orc_3, new RedOrc(orc_3.getLayoutX(), orc_3.getLayoutY()));
        orcsInGame.put(orc_4, new GreenOrc(orc_4.getLayoutX(), orc_4.getLayoutY()));
        orcsInGame.put(orc_5, new GreenOrc(orc_5.getLayoutX(), orc_5.getLayoutY()));
        orcsInGame.put(orc_6, new RedOrc(orc_6.getLayoutX(), orc_6.getLayoutY()));

        orcBoxesInGame.put(orc_1, orcBox_1);
        orcBoxesInGame.put(orc_2, orcBox_2);
        orcBoxesInGame.put(orc_3, orcBox_3);
        orcBoxesInGame.put(orc_4, orcBox_4);
        orcBoxesInGame.put(orc_5, orcBox_5);
        orcBoxesInGame.put(orc_6, orcBox_6);

        obstaclesInGame.put(tnt1, new TnT(tnt1.getLayoutX(), tnt1.getLayoutY()));
        obstaclesInGame.put(tnt2, new TnT(tnt2.getLayoutX(), tnt2.getLayoutY()));
        obstaclesInGame.put(tnt3, new TnT(tnt3.getLayoutX(), tnt3.getLayoutY()));

        AxeTab.setOpacity(0.38);
        KnifeTab.setOpacity(0.38);

        weaponsInGame.put(Knife, new Knife(Knife.getLayoutX(), Knife.getLayoutY()));
        weaponsInGame.put(Axe, new Axe(Axe.getLayoutX(), Axe.getLayoutY()));

        coinsInGame.put(c1, new Coin(c1.getLayoutX(), c1.getLayoutY()));
        coinsInGame.put(c2, new Coin(c2.getLayoutX(), c2.getLayoutY()));
        coinsInGame.put(c3, new Coin(c3.getLayoutX(), c3.getLayoutY()));

        coinChestsInGame.put(coinChest, new CoinChest(coinChest.getLayoutX(), coinChest.getLayoutY()));
        coinChestsInGame.put(coinChest1, new CoinChest(coinChest1.getLayoutX(), coinChest1.getLayoutY()));
        coinChestsInGame.put(coinChest11, new CoinChest(coinChest11.getLayoutX(), coinChest11.getLayoutY()));
        coinChestsInGame.put(coinChest111, new CoinChest(coinChest111.getLayoutX(), coinChest111.getLayoutY()));

        weaponChestsInGame.put(weaponChest, new WeaponChest(weaponChest.getLayoutX(), weaponChest.getLayoutY()));
        weaponChestsInGame.put(weaponChest1, new WeaponChest(weaponChest1.getLayoutX(), weaponChest1.getLayoutY()));
        weaponChestsInGame.put(weaponChest11, new WeaponChest(weaponChest11.getLayoutX(), weaponChest11.getLayoutY()));

        islandsInGame.put(p1, new Island(p1.getLayoutX(), p1.getLayoutY()));
        islandsInGame.put(p2, new Island(p2.getLayoutX(), p2.getLayoutY()));
        islandsInGame.put(p3, new Island(p3.getLayoutX(), p3.getLayoutY()));
        islandsInGame.put(p4, new Island(p4.getLayoutX(), p4.getLayoutY()));
        islandsInGame.put(p5, new Island(p5.getLayoutX(), p5.getLayoutY()));
        islandsInGame.put(p6, new Island(p6.getLayoutX(), p6.getLayoutY()));
        islandsInGame.put(p7, new Island(p7.getLayoutX(), p7.getLayoutY()));
        islandsInGame.put(p8, new Island(p8.getLayoutX(), p8.getLayoutY()));
        islandsInGame.put(p9, new Island(p9.getLayoutX(), p9.getLayoutY()));
        islandsInGame.put(p10, new Island(p10.getLayoutX(), p10.getLayoutY()));
        islandsInGame.put(p11, new Island(p11.getLayoutX(), p11.getLayoutY()));
        islandsInGame.put(p12, new Island(p12.getLayoutX(), p12.getLayoutY()));
        islandsInGame.put(p13, new Island(p13.getLayoutX(), p13.getLayoutY()));
        islandsInGame.put(p14, new Island(p14.getLayoutX(), p14.getLayoutY()));
        islandsInGame.put(p15, new Island(p15.getLayoutX(), p15.getLayoutY()));
        islandsInGame.put(p16, new Island(p16.getLayoutX(), p16.getLayoutY()));
        islandsInGame.put(p17, new Island(p17.getLayoutX(), p17.getLayoutY()));
        islandsInGame.put(p18, new Island(p18.getLayoutX(), p18.getLayoutY()));
        islandsInGame.put(p19, new Island(p19.getLayoutX(), p19.getLayoutY()));
        islandsInGame.put(p20, new Island(p20.getLayoutX(), p20.getLayoutY()));

        abyssInGame.put(a1, new Abyss(a1.getLayoutX(), a1.getLayoutY()));
        abyssInGame.put(a2, new Abyss(a2.getLayoutX(), a2.getLayoutY()));
        abyssInGame.put(a3, new Abyss(a3.getLayoutX(), a3.getLayoutY()));
        abyssInGame.put(a4, new Abyss(a4.getLayoutX(), a4.getLayoutY()));
        abyssInGame.put(a5, new Abyss(a5.getLayoutX(), a5.getLayoutY()));
        abyssInGame.put(a6, new Abyss(a6.getLayoutX(), a5.getLayoutY()));
        abyssInGame.put(a7, new Abyss(a7.getLayoutX(), a7.getLayoutY()));
        abyssInGame.put(a8, new Abyss(a8.getLayoutX(), a8.getLayoutY()));
        abyssInGame.put(a9, new Abyss(a9.getLayoutX(), a9.getLayoutY()));
        abyssInGame.put(a10, new Abyss(a10.getLayoutX(), a10.getLayoutY()));
        abyssInGame.put(a11, new Abyss(a11.getLayoutX(), a11.getLayoutY()));
        abyssInGame.put(a12, new Abyss(a12.getLayoutX(), a12.getLayoutY()));
        abyssInGame.put(a13, new Abyss(a13.getLayoutX(), a13.getLayoutY()));
        abyssInGame.put(a14, new Abyss(a14.getLayoutX(), a14.getLayoutY()));

        if (MainMenuController.getSoundStatus() == true) {
            soundOff.setOpacity(0);
            soundOff.setDisable(true);
            soundOn.setDisable(false);
            soundOn.setOpacity(1);
        } else {
            soundOff.setOpacity(1);
            soundOff.setDisable(false);
            soundOn.setDisable(true);
            soundOn.setOpacity(0);
        }

        pauseButton.setFocusTraversable(false);
        soundOn.setFocusTraversable(false);
        soundOff.setFocusTraversable(false);
        gameOverMenu.setDisable(true);
        gameOverMenu.setOpacity(0);
        coinCollision.start();
        chestCollision.start();
        abyssCollision.start();
        islandCollision.start();
        orcCollision.start();
        obstacleCollision.start();
    }

    public void soundOnOff(ActionEvent event){
        MainMenuController.changeSound();
        if(MainMenuController.getSoundStatus() == true)
        {
            soundOff.setOpacity(0);
            soundOff.setDisable(true);
            soundOn.setDisable(false);
            soundOn.setOpacity(1);
        }
        else
        {
            soundOff.setOpacity(1);
            soundOff.setDisable(false);
            soundOn.setDisable(true);
            soundOn.setOpacity(0);
        }
        soundOn.setFocusTraversable(false);
        soundOff.setFocusTraversable(false);
        game.requestFocus();
    }

    @FXML
    protected void heroJump() {
        if (firstJump && !pauseMenuActive && heroInGame.get(hero).isAlive()) {
            ft.stop();
            tapToStart.setOpacity(0);
            firstJump = false;
            hero.requestFocus();

            jump = new TranslateTransition();
            jump.setDuration(Duration.millis(800));
            jump.setNode(heroBox);
            jump.setByY(-110);
            jump.setCycleCount(Animation.INDEFINITE);
            jump.setAutoReverse(true);
            jump.setInterpolator(Interpolator.LINEAR);
            jump.play();

            hJump = new TranslateTransition();
            hJump.setDuration(Duration.millis(800));
            hJump.setNode(hero);
            hJump.setByY(-110);
            hJump.setCycleCount(Animation.INDEFINITE);
            hJump.setAutoReverse(true);
            hJump.setInterpolator(Interpolator.LINEAR);
            hJump.play();

            axeJump = new TranslateTransition();
            axeJump.setDuration(Duration.millis(800));
            axeJump.setNode(Axe);
            axeJump.setByY(-110);
            axeJump.setCycleCount(Animation.INDEFINITE);
            axeJump.setAutoReverse(true);
            axeJump.setInterpolator(Interpolator.LINEAR);
            axeJump.play();

            knifeJump = new TranslateTransition();
            knifeJump.setDuration(Duration.millis(800));
            knifeJump.setNode(Knife);
            knifeJump.setByY(-110);
            knifeJump.setCycleCount(Animation.INDEFINITE);
            knifeJump.setAutoReverse(true);
            knifeJump.setInterpolator(Interpolator.LINEAR);
            knifeJump.play();

            orcJump();
            coinRotate();
        }
    }

    @FXML
    protected void orcJump()
    {
        for(ImageView i: orcsInGame.keySet())
        {
            if(orcsInGame.get(i).isAlive())
            {
                orcJump = new TranslateTransition();
                orcJump.setDuration(Duration.millis(1000));
                orcJump.setNode(i);
                orcJump.setByY(-40);
                orcJump.setCycleCount(Animation.INDEFINITE);
                orcJump.setAutoReverse(true);
                orcJump.setInterpolator(Interpolator.LINEAR);
                orcJump.play();
                orcJumps.put(orcsInGame.get(i),orcJump);

                TranslateTransition orcBoxJump = new TranslateTransition();
                orcBoxJump.setDuration(Duration.millis(1000));
                orcBoxJump.setNode(orcBoxesInGame.get(i));
                orcBoxJump.setByY(-40);
                orcBoxJump.setCycleCount(Animation.INDEFINITE);
                orcBoxJump.setAutoReverse(true);
                orcBoxJump.setInterpolator(Interpolator.LINEAR);
                orcBoxJump.play();
                orcBoxJumps.add(orcBoxJump);


            }

        }
    }

    @FXML
    protected void coinRotate()
    {
        for(ImageView i: coinsInGame.keySet())
        {
            rotate = new RotateTransition(Duration.millis(4000), i);
            rotate.setByAngle(360);
            rotate.setCycleCount(Animation.INDEFINITE);
            rotate.setInterpolator(Interpolator.LINEAR);
            rotate.play();
        }
    }

    @FXML
    protected void onSpace(KeyEvent event) {
        ft.stop();
        if (event.getCode() == KeyCode.SPACE && !pauseMenuActive && !firstJump && !over) {
            moveRight = new TranslateTransition();
            moveRight.setDuration(Duration.millis(100));
            moveRight.setNode(heroBox);
            moveRight.setByX(+50);
            moveRight.setCycleCount(1);
            moveRight.play();

            hmoveRight = new TranslateTransition();
            hmoveRight.setDuration(Duration.millis(100));
            hmoveRight.setNode(hero);
            hmoveRight.setByX(+50);
            hmoveRight.setCycleCount(1);
            hmoveRight.play();

            int s = Integer.valueOf(score.getText());
            s++;
            score.setText(Integer.toString(s));

            moveMenus(50);
        }
    }

    @FXML
    private void moveMenus(int x)
    {
        pauseButtonMove = new TranslateTransition();
        pauseButtonMove.setDuration(Duration.millis(150));
        pauseButtonMove.setNode(pauseButton);
        pauseButtonMove.setByX(x);
        pauseButtonMove.setCycleCount(1);
        pauseButtonMove.play();

        sceneMove = new TranslateTransition();
        sceneMove.setDuration(Duration.millis(150));
        sceneMove.setNode(game);
        sceneMove.setByX(-1*x);
        sceneMove.setCycleCount(1);
        sceneMove.play();

        pauseMenuMove = new TranslateTransition();
        pauseMenuMove.setDuration(Duration.millis(150));
        pauseMenuMove.setNode(pausemenu);
        pauseMenuMove.setByX(x);
        pauseMenuMove.setCycleCount(1);
        pauseMenuMove.play();

        scoreMove = new TranslateTransition();
        scoreMove.setDuration(Duration.millis(150));
        scoreMove.setNode(score);
        scoreMove.setByX(x);
        scoreMove.setCycleCount(1);
        scoreMove.play();

        coinsCollectedMove = new TranslateTransition();
        coinsCollectedMove.setDuration(Duration.millis(150));
        coinsCollectedMove.setNode(coinsCollected);
        coinsCollectedMove.setByX(x);
        coinsCollectedMove.setCycleCount(1);
        coinsCollectedMove.play();

        coinsCollectedImgMove = new TranslateTransition();
        coinsCollectedImgMove.setDuration(Duration.millis(150));
        coinsCollectedImgMove.setNode(coinsCollectedImg);
        coinsCollectedImgMove.setByX(x);
        coinsCollectedImgMove.setCycleCount(1);
        coinsCollectedImgMove.play();

        saveMenuMove = new TranslateTransition();
        saveMenuMove.setDuration(Duration.millis(150));
        saveMenuMove.setNode(saveMenu);
        saveMenuMove.setByX(x);
        saveMenuMove.setCycleCount(1);
        saveMenuMove.play();

        weaponTabMove = new TranslateTransition();
        weaponTabMove.setDuration(Duration.millis(150));
        weaponTabMove.setNode(weaponTab);
        weaponTabMove.setByX(x);
        weaponTabMove.setCycleCount(1);
        weaponTabMove.play();

        volOnMove = new TranslateTransition();
        volOnMove.setDuration(Duration.millis(150));
        volOnMove.setNode(volumeButtons);
        volOnMove.setByX(x);
        volOnMove.setCycleCount(1);
        volOnMove.play();

        gameOverMenuMove = new TranslateTransition();
        gameOverMenuMove.setDuration(Duration.millis(150));
        gameOverMenuMove.setNode(gameOverMenu);
        gameOverMenuMove.setByX(x);
        gameOverMenuMove.setCycleCount(1);
        gameOverMenuMove.play();

        resurrectMenuMove = new TranslateTransition();
        resurrectMenuMove.setDuration(Duration.millis(150));
        resurrectMenuMove.setNode(resurrectMenu);
        resurrectMenuMove.setByX(x);
        resurrectMenuMove.setCycleCount(1);
        resurrectMenuMove.play();

        axeMove = new TranslateTransition();
        axeMove.setDuration(Duration.millis(150));
        axeMove.setNode(Axe);
        axeMove.setByX(x);
        axeMove.setCycleCount(1);
        axeMove.play();

        knifeMove = new TranslateTransition();
        knifeMove.setDuration(Duration.millis(150));
        knifeMove.setNode(Knife);
        knifeMove.setByX(x);
        knifeMove.setCycleCount(1);
        knifeMove.play();
    }

    @FXML
    protected void pauseGame(ActionEvent event) {
        pauseMenuActive = true;
        pausemenu.setOpacity(1);
        pausemenu.setDisable(false);

        if (jump != null)
            jump.pause();
        if(hJump!=null)
            hJump.pause();
        if (moveRight != null)
            moveRight.pause();
        if(hmoveRight!=null)
            hmoveRight.pause();
        if(axeJump!=null)
            axeJump.pause();
        if(knifeJump!=null)
            knifeJump.pause();

        for(TranslateTransition orcJump: orcJumps.values())
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
        if (hJump != null)
            hJump.play();
        for(Orcs o: orcJumps.keySet())
        {
            if(o.isAlive())
                orcJumps.get(o).play();
        }
        if(axeJump!=null)
            axeJump.play();
        if(knifeJump!=null)
            knifeJump.play();
    }

    @FXML
    protected void restartGame(ActionEvent event) throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("game.fxml"));
        Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load(), 800, 500);
        stage.setScene(scene);
        stage.show();
        over = false;
    }

    @FXML
    protected void saveGame(ActionEvent event) {
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


        //SERIALIZING
//        gameName = saveGameName.getText();
//
//        GUIController object = null;
//        try
//        {
//            try
//            {
//                FileInputStream file = new FileInputStream("save.txt");
//                ObjectInputStream in = new ObjectInputStream(file);
//                object = (GUIController)in.readObject();
//                if(object == null)
//                    this.savedGames.add(this);
//                this.savedGames.add(object);
//                in.close();
//                file.close();
//
//                System.out.println("Deserialized then serialized");
//            }
//
//            catch (Exception e)
//            {
//                System.out.println(e);
//                this.savedGames.add(this);
//            }
//
//            FileOutputStream file = new FileOutputStream
//                    ("save.txt");
//
//            ObjectOutputStream out = new ObjectOutputStream
//                    (file);
//
//            out.writeObject(object);
//            out.close();
//            file.close();
//            System.out.println("Serialized");
//        }
//
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }
//
//        try
//        {
//            FileInputStream file = new FileInputStream("save.txt");
//            ObjectInputStream in = new ObjectInputStream(file);
//            object = (GUIController)in.readObject();
//            System.out.println(object.getSavedGames());
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }
    }

    @FXML
    protected void returnToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        // soundOnOff(event);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    protected void gameOver()
    {
        if(!over)
        {
            askForResurrect();
            over = true;
        }
    }

    @FXML
    protected void askForResurrect()
    {
        pauseGame(new ActionEvent());
        pausemenu.setOpacity(0);
        pausemenu.setDisable(true);
        resurrectMenu.setOpacity(1);
        resurrectMenu.setDisable(false);
    }

    @FXML
    protected void notResurrect(ActionEvent event)
    {
        gameOverMenu.setOpacity(1);
        gameOverMenu.setDisable(false);
        resurrectMenu.setOpacity(0);
        resurrectMenu.setDisable(true);
        pauseGame(event);
        pausemenu.setDisable(true);
        pausemenu.setOpacity(0);
//        for(TranslateTransition orcJump: orcJumps.values())
//            orcJump.pause();
        Media media = new Media(Paths.get("src/main/resources/com/example/willherogui/GameOverSound.mp3").toUri().toString());
        MediaPlayer sound = new MediaPlayer(media);
        sound.setCycleCount(1);
        sound.setVolume(0.5);
        sound.play();
    }

    @FXML
    protected void resurrect(ActionEvent event)
    {
        int c = Integer.valueOf(coinsCollected.getText());
        if(c > 2)
        {
            c = c-2;
            coinsCollected.setText(Integer.toString(c));
            resurrectMenu.setOpacity(0);
            resurrectMenu.setDisable(true);
            hero.setTranslateY(0.0);
            heroBox.setTranslateY(0.0);

            game.setTranslateX(-1 * heroXbeforeFalling);
            pauseButton.setTranslateX(heroXbeforeFalling);
            pausemenu.setTranslateX(heroXbeforeFalling);
            gameOverMenu.setTranslateX(heroXbeforeFalling);
            saveMenu.setTranslateX(heroXbeforeFalling);
            resurrectMenu.setTranslateX(heroXbeforeFalling);
            weaponTab.setTranslateX(heroXbeforeFalling);
            volumeButtons.setTranslateX(heroXbeforeFalling);
            coinsCollected.setTranslateX(heroXbeforeFalling);
            coinsCollectedImg.setTranslateX(heroXbeforeFalling);
            Knife.setTranslateX(heroXbeforeFalling-70);
            Axe.setTranslateX(heroXbeforeFalling-70);
            score.setTranslateX(heroXbeforeFalling);
            hero.setTranslateX(heroXbeforeFalling - 70);
            heroBox.setTranslateX(heroBoxXbeforeFalling - 70);

            over = false;
            heroInGame.get(hero).setAlive(true);
            resumeGame(event);
        }
        else
            notResurrect(event);
    }

    public String getGameName()
    {
        return gameName;
    }

    public ArrayList<GUIController> getSavedGames()
    {
        return savedGames;
    }
}
