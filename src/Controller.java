import com.gtranslate.Audio;
import com.gtranslate.Language;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.midi.Synthesizer;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



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
    public Button deleteButton;
    public TextField deleteTextField;
    public Button editButton;
    public TextField editWordTextField;
    public TextField newWordTextField;
    public TextField newWordExplainTextField;
    public JFXButton speechButton;


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
        if (word != null) {
            recentData.remove(word);
            recentData.add(word);
            recentListView.getItems().clear();
            recentListView.getItems().addAll(recentData);
        }
        String definition = "";
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
            FileWriter myWriter = new FileWriter("F:\\TheSon\\Codejava\\DictionaryFinal3\\Resources\\Text\\Dict.txt", true);
            BufferedWriter out = new BufferedWriter(myWriter);
            out.write(addWordTextField.getText() + "\t" + addExplainTextField.getText() + "\n");
            out.close();
        } catch (IOException e) {
            System.out.println("an error occured");
            e.printStackTrace();
        }
        //xoa het tu trong map
        envi.words.clear();
        //insert lai vao trong map
        insertFromFile("F:\\TheSon\\Codejava\\DictionaryFinal3\\Resources\\Text\\Dict.txt");
        //hien thi lai danh sach tu
        dictionarySearch("");
        label2.setText("You added the word '" + addWordTextField.getText() + "'!");
        addWordTextField.setText("");
        addExplainTextField.setText("");
    }

    public void editWord(ActionEvent actionEvent) throws Exception {
        if (envi.words.containsKey(editWordTextField.getText())) {
            envi.words.remove(editWordTextField.getText());
            envi.words.put(newWordTextField.getText(), newWordExplainTextField.getText());
            try {
                FileWriter myWriter = new FileWriter("F:\\TheSon\\Codejava\\DictionaryFinal3\\Resources\\Text\\Dict.txt");
                BufferedWriter out = new BufferedWriter(myWriter);
                for (String key : DictionaryManagement.envi.words.keySet()) {
                    out.write(key + "\t" + envi.words.get(key) + "\n");
                }
                out.close();
            } catch (IOException e) {
                System.out.println("an error occured");
                e.printStackTrace();
            }
            label2.setText("You changed the word '" + "\n" + editWordTextField.getText() + "' to '" + newWordTextField.getText() + "'!");
            editWordTextField.setText("");
            newWordTextField.setText("");
            newWordExplainTextField.setText("");
        } else {
            label2.setText("Word not found!");
        }
        dictionarySearch("");

    }


    public void deleteWord(ActionEvent actionEvent) throws IOException {
        //xoa trong tu dien
        if (envi.words.containsKey(deleteTextField.getText())) {
            envi.words.remove(deleteTextField.getText());

            //viet lai file text theo tu dien
            try {
                FileWriter myWriter = new FileWriter("F:\\TheSon\\Codejava\\DictionaryFinal3\\Resources\\Text\\Dict.txt");
                BufferedWriter out = new BufferedWriter(myWriter);
                for (String key : DictionaryManagement.envi.words.keySet()) {
                    out.write(key + "\t" + envi.words.get(key) + "\n");
                }
                out.close();
            } catch (IOException e) {
                System.out.println("an error occured");
                e.printStackTrace();
            }
            label2.setText("You deleted the word '" + deleteTextField.getText() + "'!");
            deleteTextField.setText("");
        } else {
            label2.setText("Word not found!");
        }
        dictionarySearch("");


    }

    public void resetTextField(ActionEvent actionEvent) {
        textField.setText("");
        label2.setText("");
    }

    public void createTextFile(ActionEvent actionEvent) {
        try {
            File myFile = new File(fileNameTextField.getText() + ".txt");
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
            FileWriter myWriter = new FileWriter("F:\\TheSon\\Codejava\\DictionaryFinal3\\" + fileNameTextField.getText() + ".txt");
            for (String key : envi.words.keySet()) {
                myWriter.write(key + "\t" + envi.words.get(key) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("an error occured");
            e.printStackTrace();
        }
        label2.setText("You exported the \n dictionary to file \n'" + fileNameTextField.getText() + ".txt'!");
        fileNameTextField.setText("");
    }

    public void textToSpeech(ActionEvent actionEvent) throws Exception {
    }

}
