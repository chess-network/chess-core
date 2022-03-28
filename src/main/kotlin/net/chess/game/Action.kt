package net.chess.game

import net.chess.enums.ActionType
import net.chess.piece.AbstractPiece

data class Action(
    val fromPosition: Pair<Int, Int>,
    val toPosition: Pair<Int, Int>,
    val type: ActionType,
    val target: AbstractPiece? = null
){
    companion object{
        fun actionToCode(action: Action): String =
            (Board.pairToSquare(action.toPosition) + action.type.code  + (action.target?.fullCode() ?: "NULL"))

    }
}