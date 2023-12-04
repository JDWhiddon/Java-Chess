import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public abstract class ChessPiece {
	// Current x and y coords
	protected int xCoord;
	protected int yCoord;
	protected char symbol = ' ';
	protected char playerSide = ' ';
	protected boolean isAlive = true;
	protected boolean isSelected = false;
	protected int turn;
	// May mess with the JLabels in each specific JPanel later
	protected JLabel jlabelCopy;

	public ChessPiece() {
	}

	public ChessPiece(int x, int y, char ps) {
		xCoord = x;
		yCoord = y;
		playerSide = ps;
	}

	// Reads in the current xCoord and yCoord and calculates the maximum distance
	// per move
	// or the valid positions if applicable
	public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		return coordinatesList;
	}

	public int GetXCoord() {
		return xCoord;
	}

	public int GetYCoord() {
		return yCoord;
	}

	public char GetPlayerSide() {
		return playerSide;
	}

	public JLabel GetJLabel() {
		return jlabelCopy;
	}

	public abstract char GetSymbol();

	public void SetXCoord(int x) {
		xCoord = x;
	}

	public void SetYCoord(int y) {
		yCoord = y;
	}

	public void SetPlayerSide(char s) {
		playerSide = s;
	}

	public void SetJLabel(JLabel label) {
		jlabelCopy = label;
	}

	public void SelectPiece() {
		isSelected = true;
	}

	public void DeselectPiece() {
		isSelected = false;
	}

	public void RemovePiece() {
		isAlive = false;
	}

	public boolean IsAlive() {
		return isAlive;
	}

	public boolean IsSelected() {
		return isSelected;
	}

	// Pawns only
	public boolean IsMovedTwice() {
		boolean isPawn = false;
		return isPawn;
	}
	/*
	 * public void MoveTwice(boolean x) {
	 * }
	 */
}

// Derived classes temporarily here for now
class King extends ChessPiece {
	private char symbol = 'K';
	private boolean canCastle = true;
	private boolean hasMoved = false;

	public King(int x, int y, char ps) {
		super(x, y, ps);
	}

	public char GetSymbol() {
		return symbol;
	}

	public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		int leftBound = 1;
		int rightBound = 8;
		int lowerBound = 1;
		int upperBound = 8;

		if (playerSide == 'W') {
			// Going -> Top
			if (y < upperBound)
				coordinatesList.add(new int[] { x, y + 1 });
			// Going -> Bottom
			if (y > lowerBound)
				coordinatesList.add(new int[] { x, y - 1 });
			// Going -> Right
			if (x < rightBound)
				coordinatesList.add(new int[] { x + 1, y });
			// Going -> Left
			if (x > leftBound)
				coordinatesList.add(new int[] { x - 1, y });
			// Going -> Top Right
			if (y < upperBound && x < rightBound) {
				coordinatesList.add(new int[] { x + 1, y + 1 });
			}
			// Going -> Bottom Right
			if (y > lowerBound && x < rightBound) {
				coordinatesList.add(new int[] { x + 1, y - 1 });
			}
			// Going -> Top Left
			if (y < upperBound && x > leftBound) {
				coordinatesList.add(new int[] { x - 1, y + 1 });
			}
			// Going -> Bottom Left
			if (y > lowerBound && x > leftBound) {
				coordinatesList.add(new int[] { x - 1, y - 1 });
			}

		}
		// ---- Black ----
		else {
			// Going -> Top
			if (y < upperBound)
				coordinatesList.add(new int[] { x, y + 1 });
			// Going -> Bottom
			if (y > lowerBound)
				coordinatesList.add(new int[] { x, y - 1 });
			// Going -> Right
			if (x < rightBound)
				coordinatesList.add(new int[] { x + 1, y });
			// Going -> Left
			if (x > leftBound)
				coordinatesList.add(new int[] { x - 1, y });
			// Going -> Top Right
			if (y < upperBound && x < rightBound) {
				coordinatesList.add(new int[] { x + 1, y + 1 });
			}
			// Going -> Bottom Right
			if (y > lowerBound && x < rightBound) {
				coordinatesList.add(new int[] { x + 1, y - 1 });
			}
			// Going -> Top Left
			if (y < upperBound && x > leftBound) {
				coordinatesList.add(new int[] { x - 1, y + 1 });
			}
			// Going -> Bottom Left
			if (y > lowerBound && x > leftBound) {
				coordinatesList.add(new int[] { x - 1, y - 1 });
			}
		}
		return coordinatesList;
	}

	public void Move(int x, int y) {
		System.out.println("Test");
	}
}

