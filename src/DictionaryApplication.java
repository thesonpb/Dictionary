public class DictionaryApplication extends DictionaryManagement {

    public static String showLookedupWord(String lookedUpWord) {
        String s = "";
        for (String key : DictionaryManagement.envi.words.keySet()) {
            if (key.startsWith(lookedUpWord)) s += key + "\n";
        }
        return s;
    }
}
