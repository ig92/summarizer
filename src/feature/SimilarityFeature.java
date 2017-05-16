package feature;

import utils.Utils;

/**
 * Implements the similarity feature.
 * @author Ivan Grujic
 * @since September 2015
 */
public class SimilarityFeature implements Feature {

        private double weight;

        /**
         * Creates a Similarity Feature applier.
         * @param weight the weight to associate to this feature
         */
        public SimilarityFeature(double weight) {
                this.weight = weight;
        }

        /**
         * Returns the value of this feature applied on the sentence <i>s</i>.
         * @param s sentence on which this feature is applied
         * @return weight times the jaccard similarity between the title of
         * the document and a sentence <i>s</i> that belongs to that document
         */
        @Override
        public double apply(container.Sentence s) {
                return weight * Utils.jaccard(s, s.getContainer().getTitle());
        }
}
