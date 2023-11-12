// Author: Aadeesh Sharma

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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserStoryUI extends VBox{

    // Components of this UI
    private Button backButtonUserStory;
    private Button addButton;
    private TextField titleInput;
    private TextArea descriptionInput;

    public UserStoryUI() {
        // Adding UI components
        Label titleLabel = new Label("Enter the title of the user story");
        titleInput = new TextField();
        titleInput.setPrefSize(20, 30);
        Label descriptionLabel = new Label("Enter the description of the user story here");
        descriptionInput = new TextArea();

        backButtonUserStory = new Button("Back");
        addButton = new Button("Add User Story");

        HBox buttonsUserStory = new HBox();
        buttonsUserStory.getChildren().addAll(backButtonUserStory, addButton);
        descriptionInput.setPrefSize(20, 80);
        getChildren().addAll(titleLabel, titleInput, descriptionLabel, descriptionInput, buttonsUserStory);
        setAlignment(Pos.CENTER);
    }

    // Getter methods
    public VBox getVBox() {
        return this;
    }
    public Button getBackButton() {
        return this.backButtonUserStory;
    }
    public Button getAddButton() {
        return this.addButton;
    }
    public TextField getTitle() {
        return this.titleInput;
    }
    public TextArea getDescription() {
        return this.descriptionInput;
    }

    // Clears the input boxes when the add button is clicked
    public void onAddClick(TextField titlInput, TextArea descriptionInput) {
        titleInput.clear();
        descriptionInput.clear();
    }
}