class Queen extends ChessPiece {
	private char symbol = 'Q';
	int leftBound = 1;
	int rightBound = 8;
	int lowerBound = 1;
	int upperBound = 8;

	public Queen(int x, int y, char ps) {
		super(x, y, ps);
	}

	public void ResetBounds() {
		leftBound = 1;
		rightBound = 8;
		lowerBound = 1;
		upperBound = 8;
	}

	public char GetSymbol() {
		return symbol;
	}

	public void DetectCollision(JPanel[][] playSquares, int x, int y, ChessPiece cp) {
		int leftMax = x - 1;
		int rightMax = 8 - x;
		int lowerMax = y - 1;
		int upperMax = 8 - y;

		if (playerSide == 'W' && cp.IsAlive()) {
			// Going -> Top
			for (int i = 1; i <= upperMax; i++) {
				if (cp.GetXCoord() == x && cp.GetYCoord() == y + i) {
					upperBound = i;
					i = 8;
				}
			}
			// Going -> Bottom
			for (int i = 1; i <= lowerMax; i++) {
				if (cp.GetXCoord() == x && cp.GetYCoord() == y - i) {
					lowerBound = i;
					i = 8;
				}
			}
			// Going -> Right
			for (int i = 1; i <= rightMax; i++) {
				if (cp.GetXCoord() == x + i && cp.GetYCoord() == y) {
					rightBound = i;
					i = 8;
				}
			}
			// Going -> Left
			for (int i = 1; i <= leftMax; i++) {
				if (cp.GetXCoord() == x - i && cp.GetYCoord() == y) {
					leftBound = i;
					i = 8;
				}
			}
			// Going -> Top Right
			for (int i = 1; i <= upperMax; i++) {
				for (int j = 1; j <= rightMax; j++) {
					if (j == i) {

					}
				}
			}
			// Going -> Bottom Right
			for (int i = 1; i <= lowerMax; i++) {
				for (int j = 1; j <= rightMax; j++) {
					if (j == i) {

					}
				}
			}
			// Going -> Top Left
			for (int i = 1; i <= upperMax; i++) {
				for (int j = 1; j <= leftMax; j++) {
					if (j == i) {

					}
				}
			}
			// Going -> Bottom Left
			for (int i = 1; i <= lowerMax; i++) {
				for (int j = 1; j <= leftMax; j++) {
					if (j == i) {

					}
				}
			}
		} else {

		}
	}

	public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		int leftMax = x - leftBound;
		int rightMax = rightBound - x;
		int lowerMax = y - lowerBound;
		int upperMax = upperBound - y;

