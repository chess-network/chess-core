import net.chess.engine.Client
import net.chess.enums.PieceColor
import net.chess.game.Board
import net.chess.game.Game
import net.chess.game.Player
import java.util.function.Function.identity

fun main(args: Array<String>) {


    val game = Game(Player(PieceColor.WHITE, "Dito"), Player(PieceColor.BLACK, "BOT"))
    game.load("rnbq1rk1/p3bppp/2pp1n2/1p2p3/P1P1P3/3B1N2/1PQP1PPP/RNB2RK1 w - a3 0 8")
 //  game.startConsoleGame()

    println(game.convertToFEN())

    game.board.print()


}
