package net.chess.piece

import net.chess.enums.ActionType
import net.chess.enums.PieceColor
import net.chess.enums.PieceType
import net.chess.game.Action
import net.chess.game.Board
import net.chess.game.History

abstract class AbstractPiece(
    val color: PieceColor,
    var position: Pair<Int, Int>,
    val board: Board,
    val type: PieceType,
    val history: MutableList<History> = mutableListOf()
) {



    abstract fun availableActions(): List<Action>

     fun executeAction(action: Action){
         validateAction(action)
         when (action.type) {
             ActionType.MOVE -> board.move(position, action.toPosition)
             ActionType.CAPTURE -> {

                 if (action.target == null)
                     throw IllegalArgumentException("Target is null")

                 board.remove(action.target.position)
                 board.move(position, action.toPosition)
             }
             ActionType.EN_PASSANT -> {

                 if (action.target == null)
                     throw IllegalArgumentException("Target is null")

                 board.remove(action.target.position)
                 board.move(position, action.toPosition)
             }
             ActionType.PROMOTION -> {
                 board.remove(position)
                 board[action.toPosition] = action.target
             }
             ActionType.CASTLING -> {
                 board.move(position, action.toPosition)
             }
         }

         history.add(History(action))
     }

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

    override fun hashCode(): Int {
        return super.hashCode()
    }

    private fun getNumber() = board.pieceNumber(code)

    val code: String = "${color.code}${type.code}"

    fun fullCode() = "${color.code}${type.code}${getNumber()}"

    fun checkMove(pair: Pair<Int, Int>): Action? {
        return if(board[pair]?.color == color){
            null
        } else if (board[pair] != null && board[pair]?.color != color && board[pair] !is King)
            Action(this.position,pair, ActionType.CAPTURE, board[pair])
        else{
            Action(this.position, pair, ActionType.MOVE)
        }
    }

}