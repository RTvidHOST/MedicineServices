package com.example.medicineservices;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
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
                    "root", "1747");
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

}