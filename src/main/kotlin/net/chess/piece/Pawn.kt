package net.chess.piece

import net.chess.*


class Pawn(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board) {

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
        val moves = (if (history.isEmpty())
            listOfNotNull(move(position, color), doubleMove(position, color))
        else
            listOfNotNull(move(position, color)))
            .filter {
                !board.containsKey(it)
            }.map {
                Action(it, ActionType.MOVE)
            }

        val captures = capture(position, color).let {
            val piece = board[it]
            if (piece != null && piece.color != color && piece !is King)
                listOf(Action(it, ActionType.CAPTURE, piece))
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
                if (leftPiece is Pawn && !board.containsKey(passantList[2]) && leftPiece.history.size == 1)
                    Action(passantList[2], ActionType.EN_PASSANT, leftPiece)
                else null

            val rightPassant =
                if (rightPiece != null && !board.containsKey(passantList[3]) && rightPiece.history.size == 1)
                    Action(passantList[3], ActionType.EN_PASSANT, rightPiece)
                else null

            listOfNotNull(leftPassant, rightPassant)
        } else emptyList()


        val promotionCondition =
            if (position.second == 2 && color == PieceColor.BLACK) -1 else if (position.second == 7 && color == PieceColor.WHITE) 1 else 0

        val promotion = if (promotionCondition != 0) {
            val newPosition = position.first to (position.second + promotionCondition)

            listOf(
                Action(newPosition, ActionType.PROMOTION, Queen(color, newPosition, board)),
                Action(newPosition, ActionType.PROMOTION, Rook(color, newPosition, board)),
                Action(newPosition, ActionType.PROMOTION, Bishop(color, newPosition, board)),
                Action(newPosition, ActionType.PROMOTION, Knight(color, newPosition, board))
            )
        } else emptyList()

        return moves + captures + enPassant + promotion
    }

    override fun executeAction(action: Action) {
        validateAction(action)

        @Suppress("NON_EXHAUSTIVE_WHEN")
        when (action.type) {
            ActionType.MOVE -> board.move(position, action.position)
            ActionType.CAPTURE -> {

                if (action.target == null)
                    throw IllegalArgumentException("Target is null")

                board.remove(action.target.position)
                board.move(position, action.position)
            }
            ActionType.EN_PASSANT -> {

                if (action.target == null)
                    throw IllegalArgumentException("Target is null")

                board.remove(action.target.position)
                board.move(position, action.position)
            }
            ActionType.PROMOTION -> {
                board.remove(position)
                board[action.position] = action.target
            }
        }
        history.add(History(action))
    }
}