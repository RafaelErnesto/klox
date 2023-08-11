data class Error (
    var line: Int? = null,
    var where: String? = null,
    var message: String? = null
)

object ErrorRegister  {
    private val errors = mutableListOf<Error>()

    fun hadErrors(): Boolean {
        return errors.size > 0
    }

    fun register(line: Int, where: String?, message: String) {
        errors.add(Error(line, where, message))
    }

    fun clearAll() {
        errors.clear()
    }

    fun getErrors(): List<Error> {
        return errors.toList()
    }
}