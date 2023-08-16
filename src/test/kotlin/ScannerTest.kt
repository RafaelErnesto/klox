import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ScannerTest {

    @Test
    fun `GIVEN a code WHEN a string was not closed THEN error register should have an error`() {
        val code = "val test = \"test"
        val scanner = Scanner(code)
        scanner.scanTokens()
        Assertions.assertEquals(1, ErrorRegister.getErrors().size)
    }
}