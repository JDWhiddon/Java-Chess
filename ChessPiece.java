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
  public void MoveTwice(boolean x) {
  }*/
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
	  if(y < upperBound)
        coordinatesList.add(new int[] { x, y + 1});
	  // Going -> Bottom
	  if(y > lowerBound)
        coordinatesList.add(new int[] { x, y - 1});
	  // Going -> Right
	  if(x < rightBound)
        coordinatesList.add(new int[] { x + 1, y });
	  // Going -> Left
	  if(x > leftBound)
        coordinatesList.add(new int[] { x - 1, y });
	  // Going -> Top Right
	  if(y < upperBound && x < rightBound){
		coordinatesList.add(new int[] { x + 1, y + 1 });
	  }
	  // Going -> Bottom Right
	  if(y > lowerBound && x < rightBound){
		coordinatesList.add(new int[] { x + 1, y - 1 });
	  }
	  // Going -> Top Left
	  if(y < upperBound && x > leftBound){
		coordinatesList.add(new int[] { x - 1, y + 1 });
	  }
	  // Going -> Bottom Left
	  if(y > lowerBound && x > leftBound){
        coordinatesList.add(new int[] { x - 1, y - 1 });
	  }
	  
    }
	// ---- Black ----
	else{
	  // Going -> Top
	  if(y < upperBound)
        coordinatesList.add(new int[] { x, y + 1});
	  // Going -> Bottom
	  if(y > lowerBound)
        coordinatesList.add(new int[] { x, y - 1});
	  // Going -> Right
	  if(x < rightBound)
        coordinatesList.add(new int[] { x + 1, y });
	  // Going -> Left
	  if(x > leftBound)
        coordinatesList.add(new int[] { x - 1, y });
	  // Going -> Top Right
	  if(y < upperBound && x < rightBound){
		coordinatesList.add(new int[] { x + 1, y + 1 });
	  }
	  // Going -> Bottom Right
	  if(y > lowerBound && x < rightBound){
		coordinatesList.add(new int[] { x + 1, y - 1 });
	  }
	  // Going -> Top Left
	  if(y < upperBound && x > leftBound){
		coordinatesList.add(new int[] { x - 1, y + 1 });
	  }
	  // Going -> Bottom Left
	  if(y > lowerBound && x > leftBound){
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
  final int rightBound = 8;
  final int lowerBound = 1;
  final int upperBound = 8;

  public Queen(int x, int y, char ps) {
    super(x, y, ps);
  }
  
  public void ResetBounds(){
	/*leftBound = 1;
    rightBound = 8;
    lowerBound = 1;
    upperBound = 8;*/
  }

  public char GetSymbol() {
    return symbol;
  }
  
  public void DetectCollision(JPanel[][] playSquares, int x, int y, ChessPiece cp){
	
  }
  
  public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
    ArrayList<int[]> coordinatesList = new ArrayList<>();
    int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
	
    if (playerSide == 'W') {
	  // Going -> Top
	  for(int i = 1; i <= upperMax; i++){
        coordinatesList.add(new int[] { x, y + i});
	  }
	  // Going -> Bottom
	  for(int i = 1; i <= lowerMax; i++){
        coordinatesList.add(new int[] { x, y - i});
	  }
	  // Going -> Right
	  for(int i = 1; i <= rightMax; i++){
        coordinatesList.add(new int[] { x + i, y });
	  }
	  // Going -> Left
	  for(int i = 1; i <= leftMax; i++){
        coordinatesList.add(new int[] { x - i, y });
	  }
	  // Going -> Top Right
	  for(int i = 1; i <= upperMax; i++){
		for(int j = 1; j <= rightMax; j++){
		  if(j == i){
            coordinatesList.add(new int[] { x + j, y + i });
		  }
	    }
	  }
	  // Going -> Bottom Right
	  for(int i = 1; i <= lowerMax; i++){
		for(int j = 1; j <= rightMax; j++){
		  if(j == i){
            coordinatesList.add(new int[] { x + j, y - i });
		  }
	    }
	  }
	  // Going -> Top Left
	  for(int i = 1; i <= upperMax; i++){
		for(int j = 1; j <= leftMax; j++){
		  if(j == i){
            coordinatesList.add(new int[] { x - j, y + i });
		  }
	    }
	  }
	  // Going -> Bottom Left
	  for(int i = 1; i <= lowerMax; i++){
		for(int j = 1; j <= leftMax; j++){
		  if(j == i){
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
  
  public void ResetBounds(){
	limitedLeftBound = 0;
	limitedRightBound = 9;
	limitedLowerBound = 0;
	limitedUpperBound = 9;
	previousLeftBound = 0;
	previousRightBound = 9;
	previousLowerBound = 0;
	previousUpperBound = 9;
  }
  
  public void DetectCollision(JPanel[][] playSquares, int x, int y, ChessPiece cp){
	int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
	
	if(/*playerSide == 'W' &&*/ cp.IsAlive()){
	   // Going -> Top
	  for(int i = 1; i < upperMax; i++){
		if(cp.GetXCoord() == x && cp.GetYCoord() == y + i){
		  limitedUpperBound = y + i;
		}
	  }
	  // Going -> Bottom
	  for(int i = 1; i < lowerMax; i++){
        if(cp.GetXCoord() == x && cp.GetYCoord() == y - i){
		  limitedLowerBound = y - i;
		}
	  }
	  // Going -> Right
	  for(int i = 1; i < rightMax; i++){
        if(cp.GetXCoord() == x + i && cp.GetYCoord() == y){
		  limitedRightBound = x + i;
		}
	  }
	  // Going -> Left
	  for(int i = 1; i < leftMax; i++){
        if(cp.GetXCoord() == x - i && cp.GetYCoord() == y){
		  limitedLeftBound = x - i;
		}
	  }
	} /*else if (playerSide == 'B' && cp.IsAlive()) {
		// Going -> Top
	  for(int i = 1; i < upperMax; i++){
		if(cp.GetXCoord() == x && cp.GetYCoord() == y + i){
		  limitedUpperBound = y + i;
		}
	  }
	  // Going -> Bottom
	  for(int i = 1; i < lowerMax; i++){
        if(cp.GetXCoord() == x && cp.GetYCoord() == y - i){
		  limitedLowerBound = y - i;
		}
	  }
	  // Going -> Right
	  for(int i = 1; i < rightMax; i++){
        if(cp.GetXCoord() == x + i && cp.GetYCoord() == y){
		  limitedRightBound = x + i;
		}
	  }
	  // Going -> Left
	  for(int i = 1; i < leftMax; i++){
        if(cp.GetXCoord() == x - i && cp.GetYCoord() == y){
		  limitedLeftBound = x - i;
		}
	  }
	} */
	 
	DetectCapture(x, y, cp);
	CompareBounds(x, y);
	  
	previousUpperBound = limitedUpperBound;
	previousLowerBound = limitedLowerBound;
	previousRightBound = limitedRightBound;
	previousLeftBound = limitedLeftBound;
	
  }
  
  // To detect the closest piece to the selected piece to combat
  // array loop limitations.
  public void CompareBounds(int x, int y){
	if(playerSide == 'W' || playerSide == 'B'){
	  if(previousUpperBound < limitedUpperBound){
		limitedUpperBound = previousUpperBound;
	  }
	  if(previousLowerBound > limitedLowerBound){
		limitedLowerBound = previousLowerBound;
	  }
	  if(previousRightBound < limitedRightBound){
		limitedRightBound = previousRightBound;
	  }
	  if(previousLeftBound > limitedLeftBound){
		limitedLeftBound = previousLeftBound;
	  }
	}/*else if (playerSide == 'B'){
	  if(previousUpperBound < limitedUpperBound){
		limitedUpperBound = previousUpperBound;
	  }
	  if(previousLowerBound > limitedLowerBound){
		limitedLowerBound = previousLowerBound;
	  }
	  if(previousRightBound < limitedRightBound){
		limitedRightBound = previousRightBound;
	  }
	  if(previousLeftBound > limitedLeftBound){
		limitedLeftBound = previousLeftBound;
	  }
	}*/
  }
  
  public void DetectCapture(int x, int y, ChessPiece cp){
	int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
	
	if(playerSide == 'W' && cp.IsAlive() && cp.GetPlayerSide() == 'B'){
	   // Going -> Top
	  if(cp.GetXCoord() == x && cp.GetYCoord() == limitedUpperBound){
		limitedUpperBound = limitedUpperBound + 1;
	  }
	  // Going -> Bottom
	  if(cp.GetXCoord() == x && cp.GetYCoord() == limitedLowerBound){
	    limitedLowerBound = limitedLowerBound - 1;
	  }
	  // Going -> Right
      if(cp.GetXCoord() == limitedRightBound && cp.GetYCoord() == y){
		limitedRightBound = limitedRightBound + 1;
	  }
	  // Going -> Left
	  if(cp.GetXCoord() == limitedLeftBound && cp.GetYCoord() == y){
	    limitedLeftBound = limitedLeftBound - 1;
	  } 
	} else if(playerSide == 'B' && cp.IsAlive() && cp.GetPlayerSide() == 'W'){
	  // Going -> Top
	  if(cp.GetXCoord() == x && cp.GetYCoord() == limitedUpperBound){
		limitedUpperBound = limitedUpperBound + 1;
	  }
	  // Going -> Bottom
	  if(cp.GetXCoord() == x && cp.GetYCoord() == limitedLowerBound){
	    limitedLowerBound = limitedLowerBound - 1;
	  }
	  // Going -> Right
      if(cp.GetXCoord() == limitedRightBound && cp.GetYCoord() == y){
		limitedRightBound = limitedRightBound + 1;
	  }
	  // Going -> Left
	  if(cp.GetXCoord() == limitedLeftBound && cp.GetYCoord() == y){
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
	  for(int i = 1; i < upperMax; i++){
        coordinatesList.add(new int[] { x, y + i});
	  }
	  // Going -> Bottom
	  for(int i = 1; i < lowerMax; i++){
        coordinatesList.add(new int[] { x, y - i});
	  }
	  // Going -> Right
	  for(int i = 1; i < rightMax; i++){
        coordinatesList.add(new int[] { x + i, y });
	  }
	  // Going -> Left
	  for(int i = 1; i < leftMax; i++){
        coordinatesList.add(new int[] { x - i, y });
	  }
    } 
	// ---- Player 2 ----
	/*else{
	  // Going -> Top
	  for(int i = 1; i <= upperMax; i++){
        coordinatesList.add(new int[] { x, y + i});
	  }
	  // Going -> Bottom
      for(int i = 1; i <= lowerMax; i++){
        coordinatesList.add(new int[] { x, y - i});
	  }
	  // Going -> Right
	  for(int i = 1; i <= rightMax; i++){
        coordinatesList.add(new int[] { x + i, y });
      }
	  // Going -> Left
	  for(int i = 1; i <= leftMax; i++){
        coordinatesList.add(new int[] { x - i, y });
	  }
	}*/
	
	ResetBounds();
    return coordinatesList;
  }

  public void Move(int x, int y) {
    
  }
}

class Bishop extends ChessPiece {
  private char symbol = 'B';
  final int leftBound = 1;
  final int rightBound = 9;
  final int lowerBound = 1;
  final int upperBound = 9;
  // Upper Right
  private int[] UROffset = new int[2];
  // Lower Right
  private int[] LROffset = new int[2];
  // Upper Left
  private int[] ULOffset = new int[2];
  // Lower Left
  private int[] LLOffset = new int[2];
  
  // Upper Right
  private int[] previousUROffset = new int[2];
  // Lower Right
  private int[] previousLROffset = new int[2];
  // Upper Left
  private int[] previousULOffset = new int[2];
  // Lower Left
  private int[] previousLLOffset = new int[2];
  
  public Bishop(int x, int y, char ps) {
    super(x, y, ps);
	ResetOffsets();
  }

  public char GetSymbol() {
    return symbol;
  }
  
  public void ResetOffsets(){
	// [0] = upper/lower
	// [1] = right/left
	// Upper Right
	UROffset[0] = 0;
	UROffset[1] = 0;
	// Lower Right
	LROffset[0] = 0;
	LROffset[1] = 0;
	// Upper Left
	ULOffset[0] = 0;
	ULOffset[1] = 0;
	// Lower Left
	LLOffset[0] = 0;
	LLOffset[1] = 0;
  
	// Upper Right
	previousUROffset[0] = 0;
	previousUROffset[1] = 0;
	// Lower Right
	previousLROffset[0] = 0;
	previousLROffset[1] = 0;
	// Upper Left
	previousULOffset[0] = 0;
	previousULOffset[1] = 0;
	// Lower Left
	previousLLOffset[0] = 0;
	previousLLOffset[1] = 0;
  }
  
  public void DetectCollision(JPanel[][] playSquares, int x, int y, ChessPiece cp){
    int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
	
	// Calculate the difference between a blocking piece and the
	// selected piece
	if (cp.IsAlive()) {
	  // Going -> Top Right
	  for(int i = 1; i <= upperMax; i++){
		for(int j = 1; j <= rightMax; j++){
		  if(j == i){
			if(cp.GetXCoord() == x + j && cp.GetYCoord() == y + i){
			  UROffset = coordDifference(x, y, cp.GetXCoord(), cp.GetYCoord());
			  System.out.print("URxOffset: " + UROffset[1] + " | ");
			  System.out.println("URyOffset: " + UROffset[0]);
			}
		  }
	    }
	  }
	  // Going -> Bottom Right
	  for(int i = 1; i <= lowerMax; i++){
		for(int j = 1; j <= rightMax; j++){
		  if(j == i){
			if(cp.GetXCoord() == x + j && cp.GetYCoord() == y - i){
              LROffset = coordDifference(x, y, cp.GetXCoord(), cp.GetYCoord());
			  System.out.print("LRxOffset: " + LROffset[1] + " | ");
			  System.out.println("LRyOffset: " + LROffset[0]);
			}
		  }
	    }
	  }
	  // Going -> Top Left
	  for(int i = 1; i <= upperMax; i++){
		for(int j = 1; j <= leftMax; j++){
		  if(j == i){
			if(cp.GetXCoord() == x - j && cp.GetYCoord() == y + i){
              ULOffset = coordDifference(x, y, cp.GetXCoord(), cp.GetYCoord());
			  System.out.print("ULxOffset: " + ULOffset[1] + " | ");
			  System.out.println("ULyOffset: " + ULOffset[0]);
			}
		  }
	    }
	  }
	  // Going -> Bottom Left
	  for(int i = 1; i <= lowerMax; i++){
		for(int j = 1; j <= leftMax; j++){
		  if(j == i){
			if(cp.GetXCoord() == x - j && cp.GetYCoord() == y - i){
              LLOffset = coordDifference(x, y, cp.GetXCoord(), cp.GetYCoord());
			  System.out.print("LLxOffset: " + LLOffset[1] + " | ");
			  System.out.println("LLyOffset: " + LLOffset[0]);
			}
		  }
	    }
	  }
    }
	
	CompareOffsets(x, y);
	
	for(int i = 0; i < 2; i++){
	  previousUROffset[i] = UROffset[i];
	  previousLROffset[i] = LROffset[i];
	  previousULOffset[i] = ULOffset[i];
	  previousLLOffset[i] = LLOffset[i];
	}
  }
  
  public int[] coordDifference(int x, int y, int newx, int newy){
	int[] offsetArr = new int[2];
	if(newx > x){
	  offsetArr[1] = newx - x;
	} else if (newx < x){
	  offsetArr[1] = x - newx;
	}
	
	if(newy > y){
	  offsetArr[0] = newy - y;
	} else if (newy < y){
	  offsetArr[0] = y - newy;
	}
	
	return offsetArr;
  }
  
  public void CompareOffsets(int x, int y){
	if(playerSide == 'W' || playerSide == 'B'){
		
	  if(previousUROffset[1] > UROffset[1]){
		UROffset[1] = previousUROffset[1];
	  }
	  if(previousUROffset[0] > UROffset[0]){
		UROffset[0] = previousUROffset[0];
	  }
	  if(previousLROffset[1] > LROffset[1]){
		LROffset[1] = previousLROffset[1];
	  }
	  if(previousLROffset[0] > LROffset[0]){
		LROffset[0] = previousLROffset[0];
	  }
	  if(previousULOffset[1] > ULOffset[1]){
		ULOffset[1] = previousULOffset[1];
	  }
	  if(previousULOffset[0] > ULOffset[0]){
		ULOffset[0] = previousULOffset[0];
	  }
	  if(previousLLOffset[1] > LLOffset[1]){
		LLOffset[1] = previousLLOffset[1];
	  }
	  if(previousLLOffset[0] > LLOffset[0]){
		LLOffset[0] = previousLLOffset[0];
	  }
	}
  }

  public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
    ArrayList<int[]> coordinatesList = new ArrayList<>();
	System.out.println("Current Offsets: ");
	System.out.print("URxOffset: " + UROffset[1] + " | ");
	System.out.println("URyOffset: " + UROffset[0]);
	System.out.print("LRxOffset: " + LROffset[1] + " | ");
	System.out.println("LRyOffset: " + LROffset[0]);
	System.out.print("ULxOffset: " + ULOffset[1] + " | ");
	System.out.println("ULyOffset: " + ULOffset[0]);
	System.out.print("LLxOffset: " + LLOffset[1] + " | ");
	System.out.println("LLyOffset: " + LLOffset[0]);
    int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
	
    if (playerSide == 'W' || playerSide == 'B') {
	  // Going -> Top Right
	  for(int i = 1; i < upperMax - (UROffset[0]); i++){
		for(int j = 1; j < rightMax - (UROffset[1]); j++){
		  if(j == i){
            coordinatesList.add(new int[] { x + j, y + i });
		  }
	    }
	  }
	  // Going -> Bottom Right
	  for(int i = 1; i < lowerMax - (LROffset[0]); i++){
		for(int j = 1; j < rightMax - (LROffset[1]); j++){
		  if(j == i){
            coordinatesList.add(new int[] { x + j, y - i });
		  }
	    }
	  }
	  // Going -> Top Left
	  for(int i = 1; i < upperMax - (ULOffset[0]); i++){
		for(int j = 1; j < leftMax - (ULOffset[1]); j++){
		  if(j == i){
            coordinatesList.add(new int[] { x - j, y + i });
		  }
	    }
	  }
	  // Going -> Bottom Left
	  for(int i = 1; i < lowerMax - (LLOffset[0]); i++){
		for(int j = 1; j < leftMax - (LLOffset[1]); j++){
		  if(j == i){
            coordinatesList.add(new int[] { x - j, y - i });
		  }
	    }
	  }
    }
	// ---- Black ----
	else{
	  
	}
    ResetOffsets();
	return coordinatesList;
  }

  public void Move(int x, int y) {
    System.out.println("Test");
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
  
  public void DetectSpecialMove(JPanel[][] playSquares, int x, int y, ChessPiece cp){
	if(playerSide == 'W' && cp.IsAlive()){
		// Front movement (Blocked)
	    if (cp.GetXCoord() == x && cp.GetYCoord() == (y + 1)) {
		  choices[0] = true;
		  isBlocked = true;
        }
	    // Diagonal right capture
        if (cp.GetXCoord() == (x + 1) && cp.GetYCoord() == (y + 1)
			&& cp.GetPlayerSide() == 'B') {
		  choices[1] = true;
        }
	    // Diagonal left capture
	    if (cp.GetXCoord() == (x - 1) && cp.GetYCoord() == (y + 1) 
			&& cp.GetPlayerSide() == 'B') {
		  choices[2] = true;
        }
		// En Passant right
		if (cp.GetXCoord() == (x + 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice() == true  
		    && cp.GetPlayerSide() == 'B') {
		  choices[3] = true;
		}
		//En Passant left
		if (cp.GetXCoord() == (x - 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice() == true  
		    && cp.GetPlayerSide() == 'B') {
		  choices[4] = true;
        }
		// ---- Black Side ----
	  } else if(playerSide == 'B' && cp.IsAlive()){
		// Front movement (Blocked)
	    if (cp.GetXCoord() == x && cp.GetYCoord() == (y - 1)) {
		  choices[0] = true;
		  isBlocked = true;
        }
		// Diagonal right capture
        if (cp.GetXCoord() == (x + 1) && cp.GetYCoord() == (y - 1) 
			&& cp.GetPlayerSide() == 'W') {
		  choices[1] = true;
        }
	    // Diagonal left capture
	    if (cp.GetXCoord() == (x - 1) && cp.GetYCoord() == (y - 1) 
			&& cp.GetPlayerSide() == 'W') {
		  choices[2] = true;
        }
		// En Passant right
		if (cp.GetXCoord() == (x + 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice() == true 
		&& cp.GetPlayerSide() == 'W') {
		  choices[3] = true;
		}
		//En Passant left
		if (cp.GetXCoord() == (x - 1) && cp.GetYCoord() == (y) && cp.IsMovedTwice() == true 
		&& cp.GetPlayerSide() == 'W') {
		  choices[4] = true;
	    }
	  }
	  
  }

  public ArrayList<int[]> PawnSpecialMove(JPanel[][] playSquares, int x, int y) {
	  
	ArrayList<int[]> SelectedCoordinatesList = ValidMoves(playSquares, x, y);
    if (playerSide == 'W') {
	  // Diagonal Right Capture
	  if(choices[1] == true && y < upperBound && x < rightBound){
		SelectedCoordinatesList.add(new int[] { x + 1, y + 1 });
	  }
	  // Diagonal Left Capture
	  if(choices[2] == true && y < upperBound && x > leftBound){
		SelectedCoordinatesList.add(new int[] { x - 1, y + 1 });
	  }
	  // En Passant Right
	  if(choices[3] == true && y < upperBound && x < rightBound){
		SelectedCoordinatesList.add(new int[] { x + 1, y + 1 });
	  }
	  // En Passant Left
	  if(choices[4] == true && y < upperBound && x > leftBound){
		SelectedCoordinatesList.add(new int[] { x - 1, y + 1 });
	  }
    } else {
	  // Diagonal Right Capture
	  if(choices[1] == true && y > lowerBound && x < rightBound){
		SelectedCoordinatesList.add(new int[] { x + 1, y - 1 });
	  }
	  // Diagonal Left Capture
	  if(choices[2] == true && y > lowerBound && x > leftBound){
		SelectedCoordinatesList.add(new int[] { x - 1, y - 1 });
	  }
	  // En Passant Right
	  if(choices[3] == true && y > lowerBound && x < rightBound){
		SelectedCoordinatesList.add(new int[] { x + 1, y - 1 });
	  }
	  // En Passant Left
	  if(choices[4] == true && y > lowerBound && x > leftBound){
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
	  if(y < upperBound && isBlocked == false){
		if(y == 2){
		  coordinatesList.add(new int[] { x, y + 2 });
		  coordinatesList.add(new int[] { x, y + 1 });
	    } else
		  coordinatesList.add(new int[] { x, y + 1 });
	  }
    } else {
	  if(y > lowerBound && isBlocked == false){
		if(y == 7){
          coordinatesList.add(new int[] { x, y - 2 });
	      coordinatesList.add(new int[] { x, y - 1 });
	    } else
          coordinatesList.add(new int[] { x, y - 1 });
	  }
    }
    return coordinatesList;
  }
  
  public void ResetChoices(){
	for(int i = 0; i < 5; i++){
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