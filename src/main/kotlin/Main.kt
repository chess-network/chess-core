import net.chess.game.Board
import net.chess.game.ChessGame

fun main(args: Array<String>) {
    val board = Board()

    val chessGame = ChessGame(board)
    chessGame.init()
    chessGame.executeAction("1214MV")
    chessGame.executeAction("2725MV")
    chessGame.executeAction("1425CP25")

    board.print()
}
