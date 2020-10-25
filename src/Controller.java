import com.jfoenix.controls.JFXButton;
import com.voicerss.tts.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

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
    public Label notifyArea;


    ObservableList<String> data = FXCollections.observableArrayList();
    ObservableList<String> recentData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //lấy dữ liệu được nhập vào trong thanh tìm kiếm để tìm từ tương tự
        textField.textProperty().addListener((observable, oldValue, newValue) -> dictionarySearch(newValue));
    }

    private void loadData() throws FileNotFoundException {
        data.removeAll(data);
        for (String key : envi.words.keySet()) {
            if (key.startsWith(textField.getText())) data.add(key);
        }
        listView.getItems().addAll(data);
        recentData.removeAll(recentData);
        File file = new File("Resources\\Text\\Recent.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!recentData.contains(line)) {
                recentData.add(line);
            }
        }
        for (int i = recentData.size() - 1; i >= 0; --i) {
            recentListView.getItems().add(recentData.get(i));
        }

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
            if (!recentData.contains(word)) {
                try {
                    FileWriter myWriter = new FileWriter("Resources\\Text\\Recent.txt", true);
                    BufferedWriter out = new BufferedWriter(myWriter);
                    out.write(word + "\n");
                    out.close();
                } catch (IOException e) {
                    System.out.println("an error occured");
                    e.printStackTrace();
                }
            }
            recentData.remove(word);
            recentData.add(word);
            recentListView.getItems().clear();
            for (int i = recentData.size() - 1; i >= 0; --i) {
                recentListView.getItems().add(recentData.get(i));
            }
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
            notifyArea.setText("");
            label2.setText(definition);
        }
    }

    public void addWord(ActionEvent actionEvent) throws Exception {
        if (envi.words.containsKey(addWordTextField.getText())) {
            notifyArea.setText("Word already exist.");
        } else {
            envi.words.put(addWordTextField.getText(), addExplainTextField.getText());
            try {
                FileWriter myWriter = new FileWriter("Resources\\Text\\Dict.txt");
                BufferedWriter out = new BufferedWriter(myWriter);
                for (String key : DictionaryManagement.envi.words.keySet()) {
                    out.write(key + "\t" + envi.words.get(key) + "\n");
                }
                out.close();
            } catch (IOException e) {
                System.out.println("an error occured");
                e.printStackTrace();
            }

            //xoa het tu trong map
            envi.words.clear();
            //insert lai vao trong map
            insertFromFile("Resources\\Text\\Dict.txt");
            //hien thi lai danh sach tu
            dictionarySearch("");
            label2.setText("");
            dictionarySearch("");
            notifyArea.setText("You added the word '" + addWordTextField.getText() + "'!");
            addWordTextField.setText("");
            addExplainTextField.setText("");
        }

    }

    public void editWord(ActionEvent actionEvent) throws Exception {
        if (envi.words.containsKey(editWordTextField.getText())) {
            envi.words.remove(editWordTextField.getText());
            envi.words.put(newWordTextField.getText(), newWordExplainTextField.getText());
            try {
                FileWriter myWriter = new FileWriter("Resources\\Text\\Dict.txt");
                BufferedWriter out = new BufferedWriter(myWriter);
                for (String key : DictionaryManagement.envi.words.keySet()) {
                    out.write(key + "\t" + envi.words.get(key) + "\n");
                }
                out.close();
            } catch (IOException e) {
                System.out.println("an error occured");
                e.printStackTrace();
            }
            label2.setText("");
            dictionarySearch("");
            notifyArea.setText("You changed the word '" + editWordTextField.getText() + "' to '" + newWordTextField.getText() + "'!");
            editWordTextField.setText("");
            newWordTextField.setText("");
            newWordExplainTextField.setText("");
        } else {
            notifyArea.setText("Word not found!");
        }
        dictionarySearch("");

    }


    public void deleteWord(ActionEvent actionEvent) throws IOException {
        //xoa trong tu dien
        if (envi.words.containsKey(deleteTextField.getText())) {
            envi.words.remove(deleteTextField.getText());

            //viet lai file text theo tu dien
            try {
                FileWriter myWriter = new FileWriter("Resources\\Text\\Dict.txt");
                BufferedWriter out = new BufferedWriter(myWriter);
                for (String key : DictionaryManagement.envi.words.keySet()) {
                    out.write(key + "\t" + envi.words.get(key) + "\n");
                }
                out.close();
            } catch (IOException e) {
                System.out.println("an error occured");
                e.printStackTrace();
            }
            label2.setText("");
            dictionarySearch("");
            notifyArea.setText("You deleted the word '" + deleteTextField.getText() + "'!");
            deleteTextField.setText("");
        } else {
            notifyArea.setText("Word not found!");
        }
        dictionarySearch("");


    }

    public void resetTextField(ActionEvent actionEvent) {
        textField.setText("");
        label2.setText("");
    }

    public void createTextFile(ActionEvent actionEvent) {
        try {
            File myFile = new File("Resources\\OutputFile\\" + fileNameTextField.getText() + ".txt");
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
            FileWriter myWriter = new FileWriter("Resources\\OutputFile\\" + fileNameTextField.getText() + ".txt");
            for (String key : envi.words.keySet()) {
                myWriter.write(key + "\t" + envi.words.get(key) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("an error occured");
            e.printStackTrace();
        }
        label2.setText("");
        dictionarySearch("");
        notifyArea.setText("You exported the dictionary to file '" + fileNameTextField.getText() + ".txt'!");
        fileNameTextField.setText("");
    }

    public void textToSpeech(ActionEvent actionEvent) throws Exception {
        VoiceProvider tts = new VoiceProvider("2130d5966a01404e94526dd3ccdb4062");

        VoiceParameters params = new VoiceParameters(textField.getText(), Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream("src\\voice.wav");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();

        InputStream in = new FileInputStream(new File("src\\voice.wav"));
        AudioStream sound = new AudioStream(in);
        AudioPlayer.player.start(sound);
    }

}
