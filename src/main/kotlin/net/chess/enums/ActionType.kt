package net.chess.enums

enum class ActionType(val code: String) {
    MOVE("MV"),
    CAPTURE("CP"),
    CASTLING("CS"),
    EN_PASSANT("EP"),
    PROMOTION("PR");
}