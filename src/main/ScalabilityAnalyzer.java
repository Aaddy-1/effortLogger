package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.Mem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ScalabilityAnalyzer extends Application {

    private Label userBaseLabel;
    private Label responseTimeLabel;
    private Label memoryUsageLabel;
    private Label cpuUsageLabel;

    private int simulatedUserBase = 100;
    private double simulatedResponseTime = 25.0; // milliseconds
    private double simulatedMemoryUsage = 256.0; // megabytes
    private double simulatedCpuUsage = 50.0; // percentage

    private Sigar sigar;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Scalability Analyzer Simulation");

        // Initialize Sigar
        sigar = new Sigar();

        // Create labels for displaying metrics
        userBaseLabel = new Label("Simulated User Base: " + simulatedUserBase);
        responseTimeLabel = new Label("Response Time: " + simulatedResponseTime + " ms");
        memoryUsageLabel = new Label("Memory Usage: " + simulatedMemoryUsage + " MB");
        cpuUsageLabel = new Label("CPU Usage: " + simulatedCpuUsage + "%");

        // Create a VBox to hold the labels
        VBox root = new VBox(10);
        root.getChildren().addAll(userBaseLabel, responseTimeLabel, memoryUsageLabel, cpuUsageLabel);

        // Create the scene
        Scene scene = new Scene(root, 300, 200);

        // Set the scene on the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();

        // Create a timeline to periodically update metrics
        Timeline metricsTimeline = new Timeline(
            new KeyFrame(Duration.seconds(2), event -> updateMetrics())
        );
        metricsTimeline.setCycleCount(Timeline.INDEFINITE);
        metricsTimeline.play();

        // Simulate multiple user sessions
        for (int i = 0; i < simulatedUserBase; i++) {
            simulateUserSession(i);
        }
    }

    // Simulate updating metrics
    private void updateMetrics() {
        try {
            Mem mem = sigar.getMem();
            double memoryUsage = mem.getUsed() / (1024 * 1024); // Memory usage in MB

            // Fetch CPU metrics using Cpu
            Cpu cpu = sigar.getCpu();
            double cpuUsage = (1.0 - (double)cpu.getIdle() / (double)cpu.getTotal()) * 100; // CPU usage as a percentage

            // Update labels with new metrics
            userBaseLabel.setText("Simulated User Base: " + simulatedUserBase);
            responseTimeLabel.setText("Response Time: " + simulatedResponseTime + " ms");
            memoryUsageLabel.setText("Memory Usage: " + String.format("%.2f", memoryUsage) + " MB");
            cpuUsageLabel.setText("CPU Usage: " + String.format("%.2f", cpuUsage) + "%");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Simulate user sessions (this is a simplified example)
    private void simulateUserSession(int userId) {
        Thread userThread = new Thread(() -> {
            // Simulate user-specific behavior here
            // For example, you can perform tasks or operations related to the user's interaction with the application.

            // In this example, we'll just simulate random changes in metrics
            while (true) {
                simulatedResponseTime = 20 + Math.random() * 20; // Random response time between 20 and 40 ms
                simulatedMemoryUsage = 200 + Math.random() * 200; // Random memory usage between 200 and 400 MB
                simulatedCpuUsage = Math.random() * 100; // Random CPU usage percentage between 0 and 100

                // Update labels with new metrics
                Platform.runLater(() -> {
                    responseTimeLabel.setText("Response Time: " + String.format("%.2f", simulatedResponseTime) + " ms");
                    memoryUsageLabel.setText("Memory Usage: " + String.format("%.2f", simulatedMemoryUsage) + " MB");
                    cpuUsageLabel.setText("CPU Usage: " + String.format("%.2f", simulatedCpuUsage) + "%");
                });

                try {
                    Thread.sleep(2000); // Simulate every 2 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        userThread.setDaemon(true);
        userThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
