import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.FirstTimeController;
import scenes.LoginController;
import struct.Controller;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Controller _controller = new Controller();
        FXMLLoader loader = new FXMLLoader();
        Scene scene;
        String title;
        if(_controller.Exist()){
            Parent root = loader.load(getClass().getResource("/scenes/login.fxml"));
            LoginController.setController(_controller);
            LoginController.setStage(stage);
            scene = new Scene(root, 400, 300);
            title="Signum";
        }
        else{
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/first_time.fxml"));
            FirstTimeController.setController(_controller);
            FirstTimeController.setStage(stage);
            scene = new Scene(root, 400, 300);
            title="First Time Initialization";
        }
        Launch(stage, scene, title);
    }
    public static void main(String[] args) {
        launch(args);
    }

    private static void Launch(Stage stage, Scene scene, String title){
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
