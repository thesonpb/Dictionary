import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.util.ResourceBundle;


public class Controller extends DictionaryManagement implements Initializable {

    public TextField textField;
    public Button searchButton;
    public Label label2;
    public ListView<String> listView;
    public Button addButton;
    public TextField addExplainTextField;
    public TextField addWordTextField;
    public Button editButton;
    public Button deleteButton;

    ObservableList<String> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData() {
        data.removeAll(data);
        for (String key : envi.words.keySet()) {
            if (key.startsWith(textField.getText())) data.add(key);
        }
        listView.getItems().addAll(data);
    }

    public void dictionarySearch(ActionEvent actionEvent) {
        data.removeAll(data);
        listView.getItems().clear();
        for (String key : envi.words.keySet()) {
            if (key.startsWith(textField.getText())) data.add(key);
        }
        listView.getItems().addAll(data);

    }

    public void displayWord(MouseEvent actionEvent) {
        String word = listView.getSelectionModel().getSelectedItem();
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
        dictionarySearch(actionEvent);
    }

    public void editWord(ActionEvent actionEvent) {

    }

    public void deleteWord(ActionEvent actionEvent) {

    }
}
