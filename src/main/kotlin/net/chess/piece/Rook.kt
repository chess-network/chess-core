package net.chess.piece

import net.chess.Action
import net.chess.ActionType
import net.chess.Board
import net.chess.PieceColor
import java.util.stream.IntStream

class Rook(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board, PieceType.ROOK) {

    companion object {
        fun move(board: Board, position: Pair<Int, Int>): List<Pair<Int, Int>> {

            var (bottom, top) = minMax(board, position, true)
            bottom++
            top--

            var (left, right) = minMax(board, position, false)
            left++
            right--


            val verticalMoves = IntStream.range(1, 9).mapToObj {
                position.first to it
            }.toList().filter {
                it != position && it.second in bottom..top
            }

            val horizontalMoves = IntStream.range(1, 9).mapToObj {
                it to position.second
            }.toList().filter {
                it != position && it.first in left..right
            }
            return horizontalMoves + verticalMoves
        }

        private fun minMax(
            board: Board,
            position: Pair<Int, Int>,
            vertical: Boolean
        ): Pair<Int, Int> {
            val line = board.keys.filter {
                if (vertical) (it.first == position.first) else (it.second == position.second)
            }.filterNot {
                if (vertical) {
                    it.second == position.second
                } else {
                    it.first == position.first
                }
            }
            val ints = line.map {
                if (vertical)
                    it.second
                else
                    it.first
            }
            var min = ints.minOrNull() ?: 0
            var max = ints.maxOrNull() ?: 9
            if (min == max) {
                val number = if (vertical) position.second else position.first
                if (min < number)
                    max = 9
                else
                    min = 0
            }

            return min to max
        }


    }

    override fun availableActions(): List<Action> {
        val moves = move(board, position).map {
            Action(it, ActionType.MOVE)
        }

        val (top, bottom) = minMax(board, position, true)
        val topPair = position.first to top
        val bottomPair = position.first to bottom

        val (left, right) = minMax(board, position, false)
        val leftPair = left to position.second
        val rightPair = right to position.second

        val captures = listOf(topPair, bottomPair, leftPair, rightPair).mapNotNull {
            board[it]
        }.filter {
            it.color != color && it !is King
        }.map {
            Action(it.position, ActionType.CAPTURE, it)
        }

        return moves + captures
    }


}