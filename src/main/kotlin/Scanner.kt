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
        val char = advance()
        when {
            char == '(' -> addToken(TokenType.LEFT_PAREN)
            char == ')' -> addToken(TokenType.RIGHT_PAREN)
            char == '{' -> addToken(TokenType.LEFT_BRACE)
            char == '}' -> addToken(TokenType.RIGHT_BRACE)
            char == ',' -> addToken(TokenType.COMMA)
            char == '.' -> addToken(TokenType.DOT)
            char == '-' -> addToken(TokenType.MINUS)
            char == '+' -> addToken(TokenType.PLUS)
            char == ';' -> addToken(TokenType.SEMICOLON)
            char == '*' -> addToken(TokenType.STAR)
            char == '!' -> addToken(if (match('=')) TokenType.BANG_EQUAL else TokenType.BANG)
            char == '=' -> addToken(if (match('=')) TokenType.EQUAL_EQUAL else TokenType.EQUAL)
            char == '<' -> addToken(if (match('=')) TokenType.LESS_EQUAL else TokenType.LESS)
            char == '>' -> addToken(if (match('=')) TokenType.GREATER_EQUAL else TokenType.GREATER)
            char == '/' -> {
                if (match('/')) {
                    //refactor for a function that goes to the end of the line
                    while (peek() != '\n' && !isEnd()) {
                        advance()
                    }
                } else {
                    addToken(TokenType.SLASH)
                }
            }

            char == '\r' -> { /*do nothing*/
            }

            char == '\t' -> { /*do nothing*/
            }

            char == ' ' -> { /*do nothing*/
            }

            char == '\n' -> line++
            char == '"' -> processString()
            isDigit(char) -> processNumber()
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

    private fun match(char: Char): Boolean {
        if (isEnd()) return false
        if (source[current + 1] == char) {
            current++
            return true
        }
        return false
    }

    private fun peek(lookAhead: Int = 1): Char {
        return if (isEnd()) '\u0000' else source[current + lookAhead]
    }

    private fun processString() {
        while (peek() != '"' && !isEnd()) {
            if (peek() == '\n') line++
            advance()
        }

        if (isEnd()) {
            Klox.error(line, "Unterminated string")
        }

        advance()

        val value = source.substring(start + 1, current)
        addToken(TokenType.STRING, value)
    }

    private fun isDigit(char: Char): Boolean =
        char in '0'..'9'


    private fun processNumber() {
        while (isDigit(peek())) advance()

        if (peek() == '.' && isDigit(peek(2))) {
            while (isDigit(peek())) advance()
        }

        addToken(TokenType.NUMBER, source.substring(start, current).toDouble())
    }

}