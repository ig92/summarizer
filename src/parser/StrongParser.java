package parser;

import java.io.*;
import java.util.*;
import edu.stanford.nlp.ling.HasWord;
import utils.Utils;

public class StrongParser extends Parser{

        private PorterStemmer porterStemmer;
        private List<String> stops;

        public StrongParser() throws IOException {
                stops = Utils.readLines("data/stopwords");
                porterStemmer = new PorterStemmer();
        }

        private String stopsAndStem(String word) {
                return (!stops.contains(word.toLowerCase())) ?
                        porterStemmer.stem(word.toLowerCase()) : null;
        }

        @Override
        protected List<String> getWords(List<HasWord> sentence) {
                List<String> words = new ArrayList<String>();
                for (HasWord w : sentence) {
                        String word = stopsAndStem(w.word());
                        if (word != null)
                                words.add(word.toLowerCase());
                }
                return words;
        }
}
