import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

class Knight extends ChessPiece {
    private char symbol = 'N';
    final int leftBound = 1;
    final int rightBound = 8;
    final int lowerBound = 1;
    final int upperBound = 8;
    boolean[] directionCancel = new boolean[8];

    public Knight(int x, int y, char ps) {
        super(x, y, ps);
        initializeDirectionCancel();
    }

    public char GetSymbol() {
        return symbol;
    }

    public void initializeDirectionCancel() {
        for (int i = 0; i < 8; i++) {
            directionCancel[i] = false;
        }
    }

    // Detects if a piece of the same side is on the same target coordinates
    public void DetectCollision(JPanel[][] playSquares, int x, int y, ChessPiece cp) {
        if (playerSide == 'W' && cp.IsAlive() && cp.GetPlayerSide() == 'W') {
            if (((y + 2) <= upperBound) && ((x + 1) <= rightBound)) {
                if (cp.GetXCoord() == x + 1 && cp.GetYCoord() == y + 2)
                    directionCancel[0] = true;
            }
            // Going -> 2 Top + Left
            if (((y + 2) <= upperBound) && ((x - 1) >= leftBound)) {
                if (cp.GetXCoord() == x - 1 && cp.GetYCoord() == y + 2)
                    directionCancel[1] = true;
            }
            // Going -> 2 Bottom + Right
            if (((y - 2) >= lowerBound) && ((x + 1) <= rightBound)) {
                if (cp.GetXCoord() == x + 1 && cp.GetYCoord() == y - 2)
                    directionCancel[2] = true;
            }
            // Going -> 2 Bottom + Left
            if (((y - 2) >= lowerBound) && ((x - 1) >= leftBound)) {
                if (cp.GetXCoord() == x - 1 && cp.GetYCoord() == y - 2)
                    directionCancel[3] = true;
            }
            // Going -> 2 Right + Top
            if (((x + 2) <= rightBound) && ((y + 1) <= upperBound)) {
                if (cp.GetXCoord() == x + 2 && cp.GetYCoord() == y + 1)
                    directionCancel[4] = true;
            }
            // Going -> 2 Right + Bottom
            if (((x + 2) <= rightBound) && ((y - 1) >= lowerBound)) {
                if (cp.GetXCoord() == x + 2 && cp.GetYCoord() == y - 1)
                    directionCancel[5] = true;
            }
            // Going -> 2 Left + Top
            if (((x - 2) >= leftBound) && ((y + 1) <= upperBound)) {
                if (cp.GetXCoord() == x - 2 && cp.GetYCoord() == y + 1)
                    directionCancel[6] = true;
            }
            // Going -> 2 Left + Bottom
            if (((x - 2) >= leftBound) && ((y - 1) >= lowerBound)) {
                if (cp.GetXCoord() == x - 2 && cp.GetYCoord() == y - 1)
                    directionCancel[7] = true;
            }
            // ---- Black Side ---- //
        } else if (playerSide == 'B' && cp.IsAlive() && cp.GetPlayerSide() == 'B') {
            if (((y + 2) <= upperBound) && ((x + 1) <= rightBound)) {
                if (cp.GetXCoord() == x + 1 && cp.GetYCoord() == y + 2)
                    directionCancel[0] = true;
            }
            // Going -> 2 Top + Left
            if (((y + 2) <= upperBound) && ((x - 1) >= leftBound)) {
                if (cp.GetXCoord() == x - 1 && cp.GetYCoord() == y + 2)
                    directionCancel[1] = true;
            }
            // Going -> 2 Bottom + Right
            if (((y - 2) >= lowerBound) && ((x + 1) <= rightBound)) {
                if (cp.GetXCoord() == x + 1 && cp.GetYCoord() == y - 2)
                    directionCancel[2] = true;
            }
            // Going -> 2 Bottom + Left
            if (((y - 2) >= lowerBound) && ((x - 1) >= leftBound)) {
                if (cp.GetXCoord() == x - 1 && cp.GetYCoord() == y - 2)
                    directionCancel[3] = true;
            }
            // Going -> 2 Right + Top
            if (((x + 2) <= rightBound) && ((y + 1) <= upperBound)) {
                if (cp.GetXCoord() == x + 2 && cp.GetYCoord() == y + 1)
                    directionCancel[4] = true;
            }
            // Going -> 2 Right + Bottom
            if (((x + 2) <= rightBound) && ((y - 1) >= lowerBound)) {
                if (cp.GetXCoord() == x + 2 && cp.GetYCoord() == y - 1)
                    directionCancel[5] = true;
            }
            // Going -> 2 Left + Top
            if (((x - 2) >= leftBound) && ((y + 1) <= upperBound)) {
                if (cp.GetXCoord() == x - 2 && cp.GetYCoord() == y + 1)
                    directionCancel[6] = true;
            }
            // Going -> 2 Left + Bottom
            if (((x - 2) >= leftBound) && ((y - 1) >= lowerBound)) {
                if (cp.GetXCoord() == x - 2 && cp.GetYCoord() == y - 1)
                    directionCancel[7] = true;
            }
        }
    }

    public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
        ArrayList<int[]> coordinatesList = new ArrayList<>();

        if (playerSide == 'W' || playerSide == 'B') {
            // Going -> 2 Top + Right
            if (((y + 2) <= upperBound) && ((x + 1) <= rightBound)) {
                if (directionCancel[0] == false)
                    coordinatesList.add(new int[] { x + 1, y + 2 });
            }
            // Going -> 2 Top + Left
            if (((y + 2) <= upperBound) && ((x - 1) >= leftBound)) {
                if (directionCancel[1] == false)
                    coordinatesList.add(new int[] { x - 1, y + 2 });
            }
            // Going -> 2 Bottom + Right
            if (((y - 2) >= lowerBound) && ((x + 1) <= rightBound)) {
                if (directionCancel[2] == false)
                    coordinatesList.add(new int[] { x + 1, y - 2 });
            }
            // Going -> 2 Bottom + Left
            if (((y - 2) >= lowerBound) && ((x - 1) >= leftBound)) {
                if (directionCancel[3] == false)
                    coordinatesList.add(new int[] { x - 1, y - 2 });
            }
            // Going -> 2 Right + Top
            if (((x + 2) <= rightBound) && ((y + 1) <= upperBound)) {
                if (directionCancel[4] == false)
                    coordinatesList.add(new int[] { x + 2, y + 1 });
            }
            // Going -> 2 Right + Bottom
            if (((x + 2) <= rightBound) && ((y - 1) >= lowerBound)) {
                if (directionCancel[5] == false)
                    coordinatesList.add(new int[] { x + 2, y - 1 });
            }
            // Going -> 2 Left + Top
            if (((x - 2) >= leftBound) && ((y + 1) <= upperBound)) {
                if (directionCancel[6] == false)
                    coordinatesList.add(new int[] { x - 2, y + 1 });
            }
            // Going -> 2 Left + Bottom
            if (((x - 2) >= leftBound) && ((y - 1) >= lowerBound)) {
                if (directionCancel[7] == false)
                    coordinatesList.add(new int[] { x - 2, y - 1 });
            }
        }

        initializeDirectionCancel();
        return coordinatesList;
    }

    public void Move(int x, int y) {
        System.out.println("Test");
    }
}