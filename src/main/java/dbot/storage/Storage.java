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

/**
 * Handles loading and saving of tasks to and from a file.
 * The Storage class manages file I/O operations for task persistence.
 */
public class Storage {
    /** The file path where tasks are stored. */
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The file path where tasks will be saved and loaded from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     * Reads the file line by line and creates Task objects based on the saved format.
     * If the file does not exist, returns an empty list.
     *
     * @return A list of tasks loaded from the file.
     * @throws IOException If an error occurs while reading the file.
     */
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

    /**
     * Saves the given list of tasks to the file.
     * Creates the parent directory if it does not exist.
     * Overwrites the existing file with the current task list.
     *
     * @param tasks The list of tasks to save.
     * @throws IOException If an error occurs while writing to the file.
     */
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