		if (playerSide == 'W') {
			// Going -> Top
			for (int i = 1; i <= upperMax; i++) {
				coordinatesList.add(new int[] { x, y + i });
			}
			// Going -> Bottom
			for (int i = 1; i <= lowerMax; i++) {
				coordinatesList.add(new int[] { x, y - i });
			}
			// Going -> Right
			for (int i = 1; i <= rightMax; i++) {
				coordinatesList.add(new int[] { x + i, y });
			}
			// Going -> Left
			for (int i = 1; i <= leftMax; i++) {
				coordinatesList.add(new int[] { x - i, y });
			}
			// Going -> Top Right
			for (int i = 1; i <= upperMax; i++) {
				for (int j = 1; j <= rightMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x + j, y + i });
					}
				}
			}
			// Going -> Bottom Right
			for (int i = 1; i <= lowerMax; i++) {
				for (int j = 1; j <= rightMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x + j, y - i });
					}
				}
			}
			// Going -> Top Left
			for (int i = 1; i <= upperMax; i++) {
				for (int j = 1; j <= leftMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x - j, y + i });
					}
				}
			}
			// Going -> Bottom Left
			for (int i = 1; i <= lowerMax; i++) {
				for (int j = 1; j <= leftMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x - j, y - i });
					}
				}
			}

		}
		// ---- Black ----
		else {
			// Going -> Top
			for (int i = 1; i <= upperMax; i++) {
				coordinatesList.add(new int[] { x, y + i });
			}
			// Going -> Bottom
			for (int i = 1; i <= lowerMax; i++) {
				coordinatesList.add(new int[] { x, y - i });
			}
			// Going -> Right
			for (int i = 1; i <= rightMax; i++) {
				coordinatesList.add(new int[] { x + i, y });
			}
			// Going -> Left
			for (int i = 1; i <= leftMax; i++) {
				coordinatesList.add(new int[] { x - i, y });
			}
			// Going -> Top Right
			for (int i = 1; i <= upperMax; i++) {
				for (int j = 1; j <= rightMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x + j, y + i });
					}
				}
			}
			// Going -> Bottom Right
			for (int i = 1; i <= lowerMax; i++) {
				for (int j = 1; j <= rightMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x + j, y - i });
					}
				}
			}
			// Going -> Top Left
			for (int i = 1; i <= upperMax; i++) {
				for (int j = 1; j <= leftMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x - j, y + i });
					}
				}
			}
			// Going -> Bottom Left
			for (int i = 1; i <= lowerMax; i++) {
				for (int j = 1; j <= leftMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x - j, y - i });
					}
				}
			}
		}

		ResetBounds();
		return coordinatesList;
	}

	public void Move(int x, int y) {
		System.out.println("Test");
	}
}

class Rook extends ChessPiece {
	private char symbol = 'R';
	private boolean isBlockedUp = false;
	private boolean isBlockedDown = false;
	private boolean isBlockedLeft = false;
	private boolean isBlockedRight = false;
	final int leftBound = 1;
	final int rightBound = 9;
	final int lowerBound = 1;
	final int upperBound = 9;
	// ---- Limits movement ---- //
	// Lower bounds set to 0 to reach the end
	private int limitedLeftBound = 0;
	private int limitedRightBound = 9;
	private int limitedLowerBound = 0;
	private int limitedUpperBound = 9;

	private int previousLeftBound = 0;
	private int previousRightBound = 9;
	private int previousLowerBound = 0;
	private int previousUpperBound = 9;

	public Rook(int x, int y, char ps) {
		super(x, y, ps);
	}

	public char GetSymbol() {
		return symbol;
	}

	public void ResetBounds() {
		limitedLeftBound = 0;
		limitedRightBound = 9;
		limitedLowerBound = 0;
		limitedUpperBound = 9;
		isBlockedUp = false;
		isBlockedDown = false;
		isBlockedLeft = false;
		isBlockedRight = false;
		previousLeftBound = 0;
		previousRightBound = 9;
		previousLowerBound = 0;
		previousUpperBound = 9;
	}

	public void DetectCollision(JPanel[][] playSquares, int x, int y, ChessPiece cp) {
		int leftMax = x - leftBound;
		int rightMax = rightBound - x;
		int lowerMax = y - lowerBound;
		int upperMax = upperBound - y;

		if (/* playerSide == 'W' && */ cp.IsAlive()) {
			// Going -> Top
			for (int i = 1; i < upperMax; i++) {
				if (cp.GetXCoord() == x && cp.GetYCoord() == y + i) {
					limitedUpperBound = y + i;
				}
			}
			// Going -> Bottom
			for (int i = 1; i < lowerMax; i++) {
				if (cp.GetXCoord() == x && cp.GetYCoord() == y - i) {
					limitedLowerBound = y - i;
				}
			}
			// Going -> Right
			for (int i = 1; i < rightMax; i++) {
				if (cp.GetXCoord() == x + i && cp.GetYCoord() == y) {
					limitedRightBound = x + i;
				}
			}
			// Going -> Left
			for (int i = 1; i < leftMax; i++) {
				if (cp.GetXCoord() == x - i && cp.GetYCoord() == y) {
					limitedLeftBound = x - i;
				}
			}
		} /*
			 * else if (playerSide == 'B' && cp.IsAlive()) {
			 * // Going -> Top
			 * for(int i = 1; i < upperMax; i++){
			 * if(cp.GetXCoord() == x && cp.GetYCoord() == y + i){
			 * limitedUpperBound = y + i;
			 * }
			 * }
			 * // Going -> Bottom
			 * for(int i = 1; i < lowerMax; i++){
			 * if(cp.GetXCoord() == x && cp.GetYCoord() == y - i){
			 * limitedLowerBound = y - i;
			 * }
			 * }
			 * // Going -> Right
			 * for(int i = 1; i < rightMax; i++){
			 * if(cp.GetXCoord() == x + i && cp.GetYCoord() == y){
			 * limitedRightBound = x + i;
			 * }
			 * }
			 * // Going -> Left
			 * for(int i = 1; i < leftMax; i++){
			 * if(cp.GetXCoord() == x - i && cp.GetYCoord() == y){
			 * limitedLeftBound = x - i;
			 * }
			 * }
			 * }
			 */

		DetectCapture(x, y, cp);
		CompareBounds(x, y);

		previousUpperBound = limitedUpperBound;
		previousLowerBound = limitedLowerBound;
		previousRightBound = limitedRightBound;
		previousLeftBound = limitedLeftBound;

	}

