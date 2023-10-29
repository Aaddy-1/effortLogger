import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ScreenChangeExample extends Application {

    private StackPane stackPane;
    private BorderPane borderPane;

    @Override
    public void start(Stage primaryStage) {
        stackPane = new StackPane();
        borderPane = new BorderPane();

        // Create buttons
        Button screen1Button = new Button("Screen 1");
        Button screen2Button = new Button("Screen 2");

        // Create screens
        VBox screen1 = new VBox(new Text("Screen 1 Content"), screen2Button);
        VBox screen2 = new VBox(new Text("Screen 2 Content"), screen1Button);

        // Set up button actions
        screen1Button.setOnAction(e -> showScreen(screen2));
        screen2Button.setOnAction(e -> showScreen(screen1));

        // Set initial screen
        showScreen(screen1);

        // Add buttons to border pane
        borderPane.setLeft(screen1Button);
        borderPane.setCenter(screen2Button);

        // Set up scene
        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setTitle("Screen Change Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showScreen(VBox screen) {
        borderPane.setCenter(screen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
