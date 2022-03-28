import net.chess.Action
import net.chess.Board
import net.chess.command.CommandFactory
import net.chess.enums.CommandType
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
    var mainCommand = ""


    while (mainCommand != "END") {
        print("Enter command: ")
        val input = readLine()!!
        val commands = input.split(" ")
        mainCommand = commands[0]
        when(mainCommand){
            "GET" -> {
                val selectedSquare = commands[1]
                val pair = Board.squareToPair(selectedSquare)
                val piece = board[pair]
                if(piece != null){
                    piece.availableActions().forEach {
                        println(Action.actionToCode(it))
                    }
                }
                else{
                    println("No piece on this square")
                }
            }
        }
    }


}
