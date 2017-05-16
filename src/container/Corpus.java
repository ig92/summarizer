package container;

import java.util.*;

/**
 * The corpus class represents a container for both the documents
 * and the summaries. It also counts the number of sentences of the
 * whole corpus.
 * @author Ivan Grujic
 * @since September 2015
 */
public class Corpus {

        /**
         * The collection of the documents or the summaries
         */
        private Collection<Textual> collection;

        /**
         * The number of sentences in the corpus
         */
        private int numberOfSentences;

        /**
         * Creates an empty corpus.
         */
        public Corpus() {
                this(new HashSet<Textual>());
        }

        /**
         * Creates a corpus and initialize it with <i>collection</i>.
         * @param collection the documents or the summaries with which
         * the corpus is initialized
         */
        public Corpus(Collection<Textual> collection) {
                this.collection = collection;
                numberOfSentences = 0;
                addAllElements(collection);
        }

        /**
         * Returns the collection of this corpus.
         * @return the collection of this corpus
         */
        public Collection<Textual> getData() {
                return collection;
        }

        /**
         * Adds a textual to the collection of this corpus.
         * @param text the textual to be added to the collection
         */
        public void addElement(Textual text) {
                collection.add(text);
                numberOfSentences += text.getContent().size();
        }

        /**
         * Adds the collection of textual <i>collection</i> to this
         * corpus.
         * @param collection the collection of textual to be added to
         * this corpus
         */
        public void addAllElements(Collection<Textual> collection) {
                for (Textual text : collection)
                        addElement(text);
        }

        /**
         * Returns the number of sentences of this corpus.
         * @return numberOfSentences the number of sentences
         */
        public int getNumberOfSentences() {
                return numberOfSentences;
        }

        /**
         * Calculates the inverse document frequency for the word
         * <i>word</i>.
         * @param word the word for which the inverse document frequency
         * is calculated
         * @return the inverse document frequency for the word <i>word</i>
         */
        public double inverseDocumentFrequency(String word) {
                int numberOfOccurence = 0;
                for (Textual text : collection)
                        if (text.wordFrequency(word) != 0)
                                numberOfOccurence++;
                return Math.log(((double)collection.size())/numberOfOccurence);
        }

        /**
         * Returns the number of the sentences that contains the keyword
         * <i>keyword</i>.
         * @param keyword the keyword
         * @return the number of the sentence containing the keyword
         * <i>keyword</i>
         */
        public int getNumberOfSentencesWithKeyword(String keyword) {
                int occurence = 0;
                for (Textual text : collection)
                        if (text.wordFrequency(keyword) != 0)
                                for (Sentence s : text.getContent())
                                        if (s.contains(keyword))
                                                occurence++;
                return occurence;
        }
}
