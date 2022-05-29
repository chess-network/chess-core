package net.chess.engine

import java.io.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.function.Predicate
import java.util.function.Function

class Client
{
    lateinit var reader: BufferedReader
    lateinit var writer: OutputStreamWriter
    lateinit var process: Process
    fun start(cmd: String) {
        val pb = ProcessBuilder(cmd)
        try {
            process = pb.start()
            reader = BufferedReader(InputStreamReader(process.inputStream))
            writer = OutputStreamWriter(process.outputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun close() {
        if (this.process.isAlive) {
            this.process.destroy()
        }
        try {
            reader.close()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    @Throws(InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun <T> command(
        cmd: String,
        commandProcessor: Function<List<String>, T>,
        breakCondition: Predicate<String?>,
        timeout: Long
    ): T {

        // This completable future will send a command to the process
        // And gather all the output of the engine in the List<String>
        // At the end, the List<String> is translated to T through the
        // commandProcessor Function
        val command: CompletableFuture<T> = supplyAsync {
            val output: MutableList<String> = ArrayList()
            try {
                writer.flush()
                writer.write(cmd + "\n")
                writer.write("isready\n")
                writer.flush()
                var line: String
                while (reader.readLine().also { line = it } != null) {
                    if (line.contains("Unknown command")) {
                        throw RuntimeException(line)
                    }
                    if (line.contains("Unexpected token")) {
                        throw RuntimeException("Unexpected token: $line")
                    }
                    output.add(line)
                    if (breakCondition.test(line)) {
                        // At this point we are no longer interested to read any more
                        // output from the engine, we consider that the engine responded
                        break
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            commandProcessor.apply(output)
        }
        return command.get(timeout, TimeUnit.MILLISECONDS)
    }
}