	// To detect the closest piece to the selected piece to combat
	// array loop limitations.
	public void CompareBounds(int x, int y) {
		if (playerSide == 'W' || playerSide == 'B') {
			if (previousUpperBound < limitedUpperBound) {
				limitedUpperBound = previousUpperBound;
			}
			if (previousLowerBound > limitedLowerBound) {
				limitedLowerBound = previousLowerBound;
			}
			if (previousRightBound < limitedRightBound) {
				limitedRightBound = previousRightBound;
			}
			if (previousLeftBound > limitedLeftBound) {
				limitedLeftBound = previousLeftBound;
			}
		} /*
			 * else if (playerSide == 'B'){
			 * if(previousUpperBound < limitedUpperBound){
			 * limitedUpperBound = previousUpperBound;
			 * }
			 * if(previousLowerBound > limitedLowerBound){
			 * limitedLowerBound = previousLowerBound;
			 * }
			 * if(previousRightBound < limitedRightBound){
			 * limitedRightBound = previousRightBound;
			 * }
			 * if(previousLeftBound > limitedLeftBound){
			 * limitedLeftBound = previousLeftBound;
			 * }
			 * }
			 */
	}

	public void DetectCapture(int x, int y, ChessPiece cp) {
		int leftMax = x - leftBound;
		int rightMax = rightBound - x;
		int lowerMax = y - lowerBound;
		int upperMax = upperBound - y;

		if (playerSide == 'W' && cp.IsAlive() && cp.GetPlayerSide() == 'B') {
			// Going -> Top
			if (cp.GetXCoord() == x && cp.GetYCoord() == limitedUpperBound) {
				limitedUpperBound = limitedUpperBound + 1;
			}
			// Going -> Bottom
			if (cp.GetXCoord() == x && cp.GetYCoord() == limitedLowerBound) {
				limitedLowerBound = limitedLowerBound - 1;
			}
			// Going -> Right
			if (cp.GetXCoord() == limitedRightBound && cp.GetYCoord() == y) {
				limitedRightBound = limitedRightBound + 1;
			}
			// Going -> Left
			if (cp.GetXCoord() == limitedLeftBound && cp.GetYCoord() == y) {
				limitedLeftBound = limitedLeftBound - 1;
			}
		} else if (playerSide == 'B' && cp.IsAlive() && cp.GetPlayerSide() == 'W') {
			// Going -> Top
			if (cp.GetXCoord() == x && cp.GetYCoord() == limitedUpperBound) {
				limitedUpperBound = limitedUpperBound + 1;
			}
			// Going -> Bottom
			if (cp.GetXCoord() == x && cp.GetYCoord() == limitedLowerBound) {
				limitedLowerBound = limitedLowerBound - 1;
			}
			// Going -> Right
			if (cp.GetXCoord() == limitedRightBound && cp.GetYCoord() == y) {
				limitedRightBound = limitedRightBound + 1;
			}
			// Going -> Left
			if (cp.GetXCoord() == limitedLeftBound && cp.GetYCoord() == y) {
				limitedLeftBound = limitedLeftBound - 1;
			}
		}
	}

	public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		// x value - 1
		int leftMax = x - limitedLeftBound;
		// 8 - x value
		int rightMax = limitedRightBound - x;
		// y value - 1
		int lowerMax = y - limitedLowerBound;
		// 8 - y value
		int upperMax = limitedUpperBound - y;

