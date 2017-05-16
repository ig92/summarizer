package trainer;

import java.io.IOException;

import container.Corpus;

public abstract class Learner {

        protected Corpus corpus;
        private String name;

        public Learner(String name, Corpus corpus) {
                this.name = name;
                this.corpus = corpus;
        }

        public String getName() {
                return name;
        }

        /**
         * Learns the weight for a feature from the
         * <i>trainer</i>
         * @return returns the weight (of any form)
         */
        public abstract Object learnWeight();

        public abstract void recordWeight(String fileName, Object data) throws IOException;
}
