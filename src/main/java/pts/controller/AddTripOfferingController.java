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
import java.util.ArrayList;
import java.util.List;

public class AddTripOfferingController {

    @FXML private TextField txtTripNumber;
    @FXML private DatePicker datePicker;
    @FXML private TextField txtStartTime;
    @FXML private TextField txtArrivalTime;
    @FXML private TextField txtDriverName;
    @FXML private TextField txtBusID;
    @FXML private TextArea txtAddedData;
    @FXML private TableView<TripOffering> tableOfferings;
    @FXML private TableColumn<TripOffering, Integer> colTripNumber;
    @FXML private TableColumn<TripOffering, String> colDate;
    @FXML private TableColumn<TripOffering, String> colStartTime;
    @FXML private TableColumn<TripOffering, String> colArrivalTime;
    @FXML private TableColumn<TripOffering, String> colDriver;
    @FXML private TableColumn<TripOffering, String> colBus;

    private List<TripOffering> offeringsToAdd = new ArrayList<>();

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
                offerings.add(new TripOffering(
                    rs.getInt("TripNumber"),
                    rs.getString("Date"),
                    rs.getString("ScheduledStartTime"),
                    rs.getString("ScheduledArrivalTime"),
                    rs.getString("DriverName"),
                    rs.getString("BusID")
                ));
            }
            tableOfferings.setItems(offerings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddMore() {
        if (!validateFields()) return;

        String date = datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
        TripOffering offering = new TripOffering(
            Integer.parseInt(txtTripNumber.getText().trim()),
            date,
            txtStartTime.getText().trim(),
            txtArrivalTime.getText().trim(),
            txtDriverName.getText().trim(),
            txtBusID.getText().trim()
        );

        offeringsToAdd.add(offering);
        txtAddedData.appendText(String.format("Added: %d, %s, %s, %s, %s, %s\n",
            offering.getTripNumber(), offering.getDate(), offering.getScheduledStartTime(),
            offering.getScheduledArrivalTime(), offering.getDriverName(), offering.getBusID()));

        clearFields();
    }

    @FXML
    private void handleSubmit() {
        if (offeringsToAdd.isEmpty()) {
            showAlert("Error", "Please add at least one trip offering", Alert.AlertType.ERROR);
            return;
        }

        String query = "INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (TripOffering offering : offeringsToAdd) {
                pstmt.setInt(1, offering.getTripNumber());
                pstmt.setString(2, offering.getDate());
                pstmt.setString(3, offering.getScheduledStartTime());
                pstmt.setString(4, offering.getScheduledArrivalTime());
                pstmt.setString(5, offering.getDriverName());
                pstmt.setString(6, offering.getBusID());
                pstmt.executeUpdate();
            }

            showAlert("Success", offeringsToAdd.size() + " trip offering(s) added!", Alert.AlertType.INFORMATION);
            offeringsToAdd.clear();
            txtAddedData.clear();
            loadAllOfferings();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add offerings: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validateFields() {
        if (txtTripNumber.getText().trim().isEmpty() || datePicker.getValue() == null ||
            txtStartTime.getText().trim().isEmpty() || txtArrivalTime.getText().trim().isEmpty() ||
            txtDriverName.getText().trim().isEmpty() || txtBusID.getText().trim().isEmpty()) {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtTripNumber.clear();
        datePicker.setValue(null);
        txtStartTime.clear();
        txtArrivalTime.clear();
        txtDriverName.clear();
        txtBusID.clear();
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
