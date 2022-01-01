package com.example.willherogui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.Media;



public class MainMenuController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene; 
    private static MediaPlayer sound;
    private static boolean soundStatus = false;
//    private static boolean firstTime = true;
    @FXML
    private Pane instructions;
    @FXML
    private Button helpButton;

    @FXML
    private Button soundOn;
    @FXML
    private Button soundOff;


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {   
        if(sound== null){
            Media media = new Media(Paths.get("src/main/resources/com/example/willherogui/audio2.mp3").toUri().toString());
            sound = new MediaPlayer(media);
            sound.setCycleCount(AudioClip.INDEFINITE);
            sound.setVolume(0.5);
            
        }

        if(soundStatus)
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
    }
    
    public void onClickLoad(ActionEvent event) throws IOException{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadMenu.fxml")); 
        root = loader.load();
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        // sound.stop();
        stage.show();
    }

    public void onClickStart(ActionEvent event) throws IOException
    {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("game.fxml"));
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load(), 800, 500);
        stage.setScene(scene);
        // sound.stop();
        stage.show();
    }

    
    public void onClickQuit(ActionEvent event) {
        System.exit(0);

    }

    public void soundOnOff(ActionEvent event){
        soundStatus=!soundStatus;
        if(soundStatus){           
            sound.play();
            soundOn.setDisable(false);
            soundOn.setOpacity(1);
            soundOff.setDisable(true);
            soundOff.setOpacity(0);
        }
        else{
            sound.pause();
            soundOn.setDisable(true);
            soundOn.setOpacity(0);
            soundOff.setDisable(false);
            soundOff.setOpacity(1);
        }
    }

    public static void changeSound(){
        soundStatus=!soundStatus;

        if(soundStatus){
            sound.play();

        }
        else{
            sound.pause();

        }
    }

    public static boolean getSoundStatus()
    {
        return soundStatus;
    }

    @FXML
    public void showInstructionMenu(ActionEvent event)
    {
        instructions.setOpacity(1);
        instructions.setDisable(false);
    }

    public void closeHelpMenu(ActionEvent event)
    {
        instructions.setOpacity(0);
        instructions.setDisable(true);
    }
    
}
