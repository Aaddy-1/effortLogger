package main;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.PlanningPokerApp.HistoricalData;
import main.PlanningPokerApp.UserData;
import main.PlanningPokerApp.UserStory;

public class EstimationPage extends VBox {

    private Button produceEstimateButton;
    
    public EstimationPage(UserStory userStory, UserData userData) {
        setPadding(new Insets(20));

        Label titleLabel = new Label("User Story: " + userStory.getTitle());
        Label descriptionLabel = new Label("Description: " + userStory.getDescription());
        Label pickProjectsLabel = new Label("Pick Relevant Historical Projects:");

        getChildren().addAll(titleLabel, descriptionLabel, pickProjectsLabel);

        for (HistoricalData historicalData : userData.getData()) {
            CheckBox checkBox = new CheckBox(historicalData.getTitle() + "\nDescription: " +
                    historicalData.getDescription() + "\nTime: " + historicalData.getTime());
            checkBox.setSelected(true);

            ComboBox<Integer> weightComboBox = new ComboBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
            weightComboBox.setValue(1);

            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                weightComboBox.setDisable(!newValue);
            });

            getChildren().add(new HBox(10, checkBox, new Label("Weight:"), weightComboBox));

            produceEstimateButton = new Button("Produce Estimate");
            

        }
        getChildren().add(produceEstimateButton);
    }

    public Button getEstimateButton() {
        return this.produceEstimateButton;
    }
    public VBox getVBox() {
        return this;
    }
}
