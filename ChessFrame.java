import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.channels.OverlappingFileLockException;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChessFrame extends JFrame {
  // -------- Variables -------- //

  private GridLayout chessBoard;
  Color cream = new Color(238, 238, 210);
  Color green = new Color(118, 150, 86);
  Color validMoveColor = new Color(255, 255, 0);

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
  private int numPieces;
  // < Insert captured pieces on white container here >
  // < Insert captured pieces on black container here >

  /*
   * -- ChessPiece Copy --
   * // selectedPiece is temporary, copiedPiece for multiple uses
   */
  private ChessPiece copiedPiece = new NullPiece(1, 1, 'N');
  /*
   * -- Selected Indicator --
   * // Determines whether a move should be taken
   */
  private boolean isSelected = false;
  /* Extracted coords from ChessPiece object */
  private int xCoord;
  private int yCoord;
  /* Valid moves from the selected piece */
  ArrayList<int[]> SelectedCoordinatesList;

  /* For any scanner input */
  Scanner userInput = new Scanner(System.in);
  /* For the board */
  private JPanel[][] playSquare = new JPanel[8][8];

  // Turn Counter
  private int turnCount = 1;

  // -------- Methods -------- //

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

    // Sets up the board
    DefaultGameSetup();
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
     * Probably use a data structure from Collections but arrays for now
     * Each piece may need to have their coordinates compared with a set of selected
     * coordinates (and maybe symbol) to find the right piece when moving a piece
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
    numPieces = 22;

    // ---- White ---- //
    ChessPieceContainer[0] = new King(5, 1, 'W');
    ChessPieceContainer[1] = new Rook(1, 1, 'W');
    ChessPieceContainer[2] = new Rook(8, 1, 'W');
    for (int i = 0; i < 8; i++) {
      // Initialize pawns at indexes 8-15, convert indexes to "user input" indexes
      ChessPieceContainer[i + 3] = new Pawn((i + 1), 2, 'W');
    }

    // ---- Black ---- //
    ChessPieceContainer[11] = new King(5, 8, 'B');
    ChessPieceContainer[12] = new Rook(1, 8, 'B');
    ChessPieceContainer[13] = new Rook(8, 8, 'B');
    for (int i = 0; i < 8; i++) {
      // Initialize pawns at indexes 8-15, convert indexes to "user input" indexes
      ChessPieceContainer[i + 14] = new Pawn((i + 1), 7, 'B');
    }

    // A PlacePiece function may be added in place, an ArrayList may also be used
    // instead of an array
    for (int i = 0; i < numPieces; i++) {
      TranslateCoordinates(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord());
      playSquare[yCoord][xCoord].add(SymbolToLabel(ChessPieceContainer[i]), BorderLayout.CENTER);
    }
  }

  // Designed to be as compatible for different types of selection
  // selectPiece always generates a new copied piece based on the recent selection
  // Triggered by clicks
  public void selectPiece(int x, int y) {
    // A copiedPiece needs to be passed through several methods without being needed
    // as a parameter
    copiedPiece = new NullPiece(1, 1, 'N');
    TranslateCoordinates(x, y);
    for (int i = 0; i < numPieces; i++) {
      if (x == ChessPieceContainer[i].GetXCoord() && y == ChessPieceContainer[i].GetYCoord()
          && ChessPieceContainer[i].IsAlive()) {
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
  // Triggered by clicks
  public void ShowValidMoves(ChessPiece selectedPiece) {
    int pivotxCoord = copiedPiece.GetXCoord();
    int pivotyCoord = copiedPiece.GetYCoord();
    boolean overrideValidMove = false;
    ArrayList<int[]> coordinatesList = copiedPiece.ValidMoves(playSquare, pivotxCoord, pivotyCoord);

    // Resets the background of each square, so that the moves reset when
    // deselecting a piece
    ResetBoardBackground();

    // ---- Piece Selection ---- //
    // From java.lang.Class<T>, using .getClass().getName()
    switch (copiedPiece.getClass().getName()) {
      case "Pawn":
        coordinatesList = ModifyPawnMovement(pivotxCoord, pivotyCoord);
        System.out.println("Pawn ");
        break;
      case "Rook":
        coordinatesList = ModifyRookMovement(pivotxCoord, pivotyCoord);
        System.out.println("Rook ");
        break;
      case "Bishop":
        coordinatesList = ModifyBishopMovement(pivotxCoord, pivotyCoord);
        System.out.println("Bishop ");
        break;
      case "Knight":
        coordinatesList = ModifyKnightMovement(pivotxCoord, pivotyCoord);
        System.out.println("Knight ");
        break;
      case "Queen":
        coordinatesList = ModifyQueenMovement(pivotxCoord, pivotyCoord);
        System.out.println("Queen ");
        break;
      case "King":
        coordinatesList = ModifyKingMovement(copiedPiece.GetPlayerSide(), ChessPieceContainer, playSquare);
        System.out.println("King ");
        break;
    }

    SelectedCoordinatesList = coordinatesList;

    for (int[] coordinates : coordinatesList) {
      playSquare[8 - coordinates[1]][coordinates[0] - 1].setBackground(validMoveColor);
    }
  }

  // -------- Valid Move Helper Functions -------- //
  // Shows valid pawn movement based on the position of other pieces
  // May integrate into the pawn class if possible
  public ArrayList<int[]> ModifyPawnMovement(int x, int y) {
    // To access pawn only methods
    Pawn copiedPawn = (Pawn) copiedPiece;
    ArrayList<int[]> coordinatesList = new ArrayList<>();

    for (int i = 0; i < numPieces; i++) {
      copiedPawn.DetectSpecialMove(playSquare, x, y, ChessPieceContainer[i]);
    }

    coordinatesList = copiedPawn.PawnSpecialMove(playSquare, x, y);

    return coordinatesList;
  }

  public ArrayList<int[]> ModifyRookMovement(int x, int y) {
    // To access Rook only methods
    Rook copiedRook = (Rook) copiedPiece;
    ArrayList<int[]> coordinatesList = new ArrayList<>();

    for (int i = 0; i < numPieces; i++) {
      copiedRook.DetectCollision(playSquare, x, y, ChessPieceContainer[i]);
    }

    coordinatesList = copiedRook.ValidMoves(playSquare, x, y);

    return coordinatesList;
  }

  public ArrayList<int[]> ModifyBishopMovement(int x, int y) {
    // To access Bishop only methods
    Bishop copiedBishop = (Bishop) copiedPiece;
    ArrayList<int[]> coordinatesList = new ArrayList<>();

    for (int i = 0; i < numPieces; i++) {
      // copiedBishop.DetectCollision(playSquare, x, y, ChessPieceContainer[i]);
    }

    coordinatesList = copiedBishop.ValidMoves(playSquare, x, y);

    return coordinatesList;
  }

  public ArrayList<int[]> ModifyKnightMovement(int x, int y) {
    // To access Bishop only methods
    Knight copiedKnight = (Knight) copiedPiece;
    ArrayList<int[]> coordinatesList = new ArrayList<>();

    for (int i = 0; i < numPieces; i++) {
      // copiedKnight.DetectCollision(playSquare, x, y, ChessPieceContainer[i]);
    }

    coordinatesList = copiedKnight.ValidMoves(playSquare, x, y);

    return coordinatesList;
  }

  public ArrayList<int[]> ModifyQueenMovement(int x, int y) {
    // To access Queen only methods
    Queen copiedQueen = (Queen) copiedPiece;
    ArrayList<int[]> coordinatesList = new ArrayList<>();

    for (int i = 0; i < numPieces; i++) {
      copiedQueen.DetectCollision(playSquare, x, y, ChessPieceContainer[i]);
    }

    coordinatesList = copiedQueen.ValidMoves(playSquare, x, y);

    return coordinatesList;
  }

  public ArrayList<int[]> ModifyKingMovement(char playerSide, ChessPiece[] ChessPieceContainer, JPanel[][] playSqaure) {
    ArrayList<int[]> opponentsMoves = new ArrayList<>();
    ArrayList<int[]> KingMoves = new ArrayList<>();
    // Adds all of their moves to an ArrayList
    for (int i = 0; i < 32; i++) {
      if (ChessPieceContainer[i].GetSymbol() == 'K' && ChessPieceContainer[i].GetPlayerSide() == playerSide) {
        // If this is our king
        KingMoves = ChessPieceContainer[i].ValidMoves(playSquare, ChessPieceContainer[i].GetXCoord(),
            ChessPieceContainer[i].GetYCoord());
      } else if (ChessPieceContainer[i].IsAlive() && ChessPieceContainer[i].GetPlayerSide() != playerSide) {
        // If the opponent's chess piece is alive
        ArrayList<int[]> tempMoves = ChessPieceContainer[i].ValidMoves(playSquare, ChessPieceContainer[i].GetXCoord(),
            ChessPieceContainer[i].GetYCoord());
        for (int[] moves : tempMoves) {
          int x = moves[0];
          int y = moves[1];
          opponentsMoves.add(new int[] { x, y });
        }
      }
    }
    ArrayList<int[]> AvailableMoves = new ArrayList<>();
    for (int[] moves : KingMoves) {
      int kingX = moves[0];
      int kingY = moves[1];

      boolean shouldAdd = true;

      // Remove all the moves that would put it in check
      for (int[] theirMoves : opponentsMoves) {
        int theirX = theirMoves[0];
        int theirY = theirMoves[1];
        if (kingX == theirX && kingY == theirY) {
          // Move shouldnt be added to the available moves arr
          shouldAdd = false;
        }
      }
      if (shouldAdd == true)
        AvailableMoves.add(new int[] { kingX, kingY });
    }
    return AvailableMoves;
  }

  // ------------ Move Functions ------------ //
  public void MakeMove(int x, int y, int newX, int newY) {

    boolean canMove = MoveValidator(x, y, newX, newY);

    if (canMove == true) {
      copiedPiece.SetYCoord(newY);
      copiedPiece.SetXCoord(newX);
    }
    UpdatePieces();
  }

  // -------- Move Helper Functions -------- //
  // Determines an action for a move
  private boolean MoveValidator(int x, int y, int newX, int newY) {
    boolean canMove = true;
    for (int i = 0; i < numPieces; i++) {
      // Checks to see if the piece moved on top of another, and removes it if so
      if (ChessPieceContainer[i].GetXCoord() == newX && ChessPieceContainer[i].GetYCoord() == newY
          && ChessPieceContainer[i].IsAlive()) {
        if (ChessPieceContainer[i].GetPlayerSide() != copiedPiece.GetPlayerSide()) {
          ChessPieceContainer[i].RemovePiece();
          System.out.println("Piece taken!");
        } else {
          canMove = false;
        }
      }
    }
    if (copiedPiece.getClass().getName() == "Pawn") {
      // Special cases
      // EnPassant(x, y, newX, newY);
      copiedPiece.SetYCoord(newY);
      copiedPiece.SetXCoord(newX);
      CheckForPromotion(newX, newY);
    }
    return canMove;
  }

  public void CheckForPromotion(int x, int y) {
    // If a pawn is on the bottom row, show white promotion box
    ImageIcon queen = new ImageIcon();
    ImageIcon knight = new ImageIcon();
    ImageIcon bishop = new ImageIcon();
    ImageIcon rook = new ImageIcon();
    if (y == 8) {
      queen = new ImageIcon(ChessFrame.class.getResource("Resources/WQueen.png"));
      knight = new ImageIcon(ChessFrame.class.getResource("Resources/WKnight.png"));
      bishop = new ImageIcon(ChessFrame.class.getResource("Resources/WBishop.png"));
      rook = new ImageIcon(ChessFrame.class.getResource("Resources/WRook.png"));
    }
    // If a pawn in on the top row, show black promotion box
    else if (y == 1) {
      queen = new ImageIcon(ChessFrame.class.getResource("Resources/BQueen.png"));
      knight = new ImageIcon(ChessFrame.class.getResource("Resources/BKnight.png"));
      bishop = new ImageIcon(ChessFrame.class.getResource("Resources/BBishop.png"));
      rook = new ImageIcon(ChessFrame.class.getResource("Resources/BRook.png"));
    }
    if (y == 1 || y == 8) {
      Object[] promotionOptions = { queen, knight, bishop, rook };
      int n = JOptionPane.showOptionDialog(this, "You got promoted! Choose a piece to change your pawn to.",
          "Pawn promotion", JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE, null, promotionOptions, queen);
      // If the user closes out of the window without picking, choose queen
      if (n == -1)
        n = 0;
      int index = -1;
      for (int i = 0; i < numPieces; i++) {
        if (ChessPieceContainer[i].GetXCoord() == x && ChessPieceContainer[i].GetYCoord() == y
            && ChessPieceContainer[i].IsAlive()) {
          index = i;
          break;
        }
      }
      // Remove the old pawn
      ChessPieceContainer[index].RemovePiece();
      switch (n) {
        // Queen
        case 0:
          ChessPieceContainer[index] = new Queen(x, y, copiedPiece.GetPlayerSide());
          break;
        case 1:
          ChessPieceContainer[index] = new Knight(x, y, copiedPiece.GetPlayerSide());
          break;
        case 2:
          ChessPieceContainer[index] = new Bishop(x, y, copiedPiece.GetPlayerSide());
          break;
        case 3:
          ChessPieceContainer[index] = new Rook(x, y, copiedPiece.GetPlayerSide());
          break;
        default:
          break;

      }
    }
  }

  public void EnPassant(int x, int y, int newX, int newY) {

    // To access pawn only methods
    Pawn copiedPawn = (Pawn) copiedPiece;

    // If a pawn has moved twice
    if ((copiedPawn.GetYCoord() == 2 && newY == 4) || (copiedPawn.GetYCoord() == 7 && newY == 5)) {
      copiedPawn.MoveTwice(true);
    }

    for (int i = 0; i < numPieces; i++) {
      // En Passant Right
      if (ChessPieceContainer[i].GetXCoord() == (x + 1) && ChessPieceContainer[i].GetYCoord() == (y)
          && ChessPieceContainer[i].IsAlive() && ChessPieceContainer[i].IsMovedTwice() == true) {
        if (ChessPieceContainer[i].GetPlayerSide() != copiedPawn.GetPlayerSide()) {
          ChessPieceContainer[i].RemovePiece();
          System.out.println("Piece taken!");
        }
        // En Passant left
      } else if (ChessPieceContainer[i].GetXCoord() == (x - 1) && ChessPieceContainer[i].GetYCoord() == (y)
          && ChessPieceContainer[i].IsAlive() && ChessPieceContainer[i].IsMovedTwice() == true) {
        if (ChessPieceContainer[i].GetPlayerSide() != copiedPawn.GetPlayerSide()) {
          ChessPieceContainer[i].RemovePiece();
          System.out.println("Piece taken!");
        }
      }
    }

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
      if (ChessPieceContainer[i].IsAlive()) {
        TranslateCoordinates(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord());
        playSquare[yCoord][xCoord].add(SymbolToLabel(ChessPieceContainer[i]), BorderLayout.CENTER);
      }
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
            // ---- Select ----
            if (e.getSource() == playSquare[i][j]) {
              selectPiece(j + 1, 8 - i);
              isSelected = true;
            }
        // ---- Move/Deselect ---- //
        // If the piece is already selected
        // Uses a copied piece to represent a piece
      } else {
        isSelected = false;
        for (int i = 0; i < 8; i++)
          for (int j = 0; j < 8; j++)
            if (e.getSource() == playSquare[i][j]) {
              for (int[] coordinates : SelectedCoordinatesList) {
                if (j + 1 == coordinates[0] && 8 - i == coordinates[1]) {
                  MakeMove(copiedPiece.GetXCoord(), copiedPiece.GetYCoord(), j + 1, 8 - i);
                  ResetBoardBackground();
                  UpdatePieces();
                  // ---- Deselect ----
                } else if (j + 1 == copiedPiece.GetXCoord() && 8 - i == copiedPiece.GetYCoord()) {
                  ResetBoardBackground();
                  UpdatePieces();
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