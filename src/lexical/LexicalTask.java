package lexical;

import container.Textual;

/**
 * This interface represents a generic lexical task. The task can be
 * executed directly on a sentence.
 * @author Ivan Grujic
 * @since September 2015
 */
public interface LexicalTask {

        /**
         * Process this lexical task over the sentence <i>sentence</i>
         * @param textual the sentence over which this lexical task
         * is processed
         */
        public void execute(Textual textual);
}
