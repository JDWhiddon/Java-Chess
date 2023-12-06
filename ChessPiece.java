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
    public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
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

    public void SelectPiece() {
        isSelected = true;
    }

    public void DeselectPiece() {
        isSelected = false;
    }

    public void RemovePiece() {
        isAlive = false;
    }

    public boolean IsAlive() {
        return isAlive;
    }

    public boolean IsSelected() {
        return isSelected;
    }

    // Pawns only
    public boolean IsMovedTwice() {
        boolean isPawn = false;
        return isPawn;
    }
    /*
     * public void MoveTwice(boolean x) {
     * }
     */
}

class NullPiece extends ChessPiece {
    private char symbol = 'O';

    public NullPiece(int x, int y, char ps) {
        super(x, y, ps);
    }

    public char GetSymbol() {
        return symbol;
    }

    public void Move(int x, int y) {
        System.out.println("Test");
    }
}