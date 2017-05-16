package parser;

import java.util.*;
import edu.stanford.nlp.ling.HasWord;

public class WeakParser extends Parser {

        @Override
        protected List<String> getWords(List<HasWord> sentence) {
                List<String> words = new ArrayList<String>();
                for (HasWord w : sentence)
                        words.add(w.word());
                return words;
        }

}
