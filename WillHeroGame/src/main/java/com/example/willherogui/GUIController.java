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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    private Pane gameOverMenu;
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
    private ImageView coinChest;
    @FXML
    private ImageView weaponChest;
    @FXML
    private ImageView p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20;
    @FXML
    private Pane a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14;
    @FXML
    private TextField saveGameName;

    @FXML
    private Pane heroBox;


    //Transitions
    TranslateTransition jump,fall, moveRight, sceneMove, pauseMenuMove, pauseButtonMove, scoreMove, orcJump, coinsCollectedMove, coinsCollectedImgMove, saveMenuMove, weaponTabMove, volOnMove, gameOverMenuMove;
    ArrayList<TranslateTransition> orcJumps = new ArrayList<TranslateTransition>();
    RotateTransition rotate;
    FadeTransition ft;

    //Useful boolean values
    boolean firstJump = true;
    boolean pauseMenuActive = false;

    // Collections
    private ArrayList<HBox> coins;
    private ArrayList<ImageView> orcs;
    HashMap<ImageView, Coin> coinsInGame = new HashMap<>();
    HashMap<ImageView, CoinChest> coinChestsInGame = new HashMap<>();
    HashMap<ImageView, WeaponChest> weaponChestsInGame = new HashMap<>();
    HashMap<ImageView, Island> islandsInGame = new HashMap<>();
    HashMap<Pane, Abyss> abyssInGame = new HashMap<>();

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
                        }

                        else
                        {
                            AxeTab.setOpacity(0.38);
                            KnifeTab.setOpacity(1);
                        }
                        i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/OpenWeaponChest.png").toUri().toString()));
                    }
                }
            }
        }
    };


    AnimationTimer abyssCollision = new AnimationTimer() {
        @Override
        public void handle(long l) {
            for (Pane i : abyssInGame.keySet()) {
                if (i.getBoundsInParent().intersects(heroBox.getBoundsInParent())) {
                    jump.stop();
                    fall = new TranslateTransition();
                    fall.setDuration(Duration.millis(100));
                    fall.setNode(heroBox);
                    fall.setByY(100);
                    fall.setCycleCount(1);
                    fall.setInterpolator(Interpolator.LINEAR);
                    fall.play();
                    gameOver();
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

        coins = new ArrayList<HBox>();
        Collections.addAll(coins, coins_1, coins_2, coins_3, coins_4);

        orcs = new ArrayList<ImageView>();
        Collections.addAll(orcs, orc_1, orc_2, orc_3);

        AxeTab.setOpacity(0.38);
        KnifeTab.setOpacity(0.38);
        coinChestsInGame.put(coinChest, new CoinChest(coinChest.getLayoutX(), coinChest.getLayoutY()));
        weaponChestsInGame.put(weaponChest, new WeaponChest(weaponChest.getLayoutX(), weaponChest.getLayoutY()));

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
        chestCollision.start();
        abyssCollision.start();
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
        if (firstJump && !pauseMenuActive) {
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
            orcJump();
            coinRotate();
        }
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
        ft.stop();
        if (event.getCode() == KeyCode.SPACE && !pauseMenuActive && !firstJump) {
            moveRight = new TranslateTransition();
            moveRight.setDuration(Duration.millis(100));
            moveRight.setNode(heroBox);
            moveRight.setByX(+50);
            moveRight.setCycleCount(1);
            moveRight.play();

            int s = Integer.valueOf(score.getText());
            s++;
            score.setText(Integer.toString(s));

            pauseButtonMove = new TranslateTransition();
            pauseButtonMove.setDuration(Duration.millis(150));
            pauseButtonMove.setNode(pauseButton);
            pauseButtonMove.setByX(50);
            pauseButtonMove.setCycleCount(1);
            pauseButtonMove.play();

            sceneMove = new TranslateTransition();
            sceneMove.setDuration(Duration.millis(150));
            sceneMove.setNode(game);
            sceneMove.setByX(-50);
            sceneMove.setCycleCount(1);
            sceneMove.play();

            pauseMenuMove = new TranslateTransition();
            pauseMenuMove.setDuration(Duration.millis(150));
            pauseMenuMove.setNode(pausemenu);
            pauseMenuMove.setByX(50);
            pauseMenuMove.setCycleCount(1);
            pauseMenuMove.play();

            scoreMove = new TranslateTransition();
            scoreMove.setDuration(Duration.millis(150));
            scoreMove.setNode(score);
            scoreMove.setByX(50);
            scoreMove.setCycleCount(1);
            scoreMove.play();

            coinsCollectedMove = new TranslateTransition();
            coinsCollectedMove.setDuration(Duration.millis(150));
            coinsCollectedMove.setNode(coinsCollected);
            coinsCollectedMove.setByX(50);
            coinsCollectedMove.setCycleCount(1);
            coinsCollectedMove.play();

            coinsCollectedImgMove = new TranslateTransition();
            coinsCollectedImgMove.setDuration(Duration.millis(150));
            coinsCollectedImgMove.setNode(coinsCollectedImg);
            coinsCollectedImgMove.setByX(50);
            coinsCollectedImgMove.setCycleCount(1);
            coinsCollectedImgMove.play();

            saveMenuMove = new TranslateTransition();
            saveMenuMove.setDuration(Duration.millis(150));
            saveMenuMove.setNode(saveMenu);
            saveMenuMove.setByX(50);
            saveMenuMove.setCycleCount(1);
            saveMenuMove.play();

            weaponTabMove = new TranslateTransition();
            weaponTabMove.setDuration(Duration.millis(150));
            weaponTabMove.setNode(weaponTab);
            weaponTabMove.setByX(50);
            weaponTabMove.setCycleCount(1);
            weaponTabMove.play();

            volOnMove = new TranslateTransition();
            volOnMove.setDuration(Duration.millis(150));
            volOnMove.setNode(volumeButtons);
            volOnMove.setByX(50);
            volOnMove.setCycleCount(1);
            volOnMove.play();

            gameOverMenuMove = new TranslateTransition();
            gameOverMenuMove.setDuration(Duration.millis(150));
            gameOverMenuMove.setNode(gameOverMenu);
            gameOverMenuMove.setByX(50);
            gameOverMenuMove.setCycleCount(1);
            gameOverMenuMove.play();
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
        initialize(u, r);
        game.requestFocus();
        pausemenu.setOpacity(0);
        pausemenu.setDisable(true);
        score.setText("0");

        gameOverMenu.setDisable(true);
        gameOverMenu.setOpacity(0);

        if (jump != null)
            jump.stop();
        if (moveRight != null)
            moveRight.stop();

        heroBox.setTranslateX(0.0);
        heroBox.setTranslateY(0.0);
        game.setTranslateX(0.0);
        pauseButton.setTranslateX(0.0);
        pausemenu.setTranslateX(0.0);
        gameOverMenu.setTranslateX(0.0);
        score.setTranslateX(0.0);
        saveMenu.setTranslateX(0.0);
        saveMenu.setTranslateY(0.0);
        coinsCollectedImg.setTranslateX(0.0);
        coinsCollected.setTranslateX(0.0);
        weaponTab.setTranslateX(0.0);
        tapToStart.setTranslateX(0.0);
//        soundOn.setTranslateX(0.0);
//        soundOff.setTranslateX(0.0);
        volumeButtons.setTranslateX(0.0);
        instruction.setText("Click to start!");

        for(ImageView i: orcs)
        {
            i.setTranslateY(0.0);
        }

        for(ImageView i: coinChestsInGame.keySet())
            i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/CoinChest.png").toUri().toString()));
        for(ImageView i: weaponChestsInGame.keySet())
            i.setImage(new Image(Paths.get("src/main/resources/com/example/willherogui/Images/WeaponChest.png").toUri().toString()));

        firstJump = true;
        pauseMenuActive = false;

        if(orcJump!=null)
            orcJump.stop();
        tapToStart.setOpacity(1);
        ft.play();

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
        gameOverMenu.setOpacity(1);
        gameOverMenu.setDisable(false);
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
