import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public class Bishop extends ChessPiece {
	private char symbol = 'B';
	// -- Board Bounds -- //
  final int leftBound = 0;
  final int rightBound = 9;
  final int lowerBound = 0;
  final int upperBound = 9;
	// ---- Limits movement ---- //
  // Upper Right
  private int[] URTargetCoords = new int[2];
  // Lower Right
  private int[] LRTargetCoords = new int[2];
  // Upper Left
  private int[] ULTargetCoords = new int[2];
  // Lower Left
  private int[] LLTargetCoords = new int[2];
	
	// -- Used for comparison -- //
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

	// To reset the bounds for the next valid coordinate search
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

	// -- Comparison: -- //
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
  public void CancelCoordinates(int x, int y, ChessPiece cp){
    // Modify the max amount of a certain direction 
		// to fit within the bounds
		int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
		
		// Check if a piece is alive on a space
		if (cp.IsAlive()) {
			// Going -> Top Right //
			for(int i = 1; i <= upperMax; i++){
				for(int j = 1; j <= rightMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x + j && cp.GetYCoord() == y + i){
							URTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
			// Going -> Bottom Right //
			for(int i = 1; i <= lowerMax; i++){
				for(int j = 1; j <= rightMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x + j && cp.GetYCoord() == y - i){
							LRTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
			// Going -> Top Left //
			for(int i = 1; i <= upperMax; i++){
				for(int j = 1; j <= leftMax; j++){
					if(j == i){
						if(cp.GetXCoord() == x - j && cp.GetYCoord() == y + i){
							ULTargetCoords = SetTargetCoords(x, y, cp.GetXCoord(), cp.GetYCoord());
						}
					}
				}
			}
			// Going -> Bottom Left //
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

	// To detect the closest piece to the selected piece
  public void CompareBounds(int x, int y){
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
  
	// Detects if there is a capture in an adjacent space
	// Adds a capture space since the piece originally avoids
	// any piece
  public void DetectCapture(int x, int y, ChessPiece cp){
		// Modify the max amount of a certain direction 
		// to fit within the bounds
		int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
	
	// -- White Side Capture -- // 
	if(playerSide == 'W' && cp.IsAlive() && cp.GetPlayerSide() == 'B'){
	  // Going -> Top Right //
	  if(cp.GetXCoord() == URTargetCoords[1] && cp.GetYCoord() == URTargetCoords[0]){
			URTargetCoords[0] = URTargetCoords[0] + 1;
			URTargetCoords[1] = URTargetCoords[1] + 1;
	  }
	  // Going -> Bottom Right //
	  if(cp.GetXCoord() == LRTargetCoords[1] && cp.GetYCoord() == LRTargetCoords[0]){
	    LRTargetCoords[0] = LRTargetCoords[0] - 1;
			LRTargetCoords[1] = LRTargetCoords[1] + 1;
	  }
	  // Going -> Top Left //
    if(cp.GetXCoord() == ULTargetCoords[1] && cp.GetYCoord() == ULTargetCoords[0]){
			ULTargetCoords[0] = ULTargetCoords[0] + 1;
			ULTargetCoords[1] = ULTargetCoords[1] - 1;
	  }
	  // Going -> Bottom Left //
	  if(cp.GetXCoord() == LLTargetCoords[1] && cp.GetYCoord() == LLTargetCoords[0]){
	    LLTargetCoords[0] = LLTargetCoords[0] - 1;
			LLTargetCoords[1] = LLTargetCoords[1] - 1;
	  } 
	} 
	// -- Black Side Capture -- // 
	else if(playerSide == 'B' && cp.IsAlive() && cp.GetPlayerSide() == 'W'){
			if(cp.GetXCoord() == URTargetCoords[1] && cp.GetYCoord() == URTargetCoords[0]){
				URTargetCoords[0] = URTargetCoords[0] + 1;
				URTargetCoords[1] = URTargetCoords[1] + 1;
			}
			// Going -> Bottom Right //
			if(cp.GetXCoord() == LRTargetCoords[1] && cp.GetYCoord() == LRTargetCoords[0]){
				LRTargetCoords[0] = LRTargetCoords[0] - 1;
				LRTargetCoords[1] = LRTargetCoords[1] + 1;
			}
			// Going -> Top Left //
			if(cp.GetXCoord() == ULTargetCoords[1] && cp.GetYCoord() == ULTargetCoords[0]){
				ULTargetCoords[0] = ULTargetCoords[0] + 1;
				ULTargetCoords[1] = ULTargetCoords[1] - 1;
			}
			// Going -> Bottom Left //
			if(cp.GetXCoord() == LLTargetCoords[1] && cp.GetYCoord() == LLTargetCoords[0]){
				LLTargetCoords[0] = LLTargetCoords[0] - 1;
				LLTargetCoords[1] = LLTargetCoords[1] - 1;
			} 
		}
  }
	
	// Helper function to set the bounds based on given inputs 
	public int BoundsOrPiece(int[] TargetCoords, int index, char direction, int x, int y){
		int returnedBounds = 0;
		switch(direction){
			case 'N':
				// Upper //
				if(TargetCoords[index] != 0){
					returnedBounds = TargetCoords[index] - y;
				} else {
					returnedBounds = upperBound - y;
				} 
				break;
			case 'S':
				// Lower //
				if(TargetCoords[index] != 0){
					returnedBounds = y - TargetCoords[index];
				} else {
					returnedBounds = y - lowerBound;
				} 
				break;
			case 'E':
				// Right //
				if(TargetCoords[index] != 0){
					returnedBounds = TargetCoords[index] - x;
				} else {
					returnedBounds = rightBound - x;
				}	  
				break;
			case 'W':
				// Left //
				if(TargetCoords[index] != 0){
					returnedBounds = x - TargetCoords[index];
				} else {
					returnedBounds = x - leftBound;
				}
				break;
			}
		return returnedBounds;
	}

	// Search the each coordinate for valid moves
  public ArrayList<int[]> ValidMoves(int x, int y, ChessPiece[] copyPieceContainer, int numPieces) {
    ArrayList<int[]> coordinatesList = new ArrayList<>();
		// -- Search the whole container for occupied spaces -- //
		for (int i = 0; i < numPieces; i++) {
      CancelCoordinates(x, y, copyPieceContainer[i]);
    }
		// Modify the max amount of a certain direction 
		// to fit within the bounds
    int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
	
		// Going -> Top Right //
		for(int i = 1; i < BoundsOrPiece(URTargetCoords, 0, 'N', x, y); i++){
			for(int j = 1; j < BoundsOrPiece(URTargetCoords, 1, 'E', x, y); j++){
				if(j == i){
					coordinatesList.add(new int[] { x + j, y + i });
				}
			}
		}
		// Going -> Bottom Right //
		for(int i = 1; i < BoundsOrPiece(LRTargetCoords, 0, 'S', x, y); i++){
			for(int j = 1; j < BoundsOrPiece(LRTargetCoords, 1, 'E', x, y); j++){
				if(j == i){
					coordinatesList.add(new int[] { x + j, y - i });
				}
			}
		}
		// Going -> Top Left //
		for(int i = 1; i < BoundsOrPiece(ULTargetCoords, 0, 'N', x, y); i++){
			for(int j = 1; j < BoundsOrPiece(ULTargetCoords, 1, 'W', x, y); j++){
				if(j == i){
					coordinatesList.add(new int[] { x - j, y + i });
				}
			}
		}
		// Going -> Bottom Left //
		for(int i = 1; i < BoundsOrPiece(LLTargetCoords, 0, 'S', x, y); i++){
			for(int j = 1; j < BoundsOrPiece(LLTargetCoords, 1, 'W', x, y); j++){
				if(j == i){
					coordinatesList.add(new int[] { x - j, y - i });
				}
			}
		}  
		
		// Reset all bounds for the next instance of a move
		ResetBounds();
		return coordinatesList;
	}	
}