		if (playerSide == 'W' || playerSide == 'B') {
			// Going -> Top
			for (int i = 1; i < upperMax; i++) {
				coordinatesList.add(new int[] { x, y + i });
			}
			// Going -> Bottom
			for (int i = 1; i < lowerMax; i++) {
				coordinatesList.add(new int[] { x, y - i });
			}
			// Going -> Right
			for (int i = 1; i < rightMax; i++) {
				coordinatesList.add(new int[] { x + i, y });
			}
			// Going -> Left
			for (int i = 1; i < leftMax; i++) {
				coordinatesList.add(new int[] { x - i, y });
			}
		}
		// ---- Player 2 ----
		/*
		 * else{
		 * // Going -> Top
		 * for(int i = 1; i <= upperMax; i++){
		 * coordinatesList.add(new int[] { x, y + i});
		 * }
		 * // Going -> Bottom
		 * for(int i = 1; i <= lowerMax; i++){
		 * coordinatesList.add(new int[] { x, y - i});
		 * }
		 * // Going -> Right
		 * for(int i = 1; i <= rightMax; i++){
		 * coordinatesList.add(new int[] { x + i, y });
		 * }
		 * // Going -> Left
		 * for(int i = 1; i <= leftMax; i++){
		 * coordinatesList.add(new int[] { x - i, y });
		 * }
		 * }
		 */

		ResetBounds();
		return coordinatesList;
	}

	public void Move(int x, int y) {

	}
}

class Bishop extends ChessPiece {
	private char symbol = 'B';

	public Bishop(int x, int y, char ps) {
		super(x, y, ps);
	}

	public char GetSymbol() {
		return symbol;
	}

	public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		int leftBound = 1;
		int rightBound = 8;
		int lowerBound = 1;
		int upperBound = 8;
		int leftMax = x - leftBound;
		int rightMax = rightBound - x;
		int lowerMax = y - lowerBound;
		int upperMax = upperBound - y;

		if (playerSide == 'W') {
			// Going -> Top Right
			for (int i = 1; i <= upperMax; i++) {
				for (int j = 1; j <= rightMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x + j, y + i });
					}
				}
			}
			// Going -> Bottom Right
			for (int i = 1; i <= lowerMax; i++) {
				for (int j = 1; j <= rightMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x + j, y - i });
					}
				}
			}
			// Going -> Top Left
			for (int i = 1; i <= upperMax; i++) {
				for (int j = 1; j <= leftMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x - j, y + i });
					}
				}
			}
			// Going -> Bottom Left
			for (int i = 1; i <= lowerMax; i++) {
				for (int j = 1; j <= leftMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x - j, y - i });
					}
				}
			}
		}
		// ---- Black ----
		else {
			// Going -> Top Right
			for (int i = 1; i <= upperMax; i++) {
				for (int j = 1; j <= rightMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x + j, y + i });
					}
				}
			}
			// Going -> Bottom Right
			for (int i = 1; i <= lowerMax; i++) {
				for (int j = 1; j <= rightMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x + j, y - i });
					}
				}
			}
			// Going -> Top Left
			for (int i = 1; i <= upperMax; i++) {
				for (int j = 1; j <= leftMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x - j, y + i });
					}
				}
			}
			// Going -> Bottom Left
			for (int i = 1; i <= lowerMax; i++) {
				for (int j = 1; j <= leftMax; j++) {
					if (j == i) {
						coordinatesList.add(new int[] { x - j, y - i });
					}
				}
			}
		}
		return coordinatesList;
	}

	public void Move(int x, int y) {
		System.out.println("Test");
	}
}

class Knight extends ChessPiece {
	private char symbol = 'N';

	public Knight(int x, int y, char ps) {
		super(x, y, ps);
	}

	public char GetSymbol() {
		return symbol;
	}

	public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		int leftBound = 1;
		int rightBound = 8;
		int lowerBound = 1;
		int upperBound = 8;

