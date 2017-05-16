package utils;

import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.nio.charset.*;
import java.util.*;

import container.Sentence;


/**
 * This class contains some static method used by other packages.
 * @author Ivan Grujic
 * @since September 2015
 */
public class Utils {

	/**
	 * decimal format for the double values
	 */
  private static DecimalFormat df = new DecimalFormat("0.000");

	/**
	 * This method format <i>d</i> in a 0.000 format.
	 * @param d the value to be formatted
	 * @return the string representation of d in 0.000 format
	 */
    public static String format(double d) {
                return df.format(d);
        }

	/**
	 * Read the content of the file <i>fileName</i>.
	 * @param fileName file from which the content is read
	 * @return the string lines of the file (each element of the list is a
	 * line of <i>fileName</i>)
	 */
        public static List<String> readLines(String fileName) throws IOException {
                return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        }

	/**
	 * Writes <i>lines</i> to the file <fileName>.
	 * @param fileName the name of the file where <i>lines</i> is written.
	 * @param lines the content to be written in the file <i>fileName</i>
	 * @throws IOException if something goes wrong during the writing process
	 */
        public static void writeLines(String fileName, List<String> lines) throws IOException {
                PrintWriter writer = new PrintWriter(new File(fileName));
                for (String line : lines)
                        writer.println(line);
                writer.close();
        }

	/**
	 * Given a collection of numeric values this method calculates the
	 * standard deviation.
	 * @param values the collection of numeric values
	 * @return standard deviation of <i>values</i>
	 */
        public static double standardDeviation(Collection<? extends Number> values) {
                double arithmeticMean = arithmeticMean(values);
                double sum = 0;
                for (Number d : values)
                        sum += Math.pow((((Double)d)-arithmeticMean),2);
                return Math.sqrt(sum / (values.size() - 1));
        }

	/**
	 * Given a collection of numeric values this method calculates the
	 * arithmetic mean.
	 * @param values the collection of numeric values
	 * @return arithmetic mean of <i>values</i>
	 */
        public static double arithmeticMean(Collection<? extends Number> values) {
                double arithmeticMean = 0;
                for (Number d : values)
                        arithmeticMean += (Double)d;
                return arithmeticMean/values.size();
        }

	/**
	 * Given a collection of numeric values this method calculates the
	 * inverse standard deviation, that is 1 / standard deviation.
	 * @param values the collection of numeric values
	 * @return inverse standard deviation of <i>values</i>
	 */
        public static double inverseStandardDeviation(Collection<? extends Number> values) {
                return 1 / (standardDeviation(values) + 1);
        }

	/**
	 * Calculates the jaccard similarity between <i>sentence</i>
	 * and <i>title</i>.
	 * @param sentence the first argument for the jaccard similarity
	 * @param title the second argument for the jaccard similarity
	 * @return the value of jaccard similarity between <i>sentence</i>
	 * and <i>title</i>
	 */
        public static double jaccard(Sentence sentence, Sentence title) {
                Set<String> intersection = new HashSet<String>();
                Set<String> union = new HashSet<String>();
                union.addAll(sentence.getWords());
                for (String word : title.getWords())
                        if (!union.add(word))
                                intersection.add(word);
                return ((double)intersection.size()) / union.size();
        }


}
