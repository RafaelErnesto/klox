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

    private fun scanToken() {

        when (advance()) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '{' -> addToken(TokenType.LEFT_BRACE)
            '}' -> addToken(TokenType.RIGHT_BRACE)
            ',' -> addToken(TokenType.COMMA)
            '.' -> addToken(TokenType.DOT)
            '-' -> addToken(TokenType.MINUS)
            '+' -> addToken(TokenType.PLUS)
            ';' -> addToken(TokenType.SEMICOLON)
            '*' -> addToken(TokenType.STAR)
            else -> Klox.error(line, "Unexpected character")
        }
    }

    private fun advance(): Char {
        val char = source[current]
        current++
        return char
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Any?) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }

}