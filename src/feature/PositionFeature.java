package feature;

/**
 * Implements the position feature.
 * @author Ivan Grujic
 * @since September 2015
 */
public class PositionFeature implements Feature {

        /**
         * Three positions: 0 first, 1 middle, 2 last
         * The value of each element represents the weight of that position
         */
        private double[] positions;

        /**
         * Creates the Position Feature applier.
         * @param positions the weights for the positions
         */
        public PositionFeature(double[] positions) {
                this.positions = positions;
        }

        /**
         * Returns the value of this feature applied on the sentence <i>s</i>.
         * @param s sentence on which this feature is applied
         * @return the weight for the sentence <i>s</i> given its position
         * within the document
         */
        @Override
        public double apply(container.Sentence s) {
                return positions[s.getPart()];
        }
}
