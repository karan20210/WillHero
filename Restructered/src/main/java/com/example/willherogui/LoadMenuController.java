package com.example.willherogui;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private Label noSaveFound;

    @FXML
    private Button soundOn;
    @FXML
    private Button soundOff;

    private ArrayList<HashMap<String, ArrayList<GameObjects>>> s;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
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
//            FileInputStream file = new FileInputStream("save.txt");
//            ObjectInputStream in = new ObjectInputStream(file);
//            ArrayList<HashMap<String, ArrayList<GameObjects>>> s = (ArrayList<HashMap<String, ArrayList<GameObjects>>>)in.readObject();
//            for(HashMap<String, ArrayList<GameObjects>> a: s) {
//                for(String i: a.keySet())
//                    Box.getChildren().add(new slot(i, s));
//            }
//            in.close();
//            file.close();

//            file = new FileInputStream("saveObj.txt");
//            in = new ObjectInputStream(file);
//            GUIController g = (GUIController) in.readObject();
//            System.out.println(g.getGameName());
//            in.close();
//            file.close();

            int no_of_saves = 0;
            FileInputStream file = new FileInputStream("save.txt");
            ObjectInputStream in = new ObjectInputStream(file);
            ArrayList<HashMap<GUIController, ArrayList<GameObjects>>> s = (ArrayList<HashMap<GUIController, ArrayList<GameObjects>>>)in.readObject();
            for(HashMap<GUIController, ArrayList<GameObjects>> a: s) {
                for(GUIController i: a.keySet())
                {
                    Box.getChildren().add(new slot(i.getGameName(), s));
                    no_of_saves++;
                }
            }
            in.close();
            file.close();

            if(no_of_saves == 0)
                throw new saveNotFoundException("No saves found");
        }

        catch (Exception e)
        {
            System.out.println(e);
            noSaveFound.setOpacity(1);
            noSaveFound.setDisable(false);
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

    @FXML
    protected void onClickClearAll(ActionEvent event)
    {
        Box.getChildren().clear();
        File savedFile = new File("save.txt");
        savedFile.delete();
        savedFile = new File("saveObj.txt");
        savedFile.delete();
    }
}

class slot extends Button {

    slot(String name, ArrayList<HashMap<GUIController, ArrayList<GameObjects>>> s) {
        HBox h = new HBox(50);
        h.setPrefWidth(200);
        h.setPrefHeight(30);

        Button labelname = new Button(name);
        labelname.setFont(new Font("Copperplate Gothic Bold", 12));
        labelname.setId("slot");

        labelname.setOnAction(new EventHandler<ActionEvent>() {
                                  @Override
                                  public void handle(ActionEvent event) {
                                      for (HashMap<GUIController, ArrayList<GameObjects>> a : s) {
                                          for (GUIController i : a.keySet()) {
                                              if (i.getGameName().equals(labelname.getText()))
                                              {
                                                  System.out.println("Loading... " + i);
                                                  for(ArrayList<GameObjects> go: a.values())
                                                  {
//                                                      GUIController.loadSavedGame(go);
                                                      try {
                                                          i.loadSavedGame(go, event);
                                                      } catch (IOException e) {
                                                          e.printStackTrace();
                                                      }
                                                  }

                                              }
                                          }
                                      }
                                  }
                              });

        h.getChildren().addAll(labelname);
        h.setAlignment(Pos.CENTER);

        setAlignment(Pos.CENTER);
        setGraphic(h);
        setPrefWidth(226);
        setPrefHeight(37);
        setId("slot");
    }
}