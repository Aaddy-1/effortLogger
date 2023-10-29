import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Soundbank;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlanningPokerApp extends Application {

    private BorderPane borderPane;

    public class userStory {
        String title = "No title";
        String description = "No Description";

        public void setTitle(String newTitle) {
            this.title = newTitle;
        }
        public void setDescription(String newDescription) {
            this.description = newDescription;
        }
        public String getTitle() {
            return this.title;
        }
        public String getDescription() {
            return this.description;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        borderPane = new BorderPane();
        List<userStory> userStoryList= new ArrayList<userStory>();
        // These will be our main screens for now
        VBox planningPokerScreen = new VBox(new Text("Add Planning Poker here"));
        VBox mainMenu = new VBox(50, new Text("Main Menu Here"));
        VBox userStoryScreen = new VBox(50, new Text("Add user stories here"));

        // This is our user story screen
        Label titleLabel = new Label("Enter the title of the user story");
        TextField titleInput = new TextField();
        titleInput.setPrefSize(20, 30);
        Label descriptionLabel = new Label("Enter the description of the user story here");
        TextArea descriptionInput = new TextArea();
        Button addUserStoryButton = new Button("Add User Story");
        addUserStoryButton.setOnAction(e -> addUserStory(titleInput, descriptionInput, userStoryList));
        Button backButtonUserStory = new Button("Back");
        Button startPlanningPokerButton = new Button("Start Planning Poker");
        startPlanningPokerButton.setOnAction(e -> showScreen(planningPokerScreen, "Planning Poker", primaryStage));
        HBox buttonsUserStory = new HBox();
        buttonsUserStory.getChildren().addAll(backButtonUserStory, addUserStoryButton, startPlanningPokerButton);
        descriptionInput.setPrefSize(20, 80);
        userStoryScreen.getChildren().addAll(titleLabel, titleInput, descriptionLabel, descriptionInput, buttonsUserStory);
        userStoryScreen.setAlignment(Pos.CENTER);
        backButtonUserStory.setOnAction((e -> showScreen(mainMenu, "Main Menu", primaryStage)));

        // This is our main menu
        Button userStoryButton = new Button("Add user stories");
        userStoryButton.setPadding(new Insets(10));
        Button planningPokerButton = new Button("Start planning poker session");
        planningPokerButton.setPadding(new Insets(10));
        userStoryButton.setOnAction(e -> showScreen(userStoryScreen, "Adding user stories", primaryStage));
        mainMenu.getChildren().addAll(userStoryButton, planningPokerButton);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setPadding(new Insets(5));
        
        


        // Setting up the scene
        Scene scene = new Scene(borderPane, 500, 500);
        primaryStage.setTitle("Screen Change Example");
        primaryStage.setScene(scene);
        primaryStage.show();
        showScreen(mainMenu, "Main Menu", primaryStage);
    }
        
    private void showScreen(VBox screen, String title, Stage primaryStage) {
        primaryStage.setTitle(title);
        borderPane.setCenter(screen);
    }

    private void addUserStory(TextField titleBox, TextArea descriptionBox, List<userStory> list) {
        userStory newStory = new userStory();
        String title = titleBox.getText();
        String description = descriptionBox.getText();
        titleBox.clear();
        descriptionBox.clear();
        newStory.setTitle(title);
        newStory.setDescription(description);
        list.add(newStory);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getTitle());
        }
        System.out.println("========");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
