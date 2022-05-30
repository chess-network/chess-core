package net.chess.game

import net.chess.enums.ActionType
import net.chess.enums.PieceColor
import net.chess.enums.PieceColor.*
import net.chess.enums.PieceType
import net.chess.piece.*

class Game(private val whitePlayer: Player, private val blackPlayer: Player) {

    private val events: MutableList<Event> = mutableListOf()
    lateinit var currentPlayer: Player
    lateinit var board: Board

    var halfMoves = 0
    var fullMoves = 1

    fun startConsoleGame(){
        init()
        while (true) {
          board.print()
            while (true) {
                print("Enter command: ")
                val command = readLine() ?: continue
                if (command == "exit") {
                    break
                }

                try {
                    executeAction(command)
                } catch (e: IllegalArgumentException) {
                    println( "Wrong command: ${e.message}" )
                    continue
                }
                break
            }

        }
    }

    //TODO add
    fun load(FEN: String){
        board = Board()
        val split: List<String> = FEN.split(" ")
        val positionString: String = split.first()
        val positionsList: List<String> = positionString.split("/")
        var currentRow=8;
        positionsList.forEach { s->

            var lastPos=1
            for(i in s.indices){
                if(s[i].isDigit()){

                    lastPos+=s[i].digitToInt()
                }else{

                    val piece =
                        if (s[i].isUpperCase())
                            getFigure(s[i].lowercase(), board, WHITE, Pair(lastPos, currentRow))
                        else
                            getFigure(s[i].lowercase(), board, BLACK, Pair(lastPos, currentRow))
                    board[lastPos to currentRow]=piece;
                    lastPos++;
                }
            }
            currentRow--;
        }
        currentPlayer =if(split[1]=="w")  whitePlayer else blackPlayer;
        val enPessantPos = split[3]
        if(enPessantPos !="-"){
          board.enPassantTarget=board[Pair(enPessantPos[1].digitToInt()-1 , enPessantPos[0].code-96)]
        }

        halfMoves= split[4].toInt();
        fullMoves= split[5].toInt();
    }
    fun getFigure (figure:String,board: Board,color:PieceColor,position:Pair<Int,Int>):AbstractPiece?{
      return when(figure){
            PieceType.BISHOP.code ->Bishop(color,position,board)
            PieceType.PAWN.code ->Pawn(color,position,board)
            PieceType.KNIGHT.code ->Knight(color,position,board)
            PieceType.ROOK.code ->Rook(color,position,board)
            PieceType.QUEEN.code ->Queen(color,position,board)
            PieceType.KING.code ->King(color,position,board)
          else -> null;
      }
    }

    fun executeAction(actionCode: String){
        if(actionCode.length != 6 && actionCode.length != 8) throw IllegalArgumentException("Invalid action code")
        val fromStr = actionCode.substring(0,2)
        val toStr = actionCode.substring(2,4)
        val typeStr = actionCode.substring(4,6)
        val targetPiecePositionStr = if(actionCode.length== 8) actionCode.substring(6,8) else null

        val from = Pair(Integer.parseInt(fromStr[0].toString()), Integer.parseInt(fromStr[1].toString()))
        val to = Pair(Integer.parseInt(toStr[0].toString()), Integer.parseInt(toStr[1].toString()))
        val type = ActionType.values().firstOrNull { it.code == typeStr } ?: throw IllegalArgumentException("Invalid action type")
        val causePiece = board[from] ?: throw IllegalArgumentException("Piece not found")
        if(causePiece.color != currentPlayer.color) throw IllegalArgumentException("Wrong player")


        val targetPiece =   if(targetPiecePositionStr != null){
            val targetPiecePosition = Pair(Integer.parseInt(targetPiecePositionStr[0].toString()), Integer.parseInt(targetPiecePositionStr[1].toString()))
            board[targetPiecePosition] ?: throw IllegalArgumentException("Piece not found")
        }
        else{
            null
        }
        val action = Action(from, to, type, targetPiece)
        causePiece.executeAction(action)
        if(action.type == ActionType.CAPTURE ||  causePiece is Pawn){
            halfMoves = 0
        }
        else{
            halfMoves++
        }
        events.add(Event(causePiece, action))
    }


    fun convertToFEN(): String{
        var fen = ""
        var emptyCount = 0
        for(i in 8 downTo 1) {
            for (j in 1..8) {
                val code = board[j to i]?.code
                if(code == null) emptyCount++
                else if(emptyCount == 0) fen += code
                else{
                    fen += emptyCount.toString()
                    emptyCount = 0
                    fen += code
                }
            }
            if(emptyCount != 0){
                fen += emptyCount.toString()
                emptyCount = 0
            }
            if(i != 1) fen += "/"
        }
        fen += " " + currentPlayer.color.code + " "


        var CanCastling = false
        if(board[8 to 1]?.availableActions()?.any {
                it.type == ActionType.CASTLING
            } == true){
            fen += "K"
            CanCastling = true
        }
        if(board[1 to 1]?.availableActions()?.any {
                it.type == ActionType.CASTLING
            } == true){
            fen += "Q"
            CanCastling = true
        }
        if(board[8 to 8]?.availableActions()?.any {
                it.type == ActionType.CASTLING
            } == true){
            fen += "k"
            CanCastling = true
        }
        if(board[1 to 8]?.availableActions()?.any {
                it.type == ActionType.CASTLING
            } == true){
            fen += "q"
            CanCastling = true
        }
        if(!CanCastling){
            fen += "-"
        }
        fen += " "
        fen += if(board.enPassantTarget != null){
            board.enPassantTarget!!.positionCode
        } else{
            "-"
        }


        fen += " $halfMoves $fullMoves"

        return fen

    }

    fun giveTurn(){
        if(whitePlayer.canMove){
            whitePlayer.canMove = false
            blackPlayer.canMove = true
            currentPlayer = blackPlayer
            fullMoves++
        }else{
            whitePlayer.canMove = true
            blackPlayer.canMove = false
            currentPlayer = whitePlayer
        }
    }





    fun init(){
        if(blackPlayer.color != BLACK || whitePlayer.color != WHITE) throw IllegalArgumentException("Invalid player color")

        board = Board()
        whitePlayer.canMove = true
        currentPlayer = whitePlayer

        for(i in 1..8 step 7){
            val color = if(i  == 1) WHITE else BLACK
            board[1 to i] = Rook(color, 1 to i, board)
            board[2 to i] = Knight(color, 2 to i, board)
            board[3 to i] = Bishop(color, 3 to i, board)
            board[4 to i] = Queen(color, 4 to i, board)
            board[5 to i] = King(color, 5 to i, board)
            board[6 to i] = Bishop(color, 6 to i, board)
            board[7 to i] = Knight(color, 7 to i, board)
            board[8 to i] = Rook(color, 8 to i, board)
        }
        for (i in 1..8) {
            board[i to 2] = Pawn(WHITE, i to 2, board)
            board[i to 7] = Pawn(BLACK, i to 7, board)
        }
    }






}