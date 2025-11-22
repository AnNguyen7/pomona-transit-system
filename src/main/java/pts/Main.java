package pts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pts.database.DatabaseConnection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize database
            DatabaseConnection.initializeDatabase();

            // Load home window
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Pomona Transit System - Database Manager");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        // Close database connection when application closes
        DatabaseConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
