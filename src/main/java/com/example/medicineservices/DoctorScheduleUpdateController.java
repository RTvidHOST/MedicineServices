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

public class DoctorScheduleUpdateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TextField DOCTOR;

    @FXML
    private TextField time1;

    @FXML
    private TextField time2;

    private Doctor2 Doctor2;

    @FXML
    void initialize() {
        addButton.setOnAction(event -> {
            saveData();
        });
        addButton.setOnAction(event -> {
            String doctor = DOCTOR.getText().trim();
            String TIME1 = time1.getText().trim();
            String TIME2 = time2.getText().trim();
            saveData();

            if (!doctor.equals("") && !TIME1.equals("") && !TIME2.equals(""))
                saveData();
            else
                showAlert("Заполните поля");
        });
    }

    public void setDoctor(Doctor2 Doctor2){
        this.Doctor2 = Doctor2;

        DOCTOR.setText(Doctor2.getDoctor());
        time1.setText(Doctor2.getSchedule());
        time2.setText(Doctor2.getScheduleEND());
    }

    private void saveData() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "mysql");
            PreparedStatement statement = connection.prepareStatement("UPDATE schedule SET schedule = " +
                    "'" + time1.getText() + "'" + " WHERE doctor = ?");
            statement.setString(1, Doctor2.getDoctor());
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement("UPDATE schedule SET scheduleEND = " +
                    "'" + time2.getText() + "'" + " WHERE doctor = ?");
            statement1.setString(1, Doctor2.getDoctor());
            statement1.executeUpdate();
            PreparedStatement statement2 = connection.prepareStatement("UPDATE schedule SET doctor = " +
                    "'" + DOCTOR.getText() + "'" + " WHERE doctor = ?");
            statement2.setString(1, Doctor2.getDoctor());
            statement2.executeUpdate();
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