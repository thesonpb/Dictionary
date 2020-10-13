import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Dictionary Application");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {

        DictionaryManagement.insertFromFile("F:\\TheSon\\Codejava\\FinalProject1\\Resources\\Text\\Dict.txt");
        DictionaryCommandline.showAllWords();
        launch(args);
    }
}
