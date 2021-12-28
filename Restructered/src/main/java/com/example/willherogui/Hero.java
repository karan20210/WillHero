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
    private transient ImageView heroImg;
    private boolean alive;
    private int currentCoins;
    private int score;

    private transient Text coinsCollected;

    private transient ImageView tab1;
    private transient ImageView tab2;

    private Helmet currentHelmet;
    private Weapon currentWeapon=null;

//    private TranslateTransition moveRight;
    private transient TranslateTransition hmoveRight, helmetJump, helmetMoveRight;

    private transient TranslateTransition hJump;

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

    

    public void moveRight(Pane game){
        hmoveRight = new TranslateTransition();
        hmoveRight.setDuration(Duration.millis(100));
        hmoveRight.setNode(heroImg);
        hmoveRight.setByX(+50);
        hmoveRight.setCycleCount(1);
        hmoveRight.play();

        helmetMoveRight = new TranslateTransition();
        helmetMoveRight.setDuration(Duration.millis(100));
        helmetMoveRight.setNode(currentHelmet.getHelmetImg());
        helmetMoveRight.setByX(+50);
        helmetMoveRight.setCycleCount(1);
        helmetMoveRight.play();
        score++;

//        if(currentWeapon!=null){
//            currentWeapon.shoot(this);
//        }
    }

    public void weaponShoot()
    {
        if(currentWeapon!=null){
            currentWeapon.shoot(this);
        }
    }
    public void resumeJump(){   
        hJump.play();
        if(helmetJump!=null)
            helmetJump.play();
    }

    public void pauseJump(){
        hJump.pause();
        if(hmoveRight!=null){
            hmoveRight.pause();
        }
        if(helmetMoveRight!=null)
            helmetMoveRight.pause();
        if(helmetJump!=null)
            helmetJump.pause();
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
        
        helmetJump = new TranslateTransition(Duration.millis(800), currentHelmet.getHelmetImg());
        helmetJump.setByY(-110);
        helmetJump.setCycleCount(Animation.INDEFINITE);
        helmetJump.setAutoReverse(true);
        helmetJump.setInterpolator(Interpolator.LINEAR);
        helmetJump.play();
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

        // helmetJump.stop();

        // TranslateTransition helmetfall = new TranslateTransition();
        // helmetfall.setDuration(Duration.millis(200));
        // helmetfall.setNode(currentHelmet.getHelmetImg());
        // helmetfall.setByY(400);
        // helmetfall.setCycleCount(1);
        // helmetfall.setInterpolator(Interpolator.LINEAR);
        // helmetfall.play();


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
