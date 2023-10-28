import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlanningPokerApp extends Application {

    private BorderPane borderPane;

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        borderPane = new BorderPane();

        // These will be our main screens for now
        VBox planningPokerScreen = new VBox(new Text("Add Planning Poker here"));
        

        // This is our user story screen
        VBox userStoryScreen = new VBox(50, new Text("Add user stories here"));
        Label titleLabel = new Label("Enter the title of the user story");
        TextField titleInput = new TextField();
        Label descriptionLabel = new Label("Enter the description of the user story here");
        TextField descriptionInput = new TextField();

        userStoryScreen.getChildren().addAll(titleLabel, titleInput, descriptionLabel, descriptionInput);

        // These is our main menu
        VBox mainMenu = new VBox(50, new Text("Main Menu Here"));
        Button userStoryButton = new Button("Add user stories");
        userStoryButton.setPadding(new Insets(10));
        Button planningPokerButton = new Button("Start planning poker session");
        planningPokerButton.setPadding(new Insets(10));
        userStoryButton.setOnAction(e -> showScreen(userStoryScreen, "Adding user stories", primaryStage));
        mainMenu.getChildren().addAll(userStoryButton, planningPokerButton);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setPadding(new Insets(20));


        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setTitle("Screen Change Example");
        primaryStage.setScene(scene);
        primaryStage.show();
        showScreen(mainMenu, "Main Menu", primaryStage);
    }
        
    private void showScreen(VBox screen, String title, Stage primaryStage) {
        primaryStage.setTitle(title);
        borderPane.setCenter(screen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
