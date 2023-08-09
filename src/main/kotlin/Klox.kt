import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

class Klox {
    private var hadError = false

    fun main(args: Array<String>) {
        when {
            args.size > 1 -> {
                println("Usage: klox [script]")
                exitProcess(64)
            }

            args.size == 1 -> {
                runFile(args[0])
            }

            else -> {
                runPrompt()
            }
        }
    }

    fun runFile(path: String) {
        val bytes = Files.readAllBytes(Paths.get(path))
        run(String(bytes, Charset.defaultCharset()))

        if (hadError) exitProcess(64)
    }

    fun runPrompt() {

        val input = InputStreamReader(System.`in`)
        val bufferedReader = BufferedReader(input)

        while (true) {
            print("> ")
            val line: String = bufferedReader.readLine() ?: break
            run(line)
        }
    }

    private fun run(source: String) {
        val scanner = Scanner(source)
        val tokens = scanner.getTokens()

        for (token in tokens) {
            println(token)
        }
    }


    fun error(line: Int, message: String) {
        report(line, "", message)
    }

    private fun report(line: Int, where: String, message: String) {
        println("[line $line] Error $where: $message")
    }

}