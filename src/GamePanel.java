import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Random;

@SuppressWarnings({ "serial", "unused" })

public class GamePanel extends JPanel implements KeyListener, ActionListener {
	// snakeBody coordinates 
	private int[] snakeX = new int[750]; 
	private int[] snakeY = new int [750]; 
	
	// direction bools 
	private boolean rDir = false; 
	private boolean lDir = false; 
	private boolean upDir = false; 
	private boolean downDir = false;
	
	// stats 
	private int initBody = 3; 
	private int moves = 0; 
	private int score = 0;  
	
	// movement 
	private Timer timer;
	private int wait = 150; 
	

	// images 
	private ImageIcon banner;
	private ImageIcon snakeBody;
	private ImageIcon food;

	
	// food on the board 
	private Random randX;
	private Random randY; 
	private int foodX; 
	private int foodY; 
	
	
	private boolean thanksMessage = false; 

	// snake moves after 
	private boolean moveAfter = true; 
	
	
	public GamePanel() {
		this.setBackground(Color.BLACK);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(wait, this); 
		timer.start(); 
	}
	
	// use object of the graphics class to draw the elements onto the board 
	public void paint(Graphics g) {
		
		randX = new Random(); 
		randY = new Random();
		
		// initializing the board with snakeBody of size 3
		// at the top left hand side of the board 
		if (moves == 0) {
			snakeX[2] = 50; 
			snakeX[1] = 75; 
			snakeX[0] = 100; 
			
			snakeY[2] = 100; 
			snakeY[1] = 100; 
			snakeY[0] = 100; 		
			
			foodX = (randX.nextInt(31)+3) * 25; 
			foodY = (randY.nextInt(21)+3) * 25; 
			
		}
		
		// banner stuff 
		g.setColor(Color.BLACK);
		g.drawRect(24, 10, 851, 55);
		banner = new ImageIcon("title.jpg");
		banner.paintIcon(this, g, 25, 11);
		
		
		// background of the game panel 
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);
		
		// messaging 
		if (moves == 0) { 
			g.setFont(new Font("helvetica" , Font.BOLD, 30));
			g.setColor(Color.white); 
			g.drawString("Welcome to the Snake Game!", 270, 300);
			g.setFont(new Font("helvetica", Font.BOLD, 20));
			g.drawString("Press arrows to start and q to quit", 320, 350);
		}
		
		
		for(int i = 0 ; i < initBody; i++) {  
			snakeBody = new ImageIcon("body.png");
			snakeBody.paintIcon(this, g, snakeX[i], snakeY[i]);
		}
		
		// if the head is at the same coords as the food 
		// then we want to increment the body of the snake 
		// and set foodEaten = true 
		if (snakeX[0] == foodX && snakeY[0] == foodY) {
			initBody++;
			foodX = (randX.nextInt(31)+3) * 25; 
			foodY = (randY.nextInt(21)+3) * 25; 
			
			// make sure the randomizer doesn't create a spot on the existing snake 
			for (int i = 0; i < initBody; i++) {
				if (foodX == snakeX[i]) { 
					foodX = (randX.nextInt(31)+3) * 25;
				} else if (foodY == snakeY[i]) {
					foodY = (randY.nextInt(21)+3) * 25;
				} 
			}
			score += 10; 
		}
			 
