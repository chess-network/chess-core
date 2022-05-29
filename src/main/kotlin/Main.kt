import net.chess.engine.Client
import net.chess.enums.PieceColor
import net.chess.game.Board
import net.chess.game.Game
import net.chess.game.Player
import java.util.function.Function.identity

fun main(args: Array<String>) {
    val board = Board()

    val game = Game(board, Player(PieceColor.WHITE, "Dito"), Player(PieceColor.BLACK, "BOT"))
    game.init()
  //  game.startConsoleGame()

    println(game.convertToFEN())

}
