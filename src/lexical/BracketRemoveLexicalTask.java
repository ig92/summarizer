package lexical;

import java.util.List;

import container.*;

/**
 * The lexical task that removes the left and right brackets together
 * with their content from a set of sentences. 
 * @author Ivan Grujic
 * @since September 2015
 */
public class BracketRemoveLexicalTask implements LexicalTask {

        @Override
        public void execute(Textual textual) {
                List<Sentence> content = textual.getContent();
                for (int i = 0; i < content.size(); ++i) {
                        Sentence s = content.get(i);
                        int start = s.getWords().indexOf("-LRB-");
                        int end = s.getWords().indexOf("-RRB-");
                        if (start != -1 && end != -1)
                                while (start <= end)
                                        s.getWords().remove(start++);
                }
        }

}
