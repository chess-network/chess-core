import net.chess.game.Board
import net.chess.enums.PieceColor
import net.chess.piece.Bishop
import net.chess.piece.Knight

fun main(args: Array<String>) {
    val board = Board()

    val bishop = Bishop(PieceColor.BLACK, 4 to 4, board)
    bishop.appear()

    val knight = Knight(PieceColor.WHITE, 5 to 5, board)
knight.appear()
    val availableActions = bishop.availableActions()
    println(availableActions)


}
