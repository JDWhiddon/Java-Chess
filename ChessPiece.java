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

	// Upper Right
	private int[] URTargetCoords = new int[2];
	// Lower Right
	private int[] LRTargetCoords = new int[2];
	// Upper Left
	private int[] ULTargetCoords = new int[2];
	// Lower Left
	private int[] LLTargetCoords = new int[2];

	private int previousLeftBound = 0;
	private int previousRightBound = 9;
	private int previousLowerBound = 0;
	private int previousUpperBound = 9;
	// Upper Right
	private int[] previousURTargetCoords = new int[2];
	// Lower Right
	private int[] previousLRTargetCoords = new int[2];
	// Upper Left
	private int[] previousULTargetCoords = new int[2];
	// Lower Left
	private int[] previousLLTargetCoords = new int[2];

	public Queen(int x, int y, char ps) {
		super(x, y, ps);
	}

	public char GetSymbol() {
		return symbol;
	}

	// To reset the bounds fo the next valid coordinate search
	public void ResetBounds() {
	// -- N, S, E, W -- //
	limitedUpperBound = 9;
	limitedLowerBound = 0;
	limitedRightBound = 9;
	limitedLeftBound = 0;
	
	previousUpperBound = 9;
	previousLowerBound = 0;
	previousRightBound = 9;
	previousLeftBound = 0;
	
	// -- NE, SE, NW, SW -- //
	// [0] = upper/lower
	// [1] = right/left
	// Upper Right
	URTargetCoords[0] = 9;
	URTargetCoords[1] = 9;
	// Lower Right
	LRTargetCoords[0] = 0;
	LRTargetCoords[1] = 9;
	// Upper Left
	ULTargetCoords[0] = 9;
	ULTargetCoords[1] = 0;
	// Lower Left
	LLTargetCoords[0] = 0;
	LLTargetCoords[1] = 0;
	
	// Upper Right
	previousURTargetCoords[0] = 9;
	previousURTargetCoords[1] = 9;
	// Lower Right
	previousLRTargetCoords[0] = 0;
	previousLRTargetCoords[1] = 9;
	// Upper Left
	previousULTargetCoords[0] = 9;
	previousULTargetCoords[1] = 0;
	// Lower Left
	previousLLTargetCoords[0] = 0;
	previousLLTargetCoords[1] = 0;
	}

	// Cancel piece movement if another piece is in the line of sight
	public void DetectCollision(JPanel[][] playSquares, int x, int y, ChessPiece cp) {
	  int leftMax = x - leftBound;
	  int rightMax = rightBound - x;
	  int lowerMax = y - lowerBound;
	  int upperMax = upperBound - y;

	  if (cp.IsAlive()) {
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
			// Going -> Top Right
			for(int i = 1; i <= upperMax; i++){
				for(int j = 1; j <= rightMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x + j && cp.GetYCoord() == y + i){
							URTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
			// Going -> Bottom Right
			for(int i = 1; i <= lowerMax; i++){
				for(int j = 1; j <= rightMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x + j && cp.GetYCoord() == y - i){
							LRTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
			// Going -> Top Left
			for(int i = 1; i <= upperMax; i++){
				for(int j = 1; j <= leftMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x - j && cp.GetYCoord() == y + i){
							ULTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
			// Going -> Bottom Left
			for(int i = 1; i <= lowerMax; i++){
				for(int j = 1; j <= leftMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x - j && cp.GetYCoord() == y - i){
							LLTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
		}
		
		// Detect if there is a capture
		DetectCapture(x, y, cp);
		// Find the closest obstructing piece
		CompareBounds(x, y);

		// Set bounds for the last search for comparison with the
		// new search
		previousUpperBound = limitedUpperBound;
		previousLowerBound = limitedLowerBound;
		previousRightBound = limitedRightBound;
		previousLeftBound = limitedLeftBound;
		
		for(int i = 0; i < 2; i++){
		  previousURTargetCoords[i] = URTargetCoords[i];
		  previousLRTargetCoords[i] = LRTargetCoords[i];
		  previousULTargetCoords[i] = ULTargetCoords[i];
		  previousLLTargetCoords[i] = LLTargetCoords[i];
		}
	}
	
	// Helper function to set coords
	public int[] SetTargetCoords(int x, int y, int newx, int newy){
	int[] offsetArr = new int[2];
	  offsetArr[1] = newx;
	  offsetArr[0] = newy;

	  return offsetArr;
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
			
			if(previousURTargetCoords[1] < URTargetCoords[1] && previousURTargetCoords[1] != 0){
				URTargetCoords[1] = previousURTargetCoords[1];
			} 
			if(previousURTargetCoords[0] < URTargetCoords[0] && previousURTargetCoords[0] != 0){
				URTargetCoords[0] = previousURTargetCoords[0];
			}
			if(previousLRTargetCoords[1] < LRTargetCoords[1] && previousLRTargetCoords[0] != 0){
				LRTargetCoords[1] = previousLRTargetCoords[1];
			}
			if(previousLRTargetCoords[0] > LRTargetCoords[0] && previousLRTargetCoords[0] != 0){
				LRTargetCoords[0] = previousLRTargetCoords[0];
			}
			if(previousULTargetCoords[1] > ULTargetCoords[1] && previousULTargetCoords[0] != 0){
				ULTargetCoords[1] = previousULTargetCoords[1];
			}
			if(previousULTargetCoords[0] < ULTargetCoords[0] && previousULTargetCoords[0] != 0){
				ULTargetCoords[0] = previousULTargetCoords[0];
			}
			if(previousLLTargetCoords[1] > LLTargetCoords[1] && previousLLTargetCoords[0] != 0){
				LLTargetCoords[1] = previousLLTargetCoords[1];
			}
			if(previousLLTargetCoords[0] > LLTargetCoords[0] && previousLLTargetCoords[0] != 0){
				LLTargetCoords[0] = previousLLTargetCoords[0];
			}
		} 
	}

	// Detects if there is a capture in an adjacent space
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
			// Going -> Top Right
			if(cp.GetXCoord() == URTargetCoords[1] && cp.GetYCoord() == URTargetCoords[0]){
			URTargetCoords[0] = URTargetCoords[0] + 1;
			URTargetCoords[1] = URTargetCoords[1] + 1;
			}
			// Going -> Bottom Right
			if(cp.GetXCoord() == LRTargetCoords[1] && cp.GetYCoord() == LRTargetCoords[0]){
			LRTargetCoords[0] = LRTargetCoords[0] - 1;
			LRTargetCoords[1] = LRTargetCoords[1] + 1;
			}
			// Going -> Top Left
			if(cp.GetXCoord() == ULTargetCoords[1] && cp.GetYCoord() == ULTargetCoords[0]){
			ULTargetCoords[0] = ULTargetCoords[0] + 1;
			ULTargetCoords[1] = ULTargetCoords[1] - 1;
			}
			// Going -> Bottom Left
			if(cp.GetXCoord() == LLTargetCoords[1] && cp.GetYCoord() == LLTargetCoords[0]){
			LLTargetCoords[0] = LLTargetCoords[0] - 1;
			LLTargetCoords[1] = LLTargetCoords[1] - 1;
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
			// Going -> Top Right
			if(cp.GetXCoord() == URTargetCoords[1] && cp.GetYCoord() == URTargetCoords[0]){
				URTargetCoords[0] = URTargetCoords[0] + 1;
				URTargetCoords[1] = URTargetCoords[1] + 1;
			}
			// Going -> Bottom Right
			if(cp.GetXCoord() == LRTargetCoords[1] && cp.GetYCoord() == LRTargetCoords[0]){
				LRTargetCoords[0] = LRTargetCoords[0] - 1;
				LRTargetCoords[1] = LRTargetCoords[1] + 1;
			}
			// Going -> Top Left
			if(cp.GetXCoord() == ULTargetCoords[1] && cp.GetYCoord() == ULTargetCoords[0]){
				ULTargetCoords[0] = ULTargetCoords[0] + 1;
				ULTargetCoords[1] = ULTargetCoords[1] - 1;
			}
			// Going -> Bottom Left
			if(cp.GetXCoord() == LLTargetCoords[1] && cp.GetYCoord() == LLTargetCoords[0]){
				LLTargetCoords[0] = LLTargetCoords[0] - 1;
				LLTargetCoords[1] = LLTargetCoords[1] - 1;
			} 
		}
	}

	// Search the each coordinate for valid moves
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
			// -- Going -> Top -- //
			for (int i = 1; i < upperMax; i++) {
				coordinatesList.add(new int[] { x, y + i });
			}
			// -- Going -> Bottom -- //
			for (int i = 1; i < lowerMax; i++) {
				coordinatesList.add(new int[] { x, y - i });
			}
			// -- Going -> Right -- //
			for (int i = 1; i < rightMax; i++) {
				coordinatesList.add(new int[] { x + i, y });
			}
			// -- Going -> Left -- //
			for (int i = 1; i < leftMax; i++) {
				coordinatesList.add(new int[] { x - i, y });
			}
			// -- Going -> Top Right -- //
			if(URTargetCoords[0] != 0){
				upperMax = URTargetCoords[0] - y;
			} else {
				upperMax = upperBound - y;
			}
			if(URTargetCoords[1] != 0){
				rightMax = URTargetCoords[1] - x;
			} else {
				rightMax = rightBound - x;
			}	  
			for(int i = 1; i < upperMax; i++){
				for(int j = 1; j < rightMax; j++){
					if(j == i){
					coordinatesList.add(new int[] { x + j, y + i });
					}
				}
			}
			// -- Going -> Bottom Right -- //
			if(LRTargetCoords[0] != 0){
				lowerMax = y - LRTargetCoords[0];
			} else {
				lowerMax = y - lowerBound;
			}
			if(LRTargetCoords[1] != 0){
				rightMax = LRTargetCoords[1] - x;
			} else {
				rightMax = rightBound - x;
			}	  
		
			for(int i = 1; i < lowerMax; i++){
				for(int j = 1; j < rightMax; j++){
					if(j == i){
					coordinatesList.add(new int[] { x + j, y - i });
					}
				}
			}
			// -- Going -> Top Left -- //
			if(ULTargetCoords[0] != 0){
				upperMax = ULTargetCoords[0] - y;
			} else {
				upperMax = upperBound - y;
			}
			if(ULTargetCoords[1] != 0){
				leftMax = x - ULTargetCoords[1];
			} else {
				leftMax = x - leftBound;
			}	
			for(int i = 1; i < upperMax; i++){
				for(int j = 1; j < leftMax; j++){
					if(j == i){
					coordinatesList.add(new int[] { x - j, y + i });
					}
				}
			}
			// -- Going -> Bottom Left -- //
			if(LLTargetCoords[0] != 0){
				lowerMax = y - LLTargetCoords[0];
			} else {
				lowerMax = y - lowerBound;
			}
			if(ULTargetCoords[1] != 0){
				leftMax = x - LLTargetCoords[1];
			} else {
				leftMax = x - leftBound;
			}	
			for(int i = 1; i < lowerMax; i++){
				for(int j = 1; j < leftMax; j++){
					if(j == i){
					coordinatesList.add(new int[] { x - j, y - i });
					}
				}
			}  
		}

		ResetBounds();
		return coordinatesList;
	}

}

class Rook extends ChessPiece {
	private char symbol = 'R';
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

	// To reset the bounds fo the next valid coordinate search
	public void ResetBounds() {
		limitedLeftBound = 0;
		limitedRightBound = 9;
		limitedLowerBound = 0;
		limitedUpperBound = 9;
		previousLeftBound = 0;
		previousRightBound = 9;
		previousLowerBound = 0;
		previousUpperBound = 9;
	}

	// Cancel piece movement if another piece is in the line of sight
	public void DetectCollision(JPanel[][] playSquares, int x, int y, ChessPiece cp) {
		int leftMax = x - leftBound;
		int rightMax = rightBound - x;
		int lowerMax = y - lowerBound;
		int upperMax = upperBound - y;

		if (cp.IsAlive()) {
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
		} 

		// Detect if there is a capture on an adjacent space
		DetectCapture(x, y, cp);
		// Find the closest obstructing piece
		CompareBounds(x, y);

		// Set bounds for the last search for comparison with the
		// new search
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
		} 
	}

	// Detects if there is a capture on an adjacent space
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

	// Search the each coordinate for valid moves
	public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		int leftMax = x - limitedLeftBound;
		int rightMax = limitedRightBound - x;
		int lowerMax = y - limitedLowerBound;
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

		ResetBounds();
		return coordinatesList;
	}

}

class Bishop extends ChessPiece {
	private char symbol = 'B';
  final int leftBound = 0;
  final int rightBound = 9;
  final int lowerBound = 0;
  final int upperBound = 9;
  // Upper Right
  private int[] URTargetCoords = new int[2];
  // Lower Right
  private int[] LRTargetCoords = new int[2];
  // Upper Left
  private int[] ULTargetCoords = new int[2];
  // Lower Left
  private int[] LLTargetCoords = new int[2];

  // Upper Right
  private int[] previousURTargetCoords = new int[2];
  // Lower Right
  private int[] previousLRTargetCoords = new int[2];
  // Upper Left
  private int[] previousULTargetCoords = new int[2];
  // Lower Left
  private int[] previousLLTargetCoords = new int[2];

  public Bishop(int x, int y, char ps) {
    super(x, y, ps);
		ResetBounds();
  }

  public char GetSymbol() {
    return symbol;
  }

  public void ResetBounds(){
	// [0] = upper/lower
	// [1] = right/left
	// Upper Right
	URTargetCoords[0] = 9;
	URTargetCoords[1] = 9;
	// Lower Right
	LRTargetCoords[0] = 0;
	LRTargetCoords[1] = 9;
	// Upper Left
	ULTargetCoords[0] = 9;
	ULTargetCoords[1] = 0;
	// Lower Left
	LLTargetCoords[0] = 0;
	LLTargetCoords[1] = 0;

	// Upper Right
	previousURTargetCoords[0] = 9;
	previousURTargetCoords[1] = 9;
	// Lower Right
	previousLRTargetCoords[0] = 0;
	previousLRTargetCoords[1] = 9;
	// Upper Left
	previousULTargetCoords[0] = 9;
	previousULTargetCoords[1] = 0;
	// Lower Left
	previousLLTargetCoords[0] = 0;
	previousLLTargetCoords[1] = 0;
  }

  // Cancel piece movement if another piece is in the line of sight
  public void DetectCollision(JPanel[][] playSquares, int x, int y, ChessPiece cp){
    int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
		if (cp.IsAlive()) {
			// Going -> Top Right
			for(int i = 1; i <= upperMax; i++){
				for(int j = 1; j <= rightMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x + j && cp.GetYCoord() == y + i){
							URTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
			// Going -> Bottom Right
			for(int i = 1; i <= lowerMax; i++){
				for(int j = 1; j <= rightMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x + j && cp.GetYCoord() == y - i){
							LRTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
			// Going -> Top Left
			for(int i = 1; i <= upperMax; i++){
				for(int j = 1; j <= leftMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x - j && cp.GetYCoord() == y + i){
							ULTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
			// Going -> Bottom Left
			for(int i = 1; i <= lowerMax; i++){
				for(int j = 1; j <= leftMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x - j && cp.GetYCoord() == y - i){
							LLTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
		}
		
		// Detect if there is a capture on an adjacent space
		DetectCapture(x, y, cp);
		// Find the closest obstructing piece
		CompareBounds(x, y);

		// Set bounds for the last search for comparison with the
		// new search
		for(int i = 0; i < 2; i++){
			previousURTargetCoords[i] = URTargetCoords[i];
			previousLRTargetCoords[i] = LRTargetCoords[i];
			previousULTargetCoords[i] = ULTargetCoords[i];
			previousLLTargetCoords[i] = LLTargetCoords[i];
		}
  }

	// Helper function to set coords
  public int[] SetTargetCoords(int x, int y, int newx, int newy){
	int[] offsetArr = new int[2];
	  offsetArr[1] = newx;
	  offsetArr[0] = newy;

	return offsetArr;
  }

	// To detect the closest piece to the selected piece to combat
	// array loop limitations.
  public void CompareBounds(int x, int y){
	if(playerSide == 'W' || playerSide == 'B'){

	  if(previousURTargetCoords[1] < URTargetCoords[1] && previousURTargetCoords[1] != 0){
		URTargetCoords[1] = previousURTargetCoords[1];
	  } 
	  if(previousURTargetCoords[0] < URTargetCoords[0] && previousURTargetCoords[0] != 0){
		URTargetCoords[0] = previousURTargetCoords[0];
	  }
	  if(previousLRTargetCoords[1] < LRTargetCoords[1] && previousLRTargetCoords[0] != 0){
		LRTargetCoords[1] = previousLRTargetCoords[1];
	  }
	  if(previousLRTargetCoords[0] > LRTargetCoords[0] && previousLRTargetCoords[0] != 0){
		LRTargetCoords[0] = previousLRTargetCoords[0];
	  }
	  if(previousULTargetCoords[1] > ULTargetCoords[1] && previousULTargetCoords[0] != 0){
		ULTargetCoords[1] = previousULTargetCoords[1];
	  }
	  if(previousULTargetCoords[0] < ULTargetCoords[0] && previousULTargetCoords[0] != 0){
		ULTargetCoords[0] = previousULTargetCoords[0];
	  }
	  if(previousLLTargetCoords[1] > LLTargetCoords[1] && previousLLTargetCoords[0] != 0){
		LLTargetCoords[1] = previousLLTargetCoords[1];
	  }
	  if(previousLLTargetCoords[0] > LLTargetCoords[0] && previousLLTargetCoords[0] != 0){
		LLTargetCoords[0] = previousLLTargetCoords[0];
	  }
	}
  }
  
	// Detects if there is a capture in an adjacent space
  public void DetectCapture(int x, int y, ChessPiece cp){
	int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
	
	if(playerSide == 'W' && cp.IsAlive() && cp.GetPlayerSide() == 'B'){
	   // Going -> Top Right
	  if(cp.GetXCoord() == URTargetCoords[1] && cp.GetYCoord() == URTargetCoords[0]){
			URTargetCoords[0] = URTargetCoords[0] + 1;
			URTargetCoords[1] = URTargetCoords[1] + 1;
	  }
	  // Going -> Bottom Right
	  if(cp.GetXCoord() == LRTargetCoords[1] && cp.GetYCoord() == LRTargetCoords[0]){
	    LRTargetCoords[0] = LRTargetCoords[0] - 1;
			LRTargetCoords[1] = LRTargetCoords[1] + 1;
	  }
	  // Going -> Top Left
    if(cp.GetXCoord() == ULTargetCoords[1] && cp.GetYCoord() == ULTargetCoords[0]){
			ULTargetCoords[0] = ULTargetCoords[0] + 1;
			ULTargetCoords[1] = ULTargetCoords[1] - 1;
	  }
	  // Going -> Bottom Left
	  if(cp.GetXCoord() == LLTargetCoords[1] && cp.GetYCoord() == LLTargetCoords[0]){
	    LLTargetCoords[0] = LLTargetCoords[0] - 1;
			LLTargetCoords[1] = LLTargetCoords[1] - 1;
	  } 
	} else if(playerSide == 'B' && cp.IsAlive() && cp.GetPlayerSide() == 'W'){
			if(cp.GetXCoord() == URTargetCoords[1] && cp.GetYCoord() == URTargetCoords[0]){
				URTargetCoords[0] = URTargetCoords[0] + 1;
				URTargetCoords[1] = URTargetCoords[1] + 1;
			}
			// Going -> Bottom Right
			if(cp.GetXCoord() == LRTargetCoords[1] && cp.GetYCoord() == LRTargetCoords[0]){
				LRTargetCoords[0] = LRTargetCoords[0] - 1;
				LRTargetCoords[1] = LRTargetCoords[1] + 1;
			}
			// Going -> Top Left
			if(cp.GetXCoord() == ULTargetCoords[1] && cp.GetYCoord() == ULTargetCoords[0]){
				ULTargetCoords[0] = ULTargetCoords[0] + 1;
				ULTargetCoords[1] = ULTargetCoords[1] - 1;
			}
			// Going -> Bottom Left
			if(cp.GetXCoord() == LLTargetCoords[1] && cp.GetYCoord() == LLTargetCoords[0]){
				LLTargetCoords[0] = LLTargetCoords[0] - 1;
				LLTargetCoords[1] = LLTargetCoords[1] - 1;
			} 
		}
  }

	// Search the each coordinate for valid moves
  public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
    ArrayList<int[]> coordinatesList = new ArrayList<>();

    int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
	
		if (playerSide == 'W' || playerSide == 'B') {
			// Going -> Top Right
			if(URTargetCoords[0] != 0){
				upperMax = URTargetCoords[0] - y;
			} else {
				upperMax = upperBound - y;
			}
			if(URTargetCoords[1] != 0){
				rightMax = URTargetCoords[1] - x;
			} else {
				rightMax = rightBound - x;
			}	  
			for(int i = 1; i < upperMax; i++){
				for(int j = 1; j < rightMax; j++){
					if(j == i){
						coordinatesList.add(new int[] { x + j, y + i });
					}
				}
			}
			
			// Going -> Bottom Right
			if(LRTargetCoords[0] != 0){
				lowerMax = y - LRTargetCoords[0];
			} else {
				lowerMax = y - lowerBound;
			}
			if(LRTargetCoords[1] != 0){
				rightMax = LRTargetCoords[1] - x;
			} else {
				rightMax = rightBound - x;
			}	  
			for(int i = 1; i < lowerMax; i++){
				for(int j = 1; j < rightMax; j++){
					if(j == i){
						coordinatesList.add(new int[] { x + j, y - i });
					}
				}
			}
			
			// Going -> Top Left
			if(ULTargetCoords[0] != 0){
				upperMax = ULTargetCoords[0] - y;
			} else {
				upperMax = upperBound - y;
			}
			if(ULTargetCoords[1] != 0){
				leftMax = x - ULTargetCoords[1];
			} else {
				leftMax = x - leftBound;
			}	
			for(int i = 1; i < upperMax; i++){
				for(int j = 1; j < leftMax; j++){
					if(j == i){
						coordinatesList.add(new int[] { x - j, y + i });
					}
				}
			}
			
			// Going -> Bottom Left
			if(LLTargetCoords[0] != 0){
				lowerMax = y - LLTargetCoords[0];
			} else {
				lowerMax = y - lowerBound;
			}
			if(ULTargetCoords[1] != 0){
				leftMax = x - LLTargetCoords[1];
			} else {
				leftMax = x - leftBound;
			}	
			for(int i = 1; i < lowerMax; i++){
				for(int j = 1; j < leftMax; j++){
					if(j == i){
						coordinatesList.add(new int[] { x - j, y - i });
					}
				}
			}  
		}
		ResetBounds();
		return coordinatesList;
	}	
}

class Knight extends ChessPiece {
	private char symbol = 'N';
	final int leftBound = 1;
	final int rightBound = 8;
	final int lowerBound = 1;
	final int upperBound = 8;
	boolean[] directionCancel = new boolean[8];

	public Knight(int x, int y, char ps) {
		super(x, y, ps);
		initializeDirectionCancel();
	}
	
	public char GetSymbol() {
		return symbol;
	}
	
	public void initializeDirectionCancel(){
	  for(int i = 0; i < 8; i++){
	    directionCancel[i] = false;
	  }
	}

    // Detects if a piece of the same side is on the same target coordinates
    public void DetectCollision(JPanel[][] playSquares, int x, int y, ChessPiece cp){
	  if (playerSide == 'W' && cp.IsAlive() && cp.GetPlayerSide() == 'W') {
	    if(((y + 2) <= upperBound) && ((x + 1) <= rightBound)){
		  if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y + 2)
            directionCancel[0] = true;
	    }
		// Going -> 2 Top + Left
		if(((y + 2) <= upperBound) && ((x - 1) >= leftBound)){
		  if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y + 2)
            directionCancel[1] = true;
	    }
	    // Going -> 2 Bottom + Right
	    if(((y - 2) >= lowerBound) && ((x + 1) <= rightBound)){
		  if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y - 2)
            directionCancel[2] = true;
	    }
	    // Going -> 2 Bottom + Left
	    if(((y - 2) >= lowerBound) && ((x - 1) >= leftBound)){
		  if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y - 2)
            directionCancel[3] = true;
	    }
	    // Going -> 2 Right + Top
	    if(((x + 2) <= rightBound) && ((y + 1) <= upperBound)){
		  if(cp.GetXCoord() == x + 2 && cp.GetYCoord() == y + 1)
            directionCancel[4] = true;
	    }
	    // Going -> 2 Right + Bottom
	    if(((x + 2) <= rightBound) && ((y - 1) >= lowerBound)){
		  if(cp.GetXCoord() == x + 2 && cp.GetYCoord() == y - 1)
            directionCancel[5] = true;
	    }
	    // Going -> 2 Left + Top
	    if(((x - 2) >= leftBound) && ((y + 1) <= upperBound)){
		  if(cp.GetXCoord() == x - 2 && cp.GetYCoord() == y + 1)
            directionCancel[6] = true;
	    }
	    // Going -> 2 Left + Bottom
	    if(((x - 2) >= leftBound) && ((y - 1) >= lowerBound)){
		  if(cp.GetXCoord() == x - 2 && cp.GetYCoord() == y - 1)
            directionCancel[7] = true;
	    }
	  // ---- Black Side ---- // 
	  } else if (playerSide == 'B' && cp.IsAlive() && cp.GetPlayerSide() == 'B') {
	    if(((y + 2) <= upperBound) && ((x + 1) <= rightBound)){
		  if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y + 2)
            directionCancel[0] = true;
	    }
	    // Going -> 2 Top + Left
	    if(((y + 2) <= upperBound) && ((x - 1) >= leftBound)){
		  if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y + 2)
            directionCancel[1] = true;
	    }
	    // Going -> 2 Bottom + Right
	    if(((y - 2) >= lowerBound) && ((x + 1) <= rightBound)){
		  if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y - 2)
            directionCancel[2] = true;
	    }
		// Going -> 2 Bottom + Left
		if(((y - 2) >= lowerBound) && ((x - 1) >= leftBound)){
		  if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y - 2)
            directionCancel[3] = true;
		}
		// Going -> 2 Right + Top
		if(((x + 2) <= rightBound) && ((y + 1) <= upperBound)){
		  if(cp.GetXCoord() == x + 2 && cp.GetYCoord() == y + 1)
            directionCancel[4] = true;
		}
		// Going -> 2 Right + Bottom
		if(((x + 2) <= rightBound) && ((y - 1) >= lowerBound)){
		  if(cp.GetXCoord() == x + 2 && cp.GetYCoord() == y - 1)
            directionCancel[5] = true;
		}
		// Going -> 2 Left + Top
		if(((x - 2) >= leftBound) && ((y + 1) <= upperBound)){
		  if(cp.GetXCoord() == x - 2 && cp.GetYCoord() == y + 1)
            directionCancel[6] = true;
		}
		// Going -> 2 Left + Bottom
		if(((x - 2) >= leftBound) && ((y - 1) >= lowerBound)){
		  if(cp.GetXCoord() == x - 2 && cp.GetYCoord() == y - 1)
            directionCancel[7] = true;
		}	
	  }
	}


	public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
    ArrayList<int[]> coordinatesList = new ArrayList<>();

    if (playerSide == 'W' || playerSide == 'B') {
	  // Going -> 2 Top + Right
	  if(((y + 2) <= upperBound) && ((x + 1) <= rightBound)){
		if(directionCancel[0] == false)
        coordinatesList.add(new int[] { x + 1, y + 2});
	  }
	  // Going -> 2 Top + Left
	  if(((y + 2) <= upperBound) && ((x - 1) >= leftBound)){
		if(directionCancel[1] == false)
        coordinatesList.add(new int[] { x - 1, y + 2});
	  }
	  // Going -> 2 Bottom + Right
	  if(((y - 2) >= lowerBound) && ((x + 1) <= rightBound)){
		if(directionCancel[2] == false)
        coordinatesList.add(new int[] { x + 1, y - 2});
	  }
	  // Going -> 2 Bottom + Left
	  if(((y - 2) >= lowerBound) && ((x - 1) >= leftBound)){
		if(directionCancel[3] == false)
        coordinatesList.add(new int[] { x - 1, y - 2});
	  }
	  // Going -> 2 Right + Top
	  if(((x + 2) <= rightBound) && ((y + 1) <= upperBound)){
		if(directionCancel[4] == false)
        coordinatesList.add(new int[] { x + 2, y + 1});
	  }
	  // Going -> 2 Right + Bottom
	  if(((x + 2) <= rightBound) && ((y - 1) >= lowerBound)){
		if(directionCancel[5] == false)
        coordinatesList.add(new int[] { x + 2, y - 1});
	  }
	  // Going -> 2 Left + Top
	  if(((x - 2) >= leftBound) && ((y + 1) <= upperBound)){
		if(directionCancel[6] == false)
        coordinatesList.add(new int[] { x - 2, y + 1});
	  }
	  // Going -> 2 Left + Bottom
	  if(((x - 2) >= leftBound) && ((y - 1) >= lowerBound)){
		if(directionCancel[7] == false)
        coordinatesList.add(new int[] { x - 2, y - 1});
	  }
    }  

	initializeDirectionCancel();
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