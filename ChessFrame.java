import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Array;
import java.nio.channels.OverlappingFileLockException;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

public class ChessFrame extends JFrame {
  // -------- Variables -------- //

  private GridLayout chessBoard;
  Color cream = new Color(238, 238, 210);
  Color green = new Color(118, 150, 86);
  Color validMoveColor = new Color(255, 255, 0);
  Color checkColor = new Color(255, 0, 0);
  AudioInputStream audioInputStream;
  File fileSound;
  Clip sound;
  boolean playCheckSound;

  public void PlayMoveSound() {
    try {
      fileSound = new File("Resources/move-self.wav");
      audioInputStream = AudioSystem.getAudioInputStream(fileSound);
      sound = AudioSystem.getClip();
      sound.open(audioInputStream);
      sound.start();

    } catch (Exception e) {
      System.out.println("Problem with finding moveSound");
    }
  }

  public void PlayCheckSound() {
    try {
      fileSound = new File("Resources/check.wav");
      audioInputStream = AudioSystem.getAudioInputStream(fileSound);
      sound = AudioSystem.getClip();
      sound.open(audioInputStream);
      sound.start();

    } catch (Exception e) {
      System.out.println("Problem with finding checkSound");
    }
  }

  public void PlayWinSound() {
    try {
      fileSound = new File("Resources/win.wav");
      audioInputStream = AudioSystem.getAudioInputStream(fileSound);
      sound = AudioSystem.getClip();
      sound.open(audioInputStream);
      sound.start();

    } catch (Exception e) {
      System.out.println("Problem with finding winSound");
    }
  }

  // This sound was used from this forum post :
  // https://www.chess.com/forum/view/general/chessboard-sound-files

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
  // private int xCoord;
  // private int yCoord;
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
    DefaultGameSetup(ChessPieceContainer);
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
  private void DefaultGameSetup(ChessPiece[] ChessPieceContainer) {
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
    DisplayOnBoard(ChessPieceContainer);
  }

  public void DisplayOnBoard(ChessPiece[] board) {
    for (int i = 0; i < numPieces; i++) {
      /*
       * EX for below method:
       * (1,1) gets taken in and is translated to be [7][0] in the array
       * where [7] is the i index (yCoord) and [0] is the j index (xCoord)
       */
      int xCoord = TranslatePieceCoordinates(ChessPieceContainer[i].GetXCoord(), 'X');
      int yCoord = TranslatePieceCoordinates(ChessPieceContainer[i].GetYCoord(), 'Y');
      // The JPanel adds a new JLabel that represents a piece at the selected
      // coordinates
      playSquare[yCoord][xCoord].add(SymbolToLabel(ChessPieceContainer[i]), BorderLayout.CENTER);
      ChessPieceContainer[i].SetMoveStatus(false);
      // Experimental, may be unused
      ChessPieceContainer[i].SetJLabel(SymbolToLabel(ChessPieceContainer[i]));
    }
  }

