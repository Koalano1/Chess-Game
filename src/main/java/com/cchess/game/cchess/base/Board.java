package com.cchess.game.cchess.base;

import com.cchess.game.cchess.pieces.*;
import lombok.Data;

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

    public static void main(String[] args) {
        Board game = new Board();
        Piece[][] board = game.getArray();

//        for (int i = 0; i < ROW; i++) {
//            for (int j = 0; j < COL; j++) {
//                if (board[i][j] == null)
//                    System.out.print(" ");
//                else System.out.print(board[i][j].getSymbol() + " ");
//            }
//            System.out.println();
//        }

//        Piece redGeneral = new General(true);
//        System.out.println(redGeneral.isValidMove(game, new Position(9, 4), new Position(8, 4)));

//        Piece redGeneral = new General(true);
//        Piece redHorse = new Horse(true);
//        Position redGeneralPosition = PieceUtils.getGeneralPosition(game, true);
//        Position currentPosition = PieceUtils.getGeneralPosition(game, true);

//        Piece leftRedAdvisor = PieceUtils.getPieceInstanceFromName("AD", true);
//        Position leftRedAdvisorPosition = new Position(8, 4);

//        Piece horse = new Horse(true);
//        Position horsePosition = new Position(7, 2);
//        Piece cannon = new Cannon(true);
//        Position cannonPosition = new Position(7, 1);
//
//        Piece chariot = new Chariot(true);
//        Position chariotPosition = new Position(9, 0);
//        Piece elephant = new Elephant(true);
//        Position elephantPosition = new Position(6, 5);
        Piece soldier = new Soldier(true);
        Position soldierPosition = new Position(8, 1);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                Position newPosition = new Position(i, j);
                if (soldier.isValidMove(game, soldierPosition, newPosition))
                    System.out.println("New valid position: " + newPosition);

            }
        }
    }

}
