import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;

public class King extends ChessPiece {
	private char symbol = 'K';
	// Is determined by hasMoved
	private boolean canCastle = true;
	// First move only
	private boolean hasMoved = false;
	// -- Board Bounds -- //
	final int leftBound = 1;
	final int rightBound = 8;
	final int lowerBound = 1;
	final int upperBound = 8;
	// To cancel the direction of a move
	boolean[] directionCancel = new boolean[8];

	public King(int x, int y, char ps) {
		super(x, y, ps);
		initializeDirectionCancel();
	}

	public char GetSymbol() {
		return symbol;
	}

	// Set all directionCancel indicators to false
	public void initializeDirectionCancel() {
		for (int i = 0; i < 8; i++) {
			directionCancel[i] = false;
		}
	}

	// Detects if a piece of the same side is on the same target coordinates
	public void CancelCoordinates(int x, int y, ChessPiece cp) {
		// -- White Side Capture/No Friendly Capture -- //
		if (playerSide == 'W' && cp.IsAlive() && cp.GetPlayerSide() == 'W') {
			// Going -> Top //
			if ((y + 1) <= upperBound) {
				if (cp.GetXCoord() == x && cp.GetYCoord() == y + 1)
					directionCancel[0] = true;
			}
			// Going -> Bottom //
			if ((y - 1) <= upperBound) {
				if (cp.GetXCoord() == x && cp.GetYCoord() == y - 1)
					directionCancel[1] = true;
			}
			// Going -> Right //
			if ((x + 1) <= rightBound) {
				if (cp.GetXCoord() == x + 1 && cp.GetYCoord() == y)
					directionCancel[2] = true;
			}
			// Going -> Left //
			if ((x - 1) >= leftBound) {
				if (cp.GetXCoord() == x - 1 && cp.GetYCoord() == y)
					directionCancel[3] = true;
			}
			// Going -> Top + Right //
			if (((x + 1) <= rightBound) && ((y + 1) <= upperBound)) {
				if (cp.GetXCoord() == x + 1 && cp.GetYCoord() == y + 1)
					directionCancel[4] = true;
			}
			// Going -> Bottom + Right //
			if (((x + 1) <= rightBound) && ((y - 1) >= lowerBound)) {
				if (cp.GetXCoord() == x + 1 && cp.GetYCoord() == y - 1)
					directionCancel[5] = true;
			}
			// Going -> 2 Left + Top //
			if (((x - 1) >= leftBound) && ((y + 1) <= upperBound)) {
				if (cp.GetXCoord() == x - 1 && cp.GetYCoord() == y + 1)
					directionCancel[6] = true;
			}
			// Going -> 2 Left + Bottom //
			if (((x - 1) >= leftBound) && ((y - 1) >= lowerBound)) {
				if (cp.GetXCoord() == x - 1 && cp.GetYCoord() == y - 1)
					directionCancel[7] = true;
			}
		}
		// -- Black Side Capture/No Friendly Capture -- //
		else if (playerSide == 'B' && cp.IsAlive() && cp.GetPlayerSide() == 'B') {
			// Going -> Top //
			if ((y + 1) <= upperBound) {
				if (cp.GetXCoord() == x && cp.GetYCoord() == y + 1)
					directionCancel[0] = true;
			}
			// Going -> Bottom //
			if ((y - 1) <= upperBound) {
				if (cp.GetXCoord() == x && cp.GetYCoord() == y - 1)
					directionCancel[1] = true;
			}
			// Going -> Right //
			if ((x + 1) <= rightBound) {
				if (cp.GetXCoord() == x + 1 && cp.GetYCoord() == y)
					directionCancel[2] = true;
			}
			// Going -> Left //
			if ((x - 1) >= leftBound) {
				if (cp.GetXCoord() == x - 1 && cp.GetYCoord() == y)
					directionCancel[3] = true;
			}
			// Going -> Top + Right //
			if (((x + 1) <= rightBound) && ((y + 1) <= upperBound)) {
				if (cp.GetXCoord() == x + 1 && cp.GetYCoord() == y + 1)
					directionCancel[4] = true;
			}
			// Going -> Bottom + Right //
			if (((x + 1) <= rightBound) && ((y - 1) >= lowerBound)) {
				if (cp.GetXCoord() == x + 1 && cp.GetYCoord() == y - 1)
					directionCancel[5] = true;
			}
			// Going -> 2 Left + Top //
			if (((x - 1) >= leftBound) && ((y + 1) <= upperBound)) {
				if (cp.GetXCoord() == x - 1 && cp.GetYCoord() == y + 1)
					directionCancel[6] = true;
			}
			// Going -> 2 Left + Bottom //
			if (((x - 1) >= leftBound) && ((y - 1) >= lowerBound)) {
				if (cp.GetXCoord() == x - 1 && cp.GetYCoord() == y - 1)
					directionCancel[7] = true;
			}
		}
	}

