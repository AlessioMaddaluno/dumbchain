import GUI.GUIController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    GUIController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        controller = new GUIController();
        controller.buildView(primaryStage);
    }

}
