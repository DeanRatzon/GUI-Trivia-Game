
/**This Program represents a GUI Trivia game 
 * @author Dean Ratzon	
 * @version 03/12/18
 * 
 */
import java.io.File;
import java.util.Scanner;

import javax.swing.JFrame;

public class Driver extends Throwable {

	public static void main(String[] args) {

		// try block starts here
		try {
			Scanner input = new Scanner(new File("trivia.txt"));

		} catch (java.io.FileNotFoundException e) {
			// TODO Auto-generated catch block
		}

		newPanel p = new newPanel();

		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p.setSize(700, 300);
		p.setLocationRelativeTo(null);
		p.setVisible(true);
	}

}
