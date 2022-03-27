package net.chess

import net.chess.piece.AbstractPiece

data class Action(
    val position: Pair<Int, Int>,
    val type: ActionType,
    val target: AbstractPiece? = null
)