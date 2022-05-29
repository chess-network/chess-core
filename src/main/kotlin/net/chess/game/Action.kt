package net.chess.game

import net.chess.enums.ActionType
import net.chess.piece.AbstractPiece

data class Action(
    val fromPosition: Pair<Int, Int>,
    val toPosition: Pair<Int, Int>,
    val type: ActionType,
    val targetPiece: AbstractPiece? = null
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Action

        if (fromPosition != other.fromPosition) return false
        if (toPosition != other.toPosition) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fromPosition.hashCode()
        result = 31 * result + toPosition.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (targetPiece?.hashCode() ?: 0)
        return result
    }
}