	// Search each valid move within the bounds of the board
	public ArrayList<int[]> TheKingsMoves(int x, int y, ChessPiece[] ChessPieceContainer, int numPieces) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		// -- Search the whole container for occupied spaces -- //
		for (int i = 0; i < numPieces; i++) {
			CancelCoordinates(x, y, ChessPieceContainer[i]);
		}

		// Going -> Top //
		if (y < upperBound) {
			if (directionCancel[0] == false)
				coordinatesList.add(new int[] { x, y + 1 });
		}
		// Going -> Bottom //
		if (y > lowerBound) {
			if (directionCancel[1] == false)
				coordinatesList.add(new int[] { x, y - 1 });
		}
		// Going -> Right //
		if (x < rightBound) {
			if (directionCancel[2] == false)
				coordinatesList.add(new int[] { x + 1, y });
		}
		// Going -> Left //
		if (x > leftBound) {
			if (directionCancel[3] == false)
				coordinatesList.add(new int[] { x - 1, y });
		}
		// Going -> Top Right //
		if (y < upperBound && x < rightBound) {
			if (directionCancel[4] == false)
				coordinatesList.add(new int[] { x + 1, y + 1 });
		}
		// Going -> Bottom Right //
		if (y > lowerBound && x < rightBound) {
			if (directionCancel[5] == false)
				coordinatesList.add(new int[] { x + 1, y - 1 });
		}
		// Going -> Top Left //
		if (y < upperBound && x > leftBound) {
			if (directionCancel[6] == false)
				coordinatesList.add(new int[] { x - 1, y + 1 });
		}
		// Going -> Bottom Left //
		if (y > lowerBound && x > leftBound) {
			if (directionCancel[7] == false)
				coordinatesList.add(new int[] { x - 1, y - 1 });
		}
		// Reset all directionCancel indicators to false for the next instance
		// of a move
		initializeDirectionCancel();
		return coordinatesList;
	}

	public ArrayList<int[]> ValidMoves(int TheKingX, int TheKingY, ChessPiece[] ChessPieceContainer, int numPieces) {
		Set<Integer> OpponentSet = new HashSet();
		ArrayList<int[]> opponentsMoves = new ArrayList<>();
		ArrayList<int[]> KingMoves = new ArrayList<>();
		ArrayList<int[]> tempMoves = new ArrayList<>();
		KingMoves = TheKingsMoves(GetXCoord(), GetYCoord(), ChessPieceContainer, numPieces);
		for (int coords[] : KingMoves) {
			for (int i = 0; i < numPieces; i++) {
				if (ChessPieceContainer[i].GetXCoord() == coords[0] && ChessPieceContainer[i].GetYCoord() == coords[1]
						&& ChessPieceContainer[i].IsAlive())
					opponentsMoves.add(new int[] { coords[0], coords[1] });
			}
		}

		// Adds all of their moves to an ArrayList
		for (int i = 0; i < numPieces; i++) {
			if (ChessPieceContainer[i].GetPlayerSide() != playerSide && ChessPieceContainer[i].GetSymbol() == 'K') {
				tempMoves = TheKingsMoves(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord(),
						ChessPieceContainer, numPieces);
			} else if (ChessPieceContainer[i].IsAlive() &&
					ChessPieceContainer[i].GetPlayerSide() != playerSide) {
				// If the opponent's chess piece is alive
				tempMoves = ChessPieceContainer[i].ValidMoves(ChessPieceContainer[i].GetXCoord(),
						ChessPieceContainer[i].GetYCoord(), ChessPieceContainer, numPieces);
			}
			for (int[] moves : tempMoves) {
				int tempX = moves[0];
				int tempY = moves[1];
				int x = ChessPieceContainer[i].GetXCoord();
				int y = ChessPieceContainer[i].GetYCoord();
				// Dont add the move directly in front of a pawn
				if (ChessPieceContainer[i].getClass().getName() == "Pawn") {
					if (tempX == x && (tempY == y - 1 || tempY == y + 1)) {
					} else {
						OpponentSet.add((tempY - 1) * 8 + tempX);
					}
				} else {
					OpponentSet.add((tempY - 1) * 8 + tempX);
				}
			}
		}

		ArrayList<int[]> AvailableMoves = new ArrayList<>();
		for (int[] moves : KingMoves) {
			int kingX = moves[0];
			int kingY = moves[1];

			boolean shouldAdd = true;

			// Remove all the moves that would put it in check
			// for (int[] theirMoves : opponentsMoves) {
			// int theirX = theirMoves[0];
			// int theirY = theirMoves[1];
			// if (kingX == theirX && kingY == theirY) {
			// // Move shouldnt be added to the available moves arr
			// shouldAdd = false;
			// }
			// }
			if (!OpponentSet.contains((kingY - 1) * 8 + kingX)) {
				AvailableMoves.add(new int[] { kingX, kingY });
			}
			// if (shouldAdd == true)
			// AvailableMoves.add(new int[] { kingX, kingY });
		}

		return AvailableMoves;
	}
}