package com.example.willherogui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;



public class LoadMenuController {
    // @FXML
    // Pane Box;



    private Parent root;
    private Stage stage;
    private Scene scene; 



    public void onClickGoBack(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();       
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,600,400);
        stage.setScene(scene);
        stage.show(); 

    }
    
    
}


// class savedGame extends StackPane{
    
//     savedGame(String name, String date){
//         Rectangle  r = new Rectangle();
//         r.setId("savedGame");
//         r.prefWidth(400);
//         r.prefHeight(60);

//         HBox h = new HBox(50);
//         Label labelname= new Label(name);
//         labelname.prefHeight(50);
//         labelname.prefWidth(200);
//         Label labeldate= new Label(date);
//         labelname.prefHeight(50);
//         labelname.prefWidth(50);

//         h.getChildren().addAll(labelname,labeldate);

//         getChildren().addAll(r,h);       


//     }

// }
