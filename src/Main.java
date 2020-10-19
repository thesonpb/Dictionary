import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //Image image = new Image(getClass().getResourceAsStream("/Resources/Image/icon.png"));
        //primaryStage.getIcons().add(image);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        primaryStage.setTitle("Homemade Dictionary");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {

        DictionaryManagement.insertFromFile("F:\\TheSon\\Codejava\\DictionaryFinal3\\Resources\\Text\\Dict.txt");
        DictionaryCommandline.showAllWords();
        launch(args);
    }
}
