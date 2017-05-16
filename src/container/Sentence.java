package container;

import java.util.*;

/**
 * Sentence is represented as a list of words.
 * @author Ivan Grujic
 * @since September 2015
 */
public class Sentence {

        /**
         * the document to which the sentence belongs
         */
        private Textual container;

        /**
         * words compounding the sentence
         */
        private List<String> words;

        /**
         * the position of the sentence within the paragraph
         */
        private int positionWithinParagraph;

        /**
         * the position of the sentence within the document
         */
        private int positionWithinDocument;

        /**
         * id of the paragraph within which this sentence is found
         */
        private int paragraphID;

        /**
         * the overall score for the sentence when the features
         * are applied
         */
        private double score;

        /**
         * Creates a sentence with words <i>words</i>.
         * @param words the list of words compounding the sentence
         */
        public Sentence(List<String> words) {
                this.words = words;
        }

        /**
         * Returns the length of the sentence, that is the number
         * of words of which it is compounded.
         * @return the number of words
         */
        public int length() {
                return words.size();
        }

        /**
         * Verifies whether this sentence contains the word <i>word</i>.
         * @param word the word
         * @return true iff the sentence contains <i>word</i>, false
         * otherwise
         */
        public boolean contains(String word) {
                return words.contains(word);
        }

        /**
         * Calculates the word frequency of <i>word</i> in this sentence.
         * @param word the word
         * @return return the frequency of the word <i>word</i> in the
         * sentence
         */
        public double wordFrequency(String word) {
                int sum = 0;
                for (String w : words)
                        if (word.equals(w)) sum += 1;
                return ((double)sum) / words.size();
        }

        /**
         * Returns the string representation of this sentence.
         * @return the sentence as a string
         */
        @Override
        public String toString() {
                return edu.stanford.nlp.ling.Sentence.listToString(words);
        }

        /**
         * Verifies the equality between this sentence and the sentence
         * <i>s</i>. The two sentences are equal when they have the same
         * length and they has the same words in the same order.
         * @param s the sentence to be compared with this sentence
         * @return true iff this sentence is equal to <i>s</i>, false otherwise
         */
        @Override
        public boolean equals(Object s) {
                Sentence sentence = (Sentence) s;
                if (words.size() != sentence.length())
                        return false;
                for (int i = 0; i < words.size(); i++)
                        if (!words.get(i).equals(sentence.words.get(i)))
                                return false;
                return true;
        }

        /**
         * Calculates the part is which the sentence belongs. The part is
         * calculated as follows: the first 20% of sentences within the
         * document are sentences in the first part (that is 0), the following
         * 60% is in the middle part (that is 1) and the finally 20% is in the
         * last part (that is 2).
         * @return 0, 1 or 2 depending on in which part the sentence is located
         */
        public int getPart() {
                int numberOfSentences = container.getContent().size();
                if (positionWithinDocument <= Math.ceil(numberOfSentences * 2.0 / 10.0))
                        return 0;
                else if (positionWithinDocument <= Math.ceil(numberOfSentences * 7.0 / 10.0))
                        return 1;
                else return 2;
        }

        /*
         * ##########################################################################
         * SETTERS AND GETTERS
         * ##########################################################################
         */
        public Textual getContainer() {
                return container;
        }

        public void setContainer(Textual container) {
                this.container = container;
        }

        public List<String> getWords() {
                return words;
        }

        public void setWords(List<String> words) {
                this.words = words;
        }

        public int getPositionWithinParagraph() {
                return positionWithinParagraph;
        }

        public void setPositionWithinParagraph(int positionWithinParagraph) {
                this.positionWithinParagraph = positionWithinParagraph;
        }

        public int getPositionWithinDocument() {
                return positionWithinDocument;
        }

        public void setPositionWithinDocument(int positionWithinDocument) {
                this.positionWithinDocument = positionWithinDocument;
        }

        public int getParagraphID() {
                return paragraphID;
        }

        public void setParagraphID(int position) {
                this.paragraphID = position;
        }

        public double getScore() {
                return score;
        }

        public void setScore(double score) {
                this.score = score;
        }
        /*
         * ##########################################################################
         */
}
