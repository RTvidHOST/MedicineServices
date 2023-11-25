package com.example.medicineservices;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DoctorScheduleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TextField doctor;

    @FXML
    private TextField time1;

    @FXML
    private TextField time2;

    @FXML
    void initialize() {
        addButton.setOnAction(event -> {
            AddMethod(event);
        });
        addButton.setOnAction(event -> {
            String DOCTOR = doctor.getText().trim();
            String TIME1 = time1.getText().trim();
            String TIME2 = time2.getText().trim();
            AddMethod(event);

            if (!DOCTOR.equals("") && !TIME1.equals("") && !TIME2.equals(""))
                AddMethod(event);
            else
                showAlert("Заполните поля");
        });
    }

    private void AddMethod(ActionEvent event) {
        String doctortext = doctor.getText();
        String time1text = time1.getText();
        String time2text = time2.getText();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "mysql");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM schedule WHERE doctor = ?");
            statement.setString(1, doctortext);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                showAlert("Расписание данного врача уже добавлено");
            } else {
                statement = connection.prepareStatement("INSERT INTO schedule (doctor, schedule, scheduleEND) VALUES (?, ?, ?)");
                statement.setString(1, doctortext);
                statement.setString(2, time1text);
                statement.setString(3, time2text);
                statement.executeUpdate();
                showAlert("Успешно добавлено");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}