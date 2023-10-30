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
        Label anonymizedTextArea = new Label(
            "Employee Name: XXXX XXX\n" +
            "Employee ID: #####\n" +
            "Project: Project X\n" +
            "Hours Worked: 40\n" +
            "");

        anonymizedTextArea.setWrapText(true);
        Button verifyButton = new Button("Verify Anonymization");

        // Set up the layout
        VBox root = new VBox(5);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(5));
        root.getChildren().addAll(titleLabel, originalLabel, originalTextArea, anonymizedLabel, anonymizedTextArea, verifyButton);

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
            fileReader(selectedFile, goToVerification, originalLabel);
        });

        mainMenu.getChildren().addAll(fileUploadButton, goToVerification);


        // Set up the scene
        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setTitle("Anonymization Verification Tool Prototype");
        primaryStage.setScene(scene);
        primaryStage.show();
        showScreen(mainMenu, "Upload Files", primaryStage);
    }

    private void fileReader(File selectedFile, Button button, Label label) {
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
            label.setText(fileContents);

            // When we implement actual anonymization we will have to uopdate the value
            // of the anonymized label inside the anonymization function
            System.out.println("======");
            System.out.println(fileContents);
            
    }

    private boolean verifyAnonymization(String originalReport, String anonymizedReport) {
        // In a real application, you would implement the verification logic here.
        // For this prototype, we'll assume it's properly anonymized if the lengths match.
        System.out.println("a +" + originalReport);
        System.out.println(originalReport.length());
        System.out.println(anonymizedReport.length());
        return originalReport.length() == anonymizedReport.length();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showScreen(VBox screen, String title, Stage primaryStage) {
        primaryStage.setTitle(title);
        borderPane.setCenter(screen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
