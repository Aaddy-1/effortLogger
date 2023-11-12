package main;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EstimationResult extends VBox{
    
    private Label resultLabel;
    private Label userEstimatesLabel;
    private Label testUser2Label;
    private Label testUser3Label;
    private Label testUser4Label;

    private Button finishPlanningPokerButton;
    private Button goBackButton;
    

    public EstimationResult(double weightedAverage) {
        setPadding(new Insets(20));

        // Assuming test users' estimates
        resultLabel = new Label("Computed Estimate: " + weightedAverage);

        userEstimatesLabel = new Label("Estimations from other users:");
        testUser2Label = new Label("Estimation from testuser2: 40");
        testUser3Label = new Label("Estimation from testuser3: 40");
        testUser4Label = new Label("Estimation from testuser4: 40");

        userEstimatesLabel.setStyle("-fx-font-weight: bold;");

        getChildren().addAll(resultLabel, userEstimatesLabel, testUser2Label, testUser3Label, testUser4Label);

        
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public void setResultLabel(Label resultLabel) {
        this.resultLabel = resultLabel;
    }

    public Button getFinishPlanningPokerButton() {
        return finishPlanningPokerButton;
    }

    public void setFinishPlanningPokerButton(Button finishPlanningPokerButton) {
        this.finishPlanningPokerButton = finishPlanningPokerButton;
    }


    public Button getGoBackButton() {
        return goBackButton;
    }

    public void setGoBackButton(Button goBackButton) {
        this.goBackButton = goBackButton;
    }

    public VBox getVBox() {
        return this;
    }
}
