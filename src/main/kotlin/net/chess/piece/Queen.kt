package net.chess.piece

import net.chess.Action
import net.chess.Board
import net.chess.PieceColor

class Queen(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board, PieceType.QUEEN) {
    override fun availableActions(): List<Action> {
        TODO("Not yet implemented")
    }


}