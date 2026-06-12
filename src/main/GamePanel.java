package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import piece.Bishop;
import piece.Horse;
import piece.King;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;

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
    
    // PIECES
    public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> simPieces = new ArrayList<>();
    
	 public GamePanel() {
		 
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
		    setBackground(new Color(50,50,50));

//			addMouseMotionListener(mouse);
//			addMouseListener(mouse);
//
	        setPieces();
		    copyPieces(pieces, simPieces);
		   
		}
	 
	public void setPieces(){
		 // WHITE TEAM
		 pieces.add(new Pawn(WHITE, 0, 6));
		 pieces.add(new Pawn(WHITE, 1, 6));
		 pieces.add(new Pawn(WHITE, 2, 6));
		 pieces.add(new Pawn(WHITE, 3, 6));
		 pieces.add(new Pawn(WHITE, 4, 6));
		 pieces.add(new Pawn(WHITE, 5, 6));
		 pieces.add(new Pawn(WHITE, 6, 6));
		 pieces.add(new Pawn(WHITE, 7, 6));
		 pieces.add(new Rook(WHITE, 0, 7));
		 pieces.add(new Rook(WHITE, 7, 7));
		 pieces.add(new Horse(WHITE, 1, 7));
		 pieces.add(new Horse(WHITE, 6, 7));
		 pieces.add(new Bishop(WHITE, 2, 7));
		 pieces.add(new Bishop(WHITE, 5, 7));
		 pieces.add(new Queen(WHITE, 3, 7));
		 pieces.add(new King(WHITE, 4, 7));

		 //pieces.add(new Queen(WHITE, 4, 4));
		 
		
		 
		 //BLACK TEAM
		 pieces.add(new Pawn(BLACK, 0, 1));
		 pieces.add(new Pawn(BLACK, 1, 1));
		 pieces.add(new Pawn(BLACK, 2, 1));
		 pieces.add(new Pawn(BLACK, 3, 1));
		 pieces.add(new Pawn(BLACK, 4, 1));
		 pieces.add(new Pawn(BLACK, 5, 1));
		 pieces.add(new Pawn(BLACK, 6, 1));
		 pieces.add(new Pawn(BLACK, 7, 1));
		 pieces.add(new Rook(BLACK, 0, 0));
		 pieces.add(new Rook(BLACK, 7, 0));
		 pieces.add(new Horse(BLACK, 1, 0));
		 pieces.add(new Horse(BLACK, 6, 0));
		 pieces.add(new Bishop(BLACK, 2, 0));
		 pieces.add(new Bishop(BLACK, 5, 0));
		 pieces.add(new Queen(BLACK, 3, 0));
		 pieces.add(new King(BLACK, 4, 0));
		 
	}

    public void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target){
		 target.clear();
		 
		 for(int i = 0; i < source.size(); i++ ){
			 target.add(source.get(i));
		 }
	
	}
	 
    public void paintComponent(Graphics g){
		    super.paintComponent(g);
		    Graphics2D g2 = (Graphics2D)g;
		    
		  //BOARD
	        board.draw(g2);
	        
	        for(Piece p : simPieces) {

				//if(p == null)   continue;
	            
				p.draw(g2);
			}
	 }
	 
	 private void update() {
			// TODO Auto-generated method stub
			
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
	
	public void launchGame() {
		
		gamThread = new Thread(this);
	   gamThread.start();   	
	}

}
