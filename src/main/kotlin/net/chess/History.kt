package net.chess

import java.time.LocalDateTime

data class History(
    val action: Action,
    val time: LocalDateTime = LocalDateTime.now()
)