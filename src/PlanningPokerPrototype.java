import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlanningPokerPrototype extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Label titleLabel = new Label("Planning Poker");
        Label storyLabel = new Label("User Story:");
        TextField storyInput = new TextField();
        Button addStoryButton = new Button("Add User Story");
        Button estimateButton = new Button("Estimate");
        ListView<String> userStoriesList = new ListView<>();
        ListView<String> estimatesList = new ListView<>();

        // Set up the layout
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(titleLabel, storyLabel, storyInput, addStoryButton, estimateButton, userStoriesList, estimatesList);

        // Handle add user story button click
        addStoryButton.setOnAction(e -> {
            String userStory = storyInput.getText();
            if (!userStory.isEmpty()) {
                userStoriesList.getItems().add(userStory);
                storyInput.clear();
            }
        });

        // Handle estimate button click
        estimateButton.setOnAction(e -> {
            String selectedUserStory = userStoriesList.getSelectionModel().getSelectedItem();
            if (selectedUserStory != null) {
                // In a real application, this is where the estimation logic would go.
                // For now, we'll just display a dummy value for demonstration purposes.
                String estimationResult = "5"; // Dummy value
                estimatesList.getItems().add("User Story: " + selectedUserStory + " - Estimation: " + estimationResult);
            }
        });

        // Set up the scene
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Planning Poker Prototype");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
