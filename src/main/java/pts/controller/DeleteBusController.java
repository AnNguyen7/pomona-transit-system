package pts.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pts.database.DatabaseConnection;
import pts.model.Bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeleteBusController {

    @FXML private TextField txtBusID;
    @FXML private TableView<Bus> tableBuses;
    @FXML private TableColumn<Bus, String> colBusID;
    @FXML private TableColumn<Bus, String> colModel;
    @FXML private TableColumn<Bus, String> colYear;

    @FXML
    private void initialize() {
        colBusID.setCellValueFactory(new PropertyValueFactory<>("busID"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        loadAllBuses();
    }

    private void loadAllBuses() {
        ObservableList<Bus> buses = FXCollections.observableArrayList();
        String query = "SELECT * FROM Bus ORDER BY BusID";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                buses.add(new Bus(
                    rs.getString("BusID"),
                    rs.getString("Model"),
                    rs.getString("Year")
                ));
            }
            tableBuses.setItems(buses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        String busID = txtBusID.getText().trim();

        if (busID.isEmpty()) {
            showAlert("Error", "Please enter a bus ID", Alert.AlertType.ERROR);
            return;
        }

        // Confirm deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Bus " + busID);
        confirmAlert.setContentText("Are you sure you want to delete this bus? This action cannot be undone.");

        if (confirmAlert.showAndWait().get() != ButtonType.OK) {
            return;
        }

        String query = "DELETE FROM Bus WHERE BusID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, busID);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Bus deleted successfully!", Alert.AlertType.INFORMATION);
                txtBusID.clear();
                loadAllBuses();
            } else {
                showAlert("Error", "No bus found with ID: " + busID, Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("FOREIGN KEY constraint failed")) {
                showAlert("Error", "Cannot delete bus - it is being used in trip offerings!", Alert.AlertType.ERROR);
            } else {
                showAlert("Error", "Failed to delete bus: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleBackToHome() {
        Stage stage = (Stage) txtBusID.getScene().getWindow();
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
