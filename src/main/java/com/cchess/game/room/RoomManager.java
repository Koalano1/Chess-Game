package com.cchess.game.room;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;
import com.cchess.game.cchess.matches.Player;
import com.cchess.game.cchess.pieces.Piece;
import com.cchess.game.exception.InvalidMoveException;
import com.cchess.game.util.PieceUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record RoomManager(Room room) {

    public boolean makeMove(Player player, Position from, Position to) {
        if (room.getStatus() != RoomStatus.PLAYING) throw new InvalidMoveException("Room is not playing");
        if (room.getGameState().getCurrentPlayer() != player) throw new InvalidMoveException("Not your turn");

        Piece piece = room.getGameState().getBoard().getArray()[from.getRow()][from.getCol()];
        if (piece == null || piece.isRed() != player.getIsRed()) throw new InvalidMoveException("Invalid piece");

        boolean isValidMove = piece.isValidMove(room.getGameState().getBoard(), from, to);
        if (!isValidMove) throw new InvalidMoveException("Invalid move for this piece");

        // Check if there is a checkmate
        boolean isCheckmated = isCheckmated(room.getGameState().getBoard(), player.getIsRed());
        if (isCheckmated) throw new InvalidMoveException("You are checkmated");

        // Check if the move will cause a checkmate
        Board clonedBoard = room.getGameState().getBoard().clonedBoard();
        clonedBoard.movePiece(from, to);
        boolean willCauseCheckmate = isCheckmated(clonedBoard, player.getIsRed());
        if (willCauseCheckmate) throw new InvalidMoveException("This move will cause a checkmate");

        room.getGameState().getBoard().movePiece(from, to);

        // ...

        room.getGameState().setCurrentPlayer(
                room.getGameState().getOtherPlayer()
        );
        room.getGameState().setOtherPlayer(player);

//        Player nextPlayer = room.getPlayers().stream()
//                .filter(p -> !p.getUsername().equals(player.getUsername()))
//                .map(userDto -> Player.builder()
//                        .username(userDto.getUsername())
//                        .isRed(!player.getIsRed())
//                        .build())
//                .findFirst()
//                .orElseThrow(() -> new NotFoundException("Player not found"));
//
//        room.getGameState().setCurrentPlayer(nextPlayer);

        return true;
    }

    public boolean isCheckmated(Board board, boolean isRed) {
        Board clonedBoard = board.clonedBoard();

        Position currentGeneralPosition = PieceUtils.getGeneralPosition(clonedBoard, isRed);
        for (int row = 0; row < Board.ROW; row++) {
            for (int col = 0; col < Board.COL; col++) {
                Piece piece = clonedBoard.getArray()[row][col];
                if (piece == null || piece.isRed() == isRed) continue;

                Position piecePosition = new Position(row, col);
                if (piece.isValidMove(clonedBoard, piecePosition, currentGeneralPosition)) {
                    log.debug("Checkmated by {} at {}", piece, piecePosition);
                    return true;
                }
            }
        }
        return false;
    }
}
