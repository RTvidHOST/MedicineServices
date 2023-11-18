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
public class adminUpdateController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addButton;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    private service service;
    @FXML
    void initialize() {
        addButton.setOnAction(event -> {
            saveData();
        });
    }
    public void setService(service service){
        this.service = service;

        name.setText(service.getName());
        price.setText(service.getPrice());
    }
    public void saveData(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "mysql");
            PreparedStatement statement = connection.prepareStatement("UPDATE service SET price = " +
                    "'" + price.getText() + "'" + " WHERE name = ?");
            statement.setString(1, service.getName());
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement("UPDATE service SET name = " +
                    "'" + name.getText() + "'" + " WHERE name = ?");
            statement1.setString(1, service.getName());
            statement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }
}
