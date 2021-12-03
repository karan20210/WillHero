import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent p = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene= new Scene(p);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Will Hero");
        primaryStage.show();
     

        
        
    }

    
}
