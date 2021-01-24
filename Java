package brickBracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;


public  class GamePlay extends JPanel implements KeyListener, ActionListener{
//starts the game
private boolean play = false;
private int score = 0 ;
// sound map by 3 times any number.
private int totalBricks =  21;
// how fast the ball will move
private Timer timer;
private int delay = 8;
//starting position 
private int playerX= 312;
//Ball
private int ballposX = 120;
private int ballposY = 350;
private int ballXdir = -1;
private int ballYdir = -2;
private  MapGenerator map;


public GamePlay() {
	 map= new MapGenerator(3,7);
	addKeyListener(this);
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);
	timer = new Timer(delay, this);
	timer.start();
}

//function
public void paint(Graphics g) {
	//Background
	g.setColor(Color.blue);
	g.fillRect(1, 1, 692, 592);
	//drawing map
	map.draw((Graphics2D)g);
	
	//Borders 
	g.setColor(Color.yellow);
	g.fillRect(0, 0, 3, 5);
	g.fillRect(0, 0, 692, 3);
	g.fillRect(691, 0, 3, 592);
	
	//Scores
	g.setColor(Color.white);
	g.setFont(new Font("serif", Font.BOLD, 25));
	g.drawString(""+score, 590, 30);
	// the paddle
	g.setColor(Color.green);
	g.fillRect(playerX,550,100,8);
	
	//The Ball	
	g.setColor(Color.yellow);
	g.fillOval(ballposX,ballposY, 20, 20);
	if(totalBricks <= 0)
	{
		play = false;
		ballXdir = 0;
		ballYdir = 0;
		g.setColor(Color.RED);
		g.setFont(new Font("serif", Font.BOLD, 30));
		g.drawString("You won,SCORES:", 260, 300);
		
		g.setFont(new Font("serif", Font.BOLD, 20));
		g.drawString("Press Enter To Restart:", 230, 350);
		
	}
	
	if(ballposY > 570){
		play = false;
		ballXdir = 0;
		ballYdir = 0;
		g.setColor(Color.RED);
		g.setFont(new Font("serif", Font.BOLD, 30));
		g.drawString("GAMEOVER, SCORES:", 190, 300);
		
		g.setFont(new Font("serif", Font.BOLD, 20));
		g.drawString("Press Enter To Restart:", 230, 350);
	}
	
	
	g.dispose();
}





	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		if (play) {
			if(new Rectangle(ballposX, ballposY,20,20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				
				ballYdir = -ballYdir;	
			}
		//	loop
			for(int i = 0; i < map.map.length; i++) {
				for(int j = 0; j < map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect= rect;
						
						 A: if(ballRect.intersects(brickRect)) {
							map.setBricksValue(0, i, j);
							totalBricks --;
							score += 5;
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
						
							ballXdir = -ballXdir;
							}else {
								ballYdir = -ballYdir;
							}
						break A;
					}
					
				}
				}
					}	
			ballposX += ballXdir;
			ballposY += ballYdir;
				if(ballposX < 0) {
					ballXdir = -ballXdir;
					
				}
				if(ballposY < 0) {
					ballYdir = -ballYdir;
					
				}
				if(ballposX > 670) {
					ballXdir = -ballXdir;
					
				}
			
			
		}
		//Recall paint method  and reDraw everything again
		repaint();
			}
		
		
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e){
		//method
	if(e.getKeyCode() == KeyEvent.VK_RIGHT){
		if(playerX >=600) {
			playerX = 600;
		} else {
			
			moveRight();
		}
	}
		//method
	if(e.getKeyCode() == KeyEvent.VK_LEFT){
		if(playerX < 10){
			playerX = 10;
		}else {
			moveLeft();
		}
	}
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(!play){
					play = true;
					ballposX = 120;
					ballposY = 350;
					ballXdir = -1;
					ballYdir = -2;
					playerX = 310;
					score = 0 ;
					totalBricks = 21;
					map = new MapGenerator(3,7);
					repaint();
				}
				
			}
			
	}


	

	private void moveLeft() {
		// TODO Auto-generated method stub
		play = true;
		playerX-= 20;
		
	}

	private void moveRight() {
		// TODO Auto-generated method stub
		play = true;
		playerX+=20;
		
	}
		
}
	
	
