package net.chess.game

import net.chess.enums.ActionType
import net.chess.enums.PieceColor.*
import net.chess.piece.*

class ChessGame(val board: Board) {

    val events: MutableList<Event> = mutableListOf()

    fun executeAction(actionCode: String){
        if(actionCode.length != 6 && actionCode.length != 8) throw IllegalArgumentException("Invalid action code")
        val fromStr = actionCode.substring(0,2)
        val toStr = actionCode.substring(2,4)
        val typeStr = actionCode.substring(4,6)
        val targetPiecePositionStr = if(actionCode.length== 8) actionCode.substring(6,8) else null

        val from = Pair(Integer.parseInt(fromStr[0].toString()), Integer.parseInt(fromStr[1].toString()))
        val to = Pair(Integer.parseInt(toStr[0].toString()), Integer.parseInt(toStr[1].toString()))
        val type = ActionType.values().firstOrNull { it.code == typeStr } ?: throw IllegalArgumentException("Invalid action type")
        val causePiece = board[from] ?: throw IllegalArgumentException("Piece not found")
        val targetPiece =   if(targetPiecePositionStr != null){
            val targetPiecePosition = Pair(Integer.parseInt(targetPiecePositionStr[0].toString()), Integer.parseInt(targetPiecePositionStr[1].toString()))
            board[targetPiecePosition] ?: throw IllegalArgumentException("Piece not found")
        }
        else{
            null
        }
        val action = Action(from, to, type, targetPiece)
        causePiece.executeAction(action)
        events.add(Event(causePiece, action))
    }





    fun init(){
        for(i in 1..8 step 7){
            val color = if(i  == 1) WHITE else BLACK
            board[1 to i] = Rook(color, 1 to i, board)
            board[2 to i] = Knight(color, 2 to i, board)
            board[3 to i] = Bishop(color, 3 to i, board)
            board[4 to i] = Queen(color, 4 to i, board)
            board[5 to i] = King(color, 5 to i, board)
            board[6 to i] = Bishop(color, 6 to i, board)
            board[7 to i] = Knight(color, 7 to i, board)
            board[8 to i] = Rook(color, 8 to i, board)
        }
        for (i in 1..8) {
            board[i to 2] = Pawn(WHITE, i to 2, board)
            board[i to 7] = Pawn(BLACK, i to 7, board)
        }
    }






}