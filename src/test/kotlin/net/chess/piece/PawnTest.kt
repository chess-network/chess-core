package net.chess.piece

import net.chess.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal

internal class PawnTest {


    private val messages = mutableListOf<String>()

    private val board = Board(
        { key, value ->
            messages.add("Put $key: ${value.color} ${value.javaClass.kotlin.simpleName}")
        },
        { key, value ->
            if (value != null) {
                messages.add("Remove $key: ${value.color} ${value.javaClass.kotlin.simpleName}")
            }
        },
    )

    @BeforeEach
    fun setUp() {
        board.clear()

    }


    @Test
    fun moveWhite() {
        val pawn = Pawn(PieceColor.WHITE, 1 to 2, board)
        pawn.appear()

        val availableActions = pawn.availableActions()
        assertEquals(
            listOf(
                Action(1 to 3, ActionType.MOVE, null),
                Action(1 to 4, ActionType.MOVE, null)
            ),
            availableActions
        )

        val action = availableActions.first {
            it.position == 1 to 4
        }
        pawn.executeAction(action)

        assertEquals(
            listOf(
                Action(1 to 5, ActionType.MOVE, null)
            ),
            pawn.availableActions()
        )

        assertEquals(
            listOf("Put (1, 2): WHITE Pawn", "Remove (1, 2): WHITE Pawn", "Put (1, 4): WHITE Pawn"),
            messages
        )
    }

    @Test
    fun moveBlack() {
        val pawn = Pawn(PieceColor.BLACK, 5 to 7, board)
        pawn.appear()

        val availableActions = pawn.availableActions()
        assertEquals(
            listOf(
                Action(5 to 6, ActionType.MOVE, null),
                Action(5 to 5, ActionType.MOVE, null)
            ),
            availableActions
        )

        val action = availableActions.first {
            it.position == 5 to 6
        }
        pawn.executeAction(action)


        assertEquals(
            listOf(
                Action(5 to 5, ActionType.MOVE, null)
            ),
            pawn.availableActions()
        )


        assertEquals(
            listOf("Put (5, 7): BLACK Pawn", "Remove (5, 7): BLACK Pawn", "Put (5, 6): BLACK Pawn"),
            messages
        )
    }

    @Test
    fun captureWhite() {
        val pawn = Pawn(PieceColor.WHITE, 1 to 2, board)
        pawn.appear()
        val pawn2 = Pawn(PieceColor.BLACK, 2 to 3, board)
        pawn2.appear()

        val actions = pawn.availableActions()
        assertEquals(
            listOf(
                Action(1 to 3, ActionType.MOVE, null),
                Action(1 to 4, ActionType.MOVE, null),
                Action(2 to 3, ActionType.CAPTURE, pawn2)
            ),
            actions
        )
        val action = actions.first {
            it.position == 2 to 3
        }
        assertEquals(board.containsValue(pawn2), true)

        pawn.executeAction(action)

        assertEquals(
            listOf(
                Action(2 to 4, ActionType.MOVE, null)
            ),
            pawn.availableActions()
        )
        assertEquals(board.containsValue(pawn2), false)

        assertEquals(
            listOf(
                "Put (1, 2): WHITE Pawn",
                "Put (2, 3): BLACK Pawn",
                "Remove (2, 3): BLACK Pawn",
                "Remove (1, 2): WHITE Pawn",
                "Put (2, 3): WHITE Pawn"
            ),
            messages
        )
    }

    @Test
    fun captureBlack() {
        val pawn = Pawn(PieceColor.BLACK, 6 to 5, board)
        pawn.appear()
        val pawn2 = Pawn(PieceColor.WHITE, 5 to 4, board)
        pawn2.appear()

        val actions = pawn.availableActions()
        assertEquals(
            listOf(
                Action(6 to 4, ActionType.MOVE, null),
                Action(5 to 4, ActionType.CAPTURE, pawn2)
            ),
            actions
        )
        val action = actions.first {
            it.position == 6 to 4
        }
        assertEquals(board.containsValue(pawn2), true)

        pawn.executeAction(action)

        assertEquals(
            listOf(
                Action(6 to 3, ActionType.MOVE, null)
            ),
            pawn.availableActions()
        )
        assertEquals(board.containsValue(pawn2), true)

        assertEquals(
            listOf(
                "Put (6, 5): BLACK Pawn",
                "Put (5, 4): WHITE Pawn",
                "Remove (6, 5): BLACK Pawn",
                "Put (6, 4): BLACK Pawn"
            ),
            messages
        )
    }

    @Test
    fun enPassant() {
        val pawn = Pawn(PieceColor.WHITE, 5 to 5, board)
        pawn.appear()

        val pawn2 = Pawn(PieceColor.BLACK, 4 to 7, board)
        pawn2.appear()
        pawn2.executeAction(Action(4 to 5, ActionType.MOVE, null))
        assertEquals(board.containsValue(pawn2), true)

        val actions = pawn.availableActions()
        assertEquals(
            listOf(
                Action(5 to 6, ActionType.MOVE, null),
                Action(4 to 6, ActionType.EN_PASSANT, pawn2)
            ),
            actions
        )
        val action = actions.first {
            it.position == 4 to 6
        }
        pawn.executeAction(action)

        assertEquals(
            listOf(
                Action(4 to 7, ActionType.MOVE, null)
            ),
            pawn.availableActions()
        )
        assertEquals(board.containsValue(pawn2), false)
    }

    @Test
    fun promotion() {

        val pawn = Pawn(PieceColor.WHITE, 3 to 7, board)
        pawn.appear()

        val pawn2 = Pawn(PieceColor.BLACK, 5 to 2, board)
        pawn2.appear()

        assertAll(
            { assert(pawn.availableActions().size == 4) },
            {
                assert(
                    pawn.availableActions()
                        .all { it.type == ActionType.PROMOTION && it.position == 3 to 8 && it.target != null })
            },
            {
                assertEquals(
                    pawn.availableActions().map { it.target!!.javaClass.kotlin.simpleName },
                    listOf("Queen", "Rook", "Bishop", "Knight")
                )
            }
        )

        assertAll(
            { assert(pawn2.availableActions().size == 4) },
            {
                assert(
                    pawn2.availableActions()
                        .all { it.type == ActionType.PROMOTION && it.position == 5 to 1 && it.target != null })
            },
            {
                assertEquals(
                    pawn2.availableActions().map { it.target!!.javaClass.kotlin.simpleName },
                    listOf("Queen", "Rook", "Bishop", "Knight")
                )
            }
        )

        pawn.availableActions()
        assertEquals(board.containsValue(pawn), true)
        assertEquals(board.containsValue(pawn2), true)

        val queen = Queen(PieceColor.WHITE, 3 to 8, board)
        pawn.executeAction(Action(3 to 8, ActionType.PROMOTION, queen))

        val rook = Rook(PieceColor.BLACK, 5 to 1, board)
        pawn2.executeAction(Action(5 to 1, ActionType.PROMOTION, rook))


        assertEquals(board.containsValue(pawn), false)
        assertEquals(board.containsValue(pawn2), false)

        assertEquals(board.containsValue(queen), true)
        assertEquals(board.containsValue(rook), true)

    }


}