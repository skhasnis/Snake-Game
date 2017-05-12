import java.awt.Color;
import javax.swing.JFrame;


public class mainController {

	public static void main(String[] args) {
		JFrame frame = new JFrame(); 
		GamePanel game = new GamePanel(); 
	
		// set the background color/ visibility (created window frame) 
		frame.setBounds(10, 10, 905, 700); 
		frame.setBackground(Color.BLACK);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// add the game board to the window 
		frame.add(game); 
		frame.setVisible(true);
		
	}
}
