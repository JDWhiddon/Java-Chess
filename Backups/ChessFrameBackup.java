import java.awt.*;
import javax.swing.*;

public class ChessFrame extends JFrame {

  private GridLayout chessBoard;
  Color cream = new Color(238, 238, 210);
  Color green = new Color(118, 150, 86);

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
    for (int i = 0; i < 64; i++) {
      JPanel playSquare = new JPanel();
      int row = i / 8;
      if (row % 2 == 0) {
        if (i % 2 == 0)
          playSquare.setBackground(cream);
        else {
          playSquare.setBackground(green);
        }
      } else {
        if (i % 2 != 0)
          playSquare.setBackground(cream);
        else {
          playSquare.setBackground(green);
        }
      }
      add(playSquare);
      BorderLayout layout = new BorderLayout(5,5);
      playSquare.setLayout(layout);

      //Switch case to determine which chess piece is needed     
      //Need to find a way to do this efficiently 
      ImageIcon chessPiece = new ImageIcon(ChessFrame.class.getResource("Resources/WKing.png"));
      JLabel chessPieceLabel = new JLabel(chessPiece);
      playSquare.add(chessPieceLabel, BorderLayout.CENTER);
    }
  }
}
