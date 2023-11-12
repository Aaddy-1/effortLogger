package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginScreen extends VBox {

    private TextField usernameTextField;
    private PasswordField passwordField;
    private Button loginButton;
    private Boolean validated = false;
    

    public LoginScreen() {
        // Set VBox properties
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));

        // Initialize components
        usernameTextField = new TextField();
        usernameTextField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginButton = new Button("Login");

        // loginButton.setOnAction(e -> {
        //     setOnLoginButtonClick();
        // });

        // Add components to VBox
        getChildren().addAll(usernameTextField, passwordField, loginButton);
    }

    // Expose the VBox for external use
    public VBox getVBox() {
        return this;
    }

    // You can provide methods to access the entered username and password if needed
    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public Button getLoginButton() {
        return this.loginButton;
    }

    public Boolean getValidation() {
        return this.validated;
    }

    // You can provide a method to set an event handler for the login button
    public Boolean setOnLoginButtonClick(String username, String password) {
        String credentialPath = "userCredentials.json";
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(" CLICKED");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String message = "Your username or password is incorrect";
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText(null);
        alert.setContentText(message);
        try {
            // Read JSON file as JsonNode
            JsonNode jsonNode = objectMapper.readTree(new File(credentialPath));

            // Get the "users" array
            JsonNode usersNode = jsonNode.get("users");

            // Initialize lists to store usernames and passwords
            List<String> usernames = new ArrayList<>();
            List<String> passwords = new ArrayList<>();

            // Iterate over the users array
            for (JsonNode userNode : usersNode) {
                // Get username and password for each user
                String fileUsername = userNode.get("username").asText();
                String filePassword = userNode.get("password").asText();

                // Add username and password to their respective lists
                usernames.add(fileUsername);
                passwords.add(filePassword);
            }

            // Print the lists
            System.out.println("Usernames: " + usernames);
            System.out.println("Passwords: " + passwords);

            int nameIndex = usernames.indexOf(username);
            if (nameIndex == -1) {
                alert.showAndWait();
                return false;
            }
            String correctPassword = passwords.get(nameIndex);
            if (password.equals(correctPassword)) {
                return true;
            }
            else {
                alert.showAndWait();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    
}
