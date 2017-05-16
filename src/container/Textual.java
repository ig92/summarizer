package container;

import java.util.*;

/**
 * Textual is a container for the document or the summary
 * content. It also holds a map for word occurrences, that
 * is, for each word it holds the number of times it appears
 * in the textual.
 * @author Ivan Grujic
 * @since September 2015
 */
public class Textual {

        /**
         * the title of the textual
         */
        private Sentence title;

        /**
         * the list of the sentences
         */
        private List<Sentence> content;

        /**
         * word occurrence table, how many times
         * each word appears in the textual
         */
        private Map<String,Integer> wordOccurenceTable;

        /**
         * word number in the textual
         */
        private int wordNumber;

        /**
         * Creates an empty textual with the title <i>title</i>.
         */
        public Textual(Sentence title) {
                this(title, new ArrayList<Sentence>());
        }

        /**
         * Creates a textual with the title <i>title</i> and the
         * initial content <i>content</i>.
         * @param title the title of the textual
         * @param content the initial content
         */
        public Textual(Sentence title, List<Sentence> content) {
                this.title = title;
                this.content = content;
                wordOccurenceTable = new HashMap<String,Integer>();
        }

        /**
         * Adds the sentence <i>sentence</i> to this textual.
         * @param sentence the sentence to be added
         */
        public void addContent(Sentence sentence) {
                content.add(sentence);
                Integer freq = null;
                for (String word : sentence.getWords()) {
                        freq = wordOccurenceTable.get(word);
                        if (freq == null)
                                freq = new Integer(0);
                        wordOccurenceTable.put(word, freq+1);
                        wordNumber++;
                }
        }

        /**
         * Adds the content <i>content</i> to this textual.
         * @param content the list of the sentences
         */
        public void addAllContent(List<Sentence> content) {
                for (Sentence sentence : content)
                        this.addContent(sentence);
        }

        /**
         * Returns the number of different words in this textual.
         * @return the set of different words in this textual
         */
        public Set<String> getWords() {
                return wordOccurenceTable.keySet();
        }

        public double wordFrequency(String word) {
                Integer wordOccurenceNumber = wordOccurenceTable.get(word.toLowerCase());
                return (wordOccurenceNumber == null) ? 0 : ((double)wordOccurenceNumber) / wordNumber;
        }

        /**
         * Returns the sentence in position <i>position</i>.
         * @param position the position
         * @return the sentence in position <i>position</i>
         */
        public Sentence getSentenceInPosition(int position) {
                return content.get(position);
        }

        /**
         * Calculates the difference between this textual and <i>summary</i>.
         * This method is principally used for generating the nonsummaries,
         * that is document sentences - summary sentences.
         * @param summary the summary
         * @return the difference between this textual and <i>summary</i>
         */
        public Textual difference(Textual summary) {
                Textual nonsummary = new Textual(title);
                for (Sentence s : content)
                        if (!summary.content.contains(s))
                             nonsummary.addContent(s);
                return nonsummary;
        }

        /**
         * Returns the sentence s. Note that this method is
         * used principally for retrieving the sentence <i>s</i>
         * from the document given the sentence <i>s</i> from
         * the relative summary.
         * @param s the sentence from the summary
         * @return the sentence <i>s</i> from the document
         */
        public Sentence getSentence(Sentence s) {
                return content.get(content.indexOf(s));
        }

        public Sentence getSentencesAsSentence() {
                List<String> words = new ArrayList<String>();
                for (Sentence sentence : content)
                        words.addAll(sentence.getWords());
                return new Sentence(words);
        }

        /*
         * ##################################################################
         * SETTERS AND GETTERS
         * ##################################################################
         */
        public Sentence getTitle() {
                return title;
        }

        public void setTitle(Sentence title) {
                this.title = title;
        }

        public List<Sentence> getContent() {
                return content;
        }

        public void setContent(List<Sentence> content) {
                this.content = content;
        }

        public Map<String, Integer> getWordOccurenceTable() {
                return wordOccurenceTable;
        }

        public void setWordOccurenceTable(HashMap<String, Integer> wordOccurenceTable) {
                this.wordOccurenceTable = wordOccurenceTable;
        }

        public int getWordNumber() {
                return wordNumber;
        }

        public void setWordNumber(int wordNumber) {
                this.wordNumber = wordNumber;
        }
        /*
         * ##################################################################
         */
}
