package net.chess.piece

import net.chess.enums.ActionType
import net.chess.enums.PieceColor
import net.chess.enums.PieceType
import net.chess.game.Action
import net.chess.game.Board


class Pawn(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board, PieceType.PAWN) {

    companion object {
        fun move(position: Pair<Int, Int>, color: PieceColor): Pair<Int, Int>? {
            return if (color == PieceColor.WHITE) {
                if (position.second in 7..8)
                    null
                else Pair(position.first, position.second + 1)
            } else
                if (position.second in 1..2)
                    null
                else Pair(position.first, position.second - 1)
        }

        fun doubleMove(position: Pair<Int, Int>, color: PieceColor): Pair<Int, Int>? {
            return if (color == PieceColor.WHITE) {
                if (position.second != 2)
                    null
                else Pair(position.first, position.second + 2)
            } else
                if (position.second != 7)
                    null
                else Pair(position.first, position.second - 2)
        }

        fun capture(position: Pair<Int, Int>, color: PieceColor): Pair<Int, Int> {
            return if (color == PieceColor.WHITE) {
                if (position.second == 8)
                    position
                else Pair(position.first + 1, position.second + 1)
            } else
                if (position.second == 1)
                    position
                else Pair(position.first - 1, position.second - 1)
        }
    }


    override fun availableActions(): List<Action> {

        val moves = (if (actions.isEmpty())
            listOfNotNull(move(position, color), doubleMove(position, color))
        else
            listOfNotNull(move(position, color)))
            .filter {
                !board.containsKey(it)
            }.map {
                Action(this.position, it, ActionType.MOVE)
            }

        val captures = capture(position, color).let {
            val piece = board[it]
            if (piece != null && piece.color != color && piece !is King)
                listOf(Action(this.position, it, ActionType.CAPTURE))
            else
                emptyList()
        }
        val enPassantTarget = board.enPassantTarget
         val enPassant =    if(enPassantTarget != null &&
            enPassantTarget.color != color &&
            enPassantTarget.position.second == position.second &&
            enPassantTarget.position.first - position.first == 1
        ){
           val toPosition = if(color == PieceColor.WHITE)
               Pair(enPassantTarget.position.first + 1, enPassantTarget.position.second)
           else
               Pair(enPassantTarget.position.first - 1, enPassantTarget.position.second)
            listOf(Action(this.position, toPosition, ActionType.EN_PASSANT, enPassantTarget))
        }
        else emptyList()


        val promotionCondition =
            if (position.second == 2 && color == PieceColor.BLACK) -1 else if (position.second == 7 && color == PieceColor.WHITE) 1 else 0

        val promotion = if (promotionCondition != 0) {
            val newPosition = position.first to (position.second + promotionCondition)

            listOf(
                Action(this.position, newPosition, ActionType.PROMOTION),
                Action(this.position, newPosition, ActionType.PROMOTION),
                Action(this.position, newPosition, ActionType.PROMOTION),
                Action(this.position, newPosition, ActionType.PROMOTION)
            )
        } else emptyList()

        return moves + captures + enPassant + promotion
    }


}