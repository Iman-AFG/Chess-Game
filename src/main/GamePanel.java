package main;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
	public static final int HEIGHT = 640;
	final int FPS = 45;
	
    // PIECE COLOR
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public int currentColor = WHITE;
	
	Thread gamThread;
    Board board = new Board();
    Mouse mouse = new Mouse();
    
    // PIECES
    public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> simPieces = new ArrayList<>();
	ArrayList<Piece> promoPieces = new ArrayList<>();
	Piece activeP;
	
	public static Piece castlingP;
	
	// BOOLEANS
	boolean canMove;
	boolean validSquare;
	boolean promotion;
	
	public GamePanel() {
		 
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
		    setBackground(new Color(50,50,50));

			addMouseMotionListener(mouse);
			addMouseListener(mouse);

			testIllegel();
//			testPromotion();
//			setPieces();
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

	public void testPromotion() {
		pieces.add(new Pawn(WHITE, 0, 3));
		pieces.add(new Pawn(BLACK, 5, 4));
	    pieces.add(new Queen(WHITE, 4, 4));
	}
	public void testIllegel() {

		 pieces.add(new Pawn( WHITE, 7, 6));
		 pieces.add(new King(WHITE, 3, 7));
		 pieces.add(new King(BLACK, 0, 3));
		 pieces.add(new Bishop(BLACK, 1, 4));
		 pieces.add(new Queen(BLACK, 4, 5));
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
	        
	        if( activeP != null) {
		    	if (canMove) {
		    		if( isIllegal(activeP) ) {
		    			g2.setColor(Color.GRAY);
		    			  
			    	       g2.setComposite(
				            AlphaComposite.getInstance(
			    		        AlphaComposite.SRC_OVER, 0.7f ) );

			    		    g2.fillRect(
			    		            activeP.col * Board.SQUARE_SIZE,
			    		            activeP.row * Board.SQUARE_SIZE,
			    		            Board.SQUARE_SIZE,
			    		            Board.SQUARE_SIZE  );

			    		    g2.setComposite(
			    		        AlphaComposite.getInstance(
			    		            AlphaComposite.SRC_OVER, 1f ) );
			    		    
		    		} else {
		    			g2.setColor(Color.WHITE);
		    			  
			    	       g2.setComposite(
				            AlphaComposite.getInstance(
			    		        AlphaComposite.SRC_OVER, 0.7f ) );

			    		    g2.fillRect(
			    		            activeP.col * Board.SQUARE_SIZE,
			    		            activeP.row * Board.SQUARE_SIZE,
			    		            Board.SQUARE_SIZE,
			    		            Board.SQUARE_SIZE  );

			    		    g2.setComposite(
			    		        AlphaComposite.getInstance(
			    		            AlphaComposite.SRC_OVER, 1f ) );
		    		}
	    	     

			    }

	    		    activeP.draw(g2);
	    	}
	        
	        
	      //	STATUS MESSAGES		//
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON );

			g2.setFont(new Font("BookAntiqe", Font.PLAIN,40));
			g2.setColor(Color.BLACK);
			
			if(promotion) {
				g2.drawString("Promote To :", 840, 150);
				
				for(Piece piece : promoPieces){
					g2.drawImage(piece.image, piece.getX(piece.col), piece.getY(piece.row),
							Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
				}
			}else {
				
				if (currentColor == WHITE) {	
					g2.drawString("White's Turn", 700, 550);
					
				} else{		g2.drawString("Black's Turn", 700, 250);	}
			}
	 }
	 
	private void update() {
		
		/*
		*  The UPDATE method is going to handle all the updating stuff.
		* update the information such as piece's x and y position or number the pieces that are left in the board.
		*  */ 
		
		if(promotion) {
			
			promoting();
			
		} else {
			
		// // MOUSE BOTTON PRESSED // //
			if(mouse.pressed) {
				if (activeP == null) {	
					
					// if activeP is null, check if you can pick up a piece
					for(Piece piece : simPieces) {

						// If the mouse is on an ally piece, pick it up as the active piece
						if (piece.color == currentColor 
							&& piece.col == mouse.x / Board.SQUARE_SIZE 
							&& piece.row == mouse.y / Board.SQUARE_SIZE) {		activeP = piece;	}
					 }
					
				}else {
					// if the player is holding a piece , simulate the move
					simulate();
				 		}
			}
						
		// // MOUSE BOTTON RESLEASED // //
			if(mouse.pressed == false){
				if(activeP != null){
					if(validSquare){

			 	// // MOVE CONFIRMED // //
						
				//Update piece list in case a piece has been captured 
				// and removed during the simulation
						copyPieces(simPieces, pieces);
						activeP.updatePosition();
						
									
						if(castlingP != null) {	castlingP.updatePosition();	}
							
							if(canPromote()){
								promotion = true;
										
							}else {
								changePlayer();
								activeP = null;
									}		
					}
					else { // The move is not confirmed so reset everything
						copyPieces(pieces, simPieces);
						activeP.resetPosition();
						activeP = null;
					}
				}	
			}
		}
	}
	 
	
	private void simulate() {
		
		canMove = false;
		validSquare = false;

		// Reset the pieces list in every loop
		// This is basically for restoring the removed piece during the simulation
		copyPieces(pieces, simPieces);
		
		//Reset the castling piece's position
		if(castlingP != null) {
			
			castlingP.col = castlingP.preCol;
			castlingP.x = castlingP.getX(castlingP.col);
			castlingP = null;
		}
		
		//If a piece is being held, update its position
		activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;
		activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
		activeP.col = activeP.getCol(activeP.x);
		activeP.row = activeP.getRow(activeP.y);
		
		// check if the piece is hovering over a reachable square
		if( activeP.canMove( activeP.col, activeP.row )) {

			canMove = true;
				
			// If hitting a piece, remove it from the list
			if (activeP.hittingP != null) {		simPieces.remove(activeP.hittingP.getIndex());		}
			
			checkCastling();
			
			if(isIllegal(activeP) == false) {	validSquare = true;		}
					
		}
	}
	
	
	public void changePlayer() {

		if (currentColor == WHITE) {
			currentColor = BLACK;
			
			//Reset black's two stepped status
			for(Piece piece : pieces) {
				if(piece.color == BLACK) {	piece.twoStepped = false;	}
			}
			
		} else{
			currentColor = 	WHITE;
			
			//Reset the white's two stepped status
			for(Piece piece : pieces) {
				if(piece.color == WHITE) {	piece.twoStepped = false;	}
			}
		}
	}
	
	
	private void checkCastling() {
		
		if(castlingP != null) {
			
			if(castlingP.col == 0) {	castlingP.col += 3;
			
			}else if(castlingP.col == 7) {	castlingP.col -= 2;
			
			 }
			
			castlingP.x = castlingP.getX(castlingP.col);
		}
	}
	
	
	
	private boolean canPromote() {
		
		if(activeP.type == Type.PAWN) {
			if(currentColor == WHITE && activeP.row == 0 || currentColor == BLACK && activeP.row == 7) {
				promoPieces.clear();
				promoPieces.add(new Rook (currentColor, 9, 2));
				promoPieces.add(new Horse (currentColor, 9, 3));
				promoPieces.add(new Bishop (currentColor, 9, 4));
				promoPieces.add(new Queen (currentColor, 9, 5));
				
				return true;
			}
		}
		return false;
	}
	
	private void promoting() {
		
		if(mouse.pressed) {
			for(Piece piece : promoPieces) {
				if(piece.col == mouse.x / Board.SQUARE_SIZE &&
					piece.row == mouse.y / Board.SQUARE_SIZE) {
									
					switch(piece.type) {
					case ROOK : simPieces.add(new Rook(currentColor,activeP.col, activeP.row)); break;
					case BISHOP : simPieces.add(new Bishop(currentColor,activeP.col, activeP.row)); break;
					case HORSE : simPieces.add(new Horse(currentColor,activeP.col, activeP.row)); break;
					case QUEEN : simPieces.add(new Queen(currentColor,activeP.col, activeP.row)); break;
					default: break;
					}
					
					simPieces.remove(activeP.getIndex());
					copyPieces(simPieces, pieces);
					activeP = null;
					promotion = false;
					changePlayer();
					
				}
			}
		}
	}
	

	private boolean isIllegal(Piece king) {
		if(king.type == Type.KING) {
			for(Piece piece : simPieces) {
				if(piece != king && piece.color != king.color 
						&& piece.canMove(king.col, king.row) ) {	return true;		}
			}
		}
		return false;
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
