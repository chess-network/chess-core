package net.chess

import net.chess.piece.AbstractPiece

data class Action(
    val position: Pair<Int, Int>,
    val type: ActionType,
    val target: AbstractPiece? = null
){
    companion object{
        fun actionToCode(action: Action): String =
            (Board.pairToSquare(action.position) + action.type.code  + (action.target?.fullCode ?: "NULL"))
        fun codeToAction(code: String, board: Board): Action{
            val chars = code.split("")
            val square = chars.subList(1,2).joinToString()
            val actionCode = chars.subList(3,4).joinToString()
            val targetCode = chars.subList(4,5).joinToString()

            val pair = Board.squareToPair(square)
            val actionType = ActionType.codeToActionType(actionCode)
            if(targetCode != "NULL"){

            }


            return Action(pair, actionType)
        }
    }
}