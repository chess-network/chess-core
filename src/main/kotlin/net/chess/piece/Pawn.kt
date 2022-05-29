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


        //En Passant Pre Condition (0 - left   piece capture  | 1 - right piece capture) (2 - left Top/Bottom exist | 3 - right  Top/Bottom exist)
        val passantList = if (color == PieceColor.WHITE && position.second == 5)
            listOf(
                (position.first - 1) to 5,
                (position.first + 1) to 5,
                (position.first - 1) to 6,
                (position.first + 1) to 6
            )
        else if (color == PieceColor.BLACK && position.second == 4)
            listOf(
                (position.first - 1) to 4,
                (position.first + 1) to 4,
                (position.first - 1) to 3,
                (position.first + 1) to 3
            )
        else emptyList()

        val enPassant = if (passantList.isNotEmpty()) {
            val leftPiece = board[passantList[0]]
            val rightPiece = board[passantList[1]]

            val leftPassant =
                if (leftPiece is Pawn && !board.containsKey(passantList[2]) && leftPiece.actions.size == 1)
                    Action(this.position, passantList[2], ActionType.EN_PASSANT)
                else null

            val rightPassant =
                if (rightPiece != null && !board.containsKey(passantList[3]) && rightPiece.actions.size == 1)
                    Action(this.position, passantList[3], ActionType.EN_PASSANT)
                else null

            listOfNotNull(leftPassant, rightPassant)
        } else emptyList()


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