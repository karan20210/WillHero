package com.example.willherogui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.Media;



public class MainMenuController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene; 
    private MediaPlayer sound;
    private boolean soundStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        Media media = new Media(Paths.get("src/main/resources/com/example/willherogui/audio.mp3").toUri().toString());
        sound = new MediaPlayer(media); 
        soundStatus=true;
        sound.play();

    }
    
    public void onClickLoad(ActionEvent event) throws IOException{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadMenu.fxml")); 
        root = loader.load();
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 

    }

    public void onClickStart(ActionEvent event) throws IOException
    {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("game.fxml"));
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load(), 800, 500);
        stage.setScene(scene);
        stage.show();

    }

    
    public void onClickQuit(ActionEvent event) {
        System.exit(0);

    }

    public void soundOnOff(ActionEvent event){        
        // Media media = new Media("/src/main/resources/com/example/willherogui/audio.mp3");         
        soundStatus=!soundStatus;
        if(soundStatus){           
            sound.play();  
        }
        else{
            sound.stop();
        }
        
        
    }

    
    
}
