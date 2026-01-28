package dbot.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dbot.task.Deadline;
import dbot.task.Event;
import dbot.task.Task;
import dbot.task.Todo;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // If file doesn't exist, return empty list
        if (!file.exists()) {
            return tasks;
        }

        // Read file line by line
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Determine task type and parse accordingly
            if (line.startsWith("T")) {
                tasks.add(Todo.fromFileFormat(line));
            } else if (line.startsWith("D")) {
                tasks.add(Deadline.fromFileFormat(line));
            } else if (line.startsWith("E")) {
                tasks.add(Event.fromFileFormat(line));
            }
        }
        scanner.close();
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException {
        // Create directory if it doesn't exist - Propagate up to dbot.Dbot to handle
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        // Write tasks to file
        FileWriter writer = new FileWriter(file);
        for (Task task : tasks) {
            writer.write(task.toFileFormat() + System.lineSeparator());
        }
        writer.close();
    }
}
