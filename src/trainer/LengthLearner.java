package trainer;

import java.io.IOException;
import java.util.*;

import container.Corpus;
import container.Sentence;
import container.Textual;
import utils.Utils;

public class LengthLearner extends Learner {

        public LengthLearner(String name, Corpus corpus) {
                super(name, corpus);
        }

        @Override
        public Object learnWeight() {
                List<Double> lengths = new ArrayList<Double>();
                double shortest = Integer.MAX_VALUE, longest = Integer.MIN_VALUE;
                for (Textual summary : corpus.getData()) {
                        for (Sentence s : summary.getContent()) {
                                double length = s.length();
                                if (length < shortest)
                                        shortest = length;
                                if (length > longest)
                                        longest = length;
                                lengths.add(length);
                        }
                }
                double weight = utils.Utils.inverseStandardDeviation(lengths);
                return new Double[]{weight, (double)shortest, (double)longest};
        }

        @Override
        public void recordWeight(String fileName, Object data) throws IOException {
                List<String> lines = new ArrayList<String>();
                lines.add("weight " + Utils.format(((Double[])data)[0]));
                lines.add("shortest " + ((Double[])data)[1]);
                lines.add("longest " + ((Double[])data)[2]);
                Utils.writeLines(fileName, lines);
        }

}
