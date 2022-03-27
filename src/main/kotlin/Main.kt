import net.chess.Board
import net.chess.piece.Pawn
import net.chess.PieceColor

fun main(args: Array<String>) {
    val board = Board(
        {key, value -> println("Put $key: $value")},
        {key, value -> println("Delete $key: $value")}
    )
    val pawn = Pawn(PieceColor.WHITE, 1 to 2, board)
    val availableActions = pawn.availableActions()

    val action = availableActions[1]
    pawn.executeAction(action)


}