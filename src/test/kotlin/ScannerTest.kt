import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ScannerTest {

    @BeforeEach
    fun prepare() {
        ErrorRegister.clearAll()
    }

    @Test
    fun `GIVEN a code WHEN a string was not closed THEN error register should have an error`() {
        val code = "var test = \"test"
        val scanner = Scanner(code)
        scanner.scanTokens()
        Assertions.assertEquals(1, ErrorRegister.getErrors().size)
        Assertions.assertEquals("Unterminated string", ErrorRegister.getErrors().first().message)
    }

    @Test
    fun `GIVEN a code WHEN code has an unexpected character THEN error register should have an error`() {
        val code = "var test = @"
        val scanner = Scanner(code)
        scanner.scanTokens()
        Assertions.assertEquals(1, ErrorRegister.getErrors().size)
        Assertions.assertEquals("Unexpected character", ErrorRegister.getErrors().first().message)
    }

    @Test
    fun `GIVEN a code WHEN code is correct THEN should return tokens`() {
        val code = "var test = 1; var test2 = \"testando scanner\""
        val scanner = Scanner(code)
        val tokens = scanner.scanTokens()
        Assertions.assertEquals(10, tokens.size)
        Assertions.assertEquals("${TokenType.VAR} var null", tokens.first().toString())
        Assertions.assertEquals("${TokenType.IDENTIFIER} test null", tokens[1].toString())
        Assertions.assertEquals("${TokenType.EQUAL} = null", tokens[2].toString())
        Assertions.assertEquals("${TokenType.NUMBER} 1 1.0", tokens[3].toString())
        Assertions.assertEquals("${TokenType.SEMICOLON} ; null", tokens[4].toString())
        Assertions.assertEquals("${TokenType.VAR} var null", tokens[5].toString())
        Assertions.assertEquals("${TokenType.IDENTIFIER} test2 null", tokens[6].toString())
        Assertions.assertEquals("${TokenType.EQUAL} = null", tokens[7].toString())
        Assertions.assertEquals("${TokenType.STRING} \"testando scanner\" \"testando scanner\"", tokens[8].toString())
        Assertions.assertEquals("${TokenType.EOF}  null", tokens[9].toString())
    }
}