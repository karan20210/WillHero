package com.example.willherogui;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Hero extends GameObjects implements Movable
{   
    // private Pane heroBox;
    private ImageView heroImg;
    private boolean alive;
    private int currentCoins;
    private int score;

    private Text coinsCollected;

    private ImageView tab1;
    private ImageView tab2;

    private Helmet currentHelmet;
    private Weapon currentWeapon=null;

    private TranslateTransition moveRight;
    private TranslateTransition hmoveRight;

    private TranslateTransition hJump;

    Hero(double x, double y)
    {
        super(x,y);
        alive = true;
        currentCoins = 0;
        score = 0;
    }

    public void setImg(ImageView heroImg){
        this.heroImg=heroImg;
    }

    public void setWeaponsTab(ImageView tab1,ImageView tab2){
        this.tab1=tab1;
        this.tab2=tab2;
    }

    public ImageView getImg(){
        return this.heroImg;
    }

    // public void setHeroBox(Pane heroBox){
    //     this.heroBox=heroBox;
    // }

    public void addCoinsText(Text coinsCollected){
        this.coinsCollected=coinsCollected;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;

    }

    public int getCurrentCoins() {
        return currentCoins;
    }

    public void setCurrentCoins(int currentCoins) {
        this.currentCoins = currentCoins;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCurrentHelmet(Helmet h){
        currentHelmet=h;


    }

    public void addCoins(int i)
    {
        currentCoins += i;
        coinsCollected.setText(Integer.toString(currentCoins));
    }

    public Weapon getCurrentWeapon(){
        return currentWeapon;
    }
    public void setCurrentWeapon(Weapon currentWeapon){
        this.currentWeapon=currentWeapon;
    }

    public boolean equalWeapon1(){
        return currentHelmet.getWeapon1()==getCurrentWeapon();

    }
    public boolean equalWeapon2(){
        return currentHelmet.getWeapon2()==getCurrentWeapon();

    }

    public void upgradeCurrentWeapon(){
        this.currentWeapon.upgrade();
    }

    public void newWeapon(String name){
        currentHelmet.NoVisibility();
        if(name.equals("Weapon1")){
            setCurrentWeapon(currentHelmet.getWeapon1());
            tab1.setOpacity(1);
            tab2.setOpacity(0.38);

            
        }
        else if(name.equals("Weapon2")){
            setCurrentWeapon(currentHelmet.getWeapon2());
            tab1.setOpacity(0.38);
            tab2.setOpacity(1);

        }
        currentWeapon.setVisibility(false, 1);
    }

    

    public void jump(){
        // moveRight = new TranslateTransition();
        // moveRight.setDuration(Duration.millis(100));
        // moveRight.setNode(heroBox);
        // moveRight.setByX(+50);
        // moveRight.setCycleCount(1);
        // moveRight.play();

        hmoveRight = new TranslateTransition();
        hmoveRight.setDuration(Duration.millis(100));
        hmoveRight.setNode(heroImg);
        hmoveRight.setByX(+50);
        hmoveRight.setCycleCount(1);
        hmoveRight.play();

    }

    public void resumeJump(){
        hJump.play();
        

    }

    public void pauseJump(){
        hJump.pause();
        if(hmoveRight!=null){
            hmoveRight.pause();
        }
      
        

    }

    @Override
    public void startMove(){
        heroImg.requestFocus();

        // TranslateTransition jump = new TranslateTransition();
        // jump.setDuration(Duration.millis(800));
        // jump.setNode(heroBox);
        // jump.setByY(-110);
        // jump.setCycleCount(Animation.INDEFINITE);
        // jump.setAutoReverse(true);
        // jump.setInterpolator(Interpolator.LINEAR);
        // jump.play();

        hJump = new TranslateTransition();
        hJump.setDuration(Duration.millis(800));
        hJump.setNode(heroImg);
        hJump.setByY(-110);
        hJump.setCycleCount(Animation.INDEFINITE);
        hJump.setAutoReverse(true);
        hJump.setInterpolator(Interpolator.LINEAR);
        hJump.play();

        // axeJump = new TranslateTransition();
        // axeJump.setDuration(Duration.millis(800));
        // axeJump.setNode(axe);
        // axeJump.setByY(-110);
        // axeJump.setCycleCount(Animation.INDEFINITE);
        // axeJump.setAutoReverse(true);
        // axeJump.setInterpolator(Interpolator.LINEAR);
        // axeJump.play();

        // knifeJump = new TranslateTransition();
        // knifeJump.setDuration(Duration.millis(800));
        // knifeJump.setNode(knife);
        // knifeJump.setByY(-110);
        // knifeJump.setCycleCount(Animation.INDEFINITE);
        // knifeJump.setAutoReverse(true);
        // knifeJump.setInterpolator(Interpolator.LINEAR);
        // knifeJump.play();
    }

    @Override
    public void fall(){
        hJump.stop();
        TranslateTransition fall = new TranslateTransition();
        fall.setDuration(Duration.millis(200));
        fall.setNode(heroImg);
        fall.setByY(400);
        fall.setCycleCount(1);
        fall.setInterpolator(Interpolator.LINEAR);
        fall.play();


         // TranslateTransition fallBox = new TranslateTransition();
        // jump.stop();
        // fallBox.setDuration(Duration.millis(100));
        // fallBox.setNode(heroBox);
        // fallBox.setByY(500);
        // fallBox.setCycleCount(1);
        // fallBox.setInterpolator(Interpolator.LINEAR);
        // fallBox.play();
    }
}
