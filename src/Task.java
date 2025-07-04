public class Task {
    private String taskName;
    private boolean completed;

    public Task(String taskName, boolean completed){
        this.taskName = taskName;
        this.completed = completed;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        completed = true;
    }

    @Override
    public String toString() {
        return taskName + " (Completed: " + completed + ")";
    }
}