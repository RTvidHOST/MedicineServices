module com.example.medicineservices {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.medicineservices to javafx.fxml;
    exports com.example.medicineservices;
}