package com.example.medicineservices;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminAddDoctorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TextField cabinet;

    @FXML
    private TextField doctor;

    @FXML
    void initialize() {
        addButton.setOnAction(event -> {
            String DOCTOR = doctor.getText().trim();
            String CABINET = cabinet.getText().trim();

            if (!DOCTOR.equals("") && !CABINET.equals(""))
                AddMethod(event);
            else
                showAlert("Заполните поля");
        });
    }

    private void AddMethod(ActionEvent event) {
        String nametext = doctor.getText();
        String pricetext = cabinet.getText();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "mysql");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM doctors WHERE doctor = ?");
            statement.setString(1, nametext);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                showAlert("Врач уже добавлен");
            } else {
                statement = connection.prepareStatement("INSERT INTO doctors (doctor, cabinet) VALUES (?, ?)");
                statement.setString(1, nametext);
                statement.setString(2, pricetext);
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