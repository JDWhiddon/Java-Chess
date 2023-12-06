import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

class King extends ChessPiece {
    private char symbol = 'K';
    private boolean canCastle = true;
    private boolean hasMoved = false;

    public King(int x, int y, char ps) {
        super(x, y, ps);
    }

    public char GetSymbol() {
        return symbol;
    }

    public ArrayList<int[]> ValidMoves(JPanel[][] playSquares, int x, int y) {
        ArrayList<int[]> coordinatesList = new ArrayList<>();
        int leftBound = 1;
        int rightBound = 8;
        int lowerBound = 1;
        int upperBound = 8;

        if (playerSide == 'W') {
            // Going -> Top
            if (y < upperBound)
                coordinatesList.add(new int[] { x, y + 1 });
            // Going -> Bottom
            if (y > lowerBound)
                coordinatesList.add(new int[] { x, y - 1 });
            // Going -> Right
            if (x < rightBound)
                coordinatesList.add(new int[] { x + 1, y });
            // Going -> Left
            if (x > leftBound)
                coordinatesList.add(new int[] { x - 1, y });
            // Going -> Top Right
            if (y < upperBound && x < rightBound) {
                coordinatesList.add(new int[] { x + 1, y + 1 });
            }
            // Going -> Bottom Right
            if (y > lowerBound && x < rightBound) {
                coordinatesList.add(new int[] { x + 1, y - 1 });
            }
            // Going -> Top Left
            if (y < upperBound && x > leftBound) {
                coordinatesList.add(new int[] { x - 1, y + 1 });
            }
            // Going -> Bottom Left
            if (y > lowerBound && x > leftBound) {
                coordinatesList.add(new int[] { x - 1, y - 1 });
            }

        }
        // ---- Black ----
        else {
            // Going -> Top
            if (y < upperBound)
                coordinatesList.add(new int[] { x, y + 1 });
            // Going -> Bottom
            if (y > lowerBound)
                coordinatesList.add(new int[] { x, y - 1 });
            // Going -> Right
            if (x < rightBound)
                coordinatesList.add(new int[] { x + 1, y });
            // Going -> Left
            if (x > leftBound)
                coordinatesList.add(new int[] { x - 1, y });
            // Going -> Top Right
            if (y < upperBound && x < rightBound) {
                coordinatesList.add(new int[] { x + 1, y + 1 });
            }
            // Going -> Bottom Right
            if (y > lowerBound && x < rightBound) {
                coordinatesList.add(new int[] { x + 1, y - 1 });
            }
            // Going -> Top Left
            if (y < upperBound && x > leftBound) {
                coordinatesList.add(new int[] { x - 1, y + 1 });
            }
            // Going -> Bottom Left
            if (y > lowerBound && x > leftBound) {
                coordinatesList.add(new int[] { x - 1, y - 1 });
            }
        }
        return coordinatesList;
    }

    public void Move(int x, int y) {
        System.out.println("Test");
    }
}