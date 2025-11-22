package pts.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pts.database.DatabaseConnection;
import pts.model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddDriverController {

    @FXML private TextField txtDriverName;
    @FXML private TextField txtPhoneNumber;
    @FXML private TableView<Driver> tableDrivers;
    @FXML private TableColumn<Driver, String> colDriverName;
    @FXML private TableColumn<Driver, String> colPhoneNumber;

    @FXML
    private void initialize() {
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("driverTelephoneNumber"));
        loadAllDrivers();
    }

    private void loadAllDrivers() {
        ObservableList<Driver> drivers = FXCollections.observableArrayList();
        String query = "SELECT * FROM Driver ORDER BY DriverName";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                drivers.add(new Driver(
                    rs.getString("DriverName"),
                    rs.getString("DriverTelephoneNumber")
                ));
            }
            tableDrivers.setItems(drivers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddDriver() {
        String driverName = txtDriverName.getText().trim();
        String phoneNumber = txtPhoneNumber.getText().trim();

        if (driverName.isEmpty() || phoneNumber.isEmpty()) {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            return;
        }

        String query = "INSERT INTO Driver (DriverName, DriverTelephoneNumber) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, driverName);
            pstmt.setString(2, phoneNumber);
            pstmt.executeUpdate();

            showAlert("Success", "Driver added successfully!", Alert.AlertType.INFORMATION);
            txtDriverName.clear();
            txtPhoneNumber.clear();
            loadAllDrivers();

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                showAlert("Error", "Driver with this name already exists!", Alert.AlertType.ERROR);
            } else {
                showAlert("Error", "Failed to add driver: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleBackToHome() {
        Stage stage = (Stage) txtDriverName.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
