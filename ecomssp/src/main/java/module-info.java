module com.example.ecomssp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ecomssp to javafx.fxml;
    exports com.example.ecomssp;
}