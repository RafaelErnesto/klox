// change in the future for lex/flex
class Scanner(private val source: String) {
    private val tokens = mutableListOf<Token>()
    private val keywords = mutableMapOf<String, TokenType>()
    private var start = 0
    private var current = -1
    private var line = 0

    init {
        initKeywordMap()
    }

    fun scanTokens(): MutableList<Token> {
        while (!isEnd()) {
            advance()
            start = current
            scanToken()
        }

        tokens.add(Token(TokenType.EOF, "", null, line))
        return tokens
    }

    private fun isEnd(): Boolean =
        current >= source.length - 1

    private fun scanToken() {
        val char = source[current]
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
            isAlphanumeric(char) -> processAlphanumeric()
            else -> ErrorRegister.register(line, null, "Unexpected character")
        }

    }

    private fun advance(): Char {
        current++
        return source[current]
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Any?) {
        val text = source.substring(start, current + 1)
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
            ErrorRegister.register(line, null, "Unterminated string")
            return
        }

        advance()

        val value = source.substring(start, current + 1)
        addToken(TokenType.STRING, value)
    }

    private fun isDigit(char: Char): Boolean =
        char in '0'..'9'

    private fun processNumber() {
        while (isDigit(peek())) advance()

        if (peek() == '.' && isDigit(peek(2))) {
            while (isDigit(peek())) advance()
        }

        addToken(TokenType.NUMBER, source.substring(start, current + 1).toDouble())
    }

    private fun isAlphanumeric(char: Char): Boolean {
        return char in 'a'..'z' || char in 'A'..'Z' || char == '_' || isDigit(char)
    }

    private fun processAlphanumeric() {
        while (isAlphanumeric(peek())) advance()
        val text = source.substring(start, current + 1)
        addToken(keywords[text] ?: TokenType.IDENTIFIER)
    }

    private fun initKeywordMap() {
        keywords["and"] = TokenType.AND
        keywords["class"] = TokenType.CLASS
        keywords["else"] = TokenType.ELSE
        keywords["false"] = TokenType.FALSE
        keywords["for"] = TokenType.FOR
        keywords["fun"] = TokenType.FUN
        keywords["if"] = TokenType.IF
        keywords["nil"] = TokenType.NIL
        keywords["or"] = TokenType.OR
        keywords["print"] = TokenType.PRINT
        keywords["return"] = TokenType.RETURN
        keywords["super"] = TokenType.SUPER
        keywords["this"] = TokenType.THIS
        keywords["true"] = TokenType.TRUE
        keywords["var"] = TokenType.VAR
        keywords["while"] = TokenType.WHILE
    }

}