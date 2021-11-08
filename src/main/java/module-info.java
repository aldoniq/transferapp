module com.example.transferapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires Java.WebSocket;


    opens com.example.transferapp to javafx.fxml;
    exports com.example.transferapp;
}