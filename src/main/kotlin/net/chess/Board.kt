package net.chess

import net.chess.enums.PieceColor
import net.chess.piece.AbstractPiece
import net.chess.enums.PieceType
import java.util.*

class Board(
    val onPut: (key: Pair<Int, Int>, value: AbstractPiece) -> (Unit) = { _, _ -> },
    val onRemove: (key: Pair<Int, Int>, value: AbstractPiece?) -> (Unit) = { _, _ -> }
) : Hashtable<Pair<Int, Int>, AbstractPiece>() {



    private val pieceNumber = hashMapOf<String, Int>()


    companion object {
        fun squareToPair(square: String): Pair<Int, Int> = (square[0].code - 64) to (square[1].digitToInt())

        fun pairToSquare(pair: Pair<Int, Int>): String = "${(pair.first + 64).toChar()}${pair.second}"
    }

    fun countByColor(color: PieceColor): Int = values.count { it.color == color }

    fun countByType(type: PieceType): Int = values.count { it.type == type }

    fun countByCode(code: String): Int = values.count { it.code == code }

    fun pieceNumber(pieceCode: String): Int = pieceNumber[pieceCode] ?: 0


    fun move(from: Pair<Int, Int>, to: Pair<Int, Int>): AbstractPiece? {
        val piece = this[from]
        if (piece != null) {
            this.remove(from)
            piece.position = to
            this[to] = piece
        }
        return piece
    }

    fun print() {
        for (i in 8 downTo 1) {
            for (j in 1..8) {
                if (j == 1) {
                    print("$i   ")
                }

                val piece = this[j to i]

                val block = piece?.fullCode() ?: "    "


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
        onPut(key, value)
        pieceNumber[value.code] = pieceNumber(value.code) + 1

        return super.putIfAbsent(key, value)
    }

    override fun put(key: Pair<Int, Int>, value: AbstractPiece): AbstractPiece? {
        allowedRange(key)
        onPut(key, value)
        pieceNumber[value.code] = pieceNumber(value.code) + 1

        return super.put(key, value)
    }

    override fun remove(key: Pair<Int, Int>): AbstractPiece? {
        val piece = super.remove(key)
        onRemove(key, piece)
        if (piece != null) {
            pieceNumber.remove(piece.code, pieceNumber(piece.code))
        }

        return piece
    }

    override fun remove(key: Pair<Int, Int>, value: AbstractPiece): Boolean {
        onRemove(key, value)
        pieceNumber.remove(value.code, pieceNumber(value.code))

        return super.remove(key, value)
    }

    private fun allowedRange(key: Pair<Int, Int>) {
        if (key.first !in 1..8 || key.second !in 1..8)
            throw IllegalArgumentException("Piece not in allowed range")
    }
}