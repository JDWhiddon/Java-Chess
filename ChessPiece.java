import java.awt.*;
import javax.swing.*;

public abstract class ChessPiece{
  // Current x and y coords
  protected int xCoord;
  protected int yCoord;
  protected char symbol = ' ';
  protected char playerSide = ' ';
  // May mess with the JLabels in each specific JPanel later
  protected JLabel jlabelCopy;

  public ChessPiece(){
  }

  public ChessPiece(int x, int y, char ps){
    xCoord = x;
    yCoord = y;
	playerSide = ps;
  }

  public int GetXCoord(){
	return xCoord;
  }

  public int GetYCoord(){
	return yCoord;
  }
  
  public char GetPlayerSide(){
	return playerSide;
  }

  public abstract char GetSymbol();

  public void SetXCoord(int x){
	xCoord = x;
  }

  public void SetYCoord(int y){
	yCoord = y;
  }
  
  public void SetPlayerSide(char s){
	playerSide = s;
  }
  
  public void SetJLabel(JLabel label){
	jlabelCopy = label;
  }
  
  // Reads in the current xCoord and yCoord and calculates the maximum distance per move
  // or the valid positions if applicable
  public abstract void ValidMoves(int x, int y);
}

// Derived classes temporarily here for now
class King extends ChessPiece{
  private char symbol = 'K';
	
  public King(int x, int y, char ps){
	super(x,y,ps);
  }
	
  public char GetSymbol(){
	return symbol;
  }
  
  public void ValidMoves(int x, int y){
	System.out.println("Test");
  }
}

class Queen extends ChessPiece{
  private char symbol = 'Q';
	
  public Queen(int x, int y, char ps){
	super(x,y, ps);
  }
	
  public char GetSymbol(){
	return symbol;
  }
  
  public void ValidMoves(int x, int y){
	System.out.println("Test");
  }
}

// The Rook will be implemented first
class Rook extends ChessPiece{
  private char symbol = 'R';
  private int maxMovement = 7;
	
  public Rook(int x, int y, char ps){
	super(x,y, ps);
  }
	
  public char GetSymbol(){
	return symbol;
  }
  
  // Reads in the current xCoord and yCoord and calculates the maximum distance per move
  public void ValidMoves(int x, int y){
	xCoord = x;
	yCoord = y;
  }
}

class Bishop extends ChessPiece{
  private char symbol = 'B';
	
  public Bishop(int x, int y, char ps){
	super(x,y, ps);
  }
	
  public char GetSymbol(){
	return symbol;
  }
  
  public void ValidMoves(int x, int y){
	System.out.println("Test");
  }
}

class Knight extends ChessPiece{
  private char symbol = 'N';
	
  public Knight(int x, int y, char ps){
	super(x,y, ps);
  }
	
  public char GetSymbol(){
	return symbol;
  }
  
  public void ValidMoves(int x, int y){
	System.out.println("Test");
  }
}

class Pawn extends ChessPiece{
  private char symbol = 'P';
	
  public Pawn(int x, int y, char ps){
	super(x,y, ps);
  }
	
  public char GetSymbol(){
	return symbol;
  }
  
  public void ValidMoves(int x, int y){
	System.out.println("Test");
  }
}

class NullPiece extends ChessPiece{
  private char symbol = 'O';
	
  public NullPiece(int x, int y, char ps){
	super(x,y,ps);
  }
	
  public char GetSymbol(){
	return symbol;
  }
  
  public void ValidMoves(int x, int y){
	System.out.println("Test");
  }
}