package net.chess.enums

enum class ActionType(val code: String) {
    MOVE("MV"),
    CAPTURE("CP"),
    CASTLING("CS"),
    EN_PASSANT("EP"),
    PROMOTION("PR");

    companion object{
        fun codeToActionType(code: String): ActionType {
            return values().first { it.code == code }
        }
    }

}