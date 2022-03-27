package net.chess.piece

import net.chess.Board
import org.junit.jupiter.api.BeforeEach

abstract class AbstractPawnTest {

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