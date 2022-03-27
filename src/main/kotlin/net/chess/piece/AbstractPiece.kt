package net.chess.piece

import net.chess.Action
import net.chess.Board
import net.chess.History
import net.chess.PieceColor

abstract class AbstractPiece(
    val color: PieceColor,
    var position: Pair<Int, Int>,
    val board: Board,
    val history: MutableList<History> = mutableListOf()
) {

    abstract fun availableActions(): List<Action>

    abstract fun executeAction(action: Action)

    fun validateAction(action: Action) {
        if (!availableActions().contains(action)) {
            throw IllegalArgumentException("Action ${action.type} is not available for piece at position $position")
        }
    }

    fun appear() {
        board[position] = this
    }

    override fun equals(other: Any?): Boolean {
        return other is AbstractPiece && other::class.java == this::class.java && other.position == position && other.color == color && other.board == board && other.history == history
    }
}