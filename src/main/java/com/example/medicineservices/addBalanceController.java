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

public class addBalanceController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addBalanceButton;

    @FXML
    private TextField balanceText;

    @FXML
    void initialize() {
        addBalanceButton.setOnAction(event -> {
            addBalance();
        });
    }

    private void addBalance() {
        HelloController helloController = new HelloController();
        Double balance = Double.valueOf(balanceText.getText());

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "mysql");
            PreparedStatement statement = connection.prepareStatement("UPDATE balance SET balance = balance + " +
                    balance + " WHERE user = ?");
            statement.setString(1, helloController.getLog());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) balanceText.getScene().getWindow();
        stage.close();
    }

}