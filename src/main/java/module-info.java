module com.example.luxurycampsitegui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.luxurycampsitegui to javafx.fxml;
    exports com.example.luxurycampsitegui;
}