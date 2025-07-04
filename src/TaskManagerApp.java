import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskManagerApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Task Manager");
        TaskManager taskManager = new TaskManager();
        taskManager.loadTasks(new File("data/tasks.txt"));

        Label title = new Label("Welcome to Task Manager");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: #333; -fx-font-weight: bold;");

        Button button1 = new Button("View Tasks");
        button1.setStyle(
                "-fx-background-color:rgb(15, 134, 213); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20;");
        // When button1 is clicked, displays tasks from TaskManager
        button1.setOnAction(e -> {
            List<Task> tasks = taskManager.getTasks();
            if (tasks.isEmpty()) {
                showAlert("No Tasks", "No tasks available.");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (Task t : tasks) {
                sb.append(t.toString()).append("\n");
            }
            showAlert("Tasks", sb.toString());
        });

        Button button2 = new Button("Add Task");
        button2.setStyle(
                "-fx-background-color:rgb(15, 134, 213); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20;");
        // When button2 is clicked, prompts user to add a task
        // and its status (completed or not)
        button2.setOnAction(e -> {
            TextInputDialog taskDialog = new TextInputDialog();
            taskDialog.setTitle("Add Task");
            taskDialog.setHeaderText("Enter the task name:");
            taskDialog.setContentText("Task:");

            // Show the dialog and store user input
            Optional<String> taskResult = taskDialog.showAndWait();

            taskResult.ifPresent(taskName -> {
                List<String> choices = Arrays.asList("Incomplete", "Completed");
                ChoiceDialog<String> statusDialog = new ChoiceDialog<>("Incomplete", choices);
                statusDialog.setTitle("Task Status");
                statusDialog.setHeaderText("Is the task completed?");
                statusDialog.setContentText("Status:");

                Optional<String> statusResult = statusDialog.showAndWait();
                statusResult.ifPresent(status -> {
                    boolean isCompleted = status.equals("Completed");
                    taskManager.addTask(taskName, isCompleted);
                    showAlert("Success", "Task added successfully.");
                });
            });
        });

        Button button3 = new Button("Mark Completed");
        button3.setStyle(
                "-fx-background-color:rgb(15, 134, 213); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20;");
            // When button3 is clicked, prompts user to select a task to mark as completed
            button3.setOnAction(e -> {
            List<Task> tasks = taskManager.getTasks();
            if (tasks.isEmpty()) {
                showAlert("No Tasks", "No tasks available to mark as completed.");
                return;
            }
            ChoiceDialog<Task> dialog = new ChoiceDialog<>(tasks.get(0), tasks);
            dialog.setTitle("Mark Task as Completed");
            dialog.setHeaderText("Select a task to mark as completed:");
            dialog.setContentText("Task:");

            Optional<Task> result = dialog.showAndWait();
            result.ifPresent(task -> {
                if (task.isCompleted()) {
                    showAlert("Already Completed", "This task is already marked as completed.");
                    return;
                }
                taskManager.markTaskAsCompleted(task.getTaskName());
                showAlert("Success", "Task marked as completed.");
            });
        });

        Button button4 = new Button("Remove Task");
        button4.setStyle(
                "-fx-background-color:rgb(15, 134, 213); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20;");
        // When button4 is clicked, prompts user to select a task to remove
        button4.setOnAction(e -> {
            List<Task> tasks = taskManager.getTasks();
            if (tasks.isEmpty()) {
                showAlert("No Tasks", "No tasks available to remove.");
                return;
            }
            ChoiceDialog<Task> dialog = new ChoiceDialog<>(tasks.get(0), tasks);
            dialog.setTitle("Remove Task");
            dialog.setHeaderText("Select a task to remove:");
            dialog.setContentText("Task:");

            Optional<Task> result = dialog.showAndWait();
            result.ifPresent(task -> {
                tasks.remove(task);
                showAlert("Success", "Task removed successfully.");
            });
        });

        // Set consistent button width
        double buttonWidth = 200;
        button1.setPrefWidth(buttonWidth);
        button2.setPrefWidth(buttonWidth);
        button3.setPrefWidth(buttonWidth);
        button4.setPrefWidth(buttonWidth);

        // Title positioning
        VBox titleBox = new VBox();
        titleBox.setPadding(new Insets(10, 0, 10, 0));
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.getChildren().add(title);

        // Buttons positioning
        VBox layout = new VBox(20);
        layout.getChildren().addAll(titleBox, button1, button2, button3, button4);
        layout.setStyle("-fx-padding: 10 30 30 30; -fx-alignment: center;");

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to show tasks
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setResizable(true);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}