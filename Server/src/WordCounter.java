import java.util.HashMap;

public class WordCounter {
    public static HashMap<String, Integer> CountWords(String text){
        String[] splittedText = text.toLowerCase().split("[\\s\\W]");
        HashMap<String, Integer> mapOfWords = new HashMap<String, Integer>(){};
        for (String word : splittedText) {      //Adds value if word is present
            if(mapOfWords.containsKey(word)){
                int count = mapOfWords.get(word);
                mapOfWords.put(word, ++count);
            } else mapOfWords.put(word, 1);
        };
        if (mapOfWords.containsKey("")) mapOfWords.remove("");
        return mapOfWords;
    }
}
