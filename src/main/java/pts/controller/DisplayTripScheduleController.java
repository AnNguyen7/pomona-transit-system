package pts.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DisplayTripScheduleController {

    @FXML
    private TextField txtStartLocation;

    @FXML
    private TextField txtDestination;

    @FXML
    private DatePicker datePicker;

    @FXML
    private void handleSearch() {
        String startLocation = txtStartLocation.getText().trim();
        String destination = txtDestination.getText().trim();
        LocalDate date = datePicker.getValue();

        // Validation
        if (startLocation.isEmpty() || destination.isEmpty() || date == null) {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Format date as YYYY-MM-DD
            String formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Load result window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DisplayTripScheduleResult.fxml"));
            Parent root = loader.load();

            // Pass data to result controller
            DisplayTripScheduleResultController resultController = loader.getController();
            resultController.loadSchedule(startLocation, destination, formattedDate);

            // Open result window
            Stage stage = new Stage();
            stage.setTitle("Trip Schedule Result");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load schedule: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleBackToHome() {
        Stage stage = (Stage) txtStartLocation.getScene().getWindow();
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