		for(int i = 1; i < initBody ; i++) {
			if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]){
				rDir = false;
				lDir = false; 
				upDir = false;
				downDir = false; 
				// display Game Over Message 
				g.setFont(new Font("helvetica" , Font.BOLD, 45));
				g.setColor(Color.white); 
				g.drawString("Game Over!", 300, 300);
				g.setFont(new Font("helvetica", Font.BOLD, 30));
				g.drawString("Press Space to restart", 300, 350);
			}
			
		}
		
		if (thanksMessage) {
			g.setFont(new Font("helvetica" , Font.BOLD, 45));
			g.setColor(Color.white); 
			g.drawString("Thanks for playing!", 270, 300);
			
		}
		
		
			food = new ImageIcon("food.png");
			food.paintIcon(this, g, foodX, foodY);	
		
			// score board and length of tail 
			g.setColor(Color.white);
			g.setFont(new Font("helvetica", Font.PLAIN , 15));
			g.drawString("Score: " + score, 750, 33);
			
			g.setFont(new Font("helvetica", Font.PLAIN , 15));
			g.drawString("Length: " + initBody, 750, 50);
			
			g.dispose();		
				
	}

	
	// called when timer starts 
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (rDir == true) { 
			for(int i = initBody; i > 0; i--) {
				snakeY[i] = snakeY[i-1];
			}
			for(int i = initBody; i >= 0 ; i--) {
				if (i == 0) {
					// else move the head ahead by 25 px 
					snakeX[i] = snakeX[i] + 25;
				} else {
					// move the body of the snake up by one 
					snakeX[i] = snakeX[i-1]; 
				}
				if (snakeX[i] > 850) {
					// handles snake going off-screen 
					snakeX[i] = 25; 				
				}
			}
			// to show the new graphics 
			repaint(); 
		}
		if (lDir) {
			for(int i = initBody; i > 0 ; i--) {
				snakeY[i] = snakeY[i-1]; 
			}
			for(int i = initBody; i >= 0 ; i--) {
				if (i == 0) {
					snakeX[i] = snakeX[i] - 25; 
				} else {
					snakeX[i] = snakeX[i-1];
				}
				
				if(snakeX[i] < 25) {
					snakeX[i] = 850; 
				}
				repaint(); 
			}		
		}
		if (upDir) {
			for(int i = initBody; i >= 0 ; i--) {
				if (i == 0) {
					snakeY[i] = snakeY[i] - 25; 
				} else {
					snakeY[i] = snakeY[i-1]; 
				}
				if (snakeY[i] < 75) {
					snakeY[i] = 625;  
				}
			} 
			
			for(int i = initBody; i > 0; i--) {
				snakeX[i] = snakeX[i-1]; 
			}
			repaint(); 
		}
		
		if (downDir) {
			for(int i = initBody; i >= 0 ; i--) {
				if (i == 0) {
					snakeY[i] = snakeY[i] + 25; 
				} else {
					snakeY[i] = snakeY[i-1]; 
				}
				if (snakeY[i] > 625) {
					snakeY[i] = 75;  
				}
			} 
			for(int i = initBody; i > 0; i--) {
				snakeX[i] = snakeX[i-1]; 
			}
			repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			rDir = false; 
			lDir = false; 
			upDir = false;
			downDir = false; 
			thanksMessage = true;
			moveAfter = false; 
			repaint(); 
		
		}
		// space to restart so reset variables and start new 
		if (e.getKeyCode() == KeyEvent.VK_SPACE) { 	
			for(int i = 3 ; i < initBody ; i++) {
				snakeX[i] = 0 ; 
				snakeY[i] = 0 ;
			}
			
			moves = 0; 
			initBody = 3; 
			score = 0; 
			repaint(); 
		}	
		moves++; // change the number of moves so it doesn't initialize all the time 
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && moveAfter) {
			// add a check to ensure the snake doesn't collide with itself 
			if (lDir == false) { 
				rDir = true; 
			} else {
				lDir = true;
				rDir = false; 
			}
			upDir = false;
			downDir = false;
			 
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && moveAfter) {
			// add a check to ensure the snake doesn't collide with itself 
			if (rDir == false ) { 
				lDir = true; 
			} else {
				lDir = false;
				rDir = true; 
			}
			upDir = false;
			downDir = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && moveAfter) {
			// add a check to ensure the snake doesn't collide with itself 
			if (downDir == false ) { 
				upDir = true; 
			} else {
				upDir = false;
				downDir = true; 
			}
			rDir = false;
			lDir = false; 
		}
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN && moveAfter) {
			// add a check to ensure the snake doesn't collide with itself 
			if (upDir == false ) { 
				downDir = true; 
			} else {
				downDir = false;
				upDir = true; 
			}
			rDir = false;
			lDir = false;
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {	
	}
		
}
