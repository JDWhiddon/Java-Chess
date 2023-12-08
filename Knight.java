import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public class Knight extends ChessPiece {
	private char symbol = 'N';
	// -- Board Bounds -- //
	final int leftBound = 1;
	final int rightBound = 8;
	final int lowerBound = 1;
	final int upperBound = 8;
	// To cancel the direction of a move
	boolean[] directionCancel = new boolean[8];

	public Knight(int x, int y, char ps) {
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
		// Going -> 2 Top + Right //
		if(((y + 2) <= upperBound) && ((x + 1) <= rightBound)){
			if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y + 2)
				directionCancel[0] = true;
	  }
		// Going -> 2 Top + Left //
		if(((y + 2) <= upperBound) && ((x - 1) >= leftBound)){
		  if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y + 2)
				directionCancel[1] = true;
	  }
	  // Going -> 2 Bottom + Right //
	  if(((y - 2) >= lowerBound) && ((x + 1) <= rightBound)){
		  if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y - 2)
				directionCancel[2] = true;
	  }
		// Going -> 2 Bottom + Left //
		if(((y - 2) >= lowerBound) && ((x - 1) >= leftBound)){
		  if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y - 2)
				directionCancel[3] = true;
	  }
		// Going -> 2 Right + Top //
		if(((x + 2) <= rightBound) && ((y + 1) <= upperBound)){
		  if(cp.GetXCoord() == x + 2 && cp.GetYCoord() == y + 1)
				directionCancel[4] = true;
	  }
		// Going -> 2 Right + Bottom //
		if(((x + 2) <= rightBound) && ((y - 1) >= lowerBound)){
		  if(cp.GetXCoord() == x + 2 && cp.GetYCoord() == y - 1)
				directionCancel[5] = true;
	  }
		// Going -> 2 Left + Top //
		if(((x - 2) >= leftBound) && ((y + 1) <= upperBound)){
		  if(cp.GetXCoord() == x - 2 && cp.GetYCoord() == y + 1)
				directionCancel[6] = true;
	  }
		// Going -> 2 Left + Bottom //
		if(((x - 2) >= leftBound) && ((y - 1) >= lowerBound)){
		  if(cp.GetXCoord() == x - 2 && cp.GetYCoord() == y - 1)
				directionCancel[7] = true;
	  } 
	} 
	// -- Black Side Capture/No Friendly Capture -- //
	else if (playerSide == 'B' && cp.IsAlive() && cp.GetPlayerSide() == 'B') {
			// Going -> 2 Top + Right //
			if(((y + 2) <= upperBound) && ((x + 1) <= rightBound)){
				if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y + 2)
							directionCancel[0] = true;
			}
			// Going -> 2 Top + Left //
			if(((y + 2) <= upperBound) && ((x - 1) >= leftBound)){
				if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y + 2)
					directionCancel[1] = true;
			}
			// Going -> 2 Bottom + Right //
			if(((y - 2) >= lowerBound) && ((x + 1) <= rightBound)){
				if(cp.GetXCoord() == x + 1 && cp.GetYCoord() == y - 2)
					directionCancel[2] = true;
			}
			// Going -> 2 Bottom + Left //
			if(((y - 2) >= lowerBound) && ((x - 1) >= leftBound)){
				if(cp.GetXCoord() == x - 1 && cp.GetYCoord() == y - 2)
					directionCancel[3] = true;
			}
			// Going -> 2 Right + Top //
			if(((x + 2) <= rightBound) && ((y + 1) <= upperBound)){
				if(cp.GetXCoord() == x + 2 && cp.GetYCoord() == y + 1)
					directionCancel[4] = true;
			}
			// Going -> 2 Right + Bottom //
			if(((x + 2) <= rightBound) && ((y - 1) >= lowerBound)){
				if(cp.GetXCoord() == x + 2 && cp.GetYCoord() == y - 1)
					directionCancel[5] = true;
			}
			// Going -> 2 Left + Top //
			if(((x - 2) >= leftBound) && ((y + 1) <= upperBound)){
				if(cp.GetXCoord() == x - 2 && cp.GetYCoord() == y + 1)
					directionCancel[6] = true;
			}
			// Going -> 2 Left + Bottom //
			if(((x - 2) >= leftBound) && ((y - 1) >= lowerBound)){
				if(cp.GetXCoord() == x - 2 && cp.GetYCoord() == y - 1)
					directionCancel[7] = true;
			}	
		}
	}

	// Search each valid move within the bounds of the board
	public ArrayList<int[]> ValidMoves(int x, int y, ChessPiece[] copyPieceContainer, int numPieces) {
    ArrayList<int[]> coordinatesList = new ArrayList<>();
		// -- Search the whole container for occupied spaces -- //
		for (int i = 0; i < copyPieceContainer.length; i++) {
      CancelCoordinates(x, y, copyPieceContainer[i]);
    }
		
		// Going -> 2 Top + Right //
		if(((y + 2) <= upperBound) && ((x + 1) <= rightBound)){
			if(directionCancel[0] == false)
				coordinatesList.add(new int[] { x + 1, y + 2});
		}
		// Going -> 2 Top + Left //
		if(((y + 2) <= upperBound) && ((x - 1) >= leftBound)){
			if(directionCancel[1] == false)
				coordinatesList.add(new int[] { x - 1, y + 2});
		}
		// Going -> 2 Bottom + Right //
		if(((y - 2) >= lowerBound) && ((x + 1) <= rightBound)){
			if(directionCancel[2] == false)
				coordinatesList.add(new int[] { x + 1, y - 2});
		}
		// Going -> 2 Bottom + Left //
		if(((y - 2) >= lowerBound) && ((x - 1) >= leftBound)){
			if(directionCancel[3] == false)
				coordinatesList.add(new int[] { x - 1, y - 2});
		}
		// Going -> 2 Right + Top //
		if(((x + 2) <= rightBound) && ((y + 1) <= upperBound)){
			if(directionCancel[4] == false)
				coordinatesList.add(new int[] { x + 2, y + 1});
		}
		// Going -> 2 Right + Bottom //
		if(((x + 2) <= rightBound) && ((y - 1) >= lowerBound)){
			if(directionCancel[5] == false)
				coordinatesList.add(new int[] { x + 2, y - 1});
		}
		// Going -> 2 Left + Top //
		if(((x - 2) >= leftBound) && ((y + 1) <= upperBound)){
			if(directionCancel[6] == false)
				coordinatesList.add(new int[] { x - 2, y + 1});
		}
		// Going -> 2 Left + Bottom //
		if(((x - 2) >= leftBound) && ((y - 1) >= lowerBound)){
			if(directionCancel[7] == false)
					coordinatesList.add(new int[] { x - 2, y - 1});
		}
    
		// Reset all directionCancel indicators to false for the next instance
		// of a move
		initializeDirectionCancel();
    return coordinatesList;
  }
}