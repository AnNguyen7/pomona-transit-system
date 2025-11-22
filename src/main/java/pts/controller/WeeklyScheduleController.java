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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeeklyScheduleController {

    @FXML private TextField txtDriverName;
    @FXML private DatePicker datePickerStart;
    @FXML private TableView<TripOffering> tableSchedule;
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
    }

    @FXML
    private void handleSearch() {
        String driverName = txtDriverName.getText().trim();
        LocalDate startDate = datePickerStart.getValue();

        if (driverName.isEmpty() || startDate == null) {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            return;
        }

        // Calculate end date (7 days from start date)
        LocalDate endDate = startDate.plusDays(6);
        String startDateStr = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String endDateStr = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        ObservableList<TripOffering> offerings = FXCollections.observableArrayList();
        String query = "SELECT * FROM TripOffering " +
                       "WHERE DriverName = ? AND Date BETWEEN ? AND ? " +
                       "ORDER BY Date, ScheduledStartTime";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, driverName);
            pstmt.setString(2, startDateStr);
            pstmt.setString(3, endDateStr);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                offerings.add(new TripOffering(
                    rs.getInt("TripNumber"),
                    rs.getString("Date"),
                    rs.getString("ScheduledStartTime"),
                    rs.getString("ScheduledArrivalTime"),
                    rs.getString("DriverName"),
                    rs.getString("BusID")
                ));
            }

            tableSchedule.setItems(offerings);

            if (offerings.isEmpty()) {
                showAlert("Info", "No schedule found for " + driverName + " in the selected week", Alert.AlertType.INFORMATION);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load schedule: " + e.getMessage(), Alert.AlertType.ERROR);
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
