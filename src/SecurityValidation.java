import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SecurityValidation extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Security Validation Prototype");

        // Create UI elements
        Label usernameLabel = new Label("Enter Username:");
        TextField usernameInput = new TextField();
        Button validateButton = new Button("Validate Access");
        Label resultLabel = new Label();

        // Set action for the validation button
        validateButton.setOnAction(e -> {
            String enteredUsername = usernameInput.getText();  
            String expectedUsername = removeSpecialCharacters(enteredUsername);

            // Check if the entered username contains any spaces or special characters
            if (enteredUsername.matches(".*\\s.*") || !enteredUsername.matches("[a-zA-Z0-9]+")) {
                resultLabel.setText("Access Denied: Username contains spaces or special characters.");
            } else if (expectedUsername.equals(enteredUsername)) {
                resultLabel.setText("Access Granted");
            } else {
                resultLabel.setText("Access Denied");
            }
        });

        // Create layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameLabel, usernameInput, validateButton, resultLabel);

        // Set the scene
        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    // A helper method to remove special characters and spaces from the username
    private String removeSpecialCharacters(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }
}