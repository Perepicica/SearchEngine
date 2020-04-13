import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class WordsWriter {

    private static HashMap<String, ArrayList<Word>> wordsStore = new HashMap<>();

    static void insertData(String url, List<Word> words) {
        ArrayList<Word> tmpData;
        for (Word word : words) {
            tmpData = wordsStore.get(word.getWord());
            if (tmpData == null) {
                tmpData = new ArrayList<>();
                tmpData.add(new Word(url,word.getNumberOfRepetitions(),word.isContainedInTitle()));
                wordsStore.put(word.getWord(),tmpData);
            } else {
                tmpData.add(new Word(url,word.getNumberOfRepetitions(),word.isContainedInTitle()));
                wordsStore.put(word.getWord(),tmpData);
            }
        }
    }

    static List<Word> selectUrls (String[] words){
        List<Word> result = new ArrayList<>();
        ArrayList<Word> tmpData;
        for (int i = 0; i < words.length; i++) {
            tmpData = wordsStore.get(words[i]);
            if(tmpData != null){
                result.addAll(tmpData);
            }
        }
        return result;
    }
}
