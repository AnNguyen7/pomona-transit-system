package pts.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pts.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;

public class AddActualTripDataController {

    @FXML private TextField txtTripNumber;
    @FXML private DatePicker datePicker;
    @FXML private TextField txtScheduledStartTime;
    @FXML private TextField txtStopNumber;
    @FXML private TextField txtScheduledArrivalTime;
    @FXML private TextField txtActualStartTime;
    @FXML private TextField txtActualArrivalTime;
    @FXML private TextField txtPassengerIn;
    @FXML private TextField txtPassengerOut;

    @FXML
    private void handleSubmit() {
        // Validate all fields
        if (!validateFields()) {
            return;
        }

        String date = datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);

        String query = "INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, " +
                       "ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, Integer.parseInt(txtTripNumber.getText().trim()));
            pstmt.setString(2, date);
            pstmt.setString(3, txtScheduledStartTime.getText().trim());
            pstmt.setInt(4, Integer.parseInt(txtStopNumber.getText().trim()));
            pstmt.setString(5, txtScheduledArrivalTime.getText().trim());
            pstmt.setString(6, txtActualStartTime.getText().trim());
            pstmt.setString(7, txtActualArrivalTime.getText().trim());
            pstmt.setInt(8, Integer.parseInt(txtPassengerIn.getText().trim()));
            pstmt.setInt(9, Integer.parseInt(txtPassengerOut.getText().trim()));

            pstmt.executeUpdate();

            showAlert("Success", "Actual trip stop info added successfully!", Alert.AlertType.INFORMATION);
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("FOREIGN KEY constraint failed")) {
                showAlert("Error", "Invalid Trip Offering or Stop Number. Please check your input.", Alert.AlertType.ERROR);
            } else if (e.getMessage().contains("PRIMARY KEY constraint failed") || 
                       e.getMessage().contains("UNIQUE constraint failed")) {
                showAlert("Error", "This actual trip stop info already exists in the database.", Alert.AlertType.ERROR);
            } else {
                showAlert("Error", "Failed to add actual trip data: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validateFields() {
        if (txtTripNumber.getText().trim().isEmpty() ||
            datePicker.getValue() == null ||
            txtScheduledStartTime.getText().trim().isEmpty() ||
            txtStopNumber.getText().trim().isEmpty() ||
            txtScheduledArrivalTime.getText().trim().isEmpty() ||
            txtActualStartTime.getText().trim().isEmpty() ||
            txtActualArrivalTime.getText().trim().isEmpty() ||
            txtPassengerIn.getText().trim().isEmpty() ||
            txtPassengerOut.getText().trim().isEmpty()) {

            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            return false;
        }

        try {
            Integer.parseInt(txtTripNumber.getText().trim());
            Integer.parseInt(txtStopNumber.getText().trim());
            Integer.parseInt(txtPassengerIn.getText().trim());
            Integer.parseInt(txtPassengerOut.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Error", "Trip Number, Stop Number, and Passenger counts must be valid numbers", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void clearFields() {
        txtTripNumber.clear();
        datePicker.setValue(null);
        txtScheduledStartTime.clear();
        txtStopNumber.clear();
        txtScheduledArrivalTime.clear();
        txtActualStartTime.clear();
        txtActualArrivalTime.clear();
        txtPassengerIn.clear();
        txtPassengerOut.clear();
    }

    @FXML
    private void handleBackToHome() {
        Stage stage = (Stage) txtTripNumber.getScene().getWindow();
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
