class Token(
    private val type: TokenType,
    private val lexime: String,
    private val literal: Any?,
    private val line: Int
) {

    override fun toString(): String {
        return "$type $lexime $literal"
    }
}