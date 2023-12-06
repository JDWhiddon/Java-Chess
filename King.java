import java.awt.*;
import java.util.ArrayList;
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
	public void initializeDirectionCancel(){
	  for(int i = 0; i < 8; i++){
	    directionCancel[i] = false;
	  }
	}
	
	// Detects if a piece of the same side is on the same target coordinates
	public void CancelCoordinates(int x, int y, ChessPiece cp){
		// -- White Side Capture/No Friendly Capture -- // 
		if (playerSide == 'W' && cp.IsAlive() && cp.GetPlayerSide() == 'W') {
			// Going -> Top //
			if((y + 1) <= upperBound){
				if(cp.GetXCoord() == x && cp.GetYCoord() == y + 1)
					directionCancel[0] = true;
			}
			// Going -> Bottom //
			if((y - 1) <= upperBound){
				if(cp.GetXCoord() == x && cp.GetYCoord() == y - 1)
					directionCancel[1] = true;
				}
			// Going -> Right //
			if((x + 1) <= rightBound){
				if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y)
					directionCancel[2] = true;
			}
			// Going -> Left //
			if((x - 1) >= leftBound){
				if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y)
					directionCancel[3] = true;
			}
			// Going -> Top + Right //
			if(((x + 1) <= rightBound) && ((y + 1) <= upperBound)){
				if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y + 1)
					directionCancel[4] = true;
			}
			// Going -> Bottom + Right //
			if(((x + 1) <= rightBound) && ((y - 1) >= lowerBound)){
				if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y - 1)
					directionCancel[5] = true;
			}
			// Going -> 2 Left + Top //
			if(((x - 1) >= leftBound) && ((y + 1) <= upperBound)){
				if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y + 1)
					directionCancel[6] = true;
			}
			// Going -> 2 Left + Bottom //
			if(((x - 1) >= leftBound) && ((y - 1) >= lowerBound)){
				if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y - 1)
					directionCancel[7] = true;
			}
		}
		// -- Black Side Capture/No Friendly Capture -- //
		else if (playerSide == 'B' && cp.IsAlive() && cp.GetPlayerSide() == 'B') {
			// Going -> Top //
			if((y + 1) <= upperBound){
				if(cp.GetXCoord() == x && cp.GetYCoord() == y + 1)
					directionCancel[0] = true;
			}
			// Going -> Bottom //
			if((y - 1) <= upperBound){
				if(cp.GetXCoord() == x && cp.GetYCoord() == y - 1)
					directionCancel[1] = true;
				}
			// Going -> Right //
			if((x + 1) <= rightBound){
				if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y)
					directionCancel[2] = true;
			}
			// Going -> Left //
			if((x - 1) >= leftBound){
				if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y)
					directionCancel[3] = true;
			}
			// Going -> Top + Right //
			if(((x + 1) <= rightBound) && ((y + 1) <= upperBound)){
				if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y + 1)
					directionCancel[4] = true;
			}
			// Going -> Bottom + Right //
			if(((x + 1) <= rightBound) && ((y - 1) >= lowerBound)){
				if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y - 1)
					directionCancel[5] = true;
			}
			// Going -> 2 Left + Top //
			if(((x - 1) >= leftBound) && ((y + 1) <= upperBound)){
				if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y + 1)
					directionCancel[6] = true;
			}
			// Going -> 2 Left + Bottom //
			if(((x - 1) >= leftBound) && ((y - 1) >= lowerBound)){
				if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y - 1)
					directionCancel[7] = true;
			}
		}
	}

	// Search each valid move within the bounds of the board
	public ArrayList<int[]> ValidMoves(int x, int y, ChessPiece[] copyPieceContainer) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		// -- Search the whole container for occupied spaces -- //
		for (int i = 0; i < 32; i++) {
      CancelCoordinates(x, y, copyPieceContainer[i]);
    }

		// Going -> Top //
		if (y < upperBound){
			if(directionCancel[0] == false)
				coordinatesList.add(new int[] { x, y + 1 });
		}
		// Going -> Bottom //
		if (y > lowerBound){
			if(directionCancel[1] == false)
				coordinatesList.add(new int[] { x, y - 1 });
		}
		// Going -> Right //
		if (x < rightBound){
			if(directionCancel[2] == false)
				coordinatesList.add(new int[] { x + 1, y });
		}
		// Going -> Left //
		if (x > leftBound){
			if(directionCancel[3] == false)
				coordinatesList.add(new int[] { x - 1, y });
		}
		// Going -> Top Right //
		if (y < upperBound && x < rightBound) {
			if(directionCancel[4] == false)
			coordinatesList.add(new int[] { x + 1, y + 1 });
		}
		// Going -> Bottom Right //
		if (y > lowerBound && x < rightBound) {
			if(directionCancel[5] == false)
			coordinatesList.add(new int[] { x + 1, y - 1 });
		}
		// Going -> Top Left //
		if (y < upperBound && x > leftBound) {
			if(directionCancel[6] == false)
			coordinatesList.add(new int[] { x - 1, y + 1 });
		}
		// Going -> Bottom Left //
		if (y > lowerBound && x > leftBound) {
			if(directionCancel[7] == false)
			coordinatesList.add(new int[] { x - 1, y - 1 });
		}
		
		// Reset all directionCancel indicators to false for the next instance
		// of a move
		initializeDirectionCancel();
		return coordinatesList;
	}
}