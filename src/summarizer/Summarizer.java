package summarizer;

import lexical.*;
import parser.*;
import java.util.*;
import container.*;
import feature.*;
import java.io.*;

/**
 * Generic summarizer has a set of features to apply when scoring sentences,
 * a lexical processor for performing lexical tasks over sentences, a parser
 * that is used for parsing the sentences from the source and the compression
 * rate.
 * @author Ivan Grujic
 * @since September 2015
 */
public abstract class Summarizer {

        /**
         * Set of features that are applied over the sentences
         * when scoring
         */
        private Set<Feature> features;

        /**
         * the lexical processor for lexical task execution
         */
        private LexicalProcessor lexprocessor;

        /**
         * the compression rate for the summaries
         */
        private int compressionRate;

        /**
         * the parser used for parsing the sentences from the source
         */
        private Parser parser;

        /**
         * Creates a summarizer with compression rate <i>compressionRate</i>.
         * Note that this constructor uses a Strong Parser as default.
         * @param compressionRate the compression rate for the summaries
         * @throws IOException when something goes wring while reading stopwords
         */
        public Summarizer(int compressionRate) throws IOException {
                this(compressionRate, new StrongParser());
        }

        /**
         * Creates a summarizer with compression rate <i>compressionRate</i>
         * and the parser <i>parser</i>.
         * @param compressionRate the compression rate for the summaries
         * @param parser the parser
         */
        public Summarizer(int compressionRate, Parser parser) {
                this.compressionRate = compressionRate;
                this.parser = parser;
                lexprocessor = new LexicalProcessor();
                features = new HashSet<Feature>();
        }

        /**
         * Adds <i>feature</i> to the set of features.
         * @param feature the feature to be added
         */
        public void addFeature(Feature feature) {
                features.add(feature);
        }

        /**
         * Removes <i>feature</i> from the set of features.
         * @param feature the feature to be removed
         * @return true iff <i>feature</i> has been removed, false otherise
         */
        public boolean removeFeature(Feature feature) {
                return features.remove(feature);
        }

        /**
         * Adds lexical task <i>task</i> to the list of lexical tasks of the
         * lexical processor.
         * @param task the task to be added
         */
        public void addLexicalTask(LexicalTask task) {
                lexprocessor.addLexicalTask(task);
        }

        /**
         * Removes the lexical task <i>task</i> from the list of lexical tasks
         * of the lexical processor.
         * @param task the task to be removed
         * @return true iff <i>task</i> has been removed, false otherwise
         */
        public boolean removeLexicalTask(LexicalTask task) {
                return lexprocessor.removeLexicalTask(task);
        }

        /**
         * Executes the lexical tasks that are stored in the list of lexical
         * tasks in the lexical processor.
         * @param textual the sentence over which the lexical tasks are to
         * be executed
         */
        public void executeLexicalTasks(Textual textual) {
                lexprocessor.executeTasks(textual);
        }

        /**
         * Applies the features from the set of features of this summarizer
         * over the sentence <i>sentence</i>.
         * @param sentences the sentence over which the features are applied
         */
        public void applyFeatures(List<Sentence> sentences) {
                for (Sentence sentence : sentences)
                        for (Feature feature : features)
                                sentence.setScore(sentence.getScore() + feature.apply(sentence));
        }

        /*
         * ####################################################################
         * SETTERS AND GETTERS
         * ####################################################################
         */
        public Set<Feature> getFeatures() {
                return features;
        }

        public void setFeatures(Set<Feature> features) {
                this.features = features;
        }

        public LexicalProcessor getLexprocessor() {
                return lexprocessor;
        }

        public void setLexprocessor(LexicalProcessor lexprocessor) {
                this.lexprocessor = lexprocessor;
        }

        public int getCompressionRate() {
                return compressionRate;
        }

        public void setCompressionRate(int compressionRate) {
                this.compressionRate = compressionRate;
        }

        public Parser getParser() {
                return parser;
        }

        public void setParser(Parser parser) {
                this.parser = parser;
        }
        /*
         * ####################################################################
         */

        public abstract Textual summarize(String fileName) throws IOException;
}


