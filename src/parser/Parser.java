package parser;

import java.io.*;
import java.util.*;

import container.*;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import utils.Utils;

/**
 * Parser
 * @author Ivan Grujic
 * @since September 2015
 */
public abstract class Parser {

        public List<Sentence> parseFileDocument(File file) throws IOException {
                List<String> paragraphs = Utils.readLines(file.getAbsolutePath());
                int sentencePositionWithinDocument = 1;
                int paragraphID = 1;
                List<Sentence> content = new ArrayList<Sentence>();
                for (String par : paragraphs) {
                        int sentencePositionWithinParagraph = 1;
                        List<Sentence> paragraph = parseParagraph(par);
                        for (Sentence s : paragraph) {
                                s.setPositionWithinDocument(sentencePositionWithinDocument++);
                                s.setPositionWithinParagraph(sentencePositionWithinParagraph++);
                                s.setParagraphID(paragraphID);
                        }
                        paragraphID++;
                        content.addAll(paragraph);
                }
                return content;
        }

        public List<Sentence> parseFileSummary(Textual doc, File file) throws IOException {
                List<Sentence> content = new ArrayList<Sentence>();
                for (String par : Utils.readLines(file.getAbsolutePath()))
                        for (Sentence s : parseParagraph(par))
                                content.add(doc.getSentence(s));
                return content;
        }

        public List<Sentence> parseParagraph(String paragraph) {
                List<Sentence> sentences = new ArrayList<Sentence>();
                DocumentPreprocessor dp = new DocumentPreprocessor(new StringReader(paragraph));
                for (List<HasWord> words : dp)
                        sentences.add(new Sentence(getWords(words)));
                return sentences;
        }

        public Sentence parseSentence(String string) {
                DocumentPreprocessor dp = new DocumentPreprocessor(new StringReader(string));
                return new Sentence(getWords(dp.iterator().next()));
        }

        protected abstract List<String> getWords(List<HasWord> sentence);
}
