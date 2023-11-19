package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class AnonymityVerifier extends Application {

    private BorderPane borderPane;
    String fileContents = "";
    String anonymizedText = "";

    @Override
    public void start(Stage primaryStage) {
        borderPane = new BorderPane();
        // Create UI components
        Label titleLabel = new Label("Anonymization Verification Tool");
        Label originalLabel = new Label("Original Report:");
        Label originalTextArea = new Label(fileContents);
        originalTextArea.setWrapText(true);
        Label anonymizedLabel = new Label("Anonymized Report:");
        
        Label anonymizedTextArea = new Label(anonymizedText);
        Button backButton = new Button("Back to file upload");
        anonymizedTextArea.setWrapText(true);
        Button verifyButton = new Button("Verify Anonymization");

        // Set up the layout
        VBox root = new VBox(5);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(5));
        root.getChildren().addAll(titleLabel, originalLabel, originalTextArea, anonymizedLabel, anonymizedTextArea, verifyButton, backButton);

        // Handle verify button click
        verifyButton.setOnAction(e -> {
            String originalReport = originalTextArea.getText();
            String anonymizedReport = anonymizedTextArea.getText();

            // Checking for anonymization
            boolean isAnonymized = verifyAnonymization(fileContents, anonymizedReport);
            if (isAnonymized) {
                showAlert("Verification Successful", "The report is properly anonymized.");
            } else {
                showAlert("Verification Failed", "The report may not be properly anonymized.");
            }
        });


        // Uploading files screen/main menu
        VBox mainMenu = new VBox(50, new Text("Upload files here"));
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setPadding(new Insets(5));
        Button fileUploadButton = new Button("Upload File");
        Button goToVerification = new Button("See Anonymized Report");
        goToVerification.setOnAction(e -> showScreen(root, "Anonymized Report", primaryStage));
        goToVerification.setVisible(false);
        // File choose object will help us open the dialog box to upload file
        FileChooser fileChooser = new FileChooser();
        // Selecting the file from the dialog box and then calling file reader
        fileUploadButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            fileReader(selectedFile, goToVerification, originalTextArea, anonymizedTextArea);
        });

        mainMenu.getChildren().addAll(fileUploadButton, goToVerification);
        backButton.setOnAction(e -> showScreen(mainMenu, "File Upload", primaryStage));

        // Set up the scene
        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setTitle("Anonymization Verification Tool Prototype");
        primaryStage.setScene(scene);
        primaryStage.show();
        showScreen(mainMenu, "Upload Files", primaryStage);
    }

    // This function reads the contents from the file and then makes the view report button visible
    private void fileReader(File selectedFile, Button button, Label fileLabel, Label anonymizedLabel) {
        // Reading the file line by line
            try {
                Scanner myScanner = new Scanner(selectedFile);
                while (myScanner.hasNextLine()) {
                    String data = myScanner.nextLine();
                    fileContents += data + "\n";
                    System.out.println(data);
                }
                myScanner.close();
            }   
            catch (FileNotFoundException fe) {
                System.out.println("Exception");
            }
            // Making the view report button visible
            button.setVisible(true);
            fileLabel.setText(fileContents);

            // Anonymizing the file contents
            anonymizedText = anonymizeText(fileContents);
            // Setting the value of the label
            anonymizedLabel.setText(anonymizedText);
            
    }
    public String anonymizeText(String text) {
        // Using a regex to anonymize the employee's name and ID
        String anonymizedString = text.replaceAll("(?<=Employee Name: )(.+)", "XXX");
        anonymizedString = anonymizedString.replaceAll("(?<=Employee ID: )\\d+", "XXX");
        return anonymizedString;
    }

    // This function is used to verify whether the report has been properly anonymized
    public boolean verifyAnonymization(String originalReport, String anonymizedReport) {
        
        // Extract the original and anonymized names from the original and anonymized report
        String originalName = extractValue(originalReport, "Employee Name");
        String anonymizedName = extractValue(anonymizedReport, "Employee Name");

        // Extract the original and anonymized IDs from the original and anonymized report
        String originalID = extractValue(originalReport, "Employee ID");
        String anonymizedID = extractValue(anonymizedReport, "Employee ID");

        // We can check if the anonymized and original values are the same or not
        boolean nameAnonymized = !originalName.equals(anonymizedName);
        boolean idAnonymized = !originalID.equals(anonymizedID);

        
        // If they are not the same we return true
        if (nameAnonymized && idAnonymized) {
            return true;
        }
        return false;
    }

    // Displays alert informing whether it was succesfully anonymized or not
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // To extract a particular value froma an entry
    private String extractValue(String input, String key) {
        String[] value = input.split(key + ": ");
        if (value.length > 1) {
            return value[1].split("\n")[0].trim();
        }
        return "";
    }

    // This function replaces the contents of a screen with the contents of another vbox and changes the tile
    private void showScreen(VBox screen, String title, Stage primaryStage) {
        primaryStage.setTitle(title);
        borderPane.setCenter(screen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
