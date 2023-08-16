import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ErrorRegisterTest {

    @Test
    fun `GIVEN the ErrorRegister WHEN register errors THEN should have the right amount of errors`() {
        ErrorRegister.register(1, "", "")
        ErrorRegister.register(2, "", "")
        Assertions.assertTrue(ErrorRegister.hadErrors())
        Assertions.assertEquals(2, ErrorRegister.getErrors().size)
    }

    @Test
    fun `GIVEN the ErrorRegister WHEN register errors and executes clear THEN should have 0 errors`() {
        ErrorRegister.register(1, "", "")
        ErrorRegister.register(2, "", "")
        ErrorRegister.clearAll()
        Assertions.assertFalse(ErrorRegister.hadErrors())
        Assertions.assertEquals(0, ErrorRegister.getErrors().size)
    }
}