package net.chess.piece

import net.chess.game.Board

abstract class AbstractPieceTest {

    protected val messages = mutableListOf<String>()

    protected val board = Board(
        { key, value ->
            messages.add("Put $key: ${value.color} ${value.javaClass.kotlin.simpleName}")
        },
        { key, value ->
            if (value != null) {
                messages.add("Remove $key: ${value.color} ${value.javaClass.kotlin.simpleName}")
            }
        },
    )


}