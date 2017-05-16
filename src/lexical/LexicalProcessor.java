package lexical;

import java.util.*;

import container.Textual;

/**
 * The lexical processor executes the lexical tasks that
 * are stored within its internal list of lexical tasks.
 * @author Ivan Grujic
 * @since September 2015
 */
public class LexicalProcessor {

        /**
         * list of lexical tasks
         */
        private List<LexicalTask> tasks;

        /**
         * Creates a new lexical processor
         */
        public LexicalProcessor() {
                tasks = new ArrayList<LexicalTask>();
        }

        /**
         * Adds a new lexical task <i>task</i>.
         * @param task a task to be added to the list of tasks
         */
        public void addLexicalTask(LexicalTask task) {
                tasks.add(task);
        }

        /**
         * Removes <i>task</i> from the list of tasks.
         * @param task the task to be removed form the list of tasks
         * @return true iff the task <i>task</i> has been removed from
         * the list of tasks, false otherwise
         */
        public boolean removeLexicalTask(LexicalTask task) {
                return tasks.remove(task);
        }

        /**
         * Executes the tasks contained in the list of tasks over
         * the sentence <i>sentence</i>.
         * @param textual the sentence over which the tasks are to
         * be executed
         */
        public void executeTasks(Textual textual) {
                for (LexicalTask task : tasks)
                        task.execute(textual);
        }
}
