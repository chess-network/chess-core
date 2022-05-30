package net.chess.game

import net.chess.enums.ActionType
import net.chess.enums.PieceColor
import net.chess.piece.AbstractPiece
import net.chess.enums.PieceType
import net.chess.piece.Pawn

import java.util.*
import kotlin.math.abs

class Board : Hashtable<Pair<Int, Int>, AbstractPiece>() {






    private var onPutFun: (key: Pair<Int, Int>, value: AbstractPiece) -> (Unit) = { _, _ -> }
    private var onMoveFun: (from: Pair<Int, Int>, to: Pair<Int, Int>, value: AbstractPiece) -> (Unit) = { _, _, _ -> }
    private var onRemoveFun: (key: Pair<Int, Int>, value: AbstractPiece) -> (Unit) = { _, _ -> }
    private var onActionFun: (key: Pair<Int, Int>, value: AbstractPiece, action: Action) -> (Unit) = { _, _, _ -> }

    var enPassantTarget: AbstractPiece? = null


    fun onMove(onMove: (from: Pair<Int, Int>, to: Pair<Int, Int>, value: AbstractPiece) -> (Unit)) {
        onMoveFun = onMove
    }

    fun onPut(onPut: (key: Pair<Int, Int>, value: AbstractPiece) -> (Unit)) {
        onPutFun = onPut
    }

    fun onRemove(onRemove: (key: Pair<Int, Int>, value: AbstractPiece) -> (Unit)) {
        onRemoveFun = onRemove
    }

    fun onAction(onAction: (key: Pair<Int, Int>, value: AbstractPiece, action: Action) -> (Unit)) {
        onActionFun = onAction
    }


    companion object {
        fun squareToPair(square: String): Pair<Int, Int> = (square[0].code - 64) to (square[1].digitToInt())

        fun pairToSquare(pair: Pair<Int, Int>): String = "${(pair.first + 64).toChar()}${pair.second}"
    }

    fun countByColor(color: PieceColor): Int = values.count { it.color == color }

    fun countByType(type: PieceType): Int = values.count { it.type == type }

    fun countByCode(code: String): Int = values.count { it.code == code }


    fun executeAction(action: Action) {
        val key = action.fromPosition

        val piece = this[key] ?: throw IllegalArgumentException("No piece on square $key")
        enPassantTarget = null
        when (action.type) {
             ActionType.CASTLING -> move(action.fromPosition, action.toPosition)
            ActionType.MOVE -> {
                if(piece is Pawn && abs(action.fromPosition.second - action.toPosition.second) == 2) {
                    enPassantTarget = piece
                }
                move(action.fromPosition, action.toPosition)

            }
            ActionType.CAPTURE, ActionType.EN_PASSANT -> {
                if (action.targetPiece == null) throw IllegalArgumentException("No target piece")
                remove(action.targetPiece.position)
                move(action.fromPosition, action.toPosition)
            }
            ActionType.PROMOTION -> {
                if (action.targetPiece == null) throw IllegalArgumentException("No target piece")
                remove(action.fromPosition)
                put(action.toPosition, action.targetPiece)
            }
        }
        onActionFun(key, piece, action)
    }


    private fun move(from: Pair<Int, Int>, to: Pair<Int, Int>): AbstractPiece? {
        val piece = this[from] ?: throw IllegalArgumentException("No piece on square $from")
        this.remove(from)
        piece.position = to
        this[to] = piece
        onMoveFun(from, to, piece)
        return piece
    }

    fun print() {
        for (i in 8 downTo 1) {
            for (j in 1..8) {
                if (j == 1) {
                    print("$i   ")
                }

                val piece = this[j to i]

                val block = "  "  + (piece?.code ?: " ") +" "


                print("|$block")

            }
            println("|")
            for (j in 1..8) {
                if (j == 1) {
                    print("    ")
                }
                print("|----")
            }
            println("|")
            if (i == 1) {
                print("   ")
                for (j in 1..8) {
                    print("   ${(j + 64).toChar()} ")
                }

            }
        }
        println()
    }


    override fun putIfAbsent(key: Pair<Int, Int>, value: AbstractPiece): AbstractPiece? {
        allowedRange(key)
        if(key != value.position) throw IllegalArgumentException("Piece position is not equal to key")
        onPutFun(key, value)

        return super.putIfAbsent(key, value)
    }

    override fun put(key: Pair<Int, Int>, value: AbstractPiece): AbstractPiece? {
        allowedRange(key)
        if(key != value.position) throw IllegalArgumentException("Piece position is not equal to key")
        onPutFun(key, value)

        return super.put(key, value)
    }


    override fun remove(key: Pair<Int, Int>): AbstractPiece? {
        val piece = super.remove(key)
        if (piece != null) {
            onRemoveFun(key, piece)
        }

        return piece
    }

    override fun remove(key: Pair<Int, Int>, value: AbstractPiece): Boolean {
        onRemoveFun(key, value)

        return super.remove(key, value)
    }

    private fun allowedRange(key: Pair<Int, Int>) {
        if (key.first !in 1..8 || key.second !in 1..8)
            throw IllegalArgumentException("Piece not in allowed range")
    }


}