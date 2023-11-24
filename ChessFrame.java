import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ChessFrame extends JFrame {

  private GridLayout chessBoard;
  Color cream = new Color(238, 238, 210);
  Color green = new Color(118, 150, 86);

  /*
   * -- Container for the in play chess pieces --
   * // Probably use a different data structure but arrays for now
   * // (maybe an ArrayList if sticking to arrays)
   */
  private ChessPiece[] ChessPieceContainer = new ChessPiece[32];
  /*
   * -- Will be used depending on the setup --
   * // 32 for DefaultGameSetup
   * // 6 = TestGameSetup
   */
  private static int numPieces;
  // < Insert captured pieces on white container here >
  // < Insert captured pieces on black container here >

  /*
   * -- ChessPiece Copy --
   * // selectedPiece is temporary, copiedPiece for multiple uses
   */
  private static ChessPiece copiedPiece = new NullPiece(1, 1, 'N');
  /*
   * -- Selected Indicator --
   * // Determines whether a move should be taken
   */
  private static boolean isSelected = false;
  /* Extracted coords from ChessPiece object */
  private static int xCoord;
  private static int yCoord;
  /* Valid moves from the selected piece */
  ArrayList<int[]> SelectedCoordinatesList;

  /* For any scanner input */
  Scanner userInput = new Scanner(System.in);
  /* For the board */
  private static JPanel[][] playSquare = new JPanel[8][8];

  public ChessFrame() {
    super("Chess by John and Harold");
    // Sets the grid layout to 8x8
    chessBoard = new GridLayout(8, 8);
    setLayout(chessBoard);
    CreateBoard();
  }

  // Sets up the board and places all of the pieces
  public void CreateBoard() {
    MouseHandler handler = new MouseHandler();
    // Reworked the board loops to support a 2D array
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        playSquare[i][j] = new JPanel();
        if ((j % 2 == 0) && (i % 2 == 0))
          playSquare[i][j].setBackground(cream);
        else if ((j % 2 != 0) && (i % 2 != 0))
          playSquare[i][j].setBackground(cream);
        else
          playSquare[i][j].setBackground(green);

        add(playSquare[i][j]);
        playSquare[i][j].addMouseListener(handler);
        playSquare[i][j].addMouseMotionListener(handler);
      }
    }

    DefaultGameSetup();
    // Sets up the board
    // TestGameSetup();
  }
  
  // To reset the JPanel Backgrounds
  private void ResetBoardBackground() {
	for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if ((j % 2 == 0) && (i % 2 == 0))
          playSquare[i][j].setBackground(cream);
        else if ((j % 2 != 0) && (i % 2 != 0))
          playSquare[i][j].setBackground(cream);
        else
          playSquare[i][j].setBackground(green);
      }
    }
  }

  // Initialize pieces on the board
  private void DefaultGameSetup() {
    numPieces = 32;
    /*
     * ---- Filling the ChessPieceContainer with pieces ----
     * // Probably use a data structure from Collections but arrays for now
     * // Each piece may need to have their coordinates compared with a set of
     * selected
     * // coordinates (and maybe symbol) to find the right piece when moving a piece
     */
    // ---- White ---- //
    ChessPieceContainer[0] = new King(5, 1, 'W');
    ChessPieceContainer[1] = new Queen(4, 1, 'W');
    ChessPieceContainer[2] = new Bishop(3, 1, 'W');
    ChessPieceContainer[3] = new Bishop(6, 1, 'W');
    ChessPieceContainer[4] = new Knight(2, 1, 'W');
    ChessPieceContainer[5] = new Knight(7, 1, 'W');
    ChessPieceContainer[6] = new Rook(1, 1, 'W');
    ChessPieceContainer[7] = new Rook(8, 1, 'W');
    for (int i = 0; i < 8; i++) {
      // Initialize pawns at indexes 8-15, convert indexes to "user input" indexes
      ChessPieceContainer[i + 8] = new Pawn((i + 1), 2, 'W');
    }

    // ---- Black ---- //
    ChessPieceContainer[16] = new King(5, 8, 'B');
    ChessPieceContainer[17] = new Queen(4, 8, 'B');
    ChessPieceContainer[18] = new Bishop(3, 8, 'B');
    ChessPieceContainer[19] = new Bishop(6, 8, 'B');
    ChessPieceContainer[20] = new Knight(2, 8, 'B');
    ChessPieceContainer[21] = new Knight(7, 8, 'B');
    ChessPieceContainer[22] = new Rook(1, 8, 'B');
    ChessPieceContainer[23] = new Rook(8, 8, 'B');
    for (int i = 0; i < 8; i++) {
      // Initialize pawns at indexes 8-15, convert indexes to "user input" indexes
      ChessPieceContainer[i + 24] = new Pawn((i + 1), 7, 'B');
    }
    /*
     * -- Initializes the display on the board --
     * A PlacePiece function may be added in place
     * An ArrayList may also be used instead of an array
     */
    for (int i = 0; i < numPieces; i++) {
      /*
       * EX for below method:
       * (1,1) gets taken in and is translated to be [7][0] in the array
       * where [7] is the i index (yCoord) and [0] is the j index (xCoord)
       */
      TranslateCoordinates(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord());
      // The JPanel adds a new JLabel that represents a piece at the selected
      // coordinates
      playSquare[yCoord][xCoord].add(SymbolToLabel(ChessPieceContainer[i]), BorderLayout.CENTER);
      // Experimental, may be unused
      ChessPieceContainer[i].SetJLabel(SymbolToLabel(ChessPieceContainer[i]));
    }
  }

  // Initialize specific pieces for testing purposes
  private void TestGameSetup() {
    // ** Needs to be modified per amount of content in the ChessPieceContainer **
    numPieces = 1;

    ChessPieceContainer[0] = new Rook(4, 4, 'W');

    /*
     * // ---- White ---- //
     * ChessPieceContainer[0] = new King(5,1, 'W');
     * ChessPieceContainer[1] = new Rook(1,1, 'W');
     * ChessPieceContainer[2] = new Rook(8,1, 'W');
     * 
     * // ---- Black ---- //
     * ChessPieceContainer[3] = new King(5,8, 'B');
     * ChessPieceContainer[4] = new Rook(1,8, 'B');
     * ChessPieceContainer[5] = new Rook(8,8, 'B');
     */

    // A PlacePiece function may be added in place, an ArrayList may also be used
    // instead of an array
    for (int i = 0; i < numPieces; i++) {
      TranslateCoordinates(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord());
      playSquare[yCoord][xCoord].add(SymbolToLabel(ChessPieceContainer[i]), BorderLayout.CENTER);
      // Experimental, may be unused
      ChessPieceContainer[i].SetJLabel(SymbolToLabel(ChessPieceContainer[i]));
    }
  }

  // Used for testing methods
  public void ChessMethodTester() {
    MovePiece(copiedPiece);
  }

  // (1,1) is the bottom left, (8,8) is the top left on the white side perspective
  // Text input only

  // Designed to be as compatible for different types of selection
  // selectPiece always generates a new copied piece based on the recent selection
  public void selectPiece(int x, int y) {
    // A copiedPiece needs to be passed through several methods without being needed
    // as a parameter
    copiedPiece = new NullPiece(1, 1, 'N');
    TranslateCoordinates(x, y);
    for (int i = 0; i < numPieces; i++) {
      if (x == ChessPieceContainer[i].GetXCoord() && y == ChessPieceContainer[i].GetYCoord()) {
        // From java.lang.Class<T>, using .getClass().getName()
        // JOptionPane.showMessageDialog(null, "Selected Piece: " +
        // ChessPieceContainer[i].getClass().getName());
        copiedPiece = ChessPieceContainer[i];
        // index = i;
        isSelected = true;
        ShowValidMoves(copiedPiece);
      }
    }
  }

  // Displays the valid moves on the board
  public void ShowValidMoves(ChessPiece selectedPiece) {
    // Work in progress
    int pivotxCoord = copiedPiece.GetXCoord();
    int pivotyCoord = copiedPiece.GetYCoord();
    ArrayList<int[]> coordinatesList = copiedPiece.ValidMoves(playSquare, pivotxCoord, pivotyCoord);

    // Resets the background of each square, so that the moves reset when
    // deselecting a piece
    ResetBoardBackground();

    Color validMoveColor = new Color(255, 255, 0);
    for (int[] coordinates : coordinatesList) {
      playSquare[8 - coordinates[1]][coordinates[0] - 1].setBackground(validMoveColor);
    }

  }

  public void MakeMove(int x, int y, int newX, int newY) {
    copiedPiece.SetYCoord(newY);
    copiedPiece.SetXCoord(newX);
    UpdatePieces();
  }

  public void MovePiece(ChessPiece selectedPiece) {
    selectedPiece.Move(selectedPiece.GetXCoord(), selectedPiece.GetYCoord());

    UpdatePieces();
  }

  // Needed to update the board after a piece is moved
  public void UpdatePieces() {
    // Reset all current JPanels
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        playSquare[i][j].removeAll();
        playSquare[i][j].repaint();
      }
    }

    // May add an if statement to detect if a piece is captured or not to not draw
    // it on the board
    for (int i = 0; i < numPieces; i++) {
      TranslateCoordinates(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord());
      playSquare[yCoord][xCoord].add(SymbolToLabel(ChessPieceContainer[i]), BorderLayout.CENTER);
      // Experimental, may be unused
      ChessPieceContainer[i].SetJLabel(SymbolToLabel(ChessPieceContainer[i]));
    }

    // Update any changes
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        playSquare[i][j].validate();
      }
    }
  }

  // Assuming the orientation is not flipped for now
  // A flip indicator will be needed later
  // Will be reusable later
  private void TranslateCoordinates(int x, int y) {
    // Non-flipped
    // Shift the coordinate down by one to fit in the 2D array
    int translatedXCoord = (x - 1);
    xCoord = translatedXCoord;

    // Shift the coordinate down by one to fit in the 2D array
    int translatedYCoord = (y - 1);
    // Flip the coordinate to ensure untranslated y value 1 starts at the bottom
    yCoord = (7 - translatedYCoord);
  }

  // Use the built in symbol from a piece object to find the correct image to
  // represent that piece
  private JLabel SymbolToLabel(ChessPiece selectedPiece) {
    char symbol = selectedPiece.GetSymbol();
    char playerSide = selectedPiece.GetPlayerSide();
    ImageIcon chessPiece = new ImageIcon();
    JLabel chessPieceLabel;

    if (playerSide == 'W') {
      switch (symbol) {
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
    } else if (playerSide == 'B') {
      switch (symbol) {
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

  private class MouseHandler implements MouseListener, MouseMotionListener {
    public void mouseDragged(MouseEvent e) {
      // TODO Auto-generated method stub
    }

    public void mouseMoved(MouseEvent e) {
      // TODO Auto-generated method stub
    }

    public void mouseClicked(MouseEvent e) {
      if (isSelected == false) {
        for (int i = 0; i < 8; i++)
          for (int j = 0; j < 8; j++)
            if (e.getSource() == playSquare[i][j]) {
              selectPiece(j + 1, 8 - i);
              isSelected = true;
              SelectedCoordinatesList = copiedPiece.ValidMoves(playSquare, j + 1, 8 - i);
            }
      } else {
        isSelected = false;
        for (int i = 0; i < 8; i++)
          for (int j = 0; j < 8; j++)
            if (e.getSource() == playSquare[i][j]) {
              for (int[] coordinates : SelectedCoordinatesList) {
                if (j + 1 == coordinates[0] && 8 - i == coordinates[1]) {
                  System.out.println("Before Move: x = " + copiedPiece.GetXCoord() + ", y = " + copiedPiece.GetYCoord());
                  MakeMove(copiedPiece.GetXCoord(), copiedPiece.GetYCoord(), j + 1,8 -i);
                  System.out.println("NEw X should be: " + (j + 1)+ "New y should be: " + (8 - i));
                  System.out.println("After Move: x = " + copiedPiece.GetXCoord() + ", y = " + copiedPiece.GetYCoord());
				  ResetBoardBackground();
                }
              }
            }

      }
    }

    public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub
    }

    public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub
    }

    public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub
    }

    public void mouseExited(MouseEvent e) {
    }
  }
}