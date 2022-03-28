package net.chess.piece

import net.chess.Action
import net.chess.Board
import net.chess.enums.PieceColor
import net.chess.enums.PieceType

class King(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board, PieceType.KING) {
    override fun availableActions(): List<Action> {
        TODO("Not yet implemented")
    }


}