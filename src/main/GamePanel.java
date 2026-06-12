package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 680;
	final int FPS = 45;
	
    // PIECE COLOR
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public int currentColor = WHITE;
	
	Thread gamThread;
    Board board = new Board();
    
	 public GamePanel() {
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
		    setBackground(new Color(50,50,50));

//			addMouseMotionListener(mouse);
//			addMouseListener(mouse);
//
//	        setPieces();
//		    copyPieces(pieces, simPieces);
		   
		}
	
	 public void paintComponent(Graphics g){
		    super.paintComponent(g);
		    Graphics2D g2 = (Graphics2D)g;
		    
		  //BOARD
	        board.draw(g2);
	 }
	 
	@Override
	public void run() {
	    //Game Loop
	     double drawInterval = 1000000000 / FPS ;
	     double delta = 0 ;
	     long lastTime = System.nanoTime();
	     long currentTime;

	     while (gamThread != null) {
	         currentTime = System.nanoTime();
	         delta += (currentTime - lastTime) / drawInterval;
			 lastTime = currentTime;
	         
	         if (delta >= 1) {
	             update();

	             repaint();

	             delta -- ;
	            }
	        }
	}
	
	private void update() {
		// TODO Auto-generated method stub
		
	}

	public void launchGame() {
		
		gamThread = new Thread(this);
	   gamThread.start();   	
	}

}
