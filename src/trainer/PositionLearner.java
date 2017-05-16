package trainer;

import java.io.IOException;
import java.util.*;

import container.Corpus;
import container.Sentence;
import container.Textual;
import utils.Utils;

public class PositionLearner extends Learner {

        public PositionLearner(String name, Corpus corpus) {
                super(name, corpus);
        }

        @Override
        public Object learnWeight() {
                double[] positions = new double[3];
                int size = 0;
                for (Textual summary : corpus.getData()) {
                        size += summary.getContent().size();
                        for (Sentence s : summary.getContent())
                                positions[s.getPart()] += 1.0;
                }
                positions[0] /= size;
                positions[1] /= size;
                positions[2] /= size;
                return positions;
        }

        @Override
        public void recordWeight(String fileName, Object data) throws IOException {
                List<String> lines = new ArrayList<String>();
                lines.add("first " + Utils.format(((double[])data)[0]));
                lines.add("middle " + Utils.format(((double[])data)[1]));
                lines.add("last " + Utils.format(((double[])data)[2]));
                Utils.writeLines(fileName, lines);
        }

}
