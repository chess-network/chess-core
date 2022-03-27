package net.chess

import net.chess.piece.AbstractPiece
import java.util.*

class Board(
    val onPut: (key: Pair<Int, Int>, value: AbstractPiece) -> (Unit),
    val onRemove: (key: Pair<Int, Int>, value: AbstractPiece?) -> (Unit)
) : Hashtable<Pair<Int, Int>, AbstractPiece>() {

    fun move(from: Pair<Int, Int>, to: Pair<Int, Int>): AbstractPiece? {
        val piece = this[from]
        if (piece != null) {
            this.remove(from)
            piece.position = to
            this[to] = piece
        }
        return piece
    }


    override fun putIfAbsent(key: Pair<Int, Int>, value: AbstractPiece): AbstractPiece? {
        allowedRange(key)
        onPut(key, value)
        return super.putIfAbsent(key, value)
    }

    override fun put(key: Pair<Int, Int>, value: AbstractPiece): AbstractPiece? {
        allowedRange(key)
        onPut(key, value)
        return super.put(key, value)
    }

    override fun remove(key: Pair<Int, Int>): AbstractPiece? {
        val piece = super.remove(key)
        onRemove(key, piece)
        return piece
    }

    override fun remove(key: Pair<Int, Int>, value: AbstractPiece): Boolean {
        onRemove(key, value)
        return super.remove(key, value)
    }

    private fun allowedRange(key: Pair<Int, Int>) {
        if (key.first !in 1..8 || key.second !in 1..8)
            throw IllegalArgumentException("Piece not in allowed range")
    }
}