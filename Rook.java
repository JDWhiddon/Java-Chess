import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public class Rook extends ChessPiece {
	private char symbol = 'R';
	// -- Board Bounds -- //
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

	// -- Used for comparison -- //
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

	// To reset the bounds for the next valid coordinate search
	public void ResetBounds() {
		limitedLeftBound = 0;
		limitedRightBound = 9;
		limitedLowerBound = 0;
		limitedUpperBound = 9;
		// -- Comparison: -- //
		previousLeftBound = 0;
		previousRightBound = 9;
		previousLowerBound = 0;
		previousUpperBound = 9;
	}

	// Cancel piece movement if another piece is in the line of sight
	public void CancelCoordinates(int x, int y, ChessPiece cp) {
		// Modify the max amount of a certain direction
		// to fit within the bounds
		int leftMax = x - leftBound;
		int rightMax = rightBound - x;
		int lowerMax = y - lowerBound;
		int upperMax = upperBound - y;

		// Check if a piece is alive on a space
		if (cp.IsAlive()) {
			// Going -> Top //
			for (int i = 1; i < upperMax; i++) {
				if (cp.GetXCoord() == x && cp.GetYCoord() == y + i) {
					limitedUpperBound = y + i;
				}
			}
			// Going -> Bottom //
			for (int i = 1; i < lowerMax; i++) {
				if (cp.GetXCoord() == x && cp.GetYCoord() == y - i) {
					limitedLowerBound = y - i;
				}
			}
			// Going -> Right //
			for (int i = 1; i < rightMax; i++) {
				if (cp.GetXCoord() == x + i && cp.GetYCoord() == y) {
					limitedRightBound = x + i;
				}
			}
			// Going -> Left //
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

	// To detect the closest piece to the selected piece
	public void CompareBounds(int x, int y) {
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

	// Detects if there is a capture on an adjacent space
	// Adds a capture space since the piece originally avoids
	// any piece
	public void DetectCapture(int x, int y, ChessPiece cp) {
		// Modify the max amount of a certain direction
		// to fit within the bounds
		int leftMax = x - leftBound;
		int rightMax = rightBound - x;
		int lowerMax = y - lowerBound;
		int upperMax = upperBound - y;

		// -- White Side Capture -- //
		if (playerSide == 'W' && cp.IsAlive() && cp.GetPlayerSide() == 'B') {
			// Going -> Top //
			if (cp.GetXCoord() == x && cp.GetYCoord() == limitedUpperBound) {
				limitedUpperBound = limitedUpperBound + 1;
			}
			// Going -> Bottom //
			if (cp.GetXCoord() == x && cp.GetYCoord() == limitedLowerBound) {
				limitedLowerBound = limitedLowerBound - 1;
			}
			// Going -> Right //
			if (cp.GetXCoord() == limitedRightBound && cp.GetYCoord() == y) {
				limitedRightBound = limitedRightBound + 1;
			}
			// Going -> Left //
			if (cp.GetXCoord() == limitedLeftBound && cp.GetYCoord() == y) {
				limitedLeftBound = limitedLeftBound - 1;
			}
		}
		// -- Black Side Capture -- //
		else if (playerSide == 'B' && cp.IsAlive() && cp.GetPlayerSide() == 'W') {
			// Going -> Top //
			if (cp.GetXCoord() == x && cp.GetYCoord() == limitedUpperBound) {
				limitedUpperBound = limitedUpperBound + 1;
			}
			// Going -> Bottom //
			if (cp.GetXCoord() == x && cp.GetYCoord() == limitedLowerBound) {
				limitedLowerBound = limitedLowerBound - 1;
			}
			// Going -> Right //
			if (cp.GetXCoord() == limitedRightBound && cp.GetYCoord() == y) {
				limitedRightBound = limitedRightBound + 1;
			}
			// Going -> Left //
			if (cp.GetXCoord() == limitedLeftBound && cp.GetYCoord() == y) {
				limitedLeftBound = limitedLeftBound - 1;
			}
		}
	}

	// Search the each coordinate for valid moves
	public ArrayList<int[]> ValidMoves(int x, int y, ChessPiece[] copyPieceContainer, int numPieces) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		// -- Search the whole container for occupied spaces -- //
		for (int i = 0; i < 32; i++) {
			CancelCoordinates(x, y, copyPieceContainer[i]);
		}
		// Modify the max amount of a certain direction
		// to fit within the bounds
		int leftMax = x - limitedLeftBound;
		int rightMax = limitedRightBound - x;
		int lowerMax = y - limitedLowerBound;
		int upperMax = limitedUpperBound - y;

		// Going -> Top //
		for (int i = 1; i < upperMax; i++) {
			coordinatesList.add(new int[] { x, y + i });
		}
		// Going -> Bottom //
		for (int i = 1; i < lowerMax; i++) {
			coordinatesList.add(new int[] { x, y - i });
		}
		// Going -> Right //
		for (int i = 1; i < rightMax; i++) {
			coordinatesList.add(new int[] { x + i, y });
		}
		// Going -> Left //
		for (int i = 1; i < leftMax; i++) {
			coordinatesList.add(new int[] { x - i, y });
		}

		// Reset all bounds for the next instance of a move
		ResetBounds();
		return coordinatesList;
	}
}