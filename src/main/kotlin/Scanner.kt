// change in the future for lex/flex
class Scanner(private val source: String) {
    private lateinit var tokens: MutableList<Token>
    private var start = 0
    private var current = 0
    private var line = 0

    fun scanTokens(): MutableList<Token> {
        while (!isEnd()) {
            start = current
            scanToken()
        }

        tokens.add(Token(TokenType.EOF, "", null, line))
        return tokens
    }

    private fun isEnd(): Boolean =
        current >= source.length

}