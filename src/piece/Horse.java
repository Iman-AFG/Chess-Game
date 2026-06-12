package piece;

import main.GamePanel;

public class Horse extends Piece {

	public Horse(int color, int col, int row) {
		super(color, col, row);


		if (color == GamePanel.WHITE) {
            image = getImage("/piece/w_horse");
        } else {
            image = getImage("/piece/b_horse");
        }
	}

}
