import java.io.IOException;

import dbot.Dbot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * A GUI for Dbot using FXML.
 */
public class Main extends Application {

    private Dbot dbot = new Dbot("./data/dbot.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDbot(dbot);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
