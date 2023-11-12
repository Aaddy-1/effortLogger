package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private String currentUser;
    private ArrayList<UserStory> userStoryList = new ArrayList<UserStory>();

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

    // Author: Aadeesh Sharma
    // Historical Data object which contains the title, description, and time taken
    public class HistoricalData {
        private String title = "No Title";
        private String description = "No Description";
        private int time = -1;

        // Getter and Setter methods
        public String getTitle() {
            return this.title;
        }
        public String getDescription() {
            return this.description;
        }
        public int getTime() {
            return this.time;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public void setTime(int time) {
            this.time = time;
        }
    }
    
    // Author: Aadeesh Sharma
    // This class contains the username and historical data of a user
    public class UserData {
        String username = "No Username";
        // The historical data is stored as a list
        ArrayList<HistoricalData> historicalData = null;

        // Getter and Setter methods
        public String getUsername() {
            return this.username;
        }
        public ArrayList<HistoricalData> getData() {
            return this.historicalData;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public void setData(ArrayList<HistoricalData> data) {
            this.historicalData = data;
        }
    }

    // Author: Aadeesh Sharma
    // This function imports historical data of the given from the JSON file
    public UserData importHistoricalData(String username) {
        // Creating a new UserData class for the user
        UserData userData = new UserData();
        String filePath = "historicalProjects.json";
        // Our list of previously completed historical projects
        ArrayList<HistoricalData> readData = new ArrayList<HistoricalData>();
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        userData.setUsername(username);
        // Read JSON file as JsonNode
        try {
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            // We loop through the file until we find the required user
            for (JsonNode userNode : jsonNode.get("users")) {
                String currUser = userNode.get("username").asText();
    
                // Check if the current user is the target user
                if (currUser.equals(username)) {
                    System.out.println("Found Username: " + username);
    
                    // Iterate through each project for the target user
                    for (JsonNode projectNode : userNode.get("projects")) {
                        // We create a new HistoricalData object for each previous project
                        HistoricalData data = new HistoricalData();
                        String title = projectNode.get("title").asText();
                        data.setTitle(title);
                        String description = projectNode.get("description").asText();
                        data.setDescription(description);
                        int timeTaken = projectNode.get("timeTaken").asInt();
                        data.setTime(timeTaken);
                        
                        readData.add(data);
                        // Process project data
                        // System.out.println("  Project Title: " + title);
                        // System.out.println("  Description: " + description);
                        // System.out.println("  Time Taken: " + timeTaken + " hours");
                        // System.out.println();
                    }
                    break;
                }
            }
            // Adding the list of previous projects to our userData object
            userData.setData(readData);
        }   
        catch(IOException e) {
            e.printStackTrace();
        }
        
        return userData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // Create UI components
        borderPane = new BorderPane();

        // Creating the logic for adding user stories
        // Importing the interface
        UserStoryUI userStoryInterface = new UserStoryUI();
        // Getting the VBox from the interface, this contains all of our UI elements
        VBox userStoryScreen = userStoryInterface.getVBox();
        // This button will add the entered user story to the list
        Button addButtonUserStory = userStoryInterface.getAddButton();
        addButtonUserStory.setOnAction(e -> {
            TextField title = userStoryInterface.getTitle();
            TextArea description = userStoryInterface.getDescription();
            UserStory newStory = createUserStory(title, description);
            userStoryInterface.onAddClick(title, description);
            // Creating a new user story object and adding it to the list
            userStoryList.add(newStory);
            for (int i = 0; i < userStoryList.size(); i++) {
                System.err.println("Yay");
                System.out.println(userStoryList.get(i).getTitle());
            }
        });
        // To go back to the main menu
        Button backButtonUserStory = userStoryInterface.getBackButton();
        backButtonUserStory.setOnAction(e -> {
            showScreen(mainMenu, "EffortLoggerV2.0 Main Menu");
        });
        // This button will be added to the main menu in order to access this menu
        Button addUserStoriesButton = new Button("Add User Stories");
        addUserStoriesButton.setPadding(new Insets(10));
        addUserStoriesButton.setOnAction(e -> showScreen(userStoryScreen, "Adding user stories"));

        // This button will start planning poker
        Button planningPokerButton = new Button("Start planning poker session");
        planningPokerButton.setPadding(new Insets(10));
        planningPokerButton.setOnAction(e -> showScreen(userStoryScreen, "Adding user stories"));
        
        // Scalability Analyzer Risk Reduction Prototype
        Button scalabilityAnalyzerButton = new Button("Scalability Analyzer");
        scalabilityAnalyzerButton.setPadding(new Insets(10));
        scalabilityAnalyzerButton.setOnAction(e -> {
            ScalabilityAnalyzer scalabilityAnalyzer = new ScalabilityAnalyzer();
            scalabilityAnalyzer.start(new Stage());
        });

        // Anonymity Verifier Risk Reduction Prototype
        Button anonymityVerifierButton = new Button("Anonymity Verifier");
        anonymityVerifierButton.setPadding(new Insets(10));
        anonymityVerifierButton.setOnAction(e -> {
            AnonymityVerifier anonymityVerifier = new AnonymityVerifier();
            anonymityVerifier.start(new Stage());
        });

        // Security Validation Risk Reduction Prototype
        Button securityValidationButton = new Button("Security Validation");
        securityValidationButton.setPadding(new Insets(10));
        securityValidationButton.setOnAction(e -> {
            SecurityValidation securityValidation = new SecurityValidation();
            securityValidation.start(new Stage());
        });

        // Havent added risk reduction 4
        Button riskReduction4 = new Button("Risk Reduction Prototype 4");
        riskReduction4.setPadding(new Insets(10));

        // Adding everything to the main menu
        mainMenu = new VBox(50, new Text("EffortLoggerV2.0 Main Menu"));
        mainMenu.getChildren().addAll(addUserStoriesButton, planningPokerButton, scalabilityAnalyzerButton, anonymityVerifierButton, securityValidationButton, riskReduction4);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setPadding(new Insets(5));

        // Importing the login screen interface
        LoginScreen login = new LoginScreen();
        // Getting the login VBox from the interface
        VBox loginBox = login.getVBox();
        Button loginButton = login.getLoginButton();

        loginButton.setOnAction(e -> {
            String username = login.getUsername();
            String password = login.getPassword();
            // We get the result of the password check when we click on the button
            Boolean result = login.setOnLoginButtonClick(username, password);
            
            // If it is successful, we set the current user as the username and import their data
            if (result) {
                currentUser = username;

                // This is our imported user data
                UserData data = importHistoricalData(currentUser);

                System.out.println("Imported User Data");
                System.out.println(data.getUsername());
                System.out.println(data.getData());
                // After we import the data we go to the main menu
                showScreen(mainMenu, "Main Menu");
            }
        });

        // Setting up the scene
        Scene scene = new Scene(borderPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        showScreen(loginBox, "Login");
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
