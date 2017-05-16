package trainer;

import java.io.IOException;
import java.util.*;

import container.Corpus;
import container.Sentence;
import container.Textual;
import utils.Utils;

public class SimilarityLearner extends Learner {

        public SimilarityLearner(String name, Corpus corpus) {
                super(name, corpus);
        }

        @Override
        public Object learnWeight() {
                List<Double> jaccards = new ArrayList<Double>();
                for (Textual summary : corpus.getData())
                        for (Sentence s : summary.getContent())
                                jaccards.add(Utils.jaccard(s, summary.getTitle()));
                return Utils.arithmeticMean(jaccards);
        }

        @Override
        public void recordWeight(String fileName, Object data) throws IOException {
                List<String> lines = new ArrayList<String>();
                lines.add("weight " + Utils.format((Double)data));
                Utils.writeLines(fileName, lines);
        }

}
