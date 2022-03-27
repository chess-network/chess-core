package net.chess.piece

import net.chess.PieceColor
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RookTest: AbstractPieceTest() {

    @Test
    fun move(){
        val rook = Rook(PieceColor.WHITE, 3 to 4, board)
        rook.appear()

        val actions = rook.availableActions()
        assertEquals(14, actions.size)

    }
}