module com.example.willherogui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.willherogui to javafx.fxml;
    exports com.example.willherogui;
}