package com.example.medicineservices;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class AdminUpdateDoctorController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addButton;
    @FXML
    private TextField cabinet;
    @FXML
    private TextField DOCTOR;
    private Doctor Doctor;
    @FXML
    void initialize() {
        addButton.setOnAction(event -> {
            saveData();
        });
        addButton.setOnAction(event -> {
            String doctor = DOCTOR.getText().trim();
            String CABINET = cabinet.getText().trim();
            saveData();

            if (!doctor.equals("") && !CABINET.equals(""))
                saveData();
            else
                showAlert("Заполните поля");
        });
    }
     public void setDoctor(Doctor Doctor){
        this.Doctor = Doctor;

        DOCTOR.setText(Doctor.getDoctor());
        cabinet.setText(Doctor.getCabinet());
    }
    private void saveData() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "mysql");
            PreparedStatement statement = connection.prepareStatement("UPDATE doctors SET cabinet = " +
                    "'" + cabinet.getText() + "'" + " WHERE doctor = ?");
            statement.setString(1, Doctor.getDoctor());
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement("UPDATE doctors SET doctor = " +
                    "'" + DOCTOR.getText() + "'" + " WHERE doctor = ?");
            statement1.setString(1, Doctor.getDoctor());
            statement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) DOCTOR.getScene().getWindow();
        stage.close();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}