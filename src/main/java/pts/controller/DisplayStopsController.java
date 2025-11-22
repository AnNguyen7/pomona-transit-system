package pts.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pts.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DisplayStopsController {

    @FXML private TextField txtTripNumber;
    @FXML private TableView<StopInfo> tableStops;
    @FXML private TableColumn<StopInfo, Integer> colTripNumber;
    @FXML private TableColumn<StopInfo, Integer> colStopNumber;
    @FXML private TableColumn<StopInfo, Integer> colSequence;
    @FXML private TableColumn<StopInfo, String> colDrivingTime;
    @FXML private TableColumn<StopInfo, String> colStopAddress;

    @FXML
    private void initialize() {
        colTripNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().tripNumber).asObject());
        colStopNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().stopNumber).asObject());
        colSequence.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().sequenceNumber).asObject());
        colDrivingTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().drivingTime));
        colStopAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().stopAddress));
    }

    @FXML
    private void handleSearch() {
        String tripNum = txtTripNumber.getText().trim();

        if (tripNum.isEmpty()) {
            showAlert("Error", "Please enter a trip number", Alert.AlertType.ERROR);
            return;
        }

        ObservableList<StopInfo> stops = FXCollections.observableArrayList();
        String query = "SELECT T1.TripNumber, T1.StopNumber, T1.SequenceNumber, T1.DrivingTime, T2.StopAddress " +
                       "FROM TripStopInfo AS T1 " +
                       "JOIN Stop AS T2 ON T1.StopNumber = T2.StopNumber " +
                       "WHERE T1.TripNumber = ? " +
                       "ORDER BY T1.SequenceNumber";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, Integer.parseInt(tripNum));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                stops.add(new StopInfo(
                    rs.getInt("TripNumber"),
                    rs.getInt("StopNumber"),
                    rs.getInt("SequenceNumber"),
                    rs.getString("DrivingTime"),
                    rs.getString("StopAddress")
                ));
            }

            tableStops.setItems(stops);

            if (stops.isEmpty()) {
                showAlert("Info", "No stops found for trip number: " + tripNum, Alert.AlertType.INFORMATION);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load stops: " + e.getMessage(), Alert.AlertType.ERROR);
        }
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

    public static class StopInfo {
        private final int tripNumber;
        private final int stopNumber;
        private final int sequenceNumber;
        private final String drivingTime;
        private final String stopAddress;

        public StopInfo(int tripNumber, int stopNumber, int sequenceNumber, String drivingTime, String stopAddress) {
            this.tripNumber = tripNumber;
            this.stopNumber = stopNumber;
            this.sequenceNumber = sequenceNumber;
            this.drivingTime = drivingTime;
            this.stopAddress = stopAddress;
        }
    }
}
