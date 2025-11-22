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

public class AddBusController {

    @FXML private TextField txtBusID;
    @FXML private TextField txtModel;
    @FXML private TextField txtYear;
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
    private void handleAddBus() {
        String busID = txtBusID.getText().trim();
        String model = txtModel.getText().trim();
        String year = txtYear.getText().trim();

        if (busID.isEmpty() || model.isEmpty() || year.isEmpty()) {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            return;
        }

        String query = "INSERT INTO Bus (BusID, Model, Year) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, busID);
            pstmt.setString(2, model);
            pstmt.setString(3, year);
            pstmt.executeUpdate();

            showAlert("Success", "Bus added successfully!", Alert.AlertType.INFORMATION);
            txtBusID.clear();
            txtModel.clear();
            txtYear.clear();
            loadAllBuses();

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                showAlert("Error", "Bus with this ID already exists!", Alert.AlertType.ERROR);
            } else {
                showAlert("Error", "Failed to add bus: " + e.getMessage(), Alert.AlertType.ERROR);
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
