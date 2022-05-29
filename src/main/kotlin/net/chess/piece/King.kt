package net.chess.piece

import net.chess.enums.ActionType
import net.chess.game.Action
import net.chess.game.Board
import net.chess.enums.PieceColor
import net.chess.enums.PieceType

class King(
    color: PieceColor,
    position: Pair<Int, Int>,
    board: Board
) : AbstractPiece(color, position, board, PieceType.KING) {
    override fun availableActions(): List<Action> {
        val availableActions: MutableList<Action> = mutableListOf()

        listOf(
            position.first + 1 to position.second,
            position.first to position.second + 1,
            position.first to position.second - 1,
            position.first - 1 to position.second,
            position.first - 1 to position.second - 1,
            position.first - 1 to position.second + 1,
            position.first + 1 to position.second + 1,
            position.first + 1 to position.second - 1
        ).forEach {
            if (it.first in 1..8 && it.second in 1..8) {
                checkMove(it)?.let { it1 ->
                    checkForDanger(it1)?.let { it2 ->
                        availableActions.add(it2)
                    }
                }
            }
        }
        return availableActions
    }

    private fun checkForDanger(action: Action): Action? {
        if(action.type == ActionType.CAPTURE){
            board.remove(action.toPosition , action.targetPiece)
        }
        for(x in 1..8) {
            for(y in 1..8 ){
                if(board[x to y]?.color != color && board[x to y]?.availableActions()?.any { it.toPosition == action.toPosition } == true){
                    if(action.type == ActionType.CAPTURE){
                        action.targetPiece?.let { board.put(action.toPosition , it) }
                    }
                    return null
                }
            }
        }
        if(action.type == ActionType.CAPTURE){
            action.targetPiece?.let { board.put(action.toPosition , it) }
        }
        return action
    }


}