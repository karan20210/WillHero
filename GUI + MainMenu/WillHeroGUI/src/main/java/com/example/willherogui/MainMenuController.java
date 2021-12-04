package com.example.willherogui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene; 

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
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

    }

    
    public void onClickQuit(ActionEvent event) {
        System.exit(0);

    }

    
    
}
