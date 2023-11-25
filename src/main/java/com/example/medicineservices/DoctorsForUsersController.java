package com.example.medicineservices;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DoctorsForUsersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Doctor, String> cabinet1;

    @FXML
    private TableColumn<Doctor, String> doctor1;

    @FXML
    private TableColumn<Doctor2, String> doctor2;

    @FXML
    private TableView<Doctor> doctorTable;

    @FXML
    private TableView<Doctor2> scheduleTable;

    @FXML
    private TableColumn<Doctor2, String> time2;

    @FXML
    private TableColumn<Doctor2, String> time22;
    ObservableList<Doctor> doctorsData1 = FXCollections.observableArrayList();
    ObservableList<Doctor2> doctorsData2 = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            initDoctor1();
            doctor1.setCellValueFactory(new PropertyValueFactory<>("doctor"));
            cabinet1.setCellValueFactory(new PropertyValueFactory<>("cabinet"));
            doctorTable.setItems(doctorsData1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            initDoctor2();
            doctor2.setCellValueFactory(new PropertyValueFactory<>("doctor"));
            time2.setCellValueFactory(new PropertyValueFactory<>("schedule"));
            time22.setCellValueFactory(new PropertyValueFactory<>("scheduleEND"));
            scheduleTable.setItems(doctorsData2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initDoctor2() throws SQLException{
        ResultSet resultSet = dataDoctor2();
        while (resultSet.next()){
            doctorsData2.add(new Doctor2(resultSet.getString("doctor"),
                    resultSet.getString("schedule"), resultSet.getString("scheduleEND")));
        }
    }

    private ResultSet dataDoctor2() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "1747");
            ResultSet resultSet = null;
            String select = "SELECT * FROM schedule";
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

    private void initDoctor1() throws SQLException{
        ResultSet resultSet = dataDoctor1();
        while (resultSet.next()){
            doctorsData1.add(new Doctor(resultSet.getString("doctor"),
                    resultSet.getString("cabinet")));
        }
    }

    private ResultSet dataDoctor1() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "1747");
            ResultSet resultSet = null;
            String select = "SELECT * FROM doctors";
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