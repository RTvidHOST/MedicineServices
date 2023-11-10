package com.example.medicineservices;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
public class UserPaneController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button add;
    @FXML
    private Button buscket;
    @FXML
    private TableColumn<service, String> name;
    @FXML
    private TableColumn<service, String> price;
    @FXML
    private TableView<service> service;
    ObservableList<service> serviceData = FXCollections.observableArrayList();
    @FXML
    void initialize() {
        try {
            initService();
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            service.setItems(serviceData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        add.setOnAction(event -> {
            addBasket(event);
        });
        buscket.setOnAction(event -> {
            openSecondWindow(event);
        });
    }
    private void initService() throws SQLException{
        ResultSet resultSet = dataService();
        while (resultSet.next()){
            serviceData.add(new service(resultSet.getString("name"),
                    resultSet.getString("price")));
        }
    }
    private void addBasket(ActionEvent event){
        HelloController helloController = new HelloController();
        service selectedData = service.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            String name = selectedData.getName().toString();
            String price = selectedData.getPrice().toString();
            String user = helloController.getLog();
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                        "root", "1747");
                PreparedStatement statement = connection.prepareStatement("INSERT INTO basket (name, price, user) VALUES (?, ?, ?)");
                statement.setString(1, name);
                statement.setString(2, price);
                statement.setString(3, user);
                statement.executeUpdate();
                showAlert("Добавлено в корзину");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public ResultSet dataService(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "1747");
            ResultSet resultSet = null;
            String select = "SELECT * FROM service";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(select);
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void openSecondWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("basketPane.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}