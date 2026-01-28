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

    public void save(List<Task> tasks) {
        // TODO next
    }
}
