package net.chess.game

import net.chess.piece.AbstractPiece
import java.time.LocalDateTime

data class Event(
    val causePiece: AbstractPiece,
    val action: Action,
    val time: LocalDateTime = LocalDateTime.now()
)