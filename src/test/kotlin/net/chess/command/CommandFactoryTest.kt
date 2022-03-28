package net.chess.command

import net.chess.Board
import net.chess.enums.CommandType
import net.chess.enums.PieceColor
import net.chess.piece.Knight
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CommandFactoryTest{

    @Test
    fun compilation(){
        val board = Board()

        val knight = Knight(PieceColor.BLACK, 4 to 4, board)
        knight.appear()

        val action = knight.availableActions()[1]
        val compile = CommandFactory.compile(CommandType.ACT, action)

        val decompile = CommandFactory.decompile(compile, board)

        assertEquals(decompile, CommandType.ACT to action)
    }

}