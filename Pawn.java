import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public class Pawn extends ChessPiece {
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

	public void SetSymbol(char c) {
		symbol = c;
	}

	public void DetectSpecialMove(int x, int y, ChessPiece cp) {
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
			/*
			 * // En Passant right
			 * if (cp.GetXCoord() == (x + 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice()
			 * == true) {
			 * choices[3] = true;
			 * }
			 * // En Passant left
			 * if (cp.GetXCoord() == (x - 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice()
			 * == true) {
			 * choices[4] = true;
			 * }
			 */
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
			/*
			 * // En Passant right
			 * if (cp.GetXCoord() == (x + 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice()
			 * == true) {
			 * choices[3] = true;
			 * }
			 * // En Passant left
			 * if (cp.GetXCoord() == (x - 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice()
			 * == true) {
			 * choices[4] = true;
			 * }
			 */
		}

	}

	public ArrayList<int[]> PawnSpecialMove(int x, int y, ChessPiece[] copyPieceContainer) {

		ArrayList<int[]> SelectedCoordinatesList = ValidMoves(x, y, copyPieceContainer);
		if (playerSide == 'W') {
			// Diagonal Right Capture
			if (choices[1] == true && y < upperBound && x < rightBound) {
				SelectedCoordinatesList.add(new int[] { x + 1, y + 1 });
			}
			// Diagonal Left Capture
			if (choices[2] == true && y < upperBound && x > leftBound) {
				SelectedCoordinatesList.add(new int[] { x - 1, y + 1 });
			}
			/*
			 * // En Passant Right
			 * if (choices[3] == true && y < upperBound && x < rightBound) {
			 * SelectedCoordinatesList.add(new int[] { x + 1, y + 1 });
			 * }
			 * // En Passant Left
			 * if (choices[4] == true && y < upperBound && x > leftBound) {
			 * SelectedCoordinatesList.add(new int[] { x - 1, y + 1 });
			 * }
			 */
		} else {
			// Diagonal Right Capture
			if (choices[1] == true && y > lowerBound && x < rightBound) {
				SelectedCoordinatesList.add(new int[] { x + 1, y - 1 });
			}
			// Diagonal Left Capture
			if (choices[2] == true && y > lowerBound && x > leftBound) {
				SelectedCoordinatesList.add(new int[] { x - 1, y - 1 });
			}
			/*
			 * // En Passant Right
			 * if (choices[3] == true && y > lowerBound && x < rightBound) {
			 * SelectedCoordinatesList.add(new int[] { x + 1, y - 1 });
			 * }
			 * // En Passant Left
			 * if (choices[4] == true && y > lowerBound && x > leftBound) {
			 * SelectedCoordinatesList.add(new int[] { x - 1, y - 1 });
			 * }
			 */
		}

		ResetChoices();

		return SelectedCoordinatesList;
	}

	public ArrayList<int[]> ValidMoves(int x, int y, ChessPiece[] copyPieceContainer) {
		movedTwice = false;
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		// -- Search the whole container for occupied spaces -- //
		for (int i = 0; i < copyPieceContainer.length; i++) {
			DetectSpecialMove(x, y, copyPieceContainer[i]);
		}
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