  public void DetermineTurnOrder() {
    for (int i = 0; i < numPieces; i++) {
      if (turnCount % 2 == 0) {
        if (ChessPieceContainer[i].GetPlayerSide() == 'B') {
          ChessPieceContainer[i].SetMoveableStatus(true);
        } else if (ChessPieceContainer[i].GetPlayerSide() == 'W')
          ChessPieceContainer[i].SetMoveableStatus(false);
      } else {
        if (ChessPieceContainer[i].GetPlayerSide() == 'W') {
          ChessPieceContainer[i].SetMoveableStatus(true);
        } else if (ChessPieceContainer[i].GetPlayerSide() == 'B')
          ChessPieceContainer[i].SetMoveableStatus(false);
      }
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
      int xCoord = TranslatePieceCoordinates(ChessPieceContainer[i].GetXCoord(), 'X');
      int yCoord = TranslatePieceCoordinates(ChessPieceContainer[i].GetYCoord(), 'Y');
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
    int xCoord = TranslatePieceCoordinates(x, 'X');
    int yCoord = TranslatePieceCoordinates(y, 'Y');
    for (int i = 0; i < numPieces; i++) {
      if (x == ChessPieceContainer[i].GetXCoord() && y == ChessPieceContainer[i].GetYCoord()
          && ChessPieceContainer[i].IsAlive() && ChessPieceContainer[i].GetMoveableStatus() == true) {
        copiedPiece = ChessPieceContainer[i];
        isSelected = true;
        ShowValidMoves(copiedPiece);
      }
    }
  }

  // Displays the valid moves on the board
  // Triggered by clicks
  public ArrayList<int[]> ShowValidMoves(ChessPiece selectedPiece) {
    int pivotxCoord = copiedPiece.GetXCoord();
    int pivotyCoord = copiedPiece.GetYCoord();
    boolean overrideValidMove = false;
    ArrayList<int[]> coordinatesList = copiedPiece.ValidMoves(pivotxCoord, pivotyCoord, ChessPieceContainer);

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
    }
    SelectedCoordinatesList = coordinatesList;
    if (copiedPiece.GetSymbol() != 'K') {
      SelectedCoordinatesList = CheckForMates(coordinatesList,
          copiedPiece.GetPlayerSide(), ChessPieceContainer, selectedPiece, 0);
    } else {
      SelectedCoordinatesList = coordinatesList;
    }

    for (int[] coordinates : SelectedCoordinatesList) {
      playSquare[8 - coordinates[1]][coordinates[0] - 1].setBackground(validMoveColor);
    }
    return SelectedCoordinatesList;
  }

  // -------- Valid Move Helper Functions -------- //
  // Shows valid pawn movement based on the posmakemoveition of other pieces
  // May integrate into the pawn class if possible
  public ArrayList<int[]> ModifyPawnMovement(int x, int y) {
    // To access pawn only methods
    Pawn copiedPawn = (Pawn) copiedPiece;
    ArrayList<int[]> coordinatesList = new ArrayList<>();

    coordinatesList = copiedPawn.PawnSpecialMove(x, y, ChessPieceContainer);

    return coordinatesList;
  }

  // ------------ Move Functions ------------ //
  public void MakeMove(int x, int y, int newX, int newY, ChessPiece[] board, ChessPiece piece) {

    if (MoveValidator(x, y, newX, newY, board, piece) == true) {
      piece.SetMoveStatus(true);
      piece.SetYCoord(newY);
      piece.SetXCoord(newX);
    }
    UpdatePieces();
  }

  // -------- Move Helper Functions -------- //
  // Determines an action for a move
  private boolean MoveValidator(int x, int y, int newX, int newY, ChessPiece[] board, ChessPiece piece) {
    boolean canMove = true;
    for (int i = 0; i < numPieces; i++) {
      // Checks to see if the piece moved on top of another, and removes it if so
      if (board[i].GetXCoord() == newX && board[i].GetYCoord() == newY && board[i].IsAlive()) {
        if (board[i].GetPlayerSide() != piece.GetPlayerSide()) {
          board[i].RemovePiece();
          System.out.println("Piece taken!");
        } else {
          canMove = false;
        }
      }
    }

    if (piece.GetSymbol() == 'P') {
      // Special cases
      // EnPassant(x, y, newX, newY);
      piece.SetYCoord(newY);
      piece.SetXCoord(newX);
      CheckForPromotion(newX, newY);
    }
    return canMove;
  }

