package trainer;

import java.util.*;

import container.*;

import java.io.*;
import utils.Utils;

/**
 * Keyword Learner learns the feature regarding positive and negative
 * keywords.
 * @author Ivan Grujic
 * @since September 2015
 */
public class KeywordLearner extends Learner {

        /**
         * Inner class representing a generic Keyword, both positive
         * and negative. This class must implement comparable since
         * its objects are used by the priority queue.
         * @author Ivan Grujic
         * @since September 2015
         */
        protected class Keyword implements Comparable<Keyword> {

                /**
                 * string representation of the keyword
                 */
                private String term;

                /**
                 * the frequency of the term within the corpus of
                 * the summaries
                 */
                private double freq;

                /**
                 * Creates a new keyword having <i>term</i> as term and
                 * <i>freq</i> as frequency of the term.
                 * @param term the term
                 * @param freq frequency of the term within the summaries
                 */
                public Keyword(String term, double freq) {
                        this.term = term;
                        this.freq = freq;
                }

                /**
                 * Compares this keyword with <i>keyword</i> passed as
                 * argument. The comparison is done over frequencies and
                 * note that the comparison is inverse since we want a
                 * maximum heap in priority queue.
                 * @param keyword the keyword compared to this keyword
                 * @return 1 if this keyword is less frequent that
                 * <i>keyword</i>, 0 if the two frequencies are the same
                 * or -1 otherwise
                 */
                @Override
                public int compareTo(Keyword keyword) {
                        return (freq < keyword.freq) ? 1 : (freq == keyword.freq) ? 0 : -1;
                }

                /**
                 * Verifies whether this keyword is equal to <i>keyword</i>.
                 * The equality is verified comparing the two terms and not
                 * the frequencies.
                 * @param keyword the keyword compared to this keyword
                 * @return true if this keyword is equal to <i>keyword>/i>,
                 * false otherwise
                 */
                @Override
                public boolean equals(Object keyword) {
                        return this.term.equals(((Keyword)keyword).term);
                }
        }

        /**
         * how many keywords you want to extract
         */
        private int numberOfKeywords;

        /**
         * Creates a Keyword Learner.
         * @param name the name of the file where the extracted keywords will be saved
         * @param corpus corpus over which the keyword learner works; may be the corpus
         * of summaries, non summaries or documents
         * @param numberOfKeywords the number of keywords to extract
         */
        public KeywordLearner(String name, Corpus corpus, int numberOfKeywords) {
                super(name, corpus);
                this.numberOfKeywords = numberOfKeywords;
        }

        /**
         * When this method is invoked, this Keyword Learner learns the
         * weight for the feature and extracts the <i>numberOfKeywords</i>
         * keywords. The info is returned as a collection of keywords where
         * the last one represents the feature's weight.
         * @return a collection of extracted keywords where the last one
         * represents the weight of the feature
         */
        @Override
        public Object learnWeight() {
                Queue<Keyword> queue = new PriorityQueue<Keyword>();
                List<Keyword> keys = new ArrayList<Keyword>();
                for (Textual textual : corpus.getData()) {
                        for (String term : textual.getWords()) {
                                Keyword key = new Keyword(term,textual.getWordOccurenceTable().get(term)/textual.getWords().size());
                                int index = keys.indexOf(key);
                                if (index != -1)
                                        keys.get(index).freq += key.freq;
                                else keys.add(key);
                        }
                }
                queue.addAll(keys);
                Collection<Keyword> weight = new ArrayList<Keyword>();
                for (int i = 0; i < numberOfKeywords; ++i)
                        weight.add(queue.poll());
                Collection<Double> freqs = new ArrayList<Double>();
                weight.forEach(keyword -> freqs.add(keyword.freq));
                weight.add(new Keyword("--feature weight--", Utils.arithmeticMean(freqs)));
                return weight;
        }

        /**
         * Records the feature weight and the extracted keywords in the
         * file <i>fileName</i>. The weight and the keywords are passed
         * through <i>data</i>. Note that this is actually a collection
         * of keywords.
         * @param fileName the name of the file
         * @param data the keywords to be saved in the file
         */
        @SuppressWarnings("unchecked") // cast matters
        @Override
        public void recordWeight(String fileName, Object data) throws IOException {
                List<String> lines = new ArrayList<String>();
                for (Keyword entry : ((Collection<Keyword>) data))
                        lines.add(entry.term + " " + Utils.format(entry.freq));
                Utils.writeLines(fileName, lines);
        }

}
