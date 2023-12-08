import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public class NullPiece extends ChessPiece {
	private char symbol = 'O';

	public NullPiece(int x, int y, char ps) {
		super(x, y, ps);
	}

	public char GetSymbol() {
		return symbol;
	}

	public ArrayList<int[]> ValidMoves(int x, int y, ChessPiece[] copyPieceContainer, int numPieces) {
    ArrayList<int[]> coordinatesList = new ArrayList<>();
		
		return coordinatesList;
	}	
}