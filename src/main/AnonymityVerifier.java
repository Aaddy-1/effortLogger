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
        // We will implement the actual anonymization function later
        
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

            // In a real application, this is where the verification logic would go.
            // For now, we'll just display a message for demonstration purposes.
            boolean isAnonymized = verifyAnonymization(fileContents, anonymizedReport);
            if (isAnonymized) {
                showAlert("Verification Successful", "The report is properly anonymized.");
            } else {
                showAlert("Verification Failed", "The report may not be properly anonymized.");
            }
        });


        // Uploading files screen
        VBox mainMenu = new VBox(50, new Text("Upload files here"));
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setPadding(new Insets(5));
        Button fileUploadButton = new Button("Upload File");
        Button goToVerification = new Button("See Anonymized Report");
        goToVerification.setOnAction(e -> showScreen(root, "Anonymized Report", primaryStage));
        goToVerification.setVisible(false);
        FileChooser fileChooser = new FileChooser();
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
            button.setVisible(true);
            fileLabel.setText(fileContents);

            // When we implement actual anonymization we will have to update the value
            // of the anonymized label inside the anonymization function
            anonymizedText = anonymizeText(fileContents);
            anonymizedLabel.setText(anonymizedText);
            
    }
    public String anonymizeText(String text) {
        String anonymizedString = text.replaceAll("(?<=Employee Name: )(.+)", "XXX");
        anonymizedString = anonymizedString.replaceAll("(?<=Employee ID: )\\d+", "XXX");
        return anonymizedString;
    }

    // This function is used to verify whether the report has been properly anonymized
    public boolean verifyAnonymization(String originalReport, String anonymizedReport) {
        // In a real application, you would implement the verification logic here.

        String originalName = extractValue(originalReport, "Employee Name");
        String anonymizedName = extractValue(anonymizedReport, "Employee Name");

        String originalID = extractValue(originalReport, "Employee ID");
        String anonymizedID = extractValue(anonymizedReport, "Employee ID");

        boolean nameAnonymized = !originalName.equals(anonymizedName);
        boolean idAnonymized = !originalID.equals(anonymizedID);

        System.out.println(nameAnonymized);
        System.out.println(idAnonymized);

        if (nameAnonymized && idAnonymized) {
            return true;
        }
        return false;
    }

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

    private void showScreen(VBox screen, String title, Stage primaryStage) {
        primaryStage.setTitle(title);
        borderPane.setCenter(screen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
