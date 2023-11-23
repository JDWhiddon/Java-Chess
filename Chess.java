import javax.swing.JFrame;

public class Chess {
  public static void main(String args[]) {
    ChessFrame chessFrame = new ChessFrame();
    chessFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    chessFrame.setSize(600, 600);
    chessFrame.setVisible(true);
	chessFrame.manualSelectCoords();
  }
} 