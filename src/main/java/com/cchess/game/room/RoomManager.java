package com.cchess.game.room;

import com.cchess.game.cchess.Player;
import com.cchess.game.cchess.base.Position;
import com.cchess.game.cchess.pieces.Piece;

public record RoomManager(Room room) {

    public boolean makeMove(Player player, Position from, Position to) {
        if (room.getStatus() != RoomStatus.PLAYING) return false;
        if (room.getCurrentPlayer() != player) return false;

        Piece piece = room.getBoard().getArray()[from.getRow()][from.getCol()];
        if (piece == null || piece.isRed() != player.getIsRed()) return false;

        boolean isValidMove = piece.isValidMove(room.getBoard(), from, to);
        if (!isValidMove) return false;

        room.getBoard().movePiece(from, to);

        Player nextPlayer = room.getPlayers().stream()
                .filter(p -> !p.getUsername().equals(player.getUsername()))
                .map(userDto -> Player.builder()
                        .username(userDto.getUsername())
                        .isRed(!player.getIsRed())
                        .build())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Player not found"));

        room.setCurrentPlayer(nextPlayer);

        return true;
    }

}
