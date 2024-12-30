package com.cchess.game.util;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.pieces.Piece;

public class BoardUtils {

    public static Piece[][] toMatrix(String board) {
        Piece[][] result = new Piece[Board.ROW][Board.COL];

        String[] rows = new String[10];
        for (int i = 0; i < 10; i++) {
            rows[i] = board.substring(i * 54, (i + 1) * 54);
        }

        for (String row : rows) {
            String[] pieces = row.split("_");

            for (String piece : pieces) {
                int rowIdxInMatrix = Integer.parseInt(String.valueOf(piece.charAt(0)));
                int colIdxInMatrix = Integer.parseInt(String.valueOf(piece.charAt(1)));
                char color = piece.charAt(2);
                String pieceName = piece.substring(3, 5);
                if (pieceName.equals("00")) {
                    continue;
                }

                boolean isRed = (color == 'r');
                Piece pieceInstance = PieceUtils.getPieceInstanceFromName(pieceName, isRed);
                result[rowIdxInMatrix][colIdxInMatrix] = pieceInstance;
            }
        }
        return result;
    }

}
