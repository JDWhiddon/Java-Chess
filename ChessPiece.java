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
	protected boolean isSelected = false;
	protected boolean hasMoved = false;
	protected boolean canMoveOnTurn = false;
	protected int turn;
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
	public ArrayList<int[]> ValidMoves(int x, int y, ChessPiece[] copyPieceContainer) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();
		return coordinatesList;
	}

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
	
	public void SetSelection(boolean s) {
		isSelected = s;
	}

	public void RemovePiece() {
		isAlive = false;
	}

	public boolean IsAlive() {
		return isAlive;
	}

	public boolean GetSelection() {
		return isSelected;
	}
	
	public void SetMoveableStatus(boolean s){
		canMoveOnTurn = s;
	}
	
	public boolean GetMoveableStatus(){
		return canMoveOnTurn;
	}
	
	public void SetMoveStatus(boolean s){
		hasMoved = s;
	}
	
	public boolean GetMoveStatus(){
		return hasMoved;
	}
}