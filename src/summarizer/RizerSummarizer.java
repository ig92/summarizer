package summarizer;

import java.io.*;
import java.util.*;
import container.*;
import parser.*;
import utils.Utils;
import feature.*;

public class RizerSummarizer extends Summarizer {

        public RizerSummarizer(int compressionRate) throws IOException {
                this(compressionRate, new StrongParser());
        }

        public RizerSummarizer(int compressionRate, Parser parser) {
                super(compressionRate, parser);
        }

        @Override
        public Textual summarize(String fileName) throws IOException {
                Sentence title = super.getParser().parseSentence(fileName);
                Textual document = new Textual(title);
                List<Sentence> content = super.getParser().parseFileDocument(new File(fileName));
                content.forEach(sentence -> sentence.setContainer(document));
                document.addAllContent(content);

                super.addFeature(loadLengthFeature("data/features/length.w"));
                super.addFeature(loadPositionFeature("data/features/position.w"));
                super.addFeature(loadSimilarityFeature("data/features/similarity.w"));
                super.addFeature(loadKeywordFeature("data/features/positive.w"));
                super.addFeature(loadKeywordFeature("data/features/negative.w"));

                super.applyFeatures(document.getContent());

                int numberOfSentences = document.getContent().size() * super.getCompressionRate() / 100;
                List<Sentence> newSummary = new ArrayList<Sentence>();
                for (Sentence s : document.getContent()) {
                        if (newSummary.size() < numberOfSentences)
                                newSummary.add(s);
                        else {
                                int min = 0;
                                for (int i = 0; i < newSummary.size(); i++)
                                        if (newSummary.get(i).getScore() < newSummary.get(min).getScore())
                                                min = i;
                                if (newSummary.get(min).getScore() < s.getScore()) {
                                        newSummary.remove(min);
                                        newSummary.add(s);
                                }
                        }
                }

                super.setParser(new WeakParser());
                Sentence originalTitle = super.getParser().parseSentence(fileName);
                Textual original = new Textual(originalTitle);
                content = super.getParser().parseFileDocument(new File(fileName));
                content.forEach(sentence -> sentence.setContainer(original));
                original.addAllContent(content);

                for (Sentence s : newSummary)
                        System.out.println(original.getSentenceInPosition(s.getPositionWithinDocument()-1));
                return null;
        }

        public static void main(String[] args) throws IOException {
                Summarizer summarizer = new RizerSummarizer(25);
                summarizer.summarize("test");
        }

        public Feature loadLengthFeature(String fileName) throws IOException {
                List<String> lines = Utils.readLines(fileName);
                double[] values = new double[3];
                int i = 0;
                for (String line : lines)
                        values[i++] = Double.parseDouble(line.split(" ")[1]);
                return new LengthFeature(values[0], (int)values[1], (int)values[2]);
        }

        public Feature loadPositionFeature(String fileName) throws IOException {
                List<String> lines = Utils.readLines(fileName);
                double[] values = new double[3];
                int i = 0;
                for (String line : lines)
                        values[i++] = Double.parseDouble(line.split(" ")[1]);
                return new PositionFeature(values);
        }

        public Feature loadSimilarityFeature(String fileName) throws IOException {
                List<String> lines = Utils.readLines(fileName);
                return new SimilarityFeature(Double.parseDouble(lines.get(0).split(" ")[1]));
        }

        public Feature loadKeywordFeature(String fileName) throws IOException {
                List<String> lines = Utils.readLines(fileName);
                double weight;
                Map<String,Double> keywords = new HashMap<String,Double>();
                for (int i = 0; i < 10; i++) {
                        String term = lines.get(i).split(" ")[0];
                        double freq = Double.parseDouble(lines.get(i).split(" ")[1]);
                        keywords.put(term, freq);
                }
                weight = Double.parseDouble(lines.get(10).split(" ")[2]);
                return new KeywordFeature(weight, keywords);
        }
}
