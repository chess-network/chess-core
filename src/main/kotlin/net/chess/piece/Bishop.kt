package net.chess.piece

import net.chess.game.Action
import net.chess.enums.ActionType
import net.chess.game.Board
import net.chess.enums.PieceColor
import net.chess.enums.PieceType

class Bishop(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board, PieceType.BISHOP)  {

    override fun availableActions(): List<Action> {
        val availableActions: MutableList<Action> = mutableListOf()
        var x = position.first
        var y = position.second

        while(++x in 1..8 && ++y in 1..8){
            checkMove(x to y)?.let { availableActions.add(it) }
            if(board[x to y] != null){
                break
            }
        }
        x = position.first
        y = position.second
        while(--x in 1..8 && ++y in 1..8){
            checkMove(x to y)?.let { availableActions.add(it) }
            if(board[x to y] != null){
                break
            }
        }
        x = position.first
        y = position.second
        while(++x in 1..8 && --y in 1..8){
            checkMove(x to y)?.let { availableActions.add(it) }
            if(board[x to y] != null){
                break
            }
        }
        x = position.first
        y = position.second
        while(--x in 1..8 && --y in 1..8){
            checkMove(x to y)?.let { availableActions.add(it) }
            if(board[x to y] != null){
                break
            }
        }
        return availableActions
    }

    private fun checkMove(pair: Pair<Int, Int>): Action? {
        return if(board[pair]?.color == color){
            null
        } else if (board[pair] != null && board[pair]?.color != color && board[pair] !is King)
            Action(this.position,pair, ActionType.CAPTURE, board[pair])
        else{
            Action(this.position, pair, ActionType.MOVE)
        }
    }


}