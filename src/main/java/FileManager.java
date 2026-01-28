import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> load() {
        // TODO next
        return new ArrayList<>();
    }

    public void save(List<Task> tasks) throws IOException {
        // Create directory if it doesn't exist - Propagate up to Dbot to handle
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
