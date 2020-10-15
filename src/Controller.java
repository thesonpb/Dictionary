import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;


public class Controller extends DictionaryManagement implements Initializable {

    public TextField textField;

    public Label label2;
    public ListView<String> listView;
    public TextField addExplainTextField;
    public TextField addWordTextField;
    public JFXButton resetButton;
    public Button addButton;
    public TextField fileNameTextField;
    public Button createTextFileButton;
    public ListView recentListView;


    ObservableList<String> data = FXCollections.observableArrayList();
    ObservableList<String> recentData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        //lấy dữ liệu được nhập vào trong thanh tìm kiếm để tìm từ tương tự
        textField.textProperty().addListener((observable, oldValue, newValue) -> dictionarySearch(newValue));
    }

    private void loadData() {
        data.removeAll(data);
        for (String key : envi.words.keySet()) {
            if (key.startsWith(textField.getText())) data.add(key);
        }
        listView.getItems().addAll(data);
    }

    public void dictionarySearch(String s) {
        data.removeAll(data);
        listView.getItems().clear();

        for (String key : envi.words.keySet()) {
            if (key.startsWith(s)) data.add(key);
        }
        listView.getItems().addAll(data);

    }

    public void displayWordAndAddToRecent(MouseEvent actionEvent) {
        String word = listView.getSelectionModel().getSelectedItem();
        if(recentData.contains(word)) recentData.remove(word);
        recentData.add(word);
        recentListView.getItems().clear();
        recentListView.getItems().addAll(recentData);
        String definition = new String();
        if (word == null || word.isEmpty()) {
            label2.setText("");
        } else {
            for (String key : envi.words.keySet()) {
                if (key.equals(word)) {
                    definition = envi.words.get(key);
                    textField.setText(key);
                }
            }
            label2.setText(definition);
        }
    }

    public void addWord(ActionEvent actionEvent) throws Exception {
        //envi.words.put(addWordTextField.getText(), addExplainTextField.getText());
        //viet vao trong file input
        try {
            FileWriter myWriter = new FileWriter("F:\\TheSon\\Codejava\\FinalProject1\\Resources\\Text\\Dict.txt", true);
            BufferedWriter out = new BufferedWriter(myWriter);
            out.write(addWordTextField.getText() + "\t" + addExplainTextField.getText() +"\n");
            out.close();
        } catch (IOException e) {
            System.out.println("an error occured");
            e.printStackTrace();
        }
        //xoa het tu trong map
        envi.words.clear();
        //insert lai vao trong map
        insertFromFile("F:\\TheSon\\Codejava\\FinalProject1\\Resources\\Text\\Dict.txt");
        //hien thi lai danh sach tu
        dictionarySearch("");
    }

    public void editWord(ActionEvent actionEvent) {

    }

    public void deleteWord(ActionEvent actionEvent) {

    }

    public void resetTextField(ActionEvent actionEvent) {
        textField.setText("");
        label2.setText("");
    }

    public void createTextFile(ActionEvent actionEvent) {
        try {
            File myFile = new File(fileNameTextField.getText()+".txt");
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.print("");
            }
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        //viết vào file.
        try {
            FileWriter myWriter = new FileWriter("F:\\TheSon\\Codejava\\FinalProject1\\"+fileNameTextField.getText()+".txt");
            for (String key : envi.words.keySet()) {
                myWriter.write(key + "\t" + envi.words.get(key) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("an error occured");
            e.printStackTrace();
        }
    }
}
