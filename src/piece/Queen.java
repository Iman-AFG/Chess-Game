package piece;

import main.GamePanel;
import main.Type;

public class Queen extends Piece {

	public Queen(int color, int col, int row) {
		super(color, col, row);
		type = Type.QUEEN;

		if (color == GamePanel.WHITE) {
            image = getImage("/piece/w_queen");
        } else {
            image = getImage("/piece/b_queen");
        }
	}
	
	public boolean canMove(int targetCol, int targetRow){

        if (isWhithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {

            //Vertically and Horizontally
            if( targetCol == preCol || targetRow == preRow ) {
                
                if( isValidSquare(targetCol, targetRow) && 
                    pieceIsOnStraightLine(targetCol, targetRow) == false ){
                    return true;
                }
            }
            //Diagonally
            if( Math.abs(targetRow - preRow) == Math.abs(targetCol - preCol) ) {

                if( isValidSquare(targetCol, targetRow) && 
                    pieceOnDiagonalLine(targetCol, targetRow) == false ){
                    return true;
                }
            }
        }
        return false;
    }
}
