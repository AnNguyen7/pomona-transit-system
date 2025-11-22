package pts.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private void handleDisplaySchedule() {
        openWindow("/fxml/DisplayTripSchedule.fxml", "Display Trip Schedule");
    }

    @FXML
    private void handleEditSchedule() {
        openWindow("/fxml/EditScheduleMenu.fxml", "Edit Trip Schedule");
    }

    @FXML
    private void handleDisplayStops() {
        openWindow("/fxml/DisplayStops.fxml", "Display Stops");
    }

    @FXML
    private void handleWeeklySchedule() {
        openWindow("/fxml/WeeklySchedule.fxml", "Weekly Schedule");
    }

    @FXML
    private void handleAddDriver() {
        openWindow("/fxml/AddDriver.fxml", "Add Driver");
    }

    @FXML
    private void handleAddBus() {
        openWindow("/fxml/AddBus.fxml", "Add Bus");
    }

    @FXML
    private void handleDeleteBus() {
        openWindow("/fxml/DeleteBus.fxml", "Delete Bus");
    }

    @FXML
    private void handleActualTripData() {
        openWindow("/fxml/AddActualTripData.fxml", "Add Actual Trip Stop Info");
    }

    /**
     * Helper method to open new windows
     */
    protected void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("Error opening window: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
