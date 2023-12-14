package com.example.medicineservices;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
public class AdminDoctorController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addDoctor;
    @FXML
    private Button addSchedule;
    @FXML
    private TableColumn<Doctor, String> cabinet1;
    @FXML
    private Button delDoctor;
    @FXML
    private Button delSchedule;
    @FXML
    private TableColumn<Doctor, String> doctor1;
    @FXML
    private TableView<Doctor> doctorTable;
    @FXML
    private Button refresh;
    @FXML
    private TableView<Doctor2> scheduleTable;
    @FXML
    private TableColumn<Doctor2, String> doctor2;
    @FXML
    private TableColumn<Doctor2, String> time2;
    @FXML
    private TableColumn<Doctor2, String> time22;
    @FXML
    private Button updateDoctor;
    @FXML
    private Button updateSchedule;
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
        addDoctor.setOnAction(event -> {
            openAddDoctorWindow();
        });
        refresh.setOnAction(event -> {
            doctorTable.setItems(doctorsData1);
            scheduleTable.setItems(doctorsData2);
            refreshTable();
        });
        delDoctor.setOnAction(event -> {
            deleteDoctor();
        });
        updateDoctor.setOnAction(event -> {
            openEditDoctorWindow();
        });
        delSchedule.setOnAction(event -> {
            deleteSchedule();
        });
        addSchedule.setOnAction(event -> {
            openAddScheduleWindow();
        });
        updateSchedule.setOnAction(event -> {
            openEditScheduleWindow();
        });
    }
    private void openEditScheduleWindow() {
        Doctor2 selectedData = scheduleTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorScheduleUpdate.fxml"));
        try {
            Parent root = loader.load();
            DoctorScheduleUpdateController updateController = loader.getController();
            updateController.setDoctor(selectedData);
            doctorTable.getItems();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void openAddScheduleWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorSchedule.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteSchedule() {
        Doctor2 selectedData = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            String login = selectedData.getDoctor();
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                        "root", "mysql");
                PreparedStatement statement = connection.prepareStatement("DELETE FROM schedule WHERE doctor = ?");
                statement.setString(1, login);
                statement.executeUpdate();
                scheduleTable.getItems().remove(selectedData);

            } catch (SQLException e) {
                e.printStackTrace();
            }
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
                    "root", "mysql");
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
    private void openEditDoctorWindow() {
        Doctor selectedData = doctorTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminUpdateDoctor.fxml"));
        try {
            Parent root = loader.load();
            AdminUpdateDoctorController updateController = loader.getController();
            updateController.setDoctor(selectedData);
            doctorTable.getItems();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void deleteDoctor() {
        Doctor selectedData = doctorTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            String login = selectedData.getDoctor();
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                        "root", "mysql");
                PreparedStatement statement = connection.prepareStatement("DELETE FROM doctors WHERE doctor = ?");
                statement.setString(1, login);
                statement.executeUpdate();
                doctorTable.getItems().remove(selectedData);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void refreshTable() {
        doctorsData1.clear();
        doctorsData2.clear();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "mysql");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM doctors");
            while (resultSet.next()){
                String doctor = resultSet.getString("doctor");
                String cabinet = resultSet.getString("cabinet");

                doctorsData1.add(new Doctor(doctor, cabinet));
            }
            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM schedule");
            while (resultSet1.next()){
                String doctor = resultSet1.getString("doctor");
                String schedule = resultSet1.getString("schedule");
                String scheduleEND = resultSet1.getString("scheduleEND");

                doctorsData2.add(new Doctor2(doctor, schedule, scheduleEND));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void openAddDoctorWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminAddDoctor.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
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
                    "root", "mysql");
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
