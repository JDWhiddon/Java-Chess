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

  public abstract void ValidMoves(int x, int y);

  public abstract void Move(int x, int y);

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

  public void RemovePiece() {
    isAlive = false;
  }

  public boolean IsAlive() {
    return isAlive;
  }
}

// Derived classes temporarily here for now
class King extends ChessPiece {
  private char symbol = 'K';

  public King(int x, int y, char ps) {
    super(x, y, ps);
  }

  public char GetSymbol() {
    return symbol;
  }

  public void ValidMoves(int x, int y) {
    System.out.println("Test");
  }

  public void Move(int x, int y) {
    System.out.println("Test");
  }
}

class Queen extends ChessPiece {
  private char symbol = 'Q';

  public Queen(int x, int y, char ps) {
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
    return coordinatesList;
  }

  public void ValidMoves(int x, int y) {
    System.out.println("Test");
  }

  public void Move(int x, int y) {
    System.out.println("Test");
  }
}

class Rook extends ChessPiece {
  private char symbol = 'R';
  private int maxMovement = 7;

  public Rook(int x, int y, char ps) {
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
    } 
    return coordinatesList;
  }

  // Reads in the current xCoord and yCoord and calculates the maximum distance
  // per move
  public void ValidMoves(int x, int y) {
    /* xCoord = x;
       yCoord = y;
       Example plan for a Rook at (4,4)
       Max movement is 7 at any given direction
       E: (1,4); xCoord change: 4 - 1 (left max); Moves 3 left maximum
       W: (8,4); xCoord change: (8 - 1) (right max) - 3; Moves 4 right maximum
       N: (4,1); yCoord change: 4 - 1 (bottom max); Moves 3 down maximum
       S: (4,8); yCoord change: (8 - 1) (top max) - 3; Moves 4 up maximum
       int leftBound = 1;
       int rightBound = 7;
       int lowerBound = 1;
       int upperBound = 7;
       int leftMax = x - leftBound;
       int rightMax = rightBound - x;
       int lowerMax = y - lowerBound;
       int upperMax = upperBound - y; */
  }

  public void Move(int x, int y) {
    String directionMoved = JOptionPane.showInputDialog("Select direction to move: ");
    String amountInput = JOptionPane.showInputDialog("Select amount to move (1-7): ");
    int amountMoved = Integer.parseInt(amountInput);

    int leftBound = 1;
    int rightBound = 7;
    int lowerBound = 1;
    int upperBound = 7;
    int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;

    JOptionPane.showMessageDialog(null, "Old x Coord: " + xCoord + " | Old y Coord: " + yCoord);

    switch (directionMoved) {
      case "N":
        if (amountMoved <= upperMax) {
          yCoord += amountMoved;
        }
        break;
      case "S":
        if (amountMoved <= lowerMax) {
          yCoord -= amountMoved;
        }
        break;
      case "E":
        if (amountMoved <= rightMax) {
          xCoord += amountMoved;
        }
        break;
      case "W":
        if (amountMoved <= leftMax) {
          xCoord -= amountMoved;
        }
        break;
    }
    JOptionPane.showMessageDialog(null, "New x Coord: " + xCoord + " | New y Coord: " + yCoord);
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
    return coordinatesList;
  }

  public void ValidMoves(int x, int y) {
    System.out.println("Test");
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
    int leftMax = x - leftBound;
    int rightMax = rightBound - x;
    int lowerMax = y - lowerBound;
    int upperMax = upperBound - y;
	
    if (playerSide == 'W') {
	  // Going -> 2 Top + 1 Direction
	  for(int i = 1; i <= upperMax; i++){
        coordinatesList.add(new int[] { x, y + i});
	  }
	  // Going -> 2 Right + 1 Direction
	  for(int i = 1; i <= lowerMax; i++){
        coordinatesList.add(new int[] { x, y - i});
	  }
	  // Going -> 2 Bottom + 1 Direction
	  for(int i = 1; i <= rightMax; i++){
        coordinatesList.add(new int[] { x + i, y });
	  }
	 // Going -> 2 Left + 1 Direction
	  for(int i = 1; i <= leftMax; i++){
        coordinatesList.add(new int[] { x - i, y });
	  }
    } 
    return coordinatesList;
  }

  public void ValidMoves(int x, int y) {
    System.out.println("Test");
  }

  public void Move(int x, int y) {
    System.out.println("Test");
  }
}

class Pawn extends ChessPiece {
  private char symbol = 'P';

  public Pawn(int x, int y, char ps) {
    super(x, y, ps);
  }

  public char GetSymbol() {
    return symbol;
  }

  public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
    ArrayList<int[]> coordinatesList = new ArrayList<>();
    if (playerSide == 'W') {
      coordinatesList.add(new int[] { x, y + 1 });
      coordinatesList.add(new int[] { x, y + 2 });
    } else {
      coordinatesList.add(new int[] { x, y - 1 });
      coordinatesList.add(new int[] { x, y - 2 });
    }
    return coordinatesList;
  }

  public void ValidMoves(int x, int y) {
    System.out.println("Test");
  }

  public void Move(int x, int y) {
    System.out.println("Test");
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

  public void ValidMoves(int x, int y) {
    System.out.println("Test");
  }

  public void Move(int x, int y) {
    System.out.println("Test");
  }
}