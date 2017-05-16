package trainer;

import java.io.File;
import java.io.IOException;
import java.util.*;

import container.*;
import parser.*;

public class Trainer {

        private Set<Learner> learners;
        private Corpus documents;
        private Corpus summaries;
        private Corpus nonsummaries;

        public Trainer() throws IOException {
                Parser parser = new StrongParser();
                documents = new Corpus();
                summaries = new Corpus();
                nonsummaries = new Corpus();
                learners = new HashSet<Learner>();
                for (String fileName : new File("data/docs/").list()) {
                        Sentence title = parser.parseSentence(fileName);
                        Textual doc = new Textual(title);
                        List<Sentence> docContent = parser.parseFileDocument(new File("data/docs/"+fileName));
                        // magic happens here :) (thank you lambda)
                        docContent.forEach(sentence -> sentence.setContainer(doc));
                        doc.addAllContent(docContent);
                        documents.addElement(doc);

                        Textual sum = new Textual(title);
                        List<Sentence> sumContent = parser.parseFileSummary(doc, new File("data/sums/"+fileName));
                        // again, magic happens here :) (thank you lambda)
                        sumContent.forEach(sentence -> sentence.setContainer(doc));
                        sum.addAllContent(sumContent);
                        summaries.addElement(sum);

                        nonsummaries.addElement(doc.difference(sum));
                }
        }

        public void addLearner(Learner learner) {
                learners.add(learner);
        }

        public boolean removeLearner(Learner learner) {
                return learners.remove(learner);
        }

        public void activateLearners() throws IOException {
                for (Learner learner : learners)
                        learner.recordWeight(
                                "data/features/"+learner.getName()+".w",
                                learner.learnWeight()
                        );
        }

        public static void main(String[] args) throws IOException {
                Trainer trainer = new Trainer();
                trainer.addLearner(new LengthLearner("length", trainer.summaries));
                trainer.addLearner(new PositionLearner("position", trainer.summaries));
                trainer.addLearner(new SimilarityLearner("similarity", trainer.summaries));
                trainer.addLearner(new KeywordLearner("positive", trainer.summaries, 10));
                trainer.addLearner(new KeywordLearner("negative", trainer.nonsummaries, 10));
                trainer.activateLearners();
        }

}
