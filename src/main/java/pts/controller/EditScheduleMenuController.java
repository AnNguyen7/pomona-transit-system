package pts.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditScheduleMenuController {

    @FXML
    private void handleDeleteOffering() {
        openWindow("/fxml/DeleteTripOffering.fxml", "Delete Trip Offering");
    }

    @FXML
    private void handleAddOfferings() {
        openWindow("/fxml/AddTripOffering.fxml", "Add Trip Offerings");
    }

    @FXML
    private void handleChangeDriver() {
        openWindow("/fxml/ChangeDriver.fxml", "Change Driver");
    }

    @FXML
    private void handleChangeBus() {
        openWindow("/fxml/ChangeBus.fxml", "Change Bus");
    }

    @FXML
    private void handleBackToHome(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void openWindow(String fxmlPath, String title) {
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
