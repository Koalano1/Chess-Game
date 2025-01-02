package com.cchess.game.cchess;

import com.cchess.game.cchess.base.Position;
import com.cchess.game.cchess.pieces.Piece;
import com.cchess.game.room.Room;
import com.cchess.game.room.RoomStatus;

public record GameManager(Room room) {

    public boolean makeMove(Player player, Position from, Position to) {
        if (room.getStatus() != RoomStatus.PLAYING) return false;
        if (room.getCurrentPlayer() != player) return false;

        Piece piece = room.getBoard().getArray()[from.getRow()][from.getCol()];
        if (piece == null || piece.isRed() != player.getIsRed()) return false;

        boolean isValidMove = piece.isValidMove(room.getBoard(), from, to);
        if (!isValidMove) return false;

        room.getBoard().movePiece(from, to);
        if (room.getCurrentPlayer() == room.getPlayer1()) {
            room.setCurrentPlayer(room.getPlayer2());
        } else {
            room.setCurrentPlayer(room.getPlayer1());
        }

        return true;
    }

}
