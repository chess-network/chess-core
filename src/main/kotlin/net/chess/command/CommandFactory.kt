package net.chess.command

import net.chess.Action
import net.chess.Board
import net.chess.enums.ActionType
import net.chess.enums.CommandType
import net.chess.enums.PieceColor
import net.chess.enums.PieceType
import net.chess.piece.*

object CommandFactory {


    fun compile(type: CommandType, any: Any?): Int {
        val commandStr = when (type) {
            CommandType.ADD -> {
                if (any is AbstractPiece) {
                    "${any.color.ordinal + 1}${any.position.first}${any.position.second}${any.type.ordinal + 1}"
                } else throw  IllegalArgumentException("CommandFactory.compile: any is not Piece")
            }
             CommandType.GET -> {
                 if (any is AbstractPiece) {
                     "${any.position.first}${any.position.second}"
                 } else throw  IllegalArgumentException("CommandFactory.compile: any is not Piece")
             }
            CommandType.ACT -> {
                if (any is Action) {
                    "${any.type.ordinal + 1}${any.fromPosition.first}${any.fromPosition.second}${any.toPosition.first}${any.toPosition.second}${any.target?.position?.first ?: 0}${any.target?.position?.second ?: 0}"
                } else throw IllegalArgumentException("CommandFactory.compile: any is not Action")
            }
        }

        return "${type.ordinal + 1}$commandStr".toInt()
    }



    fun decompile(command: Int, board: Board): Pair<CommandType, Any?> {
        val commandStr = command.toString()

        val commandType = CommandType.values()[commandStr[0].digitToInt() - 1]
        val any =   when(commandType){
            CommandType.ADD -> {
                val color = PieceColor.values()[commandStr[1].digitToInt() - 1]
                val position = Pair(commandStr[2].digitToInt(), commandStr[3].digitToInt())
                when(PieceType.values()[commandStr[4].digitToInt() - 1]){
                    PieceType.PAWN ->Pawn(color, position, board)
                    PieceType.ROOK ->  Rook(color, position, board)
                    PieceType.KNIGHT -> Knight(color, position, board)
                    PieceType.BISHOP -> Bishop(color, position, board)
                    PieceType.QUEEN -> Queen(color, position, board)
                    PieceType.KING -> King(color, position, board)
                }  as Any
            }
          CommandType.GET ->{
              val position = Pair(commandStr[1].digitToInt(), commandStr[2].digitToInt())
              board[position] as Any?
          }
          CommandType.ACT -> {
              val actionType = ActionType.values()[commandStr[1].digitToInt() - 1]
              val fromPosition = Pair(commandStr[2].digitToInt(), commandStr[3].digitToInt())
              val toPosition = Pair(commandStr[4].digitToInt(), commandStr[5].digitToInt())
              val targetPosition = Pair(commandStr[6].digitToInt(), commandStr[7].digitToInt())

              val target = board[targetPosition]

            Action(fromPosition, toPosition, actionType, target) as Any
          }
        }
        return Pair(commandType, any)
    }

    fun run(command: Int, board: Board){

        val decompile = decompile(command, board)
        when(decompile.first){
            CommandType.ADD -> {
                val any = decompile.second
                if(any is AbstractPiece){
                    any.appear()
                }
                else throw IllegalArgumentException("CommandFactory.run: command not have piece")
            }
            CommandType.GET -> {
                val any = decompile.second
                if(any is AbstractPiece){
                    any.availableActions().forEach {
                        board.showAction(it)
                    }
                }
                else throw IllegalArgumentException("CommandFactory.run: command not have piece")
            }
            CommandType.ACT -> {
                val any = decompile.second
                if(any is Action){
                    val fromPosition = any.fromPosition
                    val piece = board[fromPosition]
                        ?: throw IllegalArgumentException("CommandFactory.run: board not have piece for execution")
                    piece.executeAction(any)
                }
                else throw IllegalArgumentException("CommandFactory.run: command not have action")
            }
        }

    }




}