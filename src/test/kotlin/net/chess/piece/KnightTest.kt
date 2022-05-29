package net.chess.piece

import net.chess.game.Action
import net.chess.enums.ActionType
import net.chess.enums.PieceColor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class KnightTest: AbstractPieceTest() {

    @Test
    fun moveWhite() {
        val knight = Knight(PieceColor.WHITE, 1 to 1, board)
        knight.appear()

        val availableActions = knight.availableActions()
        Assertions.assertEquals(
            listOf(
                Action(knight.position, 3 to 2, ActionType.MOVE),
                Action(knight.position, 2 to 3, ActionType.MOVE),

            ),
            availableActions
        )

        val action = availableActions.first {
            it.toPosition == 3 to 2
        }
        knight.executeAction(action)

        Assertions.assertEquals(
            listOf(
                Action(knight.position, 5 to 3, ActionType.MOVE),
                Action(knight.position, 1 to 3, ActionType.MOVE),
                Action(knight.position, 5 to 1, ActionType.MOVE),
                Action(knight.position, 1 to 1, ActionType.MOVE),
                Action(knight.position, 4 to 4, ActionType.MOVE),
                Action(knight.position, 2 to 4, ActionType.MOVE)
            ),
            knight.availableActions()
        )

        Assertions.assertEquals(
            listOf("Put (1, 1): WHITE Knight", "Remove (1, 1): WHITE Knight", "Put (3, 2): WHITE Knight"),
            messages
        )
    }

    @Test
    fun moveBlack() {
        val knight = Knight(PieceColor.BLACK, 1 to 1, board)
        knight.appear()

        val availableActions = knight.availableActions()
        Assertions.assertEquals(
            listOf(
                Action(knight.position, 3 to 2, ActionType.MOVE),
                Action(knight.position, 2 to 3, ActionType.MOVE)

            ),
            availableActions
        )

        val action = availableActions.first {
            it.toPosition == 3 to 2
        }
        knight.executeAction(action)

        Assertions.assertEquals(
            listOf(
                Action(knight.position, 5 to 3, ActionType.MOVE),
                Action(knight.position, 1 to 3, ActionType.MOVE),
                Action(knight.position, 5 to 1, ActionType.MOVE),
                Action(knight.position, 1 to 1, ActionType.MOVE),
                Action(knight.position, 4 to 4, ActionType.MOVE),
                Action(knight.position, 2 to 4, ActionType.MOVE)
            ),
            knight.availableActions()
        )

        Assertions.assertEquals(
            listOf("Put (1, 1): BLACK Knight", "Remove (1, 1): BLACK Knight", "Put (3, 2): BLACK Knight"),
            messages
        )
    }

    @Test
    fun captureWhite() {
        val knight = Knight(PieceColor.WHITE, 1 to 1, board)
        knight.appear()
        val knight2 = Knight(PieceColor.BLACK, 3 to 2, board)
        knight2.appear()

        val actions = knight.availableActions()
        Assertions.assertEquals(
            listOf(
                Action(knight.position, 3 to 2, ActionType.CAPTURE),
                Action(knight.position, 2 to 3, ActionType.MOVE)
            ),
            actions
        )
        val action = actions.first {
            it.toPosition == 3 to 2
        }
        Assertions.assertEquals(board.containsValue(knight2), true)

        knight.executeAction(action)

        Assertions.assertEquals(
            listOf(
                Action(knight.position, 5 to 3, ActionType.MOVE),
                Action(knight.position, 1 to 3, ActionType.MOVE),
                Action(knight.position, 5 to 1, ActionType.MOVE),
                Action(knight.position, 1 to 1, ActionType.MOVE),
                Action(knight.position, 4 to 4, ActionType.MOVE),
                Action(knight.position, 2 to 4, ActionType.MOVE)

            ),
            knight.availableActions()
        )
        Assertions.assertEquals(board.containsValue(knight2), false)

        Assertions.assertEquals(
            listOf(
                "Put (1, 1): WHITE Knight",
                "Put (3, 2): BLACK Knight",
                "Remove (3, 2): BLACK Knight",
                "Remove (1, 1): WHITE Knight",
                "Put (3, 2): WHITE Knight"
            ),
            messages
        )
    }

    @Test
    fun captureBlack() {
        val knight = Knight(PieceColor.BLACK, 1 to 1, board)
        knight.appear()
        val knight2 = Knight(PieceColor.WHITE, 3 to 2, board)
        knight2.appear()

        val actions = knight.availableActions()
        Assertions.assertEquals(
            listOf(
                Action(knight.position, 3 to 2, ActionType.CAPTURE),
                Action(knight.position, 2 to 3, ActionType.MOVE)
            ),
            actions
        )
        val action = actions.first {
            it.toPosition == 3 to 2
        }
        Assertions.assertEquals(board.containsValue(knight2), true)

        knight.executeAction(action)

        Assertions.assertEquals(
            listOf(
                Action(knight.position, 5 to 3, ActionType.MOVE),
                Action(knight.position, 1 to 3, ActionType.MOVE),
                Action(knight.position, 5 to 1, ActionType.MOVE),
                Action(knight.position, 1 to 1, ActionType.MOVE),
                Action(knight.position, 4 to 4, ActionType.MOVE),
                Action(knight.position, 2 to 4, ActionType.MOVE)
            ),
            knight.availableActions()
        )
        Assertions.assertEquals(board.containsValue(knight2), false)

        Assertions.assertEquals(
            listOf(
                "Put (1, 1): BLACK Knight",
                "Put (3, 2): WHITE Knight",
                "Remove (3, 2): WHITE Knight",
                "Remove (1, 1): BLACK Knight",
                "Put (3, 2): BLACK Knight"
            ),
            messages
        )
    }

}