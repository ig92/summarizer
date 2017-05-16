package feature;

/**
 * Implements the length feature.
 * @author Ivan Grujic
 * @since September 2015
 */
public class LengthFeature implements Feature {

        private double weight;

        /**
         * The length of the shortest sentence in the corpus
         */
        private int shortest;

        /**
         * The length of the longest sentence in the corpus
         */
        private int longest;

        /**
         * Creates a Length Feature applier.
         * @param weight the weight of this feature
         * @param shortest the length of the shortest sentence in the corpus
         * @param longest the length of the longest sentence in the corpus
         */
        public LengthFeature(double weight, int shortest, int longest) {
                this.weight = weight;
                this.shortest = shortest;
                this.longest = longest;
        }

        /**
         * Returns the weight of the feature iff the length of the sentence
         * is between the length of the shortest and the longest sentence in
         * the corpus, otherwise 0.
         * @param s sentence on which this feature is applied
         * @return weight of the feature or 0
         */
        @Override
        public double apply(container.Sentence s) {
                return (shortest <= s.length() && s.length() <= longest) ? weight : 0;
        }
}
