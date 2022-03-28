package net.chess.piece

import net.chess.game.Action
import net.chess.game.Board
import net.chess.enums.PieceColor
import net.chess.enums.PieceType

class Queen(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board, PieceType.QUEEN) {
    override fun availableActions(): List<Action> {
        TODO("Not yet implemented")
    }


}