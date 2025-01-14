package com.cchess.game.cchess.base;

import com.cchess.game.cchess.pieces.*;
import lombok.Data;
import org.springframework.data.util.Pair;

@Data
public class Board {

    public static final int ROW = 10;
    public static final int COL = 9;

    private Piece[][] array = new Piece[ROW][COL];

    public Board() {
        this.initLayout();
    }

    public void initLayout() {
        array[0][0] = new Chariot(false);
        array[0][1] = new Horse(false);
        array[0][2] = new Elephant(false);
        array[0][3] = new Advisor(false);
        array[0][4] = new General(false);
        array[0][5] = new Advisor(false);
        array[0][6] = new Elephant(false);
        array[0][7] = new Horse(false);
        array[0][8] = new Chariot(false);

        array[2][1] = new Cannon(false);
        array[2][7] = new Cannon(false);

        for (int i = 0; i < 9; i += 2) {
            array[3][i] = new Soldier(false);
        }

        for (int i = 0; i < 9; i += 2) {
            array[6][i] = new Soldier(true);
        }

        array[7][1] = new Cannon(true);
        array[7][7] = new Cannon(true);

        array[9][0] = new Chariot(true);
        array[9][1] = new Horse(true);
        array[9][2] = new Elephant(true);
        array[9][3] = new Advisor(true);
        array[9][4] = new General(true);
        array[9][5] = new Advisor(true);
        array[9][6] = new Elephant(true);
        array[9][7] = new Horse(true);
        array[9][8] = new Chariot(true);
    }

    private Pair<Integer, Integer> lowerAndUpperRows(Position from, Position to) {
        return Pair.of(
                Math.min(from.getRow(), to.getRow()),
                Math.max(from.getRow(), to.getRow())
        );
    }

    private Pair<Integer, Integer> lowerAndUpperCols(Position from, Position to) {
        return Pair.of(
                Math.min(from.getCol(), to.getCol()),
                Math.max(from.getCol(), to.getCol())
        );
    }

    public boolean hasPiecesBetweenHorizontal(Position from, Position to) {
        int lowerCol = lowerAndUpperCols(from, to).getFirst();
        int upperCol = lowerAndUpperCols(from, to).getSecond();
        for (int col = lowerCol + 1; col <= upperCol - 1; col++)
            if (array[from.getRow()][col] != null)
                return true;
        return false;
    }

    public boolean hasPiecesBetweenVertical(Position from, Position to) {
        int lowerRow = lowerAndUpperRows(from, to).getFirst();
        int upperRow = lowerAndUpperRows(from, to).getSecond();
        for (int row = lowerRow + 1; row <= upperRow - 1; row++)
            if (array[row][from.getCol()] != null)
                return true;

        return false;
    }

    public boolean isNotInPalace(Position to) {
        return (to.getCol() < 3 || to.getCol() > 5)
                || ((to.getRow() > 2 && to.getRow() < 7));
    }

    public void movePiece(Position from, Position to) {
        Piece piece = array[from.getRow()][from.getCol()];

        array[to.getRow()][to.getCol()] = piece;
        array[from.getRow()][from.getCol()] = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                Piece piece = array[i][j];
                if (piece == null) {
                    sb.append(" . ");
                } else {
                    sb.append(String.format("%2s ", piece.getName()));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
