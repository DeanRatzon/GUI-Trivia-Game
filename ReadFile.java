
/** This class used to read the question and answers file to be used in the trivia game
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ReadFile {

	public ArrayList<String> questions = new ArrayList<String>();
	public ArrayList<String> correctAns = new ArrayList<String>();
	public ArrayList<String> readedAns = new ArrayList<String>();
	public ArrayList<ArrayList<String>> answersGuide = new ArrayList<ArrayList<String>>();

	/**
	 * Constructs appropriate lists for questions and answers by reading from a file
	 * 
	 */
	public ReadFile() {

		try {
			Scanner input = new Scanner(new File("trivia.txt"));
			while (input.hasNext()) {
				readedAns.clear();
				String q = input.nextLine();
				questions.add(q);
				String a1 = input.nextLine();
				correctAns.add(a1);
				readedAns.add(a1);
				String a2 = input.nextLine();
				readedAns.add(a2);
				String a3 = input.nextLine();
				readedAns.add(a3);
				String a4 = input.nextLine();
				readedAns.add(a4);
				ArrayList<String> read = new ArrayList<String>(readedAns);
				answersGuide.add(read);
			}
			input.close();

		} catch (java.io.FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File Not Found, Shutting down.");
			System.exit(0);
		}

	}

	/**
	 * Copy Constructor for a readed file
	 * 
	 * @param toCopy
	 *            represents the readed file to be copied
	 */
	public ReadFile(ReadFile toCopy) {
		questions = new ArrayList<String>(toCopy.questions);
		correctAns = new ArrayList<String>(toCopy.correctAns);
		answersGuide = new ArrayList<ArrayList<String>>(toCopy.answersGuide);
	}

	/**
	 * This method returns the answers for a given number question
	 * 
	 * @param num
	 *            represents the question number
	 * @returns the answers for the num question
	 */
	public ArrayList<String> getList(int num) {
		return answersGuide.get(num);
	}

	/**
	 * This method returns the questions list
	 * 
	 * @returns the question list
	 */
	public ArrayList<String> getQuestionList() {
		return questions;
	}

	/**
	 * This method returns a list that contains lists of answers for each question
	 * 
	 * @return the answers lists list
	 */
	public ArrayList<ArrayList<String>> getAnsList() {
		return answersGuide;
	}

	/**
	 * This method removes a given number question and its answers from the
	 * appropriate lists
	 * 
	 * @param num
	 *            represents the question number
	 * @return the answers lists list after the removal
	 */
	public ArrayList<ArrayList<String>> removeFromList(int num) {
		questions.remove(num);
		answersGuide.remove(num);

		return answersGuide;
	}

	/**
	 * This method removes a given number question's correct answer from the correct
	 * answers list
	 * 
	 * @param num
	 */
	public void removeAns(int num) {
		correctAns.remove(num);
	}

}
