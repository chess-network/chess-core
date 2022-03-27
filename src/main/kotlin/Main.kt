import net.chess.Board
import net.chess.PieceColor
import net.chess.piece.Knight

fun main(args: Array<String>) {
    val board = Board(
        {key, value -> println("Put $key: $value")},
        {key, value -> println("Delete $key: $value")}
    )

    val knight = Knight(PieceColor.BLACK, 4 to 4, board)
    knight.appear()
    println(knight.availableActions())


}