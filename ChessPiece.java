

public abstract class ChessPiece{
  // Current x and y coords
  protected int xCoord;
  protected int yCoord;
  protected char symbol = ' ';
  protected char playerSide = ' ';

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
}

class Queen extends ChessPiece{
  private char symbol = 'Q';
	
  public Queen(int x, int y, char ps){
	super(x,y, ps);
  }
	
  public char GetSymbol(){
	return symbol;
  }
}

// The Rook will be implemented first
class Rook extends ChessPiece{
  private char symbol = 'R';
	
  public Rook(int x, int y, char ps){
	super(x,y, ps);
  }
	
  public char GetSymbol(){
	return symbol;
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
}

class Knight extends ChessPiece{
  private char symbol = 'N';
	
  public Knight(int x, int y, char ps){
	super(x,y, ps);
  }
	
  public char GetSymbol(){
	return symbol;
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
}