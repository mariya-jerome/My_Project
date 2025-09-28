// AstronautScheduleApp.java
import java.util.*;

class Task {
    String description;
    String startTime, endTime, priority;
    boolean completed = false;

    public Task(String desc, String start, String end, String priority) {
        this.description = desc;
        this.startTime = start;
        this.endTime = end;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return startTime + " - " + endTime + ": " + description + " [" + priority + "]"
                + (completed ? " (Completed)" : "");
    }
}

// Singleton
class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks = new ArrayList<>();
    private ScheduleManager() {}
    public static ScheduleManager getInstance() {
        if(instance == null) instance = new ScheduleManager();
        return instance;
    }

    public void addTask(Task task) {
        for(Task t: tasks){
            if(overlaps(t, task)){
                System.out.println("Error: Task conflicts with existing task \"" + t.description + "\"");
                return;
            }
        }
        tasks.add(task);
        tasks.sort(Comparator.comparing(t -> t.startTime));
        System.out.println("Task added successfully. No conflicts.");
    }

    public void removeTask(String desc){
        Optional<Task> found = tasks.stream().filter(t -> t.description.equalsIgnoreCase(desc)).findFirst();
        if(found.isPresent()){
            tasks.remove(found.get());
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Error: Task not found.");
        }
    }

    public void viewTasks(){
        if(tasks.isEmpty()){
            System.out.println("No tasks scheduled for the day.");
            return;
        }
        for(Task t: tasks) System.out.println(t);
    }

    private boolean overlaps(Task t1, Task t2){
        return !(t2.endTime.compareTo(t1.startTime) <= 0 || t2.startTime.compareTo(t1.endTime) >= 0);
    }
}

public class AstronautScheduleApp {
    public static void main(String[] args) {
        ScheduleManager sm = ScheduleManager.getInstance();

        sm.addTask(new Task("Morning Exercise","07:00","08:00","High"));
        sm.addTask(new Task("Team Meeting","09:00","10:00","Medium"));
        sm.viewTasks();

        sm.removeTask("Morning Exercise");
        sm.addTask(new Task("Lunch Break","12:00","13:00","Low"));
        sm.viewTasks();

        sm.addTask(new Task("Training Session","09:30","10:30","High")); // conflict
        sm.removeTask("Non-existent Task"); // not found
        sm.addTask(new Task("Invalid Time Task","25:00","26:00","Low")); // invalid format (treated as string here)
        sm.viewTasks();
    }
}