		if (playerSide == 'W') {
			// Going -> 2 Top + Right
			if (((y + 2) <= upperBound) && ((x + 1) <= rightBound)) {
				coordinatesList.add(new int[] { x + 1, y + 2 });
			}
			// Going -> 2 Top + Left
			if (((y + 2) <= upperBound) && ((x - 1) >= leftBound)) {
				coordinatesList.add(new int[] { x - 1, y + 2 });
			}
			// Going -> 2 Bottom + Right
			if (((y - 2) >= lowerBound) && ((x + 1) <= rightBound)) {
				coordinatesList.add(new int[] { x + 1, y - 2 });
			}
			// Going -> 2 Bottom + Left
			if (((y - 2) >= lowerBound) && ((x - 1) >= leftBound)) {
				coordinatesList.add(new int[] { x - 1, y - 2 });
			}
			// Going -> 2 Right + Top
			if (((x + 2) <= rightBound) && ((y + 1) <= upperBound)) {
				coordinatesList.add(new int[] { x + 2, y + 1 });
			}
			// Going -> 2 Right + Bottom
			if (((x + 2) <= rightBound) && ((y - 1) >= lowerBound)) {
				coordinatesList.add(new int[] { x + 2, y - 1 });
			}
			// Going -> 2 Left + Top
			if (((x - 2) >= leftBound) && ((y + 1) <= upperBound)) {
				coordinatesList.add(new int[] { x - 2, y + 1 });
			}
			// Going -> 2 Left + Bottom
			if (((x - 2) >= leftBound) && ((y - 1) >= lowerBound)) {
				coordinatesList.add(new int[] { x - 2, y - 1 });
			}
		}
		// ---- Black ----
		else {
			// Going -> 2 Top + Right
			if (((y + 2) <= upperBound) && ((x + 1) <= rightBound)) {
				coordinatesList.add(new int[] { x + 1, y + 2 });
			}
			// Going -> 2 Top + Left
			if (((y + 2) <= upperBound) && ((x - 1) >= leftBound)) {
				coordinatesList.add(new int[] { x - 1, y + 2 });
			}
			// Going -> 2 Bottom + Right
			if (((y - 2) >= lowerBound) && ((x + 1) <= rightBound)) {
				coordinatesList.add(new int[] { x + 1, y - 2 });
			}
			// Going -> 2 Bottom + Left
			if (((y - 2) >= lowerBound) && ((x - 1) >= leftBound)) {
				coordinatesList.add(new int[] { x - 1, y - 2 });
			}
			// Going -> 2 Right + Top
			if (((x + 2) <= rightBound) && ((y + 1) <= upperBound)) {
				coordinatesList.add(new int[] { x + 2, y + 1 });
			}
			// Going -> 2 Right + Bottom
			if (((x + 2) <= rightBound) && ((y - 1) >= lowerBound)) {
				coordinatesList.add(new int[] { x + 2, y - 1 });
			}
			// Going -> 2 Left + Top
			if (((x - 2) >= leftBound) && ((y + 1) <= upperBound)) {
				coordinatesList.add(new int[] { x - 2, y + 1 });
			}
			// Going -> 2 Left + Bottom
			if (((x - 2) >= leftBound) && ((y - 1) >= lowerBound)) {
				coordinatesList.add(new int[] { x - 2, y - 1 });
			}
		}
		return coordinatesList;
	}

	public void Move(int x, int y) {
		System.out.println("Test");
	}
}

class Pawn extends ChessPiece {
	private char symbol = 'P';
	private boolean isBlocked = false;
	private boolean movedTwice = false;
	boolean[] choices = new boolean[5];
	int leftBound = 1;
	int rightBound = 8;
	int lowerBound = 1;
	int upperBound = 8;

	public Pawn(int x, int y, char ps) {
		super(x, y, ps);
		ResetChoices();
	}

	public char GetSymbol() {
		return symbol;
	}

