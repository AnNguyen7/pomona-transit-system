package pts.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pts.database.DatabaseConnection;
import pts.model.TripOffering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DisplayTripScheduleResultController {

    @FXML
    private Text txtStartLocation;

    @FXML
    private Text txtDestination;

    @FXML
    private Text txtDate;

    @FXML
    private TableView<TripOffering> tableSchedule;

    @FXML
    private TableColumn<TripOffering, String> colStartTime;

    @FXML
    private TableColumn<TripOffering, String> colArrivalTime;

    @FXML
    private TableColumn<TripOffering, String> colDriver;

    @FXML
    private TableColumn<TripOffering, String> colBus;

    @FXML
    private void initialize() {
        // Set up table columns
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("scheduledStartTime"));
        colArrivalTime.setCellValueFactory(new PropertyValueFactory<>("scheduledArrivalTime"));
        colDriver.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colBus.setCellValueFactory(new PropertyValueFactory<>("busID"));
    }

    public void loadSchedule(String startLocation, String destination, String date) {
        // Update header texts
        txtStartLocation.setText("Start Location: " + startLocation);
        txtDestination.setText("Destination Location: " + destination);
        txtDate.setText("Date: " + date);

        // Load data from database
        ObservableList<TripOffering> offerings = FXCollections.observableArrayList();

        String query = "SELECT T1.ScheduledStartTime, T1.ScheduledArrivalTime, T1.DriverName, T1.BusID " +
                       "FROM TripOffering AS T1 " +
                       "JOIN Trip AS T2 ON T1.TripNumber = T2.TripNumber " +
                       "WHERE T2.StartLocationName = ? AND T2.DestinationName = ? AND T1.Date = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, startLocation);
            pstmt.setString(2, destination);
            pstmt.setString(3, date);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                TripOffering offering = new TripOffering();
                offering.setScheduledStartTime(rs.getString("ScheduledStartTime"));
                offering.setScheduledArrivalTime(rs.getString("ScheduledArrivalTime"));
                offering.setDriverName(rs.getString("DriverName"));
                offering.setBusID(rs.getString("BusID"));
                offerings.add(offering);
            }

            tableSchedule.setItems(offerings);

            if (offerings.isEmpty()) {
                System.out.println("No schedules found for the given criteria.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading schedule: " + e.getMessage());
        }
    }

    @FXML
    private void handleBackToHome() {
        Stage stage = (Stage) tableSchedule.getScene().getWindow();
        stage.close();
    }
}
