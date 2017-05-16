package feature;

import container.Sentence;

/**
 * Interface for a generic Feature.
 * @author Ivan Grujic
 * @since September 2015
 */
public interface Feature {

        /**
         * Applies the feature to the sentence <i>s</i>
         * @param s sentence on which the feature is applied
         * @return the score of the feature over the sentence <i>s</i>
         */
        public double apply(Sentence s);
}
