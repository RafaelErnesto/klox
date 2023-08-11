class ErrorReporter {
    companion object {
        fun report(errorList: List<Error>) {
            for (error in errorList) {
                println("[line ${error.line}] Error ${error.where}: ${error.message}")
            }
        }
    }
}