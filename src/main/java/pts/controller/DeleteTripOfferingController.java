package pts.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pts.database.DatabaseConnection;
import pts.model.TripOffering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;

public class DeleteTripOfferingController {

    @FXML private TextField txtTripNumber;
    @FXML private DatePicker datePicker;
    @FXML private TextField txtStartTime;
    @FXML private TableView<TripOffering> tableOfferings;
    @FXML private TableColumn<TripOffering, Integer> colTripNumber;
    @FXML private TableColumn<TripOffering, String> colDate;
    @FXML private TableColumn<TripOffering, String> colStartTime;
    @FXML private TableColumn<TripOffering, String> colArrivalTime;
    @FXML private TableColumn<TripOffering, String> colDriver;
    @FXML private TableColumn<TripOffering, String> colBus;

    @FXML
    private void initialize() {
        colTripNumber.setCellValueFactory(new PropertyValueFactory<>("tripNumber"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("scheduledStartTime"));
        colArrivalTime.setCellValueFactory(new PropertyValueFactory<>("scheduledArrivalTime"));
        colDriver.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colBus.setCellValueFactory(new PropertyValueFactory<>("busID"));
        loadAllOfferings();
    }

    private void loadAllOfferings() {
        ObservableList<TripOffering> offerings = FXCollections.observableArrayList();
        String query = "SELECT * FROM TripOffering ORDER BY Date, ScheduledStartTime";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                TripOffering offering = new TripOffering(
                    rs.getInt("TripNumber"),
                    rs.getString("Date"),
                    rs.getString("ScheduledStartTime"),
                    rs.getString("ScheduledArrivalTime"),
                    rs.getString("DriverName"),
                    rs.getString("BusID")
                );
                offerings.add(offering);
            }
            tableOfferings.setItems(offerings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemove() {
        String tripNum = txtTripNumber.getText().trim();
        String startTime = txtStartTime.getText().trim();

        if (tripNum.isEmpty() || datePicker.getValue() == null || startTime.isEmpty()) {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            return;
        }

        String date = datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
        
        // Normalize time format to match database (e.g., "1:00pm" -> "01:00pm")
        startTime = normalizeTimeFormat(startTime);
        
        String query = "DELETE FROM TripOffering WHERE TripNumber = ? AND Date = ? AND ScheduledStartTime = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, Integer.parseInt(tripNum));
            pstmt.setString(2, date);
            pstmt.setString(3, startTime);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Trip offering deleted successfully!", Alert.AlertType.INFORMATION);
                txtTripNumber.clear();
                datePicker.setValue(null);
                txtStartTime.clear();
                loadAllOfferings();
            } else {
                showAlert("Error", "No matching trip offering found", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private String normalizeTimeFormat(String time) {
        // Handle formats like "1:00pm" -> "01:00pm" or "9:30am" -> "09:30am"
        if (time.matches("^\\d{1}:\\d{2}[ap]m$")) {
            return "0" + time;
        }
        return time;
    }

    @FXML
    private void handleBackToHome() {
        Stage stage = (Stage) tableOfferings.getScene().getWindow();
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
