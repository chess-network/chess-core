package net.chess.command

import net.chess.Action
import net.chess.Board
import net.chess.enums.ActionType
import net.chess.enums.CommandType

object CommandFactory {

    fun compile(type: CommandType, action: Action): Int =
        "${type.ordinal + 1}${action.type.ordinal + 1}${action.fromPosition.first}${action.fromPosition.second}${action.toPosition.first}${action.toPosition.second}${action.target?.position?.first ?: 0}${action.target?.position?.second ?: 0}".toInt()

    fun decompile(command: Int, board: Board): Pair<CommandType, Action> {
        val commandStr = command.toString()

        val commandType = CommandType.values()[commandStr[0].digitToInt() - 1]
        val actionType = ActionType.values()[commandStr[1].digitToInt() - 1]
        val fromPosition = Pair(commandStr[2].digitToInt(), commandStr[3].digitToInt())
        val toPosition = Pair(commandStr[4].digitToInt(), commandStr[5].digitToInt())
        val targetPosition = Pair(commandStr[6].digitToInt(), commandStr[7].digitToInt())

        val target = board[targetPosition]

        val action = Action(fromPosition, toPosition, actionType, target)

        return Pair(commandType, action)
    }

}