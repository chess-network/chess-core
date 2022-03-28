import net.chess.game.Board
import net.chess.enums.PieceColor
import net.chess.piece.Knight

fun main(args: Array<String>) {
    val board = Board()

    val knight = Knight(PieceColor.BLACK, 4 to 4, board)

    val knight2 = Knight(PieceColor.WHITE, 2 to 3, board)


    val knight3 = Knight(PieceColor.BLACK, 2 to 1, board)
    knight3.appear()

    knight.appear()
    knight2.appear()
    board.print()




}
