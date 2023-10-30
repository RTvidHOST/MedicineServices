package com.example.medicineservices;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminPaneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addService;

    @FXML
    private Button delService;

    @FXML
    private Button delUser;

    @FXML
    private TableColumn<User, String> login;

    @FXML
    private TableColumn<service, String> nameService;

    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<service, String> price;

    @FXML
    private TableView<service> serviceTable;

    @FXML
    private Button updateService;

    @FXML
    private Button refresh;

    @FXML
    private TableView<User> usersTable;
    ObservableList<User> usersData = FXCollections.observableArrayList();
    ObservableList<service> serviceData = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            initUsers();
            login.setCellValueFactory(new PropertyValueFactory<>("login"));
            password.setCellValueFactory(new PropertyValueFactory<>("password"));
            usersTable.setItems(usersData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            initService();
            nameService.setCellValueFactory(new PropertyValueFactory<>("name"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            serviceTable.setItems(serviceData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        delUser.setOnAction(event -> {
            deleteUser();
        });
        delService.setOnAction(event -> {
            deleteService();
        });
        updateService.setOnAction(event -> {
            openEditWindow();
        });
        refresh.setOnAction(event -> {
            serviceTable.setItems(serviceData);
            refreshTable();
        });
        addService.setOnAction(event -> {
            openAddWindow(event);
        });
    }

    private void refreshTable() {
        serviceData.clear();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "1747");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM service");
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String price = resultSet.getString("price");

                serviceData.add(new service(name, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void openAddWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminAdd.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openEditWindow(){
        service selectedData = serviceTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminUpdate.fxml"));
        try {
            Parent root = loader.load();
            adminUpdateController updateController = loader.getController();
            updateController.setService(selectedData);
            serviceTable.getItems();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteUser(){
        User selectedData = usersTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            String login = selectedData.getLogin();
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                        "root", "1747");
                PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE login = ?");
                statement.setString(1, login);
                statement.executeUpdate();
                usersTable.getItems().remove(selectedData);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteService(){
        service selectedData = serviceTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            String name = selectedData.getName();
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                        "root", "1747");
                PreparedStatement statement = connection.prepareStatement("DELETE FROM service WHERE name = ?");
                statement.setString(1, name);
                statement.executeUpdate();
                serviceTable.getItems().remove(selectedData);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void initService() throws SQLException{
        ResultSet resultSet = dataService();
        while (resultSet.next()){
            serviceData.add(new service(resultSet.getString("name"),
                    resultSet.getString("price")));
        }
    }

    private void initUsers() throws SQLException{
        ResultSet resultSet = dataUsers();
        while (resultSet.next()){
            usersData.add(new User(resultSet.getString("login"),
                    resultSet.getString("password")));
        }
    }

    private ResultSet dataUsers() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "1747");
            ResultSet resultSet = null;
            String select = "SELECT * FROM users";
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

    private ResultSet dataService() {
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
}