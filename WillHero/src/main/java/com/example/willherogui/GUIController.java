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
    private Abyss abyss;

    // Game elements
    @FXML
    private transient Pane game;


    //gate
    @FXML
    private transient ImageView gate;

    //Hero
    private Hero hero1;
    @FXML
    private transient ImageView hero;
    // @FXML
    // private Pane heroBox;
    
    //Helmet
    private Helmet helmet1;
    @FXML
    private transient Pane helmet;

    //Coins
    @FXML
    private transient Text coinsCollected;
    @FXML
    private transient ImageView coinsCollectedImg;
 

    //Weapons
     @FXML
     private transient Pane weaponTab;
     @FXML
     private transient ImageView tab1;
     @FXML
     private transient ImageView tab2;
     @FXML
     private transient ImageView axe;
     @FXML
     private transient ImageView knife;
     private transient Image axeImg, knifeImg;

     @FXML
     private transient Label knifeLevel, axeLevel;

     private Weapon Weapon1,Weapon2;
     
  

    //Menus
    @FXML
    private transient Pane pausemenu;
    @FXML
    private transient VBox saveMenu;
    @FXML
    private transient Pane resurrectMenu;
    @FXML
    private transient Pane gameOverMenu;
    @FXML
    private transient Button pauseButton;
    @FXML
    private transient Pane winMenu;

    @FXML
    private transient Label score;
    private int previousScore;
    @FXML
    private transient Label tapToStart;
   
    //Orcs
    @FXML
    private transient ImageView orc_1, orc_2, orc_3, orc_4, orc_5, orc_6, orc_7, orc_8, orc_9, orc_11, orc_12, orc_13, orc_14, orc_15, orc_16;
    @FXML
    private transient ImageView boss;
    private Boss boss1; 

    
   
    @FXML
    private transient Text instruction;

    //Volume
    @FXML
    private transient Button soundOn;
    @FXML
    private transient Button soundOff;
    @FXML
    private transient Pane volumeButtons;

    //Abyss
    @FXML
    private transient Pane abyssPane;
   

    //Chests
    @FXML
    private transient ImageView c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15;
    @FXML
    private transient ImageView coinChest, coinChest1, coinChest11, coinChest111;
    @FXML
    private transient ImageView weaponChest, weaponChest1, weaponChest11;
    @FXML

    //Platforms
    private transient ImageView p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21;
    @FXML
    private transient Pane a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14;
    @FXML
    private transient TextField saveGameName;

    private static final long serialversionUID = 129348938L;
  
    //Obstacle
    @FXML
    private transient ImageView tnt1, tnt2, tnt3;

    @FXML
    private transient Label winCoinText;

    @FXML
    private transient Button freezeButton;

    //Sounds
    private transient MediaPlayer weaponSound;
    private transient MediaPlayer coinChestSound;
    private transient MediaPlayer blastSound;
   

    //Transitions
    transient TranslateTransition hJump, jump,fall, hmoveRight, moveRight, sceneMove, pauseMenuMove, pauseButtonMove, scoreMove, orcJump, coinsCollectedMove, coinsCollectedImgMove, saveMenuMove, weaponTabMove, volOnMove, gameOverMenuMove, resurrectMenuMove, axeMove, knifeMove, axeJump, knifeJump, freezeButtonMove;
    transient HashMap<Orcs, TranslateTransition> orcJumps = new HashMap<>();
    transient ArrayList<TranslateTransition> orcBoxJumps = new ArrayList<TranslateTransition>();
    transient RotateTransition rotate, freezeRotate;
    transient FadeTransition ft;

    //Useful boolean values
    boolean firstJump = true;
    boolean pauseMenuActive = false;
    boolean over = false;
    boolean jumpOver = true;
    boolean firstRessurect = true;

    private double heroXbeforeFalling;

    // Collections
    transient HashMap<ImageView, Coin> coinsInGame = new HashMap<>();
    transient HashMap<ImageView, Chests> ChestsInGame= new HashMap<>();
    transient HashMap<ImageView, Island> islandsInGame = new HashMap<>();
    transient HashMap<ImageView, Orcs> orcsInGame = new HashMap<>();
    transient HashMap<ImageView, Pane> orcBoxesInGame = new HashMap<>();
    transient HashMap<ImageView, Hero> heroInGame = new HashMap<>();
    transient HashMap<ImageView, Weapon> weaponsInGame = new HashMap<>();
    transient HashMap<Pane,Helmet> helmetsInGame= new HashMap<>();
    transient HashMap<ImageView, Obstacles> obstaclesInGame = new HashMap<>();

    // Collections required for saving
    ArrayList<GameObjects> gameObjectsInGame = new ArrayList<>();
    HashMap<String, ArrayList<GameObjects>> savedGame = new HashMap<>();
    ArrayList<HashMap<String, ArrayList<GameObjects>>> savedGames = new ArrayList<>();

    HashMap<GUIController, ArrayList<GameObjects>> savedGame1 = new HashMap<>();
    ArrayList<HashMap<GUIController, ArrayList<GameObjects>>> savedGames1 = new ArrayList<>();



    // Collisions

    //Chest Collisions
    transient AnimationTimer chestCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for(ImageView i: ChestsInGame.keySet()){
                if(i.getBoundsInParent().intersects(hero.getBoundsInParent()))
                {
                    Chests chest = ChestsInGame.get(i);
                    if(!chest.isOpen()){
                        {
                            chest.collide(hero1);

                            if(MainMenuController.getSoundStatus()){
                                Media coinChestMedia = new Media(Paths.get("src/main/resources/com/example/willherogui/coinChestSound.mp3").toUri().toString());
                                coinChestSound = new MediaPlayer(coinChestMedia);
                                coinChestSound.setCycleCount(1);
                                coinChestSound.setAutoPlay(true);
                                coinChestSound.play();
                            }
                            
                        }

                        if(hero1.getCurrentWeapon()!=null)
                        {
                            if(hero1.getCurrentWeapon().getClass().toString().equals("class com.example.willherogui.Axe"))
                            {
                                axeLevel.setText(Integer.toString(hero1.getCurrentWeapon().getLevel()));
                            }

                            if(hero1.getCurrentWeapon().getClass().toString().equals("class com.example.willherogui.Knife"))
                                knifeLevel.setText(Integer.toString(hero1.getCurrentWeapon().getLevel()));
                        }
                        break;
                    }                    
                }
            }
           
        }
    };

    //CombinedCollision
    transient AnimationTimer combinedCollision= new AnimationTimer() {
        @Override
        public void handle(long l) {
            boolean heroHitIsland =false;
            for(ImageView i: islandsInGame.keySet())
            {
                if(!heroHitIsland && i.getBoundsInParent().intersects(hero.getBoundsInParent()))
                {
                    heroXbeforeFalling = hero.getTranslateX();
                    previousScore = Integer.valueOf(score.getText());
                    heroHitIsland=true;                    
                }
            }

            if(!heroHitIsland){
                    if (abyssPane.getBoundsInParent().intersects(hero.getBoundsInParent())) {
                       
                        if(hero1.isAlive())
                        {
                            abyss.collide(hero1);
                            gameOver();
                        }    
                    }
            }

            for(ImageView o : orcsInGame.keySet()){

                boolean OrcHitIsland=false;

                for(ImageView i: islandsInGame.keySet())
                {
                    if(!OrcHitIsland && i.getBoundsInParent().intersects(o.getBoundsInParent()))
                    {
                        OrcHitIsland=true;          
                    }   
                }    
                
                if(!OrcHitIsland){

                    if (abyssPane.getBoundsInParent().intersects(o.getBoundsInParent())) {
                        {
                            if(orcsInGame.get(o).isAlive())
                                abyss.collide(orcsInGame.get(o), heroInGame.get(hero));
                        }    
                    }
                }
            }   
        }
        
    };

    //OrcCollision
   transient AnimationTimer orcCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            
            for(ImageView i: orcsInGame.keySet())
            {
                if (i.getBoundsInParent().intersects(hero.getBoundsInParent())) {
                    orcsInGame.get(i).collide(hero1);
                }

                boolean collidedwithOrc=false;

//                for(ImageView iv: orcsInGame.keySet())
//                {
//                    if (i.getBoundsInParent().intersects(iv.getBoundsInParent()))
//                    {
//                        if(i != iv)
//                        {
//                            collidedwithOrc=true;
//                            orcsInGame.get(iv).collideWithOrc();
//                        }
//                    }
//                }
                if(collidedwithOrc){
                    break;
                }
            }
        }
    };

    //CoinCollision
    transient AnimationTimer coinCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for(ImageView i: coinsInGame.keySet())
            {
                if(i.getBoundsInParent().intersects(hero.getBoundsInParent()))
                {
                    if(!coinsInGame.get(i).isCollected())
                    {
                        coinsInGame.get(i).collide(hero1);
                        coinsInGame.get(i).setCollected(true);
                    }
                }
            }
        }
    };

    //Shoot Collision
   transient AnimationTimer shootCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {

            for(ImageView i: orcsInGame.keySet())
            {
                if(hero1.getCurrentWeapon()!=null){
                    if (i.getBoundsInParent().intersects(hero1.getCurrentWeapon().getHelmet().getBoundsInParent()) && orcsInGame.get(i).isAlive()) {
                        if(i.getBoundsInParent().intersects(hero.getBoundsInParent()))
                            break;

                        orcsInGame.get(i).shot(hero1.getCurrentWeapon().getDamage());
                        if(!orcsInGame.get(i).isAlive())
                        {
                            i.setImage(null);
                            heroInGame.get(hero).addCoins(5);
                        }
                    }
                }
            }
        }
    };

    //Obstacle Collision
    transient AnimationTimer obstacleCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for(ImageView i: obstaclesInGame.keySet())
            {
                if(i.getBoundsInParent().intersects(hero.getBoundsInParent()))
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
                                        i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/blast.png").toUri().toString()));// i.setFitWidth(500);
                                        if(MainMenuController.getSoundStatus()){
                                            
                                            Media obstacleMedia = new Media(Paths.get("src/main/resources/com/example/willherogui/blast.mp3").toUri().toString());
                                            blastSound = new MediaPlayer(obstacleMedia);
                                            blastSound.setCycleCount(1);
                                            blastSound.setAutoPlay(true);
                                            blastSound.play();

                                        } 

                                        if(hero.getTranslateX() - i.getLayoutX() < 30)
                                        {
                                            gameOver();
                                        }

                                        new java.util.Timer().schedule(
                                            new java.util.TimerTask(){
                                                @Override
                                                public void run(){
                                                    i.setOpacity(0);
                                                    
                                                }
                                            },1000
                
                                        );
                                    }
                                },
                                2000
                        );
                    }
                }
            }
        }
    };

    transient AnimationTimer updateCoordinates = new AnimationTimer() {
        @Override
        public void handle(long l) {
            hero1.setX(hero.getTranslateX());
            hero1.setY(hero.getTranslateY());

            for(ImageView i: orcsInGame.keySet())
            {
                orcsInGame.get(i).setX(i.getTranslateX());
                orcsInGame.get(i).setY(i.getTranslateY());
            }

            for(ImageView i: weaponsInGame.keySet())
            {
                weaponsInGame.get(i).setX(i.getTranslateX());
                weaponsInGame.get(i).setY(i.getTranslateY());
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

        //Adding Objects

        //Hero1 Attributes
        hero1 = Hero.getInstance(hero.getLayoutX(), hero.getLayoutY());
        hero1.setImg(hero);
        hero1.setWeaponsTab(tab1,tab2);
        hero1.addCoinsText(coinsCollected);       
        heroInGame.put(hero, hero1);
        // hero1.setHeroBox(heroBox);
        // heroBox.setOpacity(0);

        helmet1 = new Helmet(helmet.getLayoutX(),helmet.getLayoutY());
        helmet1.setHelmetImg(helmet);
        helmetsInGame.put(helmet,helmet1);
        hero1.setCurrentHelmet(helmet1);
        
        axeImg=axe.getImage();
        knifeImg=knife.getImage();
        Weapon1= new Axe(axe,axeImg, helmet);
        Weapon2 = new Knife(knife,knifeImg, helmet);
        helmet1.addWeapons(Weapon1,Weapon2);

        tab1.setOpacity(0.38);
        tab2.setOpacity(0.38);
        boss1 = new Boss(boss);

        //Orcs
        orcsInGame.put(orc_1, new GreenOrc(orc_1));
        orcsInGame.put(orc_2, new RedOrc(orc_2));
        orcsInGame.put(orc_3, new RedOrc(orc_3));
        orcsInGame.put(orc_4, new GreenOrc(orc_4));
        orcsInGame.put(orc_5, new GreenOrc(orc_5));
        orcsInGame.put(orc_6, new RedOrc(orc_6));
        orcsInGame.put(orc_7, new GreenOrc(orc_7));
        orcsInGame.put(orc_8, new RedOrc(orc_8));
        orcsInGame.put(orc_9, new RedOrc(orc_9));
        orcsInGame.put(orc_11, new GreenOrc(orc_11));
        orcsInGame.put(orc_12, new RedOrc(orc_12));
        orcsInGame.put(orc_13, new RedOrc(orc_13));
        orcsInGame.put(orc_14, new RedOrc(orc_14));
        orcsInGame.put(orc_15, new RedOrc(orc_15));
        orcsInGame.put(orc_16, new RedOrc(orc_16));
        orcsInGame.put(boss,boss1);

        //Obstacles
        obstaclesInGame.put(tnt1, new TnT(tnt1));
        obstaclesInGame.put(tnt2, new TnT(tnt2));
        obstaclesInGame.put(tnt3, new TnT(tnt3));

        //Floating Coins
        coinsInGame.put(c1, new Coin(c1));
        coinsInGame.put(c2, new Coin(c2));
        coinsInGame.put(c3, new Coin(c3));
        coinsInGame.put(c4, new Coin(c4));
        coinsInGame.put(c5, new Coin(c5));
        coinsInGame.put(c6, new Coin(c6));
        coinsInGame.put(c7, new Coin(c7));
        coinsInGame.put(c8, new Coin(c8));
        coinsInGame.put(c9, new Coin(c9));
        coinsInGame.put(c10, new Coin(c10));
        coinsInGame.put(c11, new Coin(c11));
        coinsInGame.put(c12, new Coin(c12));
        coinsInGame.put(c13, new Coin(c13));
        coinsInGame.put(c14, new Coin(c14));
        coinsInGame.put(c15, new Coin(c15));

        //Chests
        ChestsInGame.put(coinChest, new CoinChest(coinChest));
        ChestsInGame.put(coinChest1, new CoinChest(coinChest1));
        ChestsInGame.put(coinChest11, new CoinChest(coinChest11));
        ChestsInGame.put(coinChest111, new CoinChest(coinChest111));
        ChestsInGame.put(weaponChest, new WeaponChest(weaponChest));
        ChestsInGame.put(weaponChest1, new WeaponChest(weaponChest1));
        ChestsInGame.put(weaponChest11, new WeaponChest(weaponChest11));

        //Islands
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
        islandsInGame.put(p12, new Island(p12.getLayoutX(), p12.getLayoutY()));
        islandsInGame.put(p13, new Island(p13.getLayoutX(), p13.getLayoutY()));
        islandsInGame.put(p14, new Island(p14.getLayoutX(), p14.getLayoutY()));
        islandsInGame.put(p15, new Island(p15.getLayoutX(), p15.getLayoutY()));
        islandsInGame.put(p16, new Island(p16.getLayoutX(), p16.getLayoutY()));
        islandsInGame.put(p17, new Island(p17.getLayoutX(), p17.getLayoutY()));
        islandsInGame.put(p18, new Island(p18.getLayoutX(), p18.getLayoutY()));
        islandsInGame.put(p19, new Island(p19.getLayoutX(), p19.getLayoutY()));
        islandsInGame.put(p20, new Island(p20.getLayoutX(), p20.getLayoutY()));
        islandsInGame.put(p21, new Island(p21.getLayoutX(),p21.getLayoutY()));                                         


        //Abyss
        abyss=new Abyss(abyssPane.getLayoutX(),abyssPane.getLayoutY());

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

        //Starting Animations
        coinCollision.start();
        chestCollision.start();
        combinedCollision.start();
        orcCollision.start();
        obstacleCollision.start();
        updateCoordinates.start();
        shootCollision.start();
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
        if (firstJump && !pauseMenuActive && hero1.isAlive()) {
            ft.stop();
            tapToStart.setOpacity(0);
            hero1.startMove();
            firstJump = false;

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
                orcsInGame.get(i).startMove();
            }
        }
    }

    @FXML
    protected void coinRotate()
    {
        for(ImageView i: coinsInGame.keySet())
        {
           coinsInGame.get(i).rotate();
        }
    }

    private boolean moveMenu=true;
    private boolean notwon=true;
    private boolean shootEnd=true;
    @FXML
    protected void onSpace(KeyEvent event) {
        if(notwon){
           
            ft.stop();
            if(shootEnd==false){
                return;
            }
            if(event.getCode() == KeyCode.ENTER && !pauseMenuActive && !firstJump && !over && jumpOver)
            {   
                shootEnd=false;
                shootEnd=hero1.weaponShoot();
            }
            
    
            if (event.getCode() == KeyCode.SPACE && !pauseMenuActive && !firstJump && !over && jumpOver) {
                hero1.moveRight(game);
    
                int s = Integer.valueOf(score.getText());
                s++;
                score.setText(Integer.toString(s));
                if(moveMenu){
                    moveMenus(50);
                }
                
                jumpOver = false;
    
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                jumpOver = true;
                            }
                        },
                        150
                );
                
                if(s==112){
                    moveMenu=false;
                }

                if(gate.getBoundsInParent().intersects(hero.getBoundsInParent()))
                    checkWin();
        }
        }
    }

    @FXML
    private void freeze(ActionEvent event)
    {
        game.requestFocus();
        if(hero1.getCurrentCoins() > 40)
        {
            int c = Integer.valueOf(coinsCollected.getText());
            c = c-40;
            coinsCollected.setText(Integer.toString(c));
            hero1.setCurrentCoins(c);
            for(ImageView i: orcsInGame.keySet())
            {
                if(orcsInGame.get(i).isAlive()) {
                    orcsInGame.get(i).pauseJump();
                }
            }

            rotate = new RotateTransition(Duration.millis(5000), freezeButton);
            rotate.setByAngle(360);
            rotate.setCycleCount(Animation.INDEFINITE);
            rotate.setInterpolator(Interpolator.LINEAR);
            rotate.play();

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            for(ImageView i: orcsInGame.keySet())
                            {
                                if(orcsInGame.get(i).isAlive()) {
                                    orcsInGame.get(i).resumeJump();
                                }
                                rotate.pause();
                            }
                        }
                    }, 5000
            );
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

        freezeButtonMove = new TranslateTransition();
        freezeButtonMove.setDuration(Duration.millis(150));
        freezeButtonMove.setNode(freezeButton);
        freezeButtonMove.setByX(x);
        freezeButtonMove.setCycleCount(1);
        freezeButtonMove.play();

    }

    @FXML
    protected void pauseGame(ActionEvent event) {
        pauseMenuActive = true;
        pausemenu.setOpacity(1);
        pausemenu.setDisable(false);

        hero1.pauseJump();        
        
        for(ImageView i: orcsInGame.keySet())
        {
            if(orcsInGame.get(i).isAlive()) {
                orcsInGame.get(i).pauseJump();
            }
        }
    }

    @FXML
    protected void resumeGame(ActionEvent event) {
        pauseMenuActive = false;
        game.requestFocus();
        pausemenu.setOpacity(0);
        pausemenu.setDisable(true);

        hero1.resumeJump();

        for(ImageView i: orcsInGame.keySet())
        {
            if(orcsInGame.get(i).isAlive()){
                orcsInGame.get(i).resumeJump();
            }
        }
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

        try
        {
            try
            {
                FileInputStream file = new FileInputStream("save.txt");
                ObjectInputStream in = new ObjectInputStream(file);
//                ArrayList<HashMap<String, ArrayList<GameObjects>>> s = (ArrayList<HashMap<String, ArrayList<GameObjects>>>)in.readObject();
                ArrayList<HashMap <GUIController, ArrayList<GameObjects> > > s = (ArrayList <HashMap <GUIController, ArrayList<GameObjects> > >)in.readObject();
                for(HashMap<GUIController, ArrayList<GameObjects>> sg: s)
                {
//                    savedGames.add(sg);
                    savedGames1.add(sg);
                }
                in.close();
                file.close();
            }

            catch (Exception e)
            {
                System.out.println(e);
            }
            
            String date = new java.util.Date().toString();
            date = date.substring(0, 20);
            gameName = saveGameName.getText() + " " + date;
            FileOutputStream file = new FileOutputStream("save.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);

            gameObjectsInGame.add(hero1);
            gameObjectsInGame.add(helmet1);
            for(GameObjects i: coinsInGame.values())
                gameObjectsInGame.add(i);
            for(GameObjects i: ChestsInGame.values())
                gameObjectsInGame.add(i);
            for(GameObjects i: islandsInGame.values())
                gameObjectsInGame.add(i);
            for(GameObjects i: orcsInGame.values())
                gameObjectsInGame.add(i);
            for(GameObjects i: heroInGame.values())
                gameObjectsInGame.add(i);
            for(GameObjects i: helmetsInGame.values())
                gameObjectsInGame.add(i);
            for(GameObjects i: obstaclesInGame.values())
                gameObjectsInGame.add(i);

            gameObjectsInGame.add(abyss);

            savedGame1.put(this, gameObjectsInGame);
            savedGames1.add(savedGame1);
            out.writeObject(savedGames1);
            out.close();
            file.close();
        }

        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void loadSavedGame(ArrayList<GameObjects> g, ActionEvent e) throws IOException {
        int i = 0;
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("game.fxml"));
        Stage stage= (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load(), 800, 500);
        GUIController a = loader.getController();
        setUpLoadedGame(a, g);
        stage.setScene(scene);
        stage.show();
    }

    private void setUpLoadedGame(GUIController a, ArrayList<GameObjects> g)
    {
        Hero loadedHero = (Hero)g.get(0);
        Helmet loadedHelmet = (Helmet)g.get(1);

        a.hero.setTranslateX(loadedHero.getX());
        a.helmet.setTranslateX(loadedHero.getX());

        int i = 45;
        for(ImageView iv: a.orcsInGame.keySet())
        {
            iv.setTranslateX(g.get(i).getX());
            i++;
        }

        a.game.setTranslateX(-1*a.hero.getTranslateX());
        a.pausemenu.setTranslateX(a.hero.getTranslateX());
        a.pauseButton.setTranslateX(a.hero.getTranslateX());
        a.score.setTranslateX(a.hero.getTranslateX());
        a.coinsCollected.setTranslateX(a.hero.getTranslateX());
        a.coinsCollectedImg.setTranslateX(a.hero.getTranslateX());
        a.saveMenu.setTranslateX(a.hero.getTranslateX());
        a.weaponTab.setTranslateX(a.hero.getTranslateX());
        a.volumeButtons.setTranslateX(a.hero.getTranslateX());
        a.gameOverMenu.setTranslateX(a.hero.getTranslateX());
        a.resurrectMenu.setTranslateX(a.hero.getTranslateX());
        a.tapToStart.setTranslateX(a.hero.getTranslateX());
        a.freezeButton.setTranslateX(a.hero.getTranslateX());


        a.heroInGame.get(a.hero).setCurrentCoins(loadedHero.getCurrentCoins());
        a.heroInGame.get(a.hero).setScore(loadedHero.getScore());
        a.score.setText(Integer.toString(loadedHero.getScore()));
        a.coinsCollected.setText(Integer.toString(loadedHero.getCurrentCoins()));
    }

    @FXML
    protected void returnToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        // soundOnOff(event);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    protected void gameOver()
    {
        if(hero1.getCurrentCoins() >= 15)
        {
            if(!over && firstRessurect)
            {
                askForResurrect();
                over = true;
                firstRessurect = false;
            }

            else
                notResurrect(new ActionEvent());
        }

        else
            notResurrect(new ActionEvent());

    }

    @FXML
    protected void askForResurrect()
    {
        pauseGame(new ActionEvent());
        pausemenu.setDisable(true);
        pausemenu.setOpacity(0);
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
        if(MainMenuController.getSoundStatus()){
            Media media = new Media(Paths.get("src/main/resources/com/example/willherogui/gameOverSound.mp3").toUri().toString());
            MediaPlayer sound = new MediaPlayer(media);
            sound.setCycleCount(1);
            sound.setVolume(0.25);
            sound.play();

        }
        
    }

    public void previous(double position){
        hero.setTranslateY(0.0);
        game.setTranslateX(-1 * position);
        pauseButton.setTranslateX(position);
        pausemenu.setTranslateX(position);
        gameOverMenu.setTranslateX(position);
        saveMenu.setTranslateX(position);
        resurrectMenu.setTranslateX(position);
        weaponTab.setTranslateX(position);
        volumeButtons.setTranslateX(position);
        coinsCollected.setTranslateX(position);
        coinsCollectedImg.setTranslateX(position);
        freezeButton.setTranslateX(position);
        score.setTranslateX(position);
        hero.setTranslateX(position-70);
        helmet.setTranslateX(position-70);        
        score.setText(Integer.toString(previousScore));
    }

    @FXML
    protected void resurrect(ActionEvent event)
    {
        int c = Integer.valueOf(coinsCollected.getText());
        try
        {
            if(c >= 15)
            {
                c = c-15;
                coinsCollected.setText(Integer.toString(c));
                hero1.setCurrentCoins(c);
                resurrectMenu.setOpacity(0);
                resurrectMenu.setDisable(true);
                previous(heroXbeforeFalling);
                over = false;
                heroInGame.get(hero).setAlive(true);
                resumeGame(event);
            }
            else
            {
                throw new InsufficientCoinsException("NOT ENOUGH COINS");
            }
        }

        catch (InsufficientCoinsException e)
        {
            System.out.println(e.getMessage());
            notResurrect(event);
        }
    }

    public String getGameName()
    {
        return gameName;
    }


    public void checkWin(){
        if(!boss1.isAlive()){
            notwon=false;
            win();
        }

        else{
            previous(boss.getTranslateX());
        }
    }

    public void win()
    {   
        if(MainMenuController.getSoundStatus()){
            Media m = new Media(Paths.get("src/main/resources/com/example/willherogui/winSound.mp3").toUri().toString());
            MediaPlayer s = new MediaPlayer(m);
            s.setCycleCount(1);
            s.play();
        }
        
        winMenu.setOpacity(1);
        winMenu.setDisable(false);
        winCoinText.setText("You collected " + coinsCollected.getText() + " coins");
    }
}