	public void DetectSpecialMove(JPanel[][] playSquares, int x, int y, ChessPiece cp) {
		if (playerSide == 'W' && cp.IsAlive()) {
			// Front movement (Blocked)
			if (cp.GetXCoord() == x && cp.GetYCoord() == (y + 1)) {
				choices[0] = true;
				isBlocked = true;
			}
			// Diagonal right capture
			if (cp.GetXCoord() == (x + 1) && cp.GetYCoord() == (y + 1)) {
				choices[1] = true;
			}
			// Diagonal left capture
			if (cp.GetXCoord() == (x - 1) && cp.GetYCoord() == (y + 1)) {
				choices[2] = true;
			}
			// En Passant right
			if (cp.GetXCoord() == (x + 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice() == true) {
				choices[3] = true;
			}
			// En Passant left
			if (cp.GetXCoord() == (x - 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice() == true) {
				choices[4] = true;
			}
			// ---- Black Side ----
		} else {
			// Front movement (Blocked)
			if (cp.GetXCoord() == x && cp.GetYCoord() == (y - 1)) {
				choices[0] = true;
				isBlocked = true;
			}
			// Diagonal right capture
			if (cp.GetXCoord() == (x + 1) && cp.GetYCoord() == (y - 1)) {
				choices[1] = true;
			}
			// Diagonal left capture
			if (cp.GetXCoord() == (x - 1) && cp.GetYCoord() == (y - 1)) {
				choices[2] = true;
			}
			// En Passant right
			if (cp.GetXCoord() == (x + 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice() == true) {
				choices[3] = true;
			}
			// En Passant left
			if (cp.GetXCoord() == (x - 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice() == true) {
				choices[4] = true;
			}
		}

	}

	public ArrayList<int[]> PawnSpecialMove(JPanel[][] playSquares, int x, int y) {

		ArrayList<int[]> SelectedCoordinatesList = ValidMoves(playSquares, x, y);
		if (playerSide == 'W') {
			// Diagonal Right Capture
			if (choices[1] == true && y < upperBound && x < rightBound) {
				SelectedCoordinatesList.add(new int[] { x + 1, y + 1 });
			}
			// Diagonal Left Capture
			if (choices[2] == true && y < upperBound && x > leftBound) {
				SelectedCoordinatesList.add(new int[] { x - 1, y + 1 });
			}
			// En Passant Right
			if (choices[3] == true && y < upperBound && x < rightBound) {
				SelectedCoordinatesList.add(new int[] { x + 1, y + 1 });
			}
			// En Passant Left
			if (choices[4] == true && y < upperBound && x > leftBound) {
				SelectedCoordinatesList.add(new int[] { x - 1, y + 1 });
			}
		} else {
			// Diagonal Right Capture
			if (choices[1] == true && y > lowerBound && x < rightBound) {
				SelectedCoordinatesList.add(new int[] { x + 1, y - 1 });
			}
			// Diagonal Left Capture
			if (choices[2] == true && y > lowerBound && x > leftBound) {
				SelectedCoordinatesList.add(new int[] { x - 1, y - 1 });
			}
			// En Passant Right
			if (choices[3] == true && y > lowerBound && x < rightBound) {
				SelectedCoordinatesList.add(new int[] { x + 1, y - 1 });
			}
			// En Passant Left
			if (choices[4] == true && y > lowerBound && x > leftBound) {
				SelectedCoordinatesList.add(new int[] { x - 1, y - 1 });
			}
		}

		ResetChoices();

		return SelectedCoordinatesList;
	}

	public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
		movedTwice = false;
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		if (playerSide == 'W') {
			// At start position
			if (y < upperBound && isBlocked == false) {
				if (y == 2) {
					coordinatesList.add(new int[] { x, y + 2 });
					coordinatesList.add(new int[] { x, y + 1 });
				} else
					coordinatesList.add(new int[] { x, y + 1 });
			}
		} else {
			if (y > lowerBound && isBlocked == false) {
				if (y == 7) {
					coordinatesList.add(new int[] { x, y - 2 });
					coordinatesList.add(new int[] { x, y - 1 });
				} else
					coordinatesList.add(new int[] { x, y - 1 });
			}
		}
		return coordinatesList;
	}

	public void ResetChoices() {
		for (int i = 0; i < 5; i++) {
			choices[i] = false;
		}
		isBlocked = false;
	}

	public boolean IsMovedTwice() {
		return movedTwice;
	}

	public void MoveTwice(boolean x) {
		movedTwice = x;
	}

}

class NullPiece extends ChessPiece {
	private char symbol = 'O';

	public NullPiece(int x, int y, char ps) {
		super(x, y, ps);
	}

	public char GetSymbol() {
		return symbol;
	}

	public void Move(int x, int y) {
		System.out.println("Test");
	}
}