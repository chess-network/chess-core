package net.chess.game

import java.time.LocalDateTime

data class History(
    val action: Action,
    val time: LocalDateTime = LocalDateTime.now()
)