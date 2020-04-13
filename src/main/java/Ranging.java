import java.util.*;

public class Ranging {
    static Set<String> selectRequest(String request)  {
        String lower = request.toLowerCase();
        String newReq = lower.replaceAll("[^a-zA-Z0-9а-яА-Я]", " ");
        String[] words = newReq.split("[ ]+");
        for (int i = 0; i < words.length; i++) {
            if (StopWordsList.getStopWords().contains(words[i])) {                //change
                words[i] = null;
            }
        }
        return ranging(select(words));
    }

    private static List<Word> select(String[] words){
        return WordsWriter.selectUrls(words);
    }

    static HashSet<String> ranging(List<Word> result) {
        if (result.isEmpty()) {
            return null;
        }
        List<String> answer = new ArrayList<>();
        List<Word> firstPart = new ArrayList<>();
        List<Word> secondPart = new ArrayList<>();
        for (Word word : result) {
            if (word.isContainedInTitle()) {
                firstPart.add(word);
            } else {
                secondPart.add(word);
            }
        }
        Word[] first = firstPart.toArray(Word[]::new);
        Word[] second = secondPart.toArray(Word[]::new);
        Arrays.sort(first);
        Arrays.sort(second);
        for (int i = 0; i < first.length; i++) {
            answer.add(first[i].getWord());
        }
        for (int i = 0; i < second.length; i++) {
            answer.add(second[i].getWord());
        }
        HashSet<String> answerSet = new LinkedHashSet<>(answer);
        return answerSet;
    }
}
