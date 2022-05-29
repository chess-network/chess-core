package net.chess.piece

import net.chess.enums.ActionType
import net.chess.enums.PieceColor
import net.chess.enums.PieceType
import net.chess.game.Action
import net.chess.game.Board

abstract class AbstractPiece(
    val color: PieceColor,
    var position: Pair<Int, Int>,
    val board: Board,
    val type: PieceType,
    val actions: MutableList<Action> = mutableListOf()
) {
    abstract fun availableActions(): List<Action>

     fun executeAction(action: Action){
         validateAction(action)
         board.executeAction(action)
         actions.add(action)
     }

    private fun validateAction(action: Action) {
        if (!availableActions().contains(action)) {
            throw IllegalArgumentException("Action ${action.type} is not available for piece at position $position")
        }
    }

    fun appear() {
        board[position] = this
    }

    override fun equals(other: Any?): Boolean {
        return other is AbstractPiece && other::class.java == this::class.java && other.position == position && other.color == color && other.board == board && other.actions == actions
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }


    val code: String =
         if(color == PieceColor.WHITE)
             type.code.uppercase()
        else
            type.code




    fun checkMove(pair: Pair<Int, Int>): Action? {
        return if(board[pair]?.color == color){
            null
        } else if (board[pair] != null && board[pair]?.color != color && board[pair] !is King)
            Action(this.position, pair, ActionType.CAPTURE)
        else{
            Action(this.position, pair, ActionType.MOVE)
        }
    }




}