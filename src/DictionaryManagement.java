import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {

    protected static Dictionary envi = new Dictionary();

    /**
     * ver1.
     * nhập số lượng từ và nhập từ và nghĩa vào bằng commandline.
     */
    public static void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Insert the number of words: ");
        int numberOfWords = sc.nextInt();
        for (int i = 0; i < numberOfWords; i++) {
            Word newWord = new Word();
            System.out.println("Word target: ");
            String wordTarget = sc.next();
            System.out.println("Word explain: ");
            sc.nextLine();
            String wordExplain = sc.nextLine();
            newWord.setWord_target(wordTarget);
            newWord.setWord_explain(wordExplain);
            envi.words.put(newWord.getWord_target(), newWord.getWord_explain());
        }
    }


    /**
     * ver2.
     * nhập dữ liệu từ điển từ file Dict.txt.
     */
    public static void insertFromFile(String fileName) throws Exception {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] splitted = line.split("\t");
            String target = splitted[0].trim();
            String explain = splitted[1].trim();
            Word newWord = new Word();
            newWord.setWord_target(target);
            newWord.setWord_explain(explain);
            envi.words.put(newWord.getWord_target(), newWord.getWord_explain());
        }
    }

    /**
     * ver2.
     * tra cứu từ điển bằng dòng lệnh.
     */
    public static void dictionaryLookUp() {
        Scanner sc = new Scanner(System.in);
        System.out.print("look up for: ");
        String wordTarget = sc.next();
        if (envi.words.containsKey(wordTarget)) {
            System.out.print("the word you are looking for is: ");
            System.out.printf("%-20s %-20s", wordTarget, envi.words.get(wordTarget));
        } else {
            System.out.println("Word not found!");
        }
    }

    /**
     * ver3.
     * ham them tu vao tu dien.
     */
    public static void addWords() {
        System.out.print("Insert word: ");
        Scanner sc = new Scanner(System.in);
        String newWordTarget = sc.next();
        if (envi.words.containsKey(newWordTarget)) {
            System.out.println("Word already exist.");
        } else {
            sc.nextLine();
            System.out.print("Insert word explain: ");
            String newWordExplain = sc.nextLine();
            envi.words.put(newWordTarget, newWordExplain);
        }
    }

    /**
     * ver3.
     * ham sua tu trong tu dien.
     */
    public static void editWords() {
        System.out.print("Insert word that you want to edit: ");
        Scanner sc = new Scanner(System.in);
        String wordTarget = sc.next();
        if (!envi.words.containsKey(wordTarget)) {
            System.out.println("Word not found.");
        } else {
            envi.words.remove(wordTarget);
            System.out.print("Edit the word: ");
            wordTarget = sc.next();
            System.out.print("Edit the meaning: ");
            sc.nextLine();
            String wordExplain = sc.nextLine();
            envi.words.put(wordTarget, wordExplain);
        }
    }

    /**
     * ver3.
     * ham xoa tu trong tu dien.
     */
    public static void deleteWords() {
        System.out.print("Insert the word that you want to delete: ");
        Scanner sc = new Scanner(System.in);
        String word = sc.next();
        if (!envi.words.containsKey(word)) {
            System.out.println("Word not found.");
        } else {
            envi.words.remove(word);
        }
    }

    /**
     * ver3.
     * xuat du lieu tu dien ra file.
     */
    public static void dictionaryExportToFile() {
        //tạo file text.
        try {
            File myFile = new File("filename.txt");
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
            FileWriter myWriter = new FileWriter("Resources\\Text\\filename.txt");
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
