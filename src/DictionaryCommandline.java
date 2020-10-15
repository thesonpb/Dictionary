import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {
    /**
     * ver1.
     * hiển thị tất cả các từ có trong từ điển.
     */
    public static void showAllWords() {

        System.out.printf("%-10s %-20s %-20s", "No", "| English", "| Vietnamese");
        System.out.print("\n");
        int i = 1;
        for (String key : DictionaryManagement.envi.words.keySet()) {
            System.out.printf("%-10d", i);
            i++;
            System.out.printf("%-20s %-20s", key, DictionaryManagement.envi.words.get(key));
            System.out.print("\n");
        }
    }

    /**
     * ver1.
     */
    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandline();
        showAllWords();
    }

    /**
     * ver2.
     */
    public static void dictionaryAdvanced() throws Exception {
        DictionaryManagement.insertFromFile("D:\\Java\\Dictionaryfinal\\Resources\\Text\\Dict.txt");
        showAllWords();
        DictionaryManagement.dictionaryLookUp();
    }

    /**
     * ver3.
     * tìm kiếm từ trong từ điển, in ra danh sách các từ bắt đầu bằng xâu nhập vào.
     */
    public static void dictionarySearcher() {
        System.out.print("You are looking for: ");
        Scanner sc = new Scanner(System.in);
        String tra = sc.next();
        for (String key : DictionaryManagement.envi.words.keySet()) {
            if (key.startsWith(tra)) System.out.println(key + "\t" + DictionaryManagement.envi.words.get(key));
        }
    }
}
