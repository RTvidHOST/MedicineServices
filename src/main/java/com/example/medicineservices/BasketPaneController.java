package com.example.medicineservices;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class BasketPaneController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button del;
    @FXML
    private Button addBalance;
    @FXML
    private Text balance;
    @FXML
    private DatePicker picker;
    @FXML
    private Button pickerButton;
    @FXML
    private Button refresh;
    @FXML
    private TableColumn<Basket, String> name;
    @FXML
    private TableColumn<Basket, Double> price;
    @FXML
    private Text priceAll;
    @FXML
    private Button priceCalculator;
    @FXML
    private TableView<Basket> serviceTable;
    ObservableList<Basket> basketData = FXCollections.observableArrayList();
    @FXML
    void initialize() {
        refrashWindow();
        balance.setText(balance1);
        try {
            initService();
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            serviceTable.setItems(basketData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        del.setOnAction(event -> {
            deleteBasket();
        });
        priceCalculator.setOnAction(event -> {
            double total = calculateTotal();
            priceAll.setText(String.valueOf(total));
        });
        addBalance.setOnAction(event -> {
            openSecondWindow(event);
        });
        refresh.setOnAction(event -> {
            refrashWindow();
            balance.setText(balance1);
        });
        pickerButton.setOnAction(event -> {
            HelloController helloController = new HelloController();
            if (picker.getValue() == null){
                showAlert("Выберете дату, на котрую хотите осуществить запись");
            }
            else {
                LocalDate selectedDate = picker.getValue();
                selectedDateString = selectedDate.toString();
                double bal = Double.parseDouble(balance.getText());
                double price = Double.parseDouble(priceAll.getText());
                if (bal < price){
                    showAlert("Пополните баланс!");
                }
                else {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                                "root", "mysql");
                        PreparedStatement statement = connection.prepareStatement("INSERT INTO userpicker (user, date) VALUES (?, ?)");
                        statement.setString(1, helloController.getLog());
                        statement.setDate(2, Date.valueOf(selectedDateString));
                        statement.executeUpdate();
                        PreparedStatement statement1 = connection.prepareStatement("UPDATE balance SET balance = balance - "
                                + price + "WHERE user = ?");
                        statement1.setString(1, helloController.getLog());
                        statement1.executeUpdate();
                        refrashWindow();
                        balance.setText(balance1);
                        showAlert("Вы записаны на " + selectedDateString);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    showAlert("Успешно");
                }
            }
        });
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    String selectedDateString;
    String balance1;
    private String refrashWindow() {
        HelloController helloController = new HelloController();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "mysql");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT balance FROM balance WHERE user = " + "'" + helloController.getLog() + "'");
            while (resultSet.next()){
                balance1 = resultSet.getString("balance");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return balance1;
    }
    @FXML
    private void openSecondWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addBalance.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private double calculateTotal() {
        double total = 0;
        for (Basket basket : basketData){
            total += basket.getPrice();
        }
        return total;
    }
    private void deleteBasket(){
        HelloController helloController = new HelloController();
        Basket selectedData = serviceTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            String name = selectedData.getName();
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                        "root", "mysql");
                PreparedStatement statement = connection.prepareStatement("DELETE FROM basket WHERE name = ? and user = ?");
                statement.setString(1, name);
                statement.setString(2, helloController.getLog());
                statement.executeUpdate();
                serviceTable.getItems().remove(selectedData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void initService() throws SQLException{
        ResultSet resultSet = dataBasket();
        while (resultSet.next()){
            basketData.add(new Basket(resultSet.getString("name"),
                    resultSet.getDouble("price")));
        }
    }
    private ResultSet dataBasket() {
        HelloController helloController = new HelloController();
        String username = helloController.getLog();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "mysql");
            ResultSet resultSet = null;
            String select = "SELECT * FROM basket WHERE user = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(select);
                preparedStatement.setString(1, username);
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
