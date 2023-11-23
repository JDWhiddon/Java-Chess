import java.awt.*;
import javax.swing.*;

public class ChessFrame extends JFrame {

  private GridLayout chessBoard;
  Color cream = new Color(238, 238, 210);
  Color green = new Color(118, 150, 86);
  
  // Probably use a different data structure but arrays for now
  // (maybe an ArrayList if sticking to arrays)
  // Container for the in play chess pieces
  private ChessPiece[] ChessPieceContainer = new ChessPiece[32];
  // < Insert captured pieces on white container here >
  // < Insert captured pieces on black container here >
  
  // Extracted coords from piece object
  private static int xCoord;
  private static int yCoord;

  public ChessFrame() {
    super("Chess by John and Harold");
    // Sets the grid layout to 8x8
    chessBoard = new GridLayout(8, 8);
    setLayout(chessBoard);
    CreateBoard();

  }

  // Sets up the board and places all of the pieces
  public void CreateBoard() {
    // Creates the grid, starting with a white square at the top left
	// Each individual square can be modified
	JPanel[][] playSquare = new JPanel[8][8];
	
	// Reworked the board loops to support a 2D array
	for (int i = 0; i < 8; i++) {
	  for (int j = 0; j < 8; j++) {
		playSquare[i][j] = new JPanel();
		if((j % 2 == 0) && (i % 2 == 0))
		  playSquare[i][j].setBackground(cream); 
		else if((j % 2 != 0) && (i % 2 != 0))
		  playSquare[i][j].setBackground(cream);
		else
		  playSquare[i][j].setBackground(green);
		
		add(playSquare[i][j]);
		BorderLayout layout = new BorderLayout(5,5);
		playSquare[i][j].setLayout(layout);
	  }
	}
	
	// Sets up the board
	DefaultGameSetup(playSquare);
	
  }
  
  // Initialize pieces on the board
  private void DefaultGameSetup(JPanel[][] boardCopy){
	// Probably use a data structure from Collections but arrays for now
	// Each piece may need to have their coordinates compared with a set of selected
	// coordinates (and maybe symbol) to find the right piece when moving a piece
	// ---- White ---- //
	ChessPieceContainer[0] = new King(5,1, 'W');
	ChessPieceContainer[1] = new Queen(4,1, 'W');
	ChessPieceContainer[2] = new Bishop(3,1, 'W');
	ChessPieceContainer[3] = new Bishop(6,1, 'W');
	ChessPieceContainer[4] = new Knight(2,1, 'W');
	ChessPieceContainer[5] = new Knight(7,1, 'W');
	ChessPieceContainer[6] = new Rook(1,1, 'W');
	ChessPieceContainer[7] = new Rook(8,1, 'W');
	for(int i = 0; i < 8; i++){
	  //Initialize pawns at indexes 8-15, convert indexes to "user input" indexes
	  ChessPieceContainer[i + 8] = new Pawn((i + 1), 2, 'W');
	}
	
	// ---- Black ---- //
	ChessPieceContainer[16] = new King(5,8, 'B');
	ChessPieceContainer[17] = new Queen(4,8, 'B');
	ChessPieceContainer[18] = new Bishop(3,8, 'B');
	ChessPieceContainer[19] = new Bishop(6,8, 'B');
	ChessPieceContainer[20] = new Knight(2,8, 'B');
	ChessPieceContainer[21] = new Knight(7,8, 'B');
	ChessPieceContainer[22] = new Rook(1,8, 'B');
	ChessPieceContainer[23] = new Rook(8,8, 'B');
	for(int i = 0; i < 8; i++){
	  //Initialize pawns at indexes 8-15, convert indexes to "user input" indexes
	  ChessPieceContainer[i + 24] = new Pawn((i + 1), 7, 'B');
	}
	
	// A PlacePiece function may be added in place, an ArrayList may also be used instead of an array
	for(int i = 0; i < 32; i++){
	  TranslateCoordinates(ChessPieceContainer[i]);
	  boardCopy[yCoord][xCoord].add(SymbolToLabel(ChessPieceContainer[i]), BorderLayout.CENTER);
	}
  }
  
  
  // Assuming the orientation is not flipped for now
  // A flip indicator will be needed later
  // Will be reusable later 
  private void TranslateCoordinates(ChessPiece selectedPiece)
  {
	// Non-flipped
	// Shift the coordinate down by one to fit in the 2D array
	int translatedXCoord = (selectedPiece.GetXCoord() - 1);
	xCoord = translatedXCoord;
	
	// Shift the coordinate down by one to fit in the 2D array
	int translatedYCoord = (selectedPiece.GetYCoord() - 1);
	// Flip the coordinate to ensure untranslated y value 1 starts at the bottom
	yCoord = (7 - translatedYCoord);
  }
  
  // Use the built in symbol from a piece object to find the correct image to represent that piece  
  private JLabel SymbolToLabel(ChessPiece selectedPiece){
	char symbol = selectedPiece.GetSymbol();
	char playerSide = selectedPiece.GetPlayerSide();
	ImageIcon chessPiece = new ImageIcon();
	JLabel chessPieceLabel;
	
	if(playerSide == 'W'){
	  switch(symbol){
		case 'K':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/WKing.png"));
		  break;
		case 'Q':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/WQueen.png"));
		  break;
		case 'N':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/WKnight.png"));
		  break;
		case 'R':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/WRook.png"));
		  break;
		case 'B':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/WBishop.png"));
		  break;
		case 'P':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/WPawn.png"));
		  break;
		}
	}else if(playerSide == 'B'){
	  switch(symbol){
		case 'K':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/BKing.png"));
		  break;
		case 'Q':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/BQueen.png"));
		  break;
		case 'N':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/BKnight.png"));
		  break;
		case 'R':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/BRook.png"));
		  break;
		case 'B':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/BBishop.png"));
		  break;
		case 'P':
		  chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/BPawn.png"));
		  break;
		}
	}
	chessPieceLabel = new JLabel(chessPiece);
	return chessPieceLabel;
  }
}
