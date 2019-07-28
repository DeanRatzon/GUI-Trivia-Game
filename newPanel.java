
/** This class represents the Trivia game panel content and functionality
 * 
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionListener;

public class newPanel extends JFrame {

	private final GridLayout gridLayout1;
	private JPanel ansPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel midPanel = new JPanel();
	private JButton[] answers = new JButton[4];
	private JButton restart = new JButton("Restart");
	private JButton end = new JButton("End Game");
	private ReadFile file = new ReadFile();
	private pressEvent actEvent = new pressEvent();
	private timeEvent timeEv = new timeEvent();
	private Timer timer = new Timer(10000, timeEv);
	private int points = 0;
	private int questionNum;
	private JLabel question = new JLabel("Question", SwingConstants.CENTER);
	private JLabel pointsSum = new JLabel("  Points: " + points, SwingConstants.LEFT);

	/**
	 * Constructs the main panel
	 * 
	 */
	public newPanel() {
		super("Trivia");

		gridLayout1 = new GridLayout(4, 1, 5, 5);
		setLayout(gridLayout1);
		ansPanel.setLayout(new GridLayout(1, answers.length));

		// Welcome message, instructions and start a game
		JOptionPane.showMessageDialog(null,
				"Welcome to the Trivia! \n\nInstructions :\nYou get 10 seconds to complete each question \nPick an answer from the selection\nIf your answer is correct you earn 10 points\nIf your answer is wrong or not in the time frame you lose 5 points");
		int start = JOptionPane.showConfirmDialog(null, "Are you ready to start the Trivia?", "Welcome to the Trivia",
				JOptionPane.YES_NO_OPTION);
		if (start != JOptionPane.YES_OPTION)
			System.exit(0);

		// top panel (restart/end game)
		end.addActionListener(actEvent);
		restart.addActionListener(actEvent);
		topPanel.add(restart);
		topPanel.add(end);
		add(topPanel);

		// mid panel (points)
		midPanel.add(pointsSum);
		add(midPanel);

		// randomize and set first question
		questionNum = generateQuestion();
		question.setText(file.questions.get(questionNum));
		question.setFont(new Font("", Font.BOLD, 20));
		add(question);

		// randomize and set the answers order for the first question
		ArrayList<String> ans = new ArrayList<String>(file.answersGuide.get(questionNum));
		Collections.shuffle(ans);

		answers[0] = new JButton((ans.get(0)));
		answers[0].addActionListener(actEvent);
		answers[1] = new JButton(ans.get(1));
		answers[1].addActionListener(actEvent);
		answers[2] = new JButton(ans.get(2));
		answers[2].addActionListener(actEvent);
		answers[3] = new JButton(ans.get(3));
		answers[3].addActionListener(actEvent);

		for (int i = 0; i < 4; i++) {
			answers[i].setFont(new Font("", Font.BOLD, 12));
			ansPanel.add(answers[i]);
		}
		add(ansPanel, BorderLayout.SOUTH);
		// starts the timer and delete the used question from the questions list
		timer.start();
		file.removeFromList(questionNum);

	}

	/**
	 * This method generates the next question and answers on the panel, if there
	 * are no next question it ends the game
	 * 
	 */
	private void nextQuestion() {

		questionNum = generateQuestion();

		if (questionNum >= 0) {
			question.setText(file.questions.get(questionNum));

			ArrayList<String> ans = file.getList(questionNum);
			Collections.shuffle(ans);
			answers[0].setText(ans.get(0));
			answers[1].setText(ans.get(1));
			answers[2].setText(ans.get(2));
			answers[3].setText(ans.get(3));

			file.removeFromList(questionNum);
			timer.restart();
			timer.start();
		} else {

			gameEnded();
		}

	}

	/**
	 * This method tells the user that the game has ended, displays his score and
	 * asks the user if he wants to play again
	 * 
	 */
	private void gameEnded() {
		timer.stop();
		int start = JOptionPane.showConfirmDialog(null,
				"Game over! Your score is: " + points + "\nDo you want to Play again?", "GAME OVER",
				JOptionPane.YES_NO_OPTION);
		if (start == JOptionPane.YES_OPTION)
			initialize();
		else
			System.exit(0);
	}

	/**
	 * This method initializes the trivia game in order to start the game again
	 * 
	 */
	public void initialize() {
		ReadFile newFile = new ReadFile();
		file = new ReadFile(newFile);
		int start = JOptionPane.showConfirmDialog(null, "Are you ready to start the Trivia?", "Start a new game",
				JOptionPane.YES_NO_OPTION);
		if (start != JOptionPane.YES_OPTION)
			System.exit(0);
		points = 0;
		questionNum = generateQuestion();
		pointsSum.setText("  Points: " + String.valueOf(points));

		ArrayList<String> ans = file.getList(questionNum);
		Collections.shuffle(ans);
		question.setText(file.questions.get(questionNum));

		answers[0].setText(ans.get(0));
		answers[1].setText(ans.get(1));
		answers[2].setText(ans.get(2));
		answers[3].setText(ans.get(3));
		file.removeFromList(questionNum);
		timer.restart();
		timer.start();
	}

	/**
	 * This method generates a random number in order to select the question that
	 * will be used next
	 * 
	 * @return the a random number within the numbers of questions available for use
	 */
	public int generateQuestion() {
		Random r = new Random();
		int low = 0;
		int high = file.questions.size();
		if (high > 0) {

			int result = r.nextInt(high - low) + low;
			return result;
		} else {
			return -1;
		}
	}

	/**
	 * Action Listener for selecting an answer
	 * 
	 * 
	 */
	public class pressEvent implements ActionListener {
		public void actionPerformed(ActionEvent actEvent) {
			if (actEvent.getSource() == restart) {
				timer.stop();
				initialize();
			}
			if (actEvent.getSource() == end) {
				timer.stop();
				gameEnded();
			}
			for (int i = 0; i < 4; i++) {
				if (actEvent.getSource() == answers[i])
					if (answers[i].getText() == file.correctAns.get(questionNum)) {
						points = points + 10;
						pointsSum.setText("  Points: " + String.valueOf(points));
					}

					else {
						if (points > 0) {
							points = points - 5;
							pointsSum.setText("  Points: " + String.valueOf(points));
						}
					}
			}
			file.removeAns(questionNum);
			questionNum = generateQuestion();
			if (questionNum < 0)
				gameEnded();
			else
				nextQuestion();

		}
	}

	/**
	 * Action Listener for timer events
	 * 
	 *
	 */
	public class timeEvent implements ActionListener {
		public void actionPerformed(ActionEvent timeEv) {
			timer.stop();
			JOptionPane.showMessageDialog(null,
					"Time is up!, The correct answer is: " + file.correctAns.get(questionNum));

			if (points > 0) {
				points = points - 5;
				pointsSum.setText("  Points: " + String.valueOf(points));
			}
			file.removeAns(questionNum);
			questionNum = generateQuestion();
			if (questionNum < 0)
				gameEnded();
			else
				nextQuestion();

		}
	}

}
