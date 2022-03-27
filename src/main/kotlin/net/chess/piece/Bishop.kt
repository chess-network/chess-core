package net.chess.piece

import net.chess.Action
import net.chess.ActionType
import net.chess.Board
import net.chess.PieceColor

class Bishop(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board)  {

    override fun availableActions(): List<Action> {
        val availableActions: MutableList<Action> = mutableListOf()
        var x = position.first
        var y = position.second

        while(++x in 1..8 && ++y in 1..8){
            checkMove(x to y)?.let { availableActions.add(it) }
        }
        x = position.first
        y = position.second
        while(--x in 1..8 && ++y in 1..8){
            checkMove(x to y)?.let { availableActions.add(it) }
        }
        x = position.first
        y = position.second
        while(++x in 1..8 && --y in 1..8){
            checkMove(x to y)?.let { availableActions.add(it) }
        }
        x = position.first
        y = position.second
        while(--x in 1..8 && --y in 1..8){
            checkMove(x to y)?.let { availableActions.add(it) }
        }
        return availableActions
    }

    private fun checkMove(pair: Pair<Int, Int>): Action? {
        return if(board[pair]?.color == color){
            null
        } else if (board[pair] != null && board[pair]?.color != color && board[pair] !is King)
            Action(pair, ActionType.CAPTURE, board[pair])
        else{
            Action(pair, ActionType.MOVE)
        }
    }


}