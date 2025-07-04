import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(String taskName, boolean completed) {
        Task task = new Task(taskName, completed);
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void markTaskAsCompleted(String taskName) {
        for (Task task : tasks) {
            if (task.getTaskName().equals(taskName)) {
                task.setCompleted();
                break;
            }
        }
    }
    public void loadTasks(File file){
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String taskName = parts[0].trim();
                        boolean completed = Boolean.parseBoolean(parts[1].trim());
                        addTask(taskName, completed);
                    }
                    else {
                        addTask(line.trim(), false);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error reading tasks file: " + e.getMessage());
            } 
        }
        else {
            System.out.println("No task file found.");
        }
            
    }
}