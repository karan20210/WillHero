package com.example.willherogui;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class LoadMenuController implements Initializable {
    @FXML
    private Pane Box;
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button soundOn;
    @FXML
    private Button soundOff;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
//        Box.getChildren().add(new slot("KARAN2","08-12-2021"));
//        Box.getChildren().add(new slot("KARAN1","10-08-2021"));
//        Box.getChildren().add(new slot("JAIN1","04-12-2021"));
//        Box.getChildren().add(new slot("JAIN2","20-10-2021"));
//        Box.getChildren().add(new slot("Baboota1","24-12-2021"));
//        Box.getChildren().add(new slot("Bhavya2","27-10-2021"));

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

        try
        {
            FileInputStream file = new FileInputStream("save.txt");
            ObjectInputStream in = new ObjectInputStream(file);
            ArrayList<HashMap<String, ArrayList<GameObjects>>> s = (ArrayList<HashMap<String, ArrayList<GameObjects>>>)in.readObject();
//            System.out.println(s);
            for(HashMap<String, ArrayList<GameObjects>> a: s) {
                for(String i: a.keySet())
                    Box.getChildren().add(new slot(i));
            }
            in.close();
            file.close();
        }

        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    public void onClickGoBack(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,800,500);
        stage.setScene(scene);
        stage.show();

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
    }
}

class slot extends Button {

    slot(String name) {
        HBox h = new HBox(50);
        h.setPrefWidth(200);
        h.setPrefHeight(30);

        Label labelname = new Label(name);
        labelname.setFont(new Font("Copperplate Gothic Bold", 12));

        h.getChildren().addAll(labelname);
        h.setAlignment(Pos.CENTER);

        setAlignment(Pos.CENTER);
        setGraphic(h);
        setPrefWidth(226);
        setPrefHeight(37);
        setId("slot");
    }
}