  public void PrintWinnerDialog(char winner) {
    ImageIcon winnerIcon = new ImageIcon();
    if (winner == 'W') {
      winnerIcon = new ImageIcon(ChessFrame.class.getResource("Resources/WKing.png"));
    } else {
      winnerIcon = new ImageIcon(ChessFrame.class.getResource("ResourcesBKing.png"));
    }
    JLabel no = new JLabel("Quit");
    String[] YesOrNo = { "Play again", "Quit" };
    PlayWinSound();
    int n = JOptionPane.showOptionDialog(this, "You won! Congratulations!",
        "CheckMate!", JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE, winnerIcon, YesOrNo, no);
    if (n == -1)
      n = 1;

    if (n == 1) {
      System.exit(0);
    } else {
      DefaultGameSetup(ChessPieceContainer);
      UpdatePieces();
    }

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
        if (ChessPieceContainer[i].GetSymbol() == 'P') {
        }
        if (ChessPieceContainer[i].GetXCoord() == x && ChessPieceContainer[i].GetYCoord() == y
            && ChessPieceContainer[i].IsAlive() && ChessPieceContainer[i].GetSymbol() == 'P') {
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
    /*
     * // To access pawn only methods
     * Pawn copiedPawn = (Pawn) copiedPiece;
     * 
     * // If a pawn has moved twice
     * if ((copiedPawn.GetYCoord() == 2 && newY == 4) || (copiedPawn.GetYCoord() ==
     * 7 && newY == 5)) {
     * copiedPawn.MoveTwice(true);
     * }
     * 
     * for (int i = 0; i < numPieces; i++) {
     * // En Passant Right
     * if (ChessPieceContainer[i].GetXCoord() == (x + 1) &&
     * ChessPieceContainer[i].GetYCoord() == (y)
     * && ChessPieceContainer[i].IsAlive() && ChessPieceContainer[i].IsMovedTwice()
     * == true) {
     * if (ChessPieceContainer[i].GetPlayerSide() != copiedPawn.GetPlayerSide()) {
     * ChessPieceContainer[i].RemovePiece();
     * System.out.println("Piece taken!");
     * }
     * // En Passant left
     * } else if (ChessPieceContainer[i].GetXCoord() == (x - 1) &&
     * ChessPieceContainer[i].GetYCoord() == (y)
     * && ChessPieceContainer[i].IsAlive() && ChessPieceContainer[i].IsMovedTwice()
     * == true) {
     * if (ChessPieceContainer[i].GetPlayerSide() != copiedPawn.GetPlayerSide()) {
     * ChessPieceContainer[i].RemovePiece();
     * System.out.println("Piece taken!");
     * }
     * }
     * }
     */
  }

  public void AddMovesToSet(Set<Integer> TheSet, ChessPiece piece) {

  }

  public ArrayList<int[]> CheckForMates(ArrayList<int[]> coordinatesList, char playerSide,
      ChessPiece[] realBoard, ChessPiece selectedPiece, int RecursiveCall) {
    if (RecursiveCall == 1 && (isKingInCheck('W', ChessPieceContainer) || isKingInCheck('B', ChessPieceContainer))) {
      boolean canMove = false;
      for (int i = 0; i < 32; i++) {
        if (ChessPieceContainer[i].GetPlayerSide() == playerSide) {
          int tempX = ChessPieceContainer[i].GetXCoord();
          int tempY = ChessPieceContainer[i].GetYCoord();
          ArrayList<int[]> TempReturnMoves = CheckForMates(ChessPieceContainer[i].ValidMoves(tempX, tempY, realBoard),
              ChessPieceContainer[i].GetPlayerSide(), realBoard,
              ChessPieceContainer[i], 0);
          if (TempReturnMoves.size() > 0) {
            canMove = true;
          }
        }
      }
      if (canMove) {

      } else {
        System.out.println("Checkmate");
        if (isKingInCheck('W', realBoard))
          PrintWinnerDialog('B');
        else
          PrintWinnerDialog('W');
      }
    }
    // This ArrayList will store all of the moves that WONT put the king in check
    ArrayList<int[]> ReturnMoves = new ArrayList<>();
    int kingX = 0;
    int kingY = 0;
    // Finds our king
    for (int i = 0; i < 32; i++) {
      if (realBoard[i].GetSymbol() == 'K' && realBoard[i].GetPlayerSide() == playerSide) {
        // If this is our king
        kingX = realBoard[i].GetXCoord();
        kingY = realBoard[i].GetYCoord();
      }
    }
    // Check to see if making the move would put our king in check
    for (int[] move : coordinatesList) {
      int x = move[0];
      int y = move[1];
      ChessPiece[] tempBoard = new ChessPiece[32];
      // Sets up a copy of the real board to a container called tempBoard
      for (int i = 0; i < 32; i++) {
        char symbol = ChessPieceContainer[i].GetSymbol();
        char side = ChessPieceContainer[i].GetPlayerSide();
        JLabel chessPieceLabel;
        switch (symbol) {
          case 'K':
            tempBoard[i] = new King(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord(), side);
            break;
          case 'Q':
            tempBoard[i] = new Queen(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord(), side);
            break;
          case 'N':
            tempBoard[i] = new Knight(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord(), side);
            break;
          case 'R':
            tempBoard[i] = new Rook(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord(), side);
            break;
          case 'B':
            tempBoard[i] = new Bishop(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord(), side);
            break;
          case 'P':
            tempBoard[i] = new Pawn(ChessPieceContainer[i].GetXCoord(), ChessPieceContainer[i].GetYCoord(), side);
            tempBoard[i].SetSymbol('t');
            break;
        }
      }

      for (int i = 0; i < 32; i++) {
        tempBoard[i].SetXCoord(ChessPieceContainer[i].GetXCoord());
        tempBoard[i].SetYCoord(ChessPieceContainer[i].GetYCoord());
      }
      int tempPiece = returnNewCopiedPiece(tempBoard);

      // tempBoard[tempPiece].SetXCoord(x);
      // tempBoard[tempPiece].SetYCoord(y);
      MakeMove(tempBoard[tempPiece].GetXCoord(), tempBoard[tempPiece].GetYCoord(), x, y, tempBoard,
          tempBoard[tempPiece]);
      if (!isKingInCheck(playerSide, tempBoard)) {
        ReturnMoves.add(new int[] { x, y });
      }
    }
    for (int[] xy : ReturnMoves) {
      int x = xy[0];
      int y = xy[1];
    }
    return ReturnMoves;
  }

  public int returnNewCopiedPiece(ChessPiece[] tempBoard) {
    for (int i = 0; i < numPieces; i++) {
      if (copiedPiece.GetXCoord() == tempBoard[i].GetXCoord() && copiedPiece.GetYCoord() == tempBoard[i].GetYCoord())
        return i;
    }
    return 0;
  }

  public boolean isKingInCheck(char playerSide, ChessPiece[] board) {
    // Adds all of their moves to an ArrayList
    int kingX = 0;
    int kingY = 0;
    for (int i = 0; i < 32; i++) {
      if (ChessPieceContainer[i].GetPlayerSide() == playerSide && ChessPieceContainer[i].GetSymbol() == 'K') {
        kingX = ChessPieceContainer[i].GetXCoord();
        kingY = ChessPieceContainer[i].GetYCoord();
      }
    }
    ArrayList<int[]> opponentsMoves = new ArrayList<>();
    for (int i = 0; i < 32; i++) {
      if (board[i].IsAlive() &&
          board[i].GetPlayerSide() != playerSide) {
        // If the opponent's chess piece is alive
        ArrayList<int[]> tempMoves = board[i].ValidMoves(ChessPieceContainer[i].GetXCoord(),
            board[i].GetYCoord(), board);
        for (int[] moves : tempMoves) {
          int x = moves[0];
          int y = moves[1];
          opponentsMoves.add(new int[] { x, y });
        }
      }
    }
    for (int[] theirMoves : opponentsMoves) {
      int theirX = theirMoves[0];
      int theirY = theirMoves[1];
      if (kingX == theirX && kingY == theirY) {
        // Move shouldnt be added to the available moves arr
        playSquare[8 - kingY][kingX - 1].setBackground(checkColor);
        UpdatePieces();
        return true;
      }
    }
    return false;
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
        int xCoord = TranslatePieceCoordinates(ChessPieceContainer[i].GetXCoord(), 'X');
        int yCoord = TranslatePieceCoordinates(ChessPieceContainer[i].GetYCoord(), 'Y');
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

  // From piece coordinates to the playSquare 2D array
  private int TranslatePieceCoordinates(int coord, char direction) {
    // Non-flipped
    if (direction == 'X') {
      // Shift the coordinate down by one to fit in the 2D array
      int translatedXCoord = (coord - 1);
      coord = translatedXCoord;
    } else if (direction == 'Y') {
      // Shift the coordinate down by one to fit in the 2D array
      int translatedYCoord = (coord - 1);
      // Flip the coordinate to ensure untranslated y value 1 starts at the bottom
      coord = (7 - translatedYCoord);
    }
    return coord;
  }

  // From playSquare 2D array to piece coordinates
  private int TranslateBoardCoordinates(int coord, char direction) {
    // Non-flipped
    if (direction == 'X') {
      // Shift the coordinate up by one to fit in the 2D array
      int translatedXCoord = (coord + 1);
      coord = translatedXCoord;
    } else if (direction == 'Y') {
      int translatedYCoord = coord;
      // Flip the coordinate to ensure translated y value 1 starts at the top
      coord = (8 - translatedYCoord);
    }
    return coord;
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
              // Translate the loop indexes into piece coordinates
              int xCoord = TranslateBoardCoordinates(j, 'X');
              int yCoord = TranslateBoardCoordinates(i, 'Y');
              DetermineTurnOrder();
              selectPiece(xCoord, yCoord);
              System.out.println("Turn Count: " + turnCount);
            }
        // ---- Move/Deselect ---- //
        // If the piece is already selected
        // Uses a copied piece to represent a piece
      } else {
        isSelected = false;
        for (int i = 0; i < 8; i++)
          for (int j = 0; j < 8; j++)
            if (e.getSource() == playSquare[i][j]) {
              // Search every coordinate in the list
              for (int[] coordinates : SelectedCoordinatesList) {
                // Translate the loop indexes into piece coordinates
                int xCoord = TranslateBoardCoordinates(j, 'X');
                int yCoord = TranslateBoardCoordinates(i, 'Y');
                // Match index coordinates to piece coordinates
                if (xCoord == coordinates[0] && yCoord == coordinates[1]) {
                  MakeMove(copiedPiece.GetXCoord(), copiedPiece.GetYCoord(), xCoord, yCoord, ChessPieceContainer,
                      copiedPiece);
                  turnCount++;
                  System.out.println("New turn Count: " + turnCount);
                  PlayMoveSound();

                  // DetermineTurnOrder();
                  ResetBoardBackground();
                  UpdatePieces();
                  break;
                  // ---- Deselect ----
                } else if (xCoord == copiedPiece.GetXCoord() && yCoord == copiedPiece.GetYCoord()) {
                  System.out.println("Deselect Turn Count: " + turnCount);
                  ResetBoardBackground();
                  UpdatePieces();
                  break;
                }
              }
            }
      }
      // Changes the background to red if the king is in check
      ArrayList<int[]> tempMoves = new ArrayList<>();

      if (isKingInCheck('W', ChessPieceContainer) || isKingInCheck('B', ChessPieceContainer)) {
        CheckForMates(tempMoves, 'B', ChessPieceContainer, copiedPiece, 1);
        CheckForMates(tempMoves, 'W', ChessPieceContainer, copiedPiece, 1);
        if (!playCheckSound) {
          PlayCheckSound();
          playCheckSound = true;
        }
      } else {
        playCheckSound = false;
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