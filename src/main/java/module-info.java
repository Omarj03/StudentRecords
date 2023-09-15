module com.example.assignment3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.sql;


    opens com.example.finalproject to javafx.fxml;
    exports com.example.finalproject;
    exports model;
    opens model to javafx.fxml;
    exports control;
    opens control to javafx.fxml;
    opens view to javafx.fxml;
    exports view;


}