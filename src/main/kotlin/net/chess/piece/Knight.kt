package net.chess.piece

import net.chess.game.Action
import net.chess.enums.ActionType
import net.chess.game.Board
import net.chess.enums.PieceColor
import net.chess.enums.PieceType

class Knight(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board, PieceType.KNIGHT) {
    override fun availableActions(): List<Action> {

        val availableActions: MutableList<Action> = mutableListOf()

        listOf(
            (position.first + 2 to position.second + 1),
            (position.first - 2 to position.second + 1),
            (position.first + 2 to position.second - 1),
            (position.first - 2 to position.second - 1),
            (position.first + 1 to position.second + 2),
            (position.first - 1 to position.second + 2),
            (position.first + 1 to position.second - 2),
            (position.first - 1 to position.second - 2)
        ).forEach {
            if (it.first in 1..8 && it.second in 1..8) {
                checkMove(it)?.let { it1 -> availableActions.add(it1) }
            }
        }


        return availableActions
    }


}