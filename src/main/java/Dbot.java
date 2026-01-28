import java.io.IOException;

public class Dbot {
    private TaskList tasks;
    private final Storage storage;
    private final Ui ui;

    public Dbot(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException e) {
            // Only for REAL errors. File doesnt exist returns empty list
            ui.showLoadingError();
            this.tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            ui.showLine();

            try {
                CommandType command = Parser.parseCommand(input);

                switch (command) {
                case BYE: // Terminating condition
                    ui.showGoodbye();
                    ui.showLine();
                    ui.close();
                    return; // Exit run() method
                case LIST: // Print list
                    showList();
                    break;
                case HELP: // Show help
                    ui.showHelp();
                    break;
                case MARK:
                case UNMARK:
                    updateTask(input, command);
                    break;
                case DELETE:
                    deleteTask(input);
                    break;
                case TODO:
                case DEADLINE:
                case EVENT:
                    addTask(input, command);
                    break;
                default: // Unknown command
                    throw new DbotException("Unknown command! Type 'help' to see available commands.");
                }
            } catch (DbotException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
    }

    private void showList() {
        ui.showTaskList(tasks.getFormattedList());
    }

    private void updateTask(String input, CommandType command) throws DbotException {
        boolean isMark = (command == CommandType.MARK);
        int index = Parser.parseTaskNumber(input, isMark ? "mark " : "unmark ");

        Task task = tasks.get(index);
        if (isMark) {
            task.markAsDone();
        } else {
            task.markAsUndone();
        }
        ui.showTaskMarked(task.toString(), isMark);
        saveTasks();  // Save after marking/unmarking
    }

    private void deleteTask(String input) throws DbotException {
        int index = Parser.parseTaskNumber(input, "delete ");
        Task removedTask = tasks.delete(index);
        ui.showTaskDeleted(removedTask.toString(), tasks.size());
        saveTasks();  // Save after deleting
    }

    private void addTask(String input, CommandType type) throws DbotException {
        Task task = Parser.parseTask(input, type);
        tasks.add(task);
        ui.showTaskAdded(task.toString(), tasks.size());
        saveTasks();  // Save after adding
    }

    private void saveTasks() {
        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showError("Error saving tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Dbot("./data/dbot.txt").run();
    }
}
