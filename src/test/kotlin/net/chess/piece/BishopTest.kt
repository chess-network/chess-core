package net.chess.piece

import net.chess.Action
import net.chess.ActionType
import net.chess.PieceColor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BishopTest : AbstractPieceTest() {


    @Test
    fun moveWhite() {
        val bishop = Bishop(PieceColor.WHITE, 4 to 4, board)
        bishop.appear()

        val availableActions = bishop.availableActions()
        Assertions.assertEquals(
            listOf(
                Action(5 to 5, ActionType.MOVE, null),
                Action(6 to 6, ActionType.MOVE, null),
                Action(7 to 7, ActionType.MOVE, null),
                Action(8 to 8, ActionType.MOVE, null),
                Action(3 to 5, ActionType.MOVE, null),
                Action(2 to 6, ActionType.MOVE, null),
                Action(1 to 7, ActionType.MOVE, null),
                Action(5 to 3, ActionType.MOVE, null),
                Action(6 to 2, ActionType.MOVE, null),
                Action(7 to 1, ActionType.MOVE, null),
                Action(3 to 3, ActionType.MOVE, null),
                Action(2 to 2, ActionType.MOVE, null),
                Action(1 to 1, ActionType.MOVE, null)




            ),
            availableActions
        )

        val action = availableActions.first {
            it.position == 5 to 5
        }
        bishop.executeAction(action)

        Assertions.assertEquals(
            listOf(
                Action(6 to 6, ActionType.MOVE, null),
                Action(7 to 7, ActionType.MOVE, null),
                Action(8 to 8, ActionType.MOVE, null),
                Action(4 to 6, ActionType.MOVE, null),
                Action(3 to 7, ActionType.MOVE, null),
                Action(2 to 8, ActionType.MOVE, null),
                Action(6 to 4, ActionType.MOVE, null),
                Action(7 to 3, ActionType.MOVE, null),
                Action(8 to 2, ActionType.MOVE, null),
                Action(4 to 4, ActionType.MOVE, null),
                Action(3 to 3, ActionType.MOVE, null),
                Action(2 to 2, ActionType.MOVE, null),
                Action(1 to 1, ActionType.MOVE, null)
            ),
            bishop.availableActions()
        )

        Assertions.assertEquals(
            listOf("Put (4, 4): WHITE Bishop", "Remove (4, 4): WHITE Bishop", "Put (5, 5): WHITE Bishop"),
            messages
        )
    }
    @Test
    fun moveBlack() {
        val bishop = Bishop(PieceColor.BLACK, 4 to 4, board)
        bishop.appear()

        val availableActions = bishop.availableActions()
        Assertions.assertEquals(
            listOf(
                Action(5 to 5, ActionType.MOVE, null),
                Action(6 to 6, ActionType.MOVE, null),
                Action(7 to 7, ActionType.MOVE, null),
                Action(8 to 8, ActionType.MOVE, null),
                Action(3 to 5, ActionType.MOVE, null),
                Action(2 to 6, ActionType.MOVE, null),
                Action(1 to 7, ActionType.MOVE, null),
                Action(5 to 3, ActionType.MOVE, null),
                Action(6 to 2, ActionType.MOVE, null),
                Action(7 to 1, ActionType.MOVE, null),
                Action(3 to 3, ActionType.MOVE, null),
                Action(2 to 2, ActionType.MOVE, null),
                Action(1 to 1, ActionType.MOVE, null)
            ),
            availableActions
        )

        val action = availableActions.first {
            it.position == 5 to 5
        }
        bishop.executeAction(action)

        Assertions.assertEquals(
            listOf(
                Action(6 to 6, ActionType.MOVE, null),
                Action(7 to 7, ActionType.MOVE, null),
                Action(8 to 8, ActionType.MOVE, null),
                Action(4 to 6, ActionType.MOVE, null),
                Action(3 to 7, ActionType.MOVE, null),
                Action(2 to 8, ActionType.MOVE, null),
                Action(6 to 4, ActionType.MOVE, null),
                Action(7 to 3, ActionType.MOVE, null),
                Action(8 to 2, ActionType.MOVE, null),
                Action(4 to 4, ActionType.MOVE, null),
                Action(3 to 3, ActionType.MOVE, null),
                Action(2 to 2, ActionType.MOVE, null),
                Action(1 to 1, ActionType.MOVE, null)
            ),
            bishop.availableActions()
        )

        Assertions.assertEquals(
            listOf("Put (4, 4): BLACK Bishop", "Remove (4, 4): BLACK Bishop", "Put (5, 5): BLACK Bishop"),
            messages
        )
    }

    @Test
    fun captureWhite() {
        val bishop = Bishop(PieceColor.WHITE, 1 to 1, board)
        bishop.appear()
        val bishop2 = Bishop(PieceColor.BLACK, 2 to 2, board)
        bishop2.appear()

        val actions = bishop.availableActions()
        Assertions.assertEquals(
            listOf(
                Action(2 to 2, ActionType.CAPTURE, bishop2),
                Action(3 to 3, ActionType.MOVE, null),
                Action(4 to 4, ActionType.MOVE, null),
                Action(5 to 5, ActionType.MOVE, null),
                Action(6 to 6, ActionType.MOVE, null),
                Action(7 to 7, ActionType.MOVE, null),
                Action(8 to 8, ActionType.MOVE, null),
            ),
            actions
        )
        val action = actions.first {
            it.position == 2 to 2
        }
        Assertions.assertEquals(board.containsValue(bishop2), true)

        bishop.executeAction(action)

        Assertions.assertEquals(
            listOf(

                Action(3 to 3, ActionType.MOVE, null),
                Action(4 to 4, ActionType.MOVE, null),
                Action(5 to 5, ActionType.MOVE, null),
                Action(6 to 6, ActionType.MOVE, null),
                Action(7 to 7, ActionType.MOVE, null),
                Action(8 to 8, ActionType.MOVE, null),
                Action(1 to 3, ActionType.MOVE, null),
                Action(3 to 1, ActionType.MOVE, null),
                Action(1 to 1, ActionType.MOVE, null),
            ),
            bishop.availableActions()
        )
        Assertions.assertEquals(board.containsValue(bishop2), false)

        Assertions.assertEquals(
            listOf(
                "Put (1, 1): WHITE Bishop",
                "Put (2, 2): BLACK Bishop",
                "Remove (2, 2): BLACK Bishop",
                "Remove (1, 1): WHITE Bishop",
                "Put (2, 2): WHITE Bishop"
            ),
            messages
        )
    }

    @Test
    fun captureBlack() {
        val bishop = Bishop(PieceColor.BLACK, 1 to 1, board)
        bishop.appear()
        val bishop2 = Bishop(PieceColor.WHITE, 2 to 2, board)
        bishop2.appear()

        val actions = bishop.availableActions()
        Assertions.assertEquals(
            listOf(
                Action(2 to 2, ActionType.CAPTURE, bishop2),
                Action(3 to 3, ActionType.MOVE, null),
                Action(4 to 4, ActionType.MOVE, null),
                Action(5 to 5, ActionType.MOVE, null),
                Action(6 to 6, ActionType.MOVE, null),
                Action(7 to 7, ActionType.MOVE, null),
                Action(8 to 8, ActionType.MOVE, null),
            ),
            actions
        )
        val action = actions.first {
            it.position == 2 to 2
        }
        Assertions.assertEquals(board.containsValue(bishop2), true)

        bishop.executeAction(action)

        Assertions.assertEquals(
            listOf(
                Action(3 to 3, ActionType.MOVE, null),
                Action(4 to 4, ActionType.MOVE, null),
                Action(5 to 5, ActionType.MOVE, null),
                Action(6 to 6, ActionType.MOVE, null),
                Action(7 to 7, ActionType.MOVE, null),
                Action(8 to 8, ActionType.MOVE, null),
                Action(1 to 3, ActionType.MOVE, null),
                Action(3 to 1, ActionType.MOVE, null),
                Action(1 to 1, ActionType.MOVE, null),
            ),
            bishop.availableActions()
        )
        Assertions.assertEquals(board.containsValue(bishop2), false)

        Assertions.assertEquals(
            listOf(
                "Put (1, 1): BLACK Bishop",
                "Put (2, 2): WHITE Bishop",
                "Remove (2, 2): WHITE Bishop",
                "Remove (1, 1): BLACK Bishop",
                "Put (2, 2): BLACK Bishop"
            ),
            messages
        )
    }
}
