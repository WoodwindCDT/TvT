import Objects.EnvironmentPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    // Overriden method from Application inheritance
    @Override
    public void start(Stage stage) throws Exception {
        EnvironmentPane ep = new EnvironmentPane();

        Scene scene = new Scene(ep, 700, 600);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        ep.requestFocus();
    }

    // To launch
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}