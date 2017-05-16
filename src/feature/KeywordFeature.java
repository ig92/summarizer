package feature;

import java.util.Map;

/**
 * Keyword Feature
 * @author Ivan Grujic
 * @since September 2015
 */
public class KeywordFeature implements Feature {

        private double weight;

        /**
         * The keywords map
         */
        private Map<String,Double> keywords;

        /**
         * Creates a Keyword Feature instance.
         * @param weight the feature's weight
         * @param keywords the keywords map
         */
        public KeywordFeature(double weight, Map<String,Double> keywords) {
                this.weight = weight;
                this.keywords = keywords;
        }

        /**
         * Returns the value of this feature applied on the sentence <i>s</i>.
         * @param s sentence on which this feature is applied
         * @return TO DESCRIBE
         */
        @Override
        public double apply(container.Sentence s) {
                double sum = 0;
                for (String keyword : keywords.keySet())
                        sum += s.wordFrequency(keyword) * keywords.get(keyword);
                return weight * sum / s.length();
        }
}
