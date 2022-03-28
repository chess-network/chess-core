package net.chess.piece

import net.chess.game.Action
import net.chess.enums.ActionType
import net.chess.enums.PieceColor

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RookTest: AbstractPieceTest() {

    @Test
    fun move(){
        val rook = Rook(PieceColor.WHITE, 3 to 4, board)
        rook.appear()

        val actions = rook.availableActions()
        assertEquals(14, actions.size)

        assertTrue(actions.contains(Action(rook.position, 3 to 2, ActionType.MOVE )))
        assertTrue(actions.contains(Action(rook.position, 3 to 7, ActionType.MOVE )))

        assertTrue(actions.contains(Action(rook.position, 6 to 4, ActionType.MOVE )))
        assertTrue(actions.contains(Action(rook.position, 1 to 4, ActionType.MOVE )))
    }

    @Test
    fun capture(){
        val rook = Rook(PieceColor.WHITE, 5 to 5, board)
        rook.appear()

        val pawn = Pawn(PieceColor.BLACK, 3 to 5, board)
        pawn.appear()
        val rook2 = Pawn(PieceColor.BLACK, 5 to 2, board)
      rook2.appear()
        val actions = rook.availableActions()
        val moves = actions.count { it.type == ActionType.MOVE }
        val captures = actions.count { it.type == ActionType.CAPTURE }
        assertEquals(9, moves)
        assertEquals(2, captures)


    }
}