import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PlanningPokerApp extends Application {

    private BorderPane borderPane;
    private VBox mainMenu;
    private Stage primaryStage, estimationStage1, estimationStage2, estimationStage3, estimationStage4;
    private int estimation1;
    private int estimation2;
    private int estimation3;
    private int estimation4;

    public class UserStory {
        String title = "No title";
        String description = "No Description";

        public UserStory(String title, String description) {
            this.title = title;
            this.description = description;
        }

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
        this.primaryStage = primaryStage;
        // Create UI components
        borderPane = new BorderPane();
        // These will be our main screens for now
        mainMenu = new VBox(50, new Text("EffortLoggerV2.0 Main Menu"));
        VBox userStoryScreen = new VBox(50, new Text("Add a User Story"));

        // This is our user story screen
        Label titleLabel = new Label("Enter the title of the user story");
        TextField titleInput = new TextField();
        titleInput.setPrefSize(20, 30);
        Label descriptionLabel = new Label("Enter the description of the user story here");
        TextArea descriptionInput = new TextArea();
        Button startPlanningPokerButton = new Button("Start Planning Poker");
        startPlanningPokerButton.setOnAction(e -> {
            UserStory userStory = createUserStory(titleInput, descriptionInput);
            startPlanningPoker(userStory);
        });
        Button backButtonUserStory = new Button("Back");
        backButtonUserStory.setOnAction((e -> showScreen(mainMenu, "Main Menu")));
        HBox buttonsUserStory = new HBox();
        buttonsUserStory.getChildren().addAll(backButtonUserStory, startPlanningPokerButton);
        descriptionInput.setPrefSize(20, 80);
        userStoryScreen.getChildren().addAll(titleLabel, titleInput, descriptionLabel, descriptionInput, buttonsUserStory);
        userStoryScreen.setAlignment(Pos.CENTER);

        // This is our main menu
        Button planningPokerButton = new Button("Start planning poker session");
        planningPokerButton.setPadding(new Insets(10));
        planningPokerButton.setOnAction(e -> showScreen(userStoryScreen, "Adding user stories"));
        Button riskReduction1 = new Button("Risk Reduction Prototype 1");
        riskReduction1.setPadding(new Insets(10));
        Button riskReduction2 = new Button("Risk Reduction Prototype 2");
        riskReduction1.setPadding(new Insets(10));
        Button riskReduction3 = new Button("Risk Reduction Prototype 3");
        riskReduction3.setPadding(new Insets(10));
        Button riskReduction4 = new Button("Risk Reduction Prototype 4");
        riskReduction4.setPadding(new Insets(10));
        mainMenu.getChildren().addAll(planningPokerButton, riskReduction1, riskReduction2, riskReduction3, riskReduction4);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setPadding(new Insets(5));

        // Setting up the scene
        Scene scene = new Scene(borderPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        showScreen(mainMenu, "Main Menu");
    }
        
    private void showScreen(VBox screen, String title) {
        primaryStage.setTitle(title);
        borderPane.setCenter(screen);
    }

    private void startPlanningPoker(UserStory userStory) {
        renderPlanningPokerMainStage();

        Screen screen = Screen.getPrimary();
        Rectangle2D screenBounds = screen.getVisualBounds();
        estimationStage1 = createPlanningPokerEstimationStage(userStory, screenBounds.getMinX() + 20, screenBounds.getMinY() + 20, 1);
        estimationStage2 = createPlanningPokerEstimationStage(userStory, screenBounds.getMaxX() - 320, screenBounds.getMinY() + 20, 2);
        estimationStage3 = createPlanningPokerEstimationStage(userStory, screenBounds.getMinX() + 20, screenBounds.getMaxY() - 220, 3);
        estimationStage4 = createPlanningPokerEstimationStage(userStory, screenBounds.getMaxX() - 320, screenBounds.getMaxY() - 220, 4);
    }

    private void renderPlanningPokerMainStage() {
        int estimationCount = 0;
        if (estimation1 != 0) estimationCount++;
        if (estimation2 != 0) estimationCount++;
        if (estimation3 != 0) estimationCount++;
        if (estimation4 != 0) estimationCount++;

        Label label;
        Button quitButton = new Button("Quit Planning Poker");
        if (estimationCount == 4) {
            if (estimation1 == estimation2 && estimation2 == estimation3 && estimation3 == estimation4) {
                label = new Label("Session Successfull, estimation: " + estimation1);
                quitButton = new Button("Finish Planning Poker");
            }
            else
                label = new Label("Estimations are not same, discuss and adjust estimations");
        } else
            label = new Label("Total Estimations Made: " + estimationCount);
        quitButton.setOnAction(e -> quitPlanningPoker());
        VBox vbox = new VBox(50, label, quitButton);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        showScreen(vbox, "Planning Poker Session");
    }

    private void quitPlanningPoker() {
        showScreen(mainMenu, "Main Menu");
        estimationStage1.close();
        estimationStage2.close();
        estimationStage3.close();
        estimationStage4.close();
    }

    private Stage createPlanningPokerEstimationStage(UserStory userStory, double x, double y, int stageIdx) {
        Stage stage = new Stage();
        stage.setTitle("Planning Poker Session " + stageIdx);

        Label titleText = new Label("Title: " + userStory.getTitle());
        Label descriptionText = new Label("Description: " + userStory.getDescription());

        Label estimationLabel = new Label("Estimation (1-10) hrs:");
        ComboBox<Integer> estimationComboBox = new ComboBox<>();
        for (int i = 1; i <= 10; i++) {
            estimationComboBox.getItems().add(i);
        }
        estimationComboBox.setValue(1);

        estimationComboBox.setOnAction(e -> {
            int selectedValue = estimationComboBox.getValue();
            if (stageIdx == 1) estimation1 = selectedValue;
            else if (stageIdx == 2) estimation2 = selectedValue;
            else if (stageIdx == 3) estimation3 = selectedValue;
            else if (stageIdx == 4) estimation4 = selectedValue;
            renderPlanningPokerMainStage();
        });

        VBox layout = new VBox(50, titleText, descriptionText, estimationLabel, estimationComboBox);

        Scene scene = new Scene(layout, 300, 300);
        stage.setScene(scene);
        stage.setX(x);
        stage.setY(y);
        stage.show();

        return stage;
    }

    private UserStory createUserStory(TextField titleBox, TextArea descriptionBox) {
        String title = titleBox.getText();
        String description = descriptionBox.getText();
        titleBox.clear();
        descriptionBox.clear();
        UserStory userStory = new UserStory(title, description);

        return userStory;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
