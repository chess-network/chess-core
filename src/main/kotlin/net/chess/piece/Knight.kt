package net.chess.piece

import net.chess.Action
import net.chess.Board
import net.chess.PieceColor

class Knight(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board)  {
    override fun availableActions(): List<Action> {
        TODO("Not yet implemented")
    }

    override fun executeAction(action: Action) {
        TODO("Not yet implemented")
    }
}