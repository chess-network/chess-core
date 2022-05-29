package net.chess.game

import net.chess.enums.PieceColor

 class Player(
    val color: PieceColor,
    val username: String,
    var canMove: Boolean = false
)