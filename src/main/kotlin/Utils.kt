import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.system.measureTimeMillis

val NorthSouthWestEast = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)

fun readInput() = Path("src/input.txt").readText().trim().lines()

typealias IntMatrix = List<List<Int>>
typealias IntInt = Pair<Int, Int>

fun <T> List<List<T>>.forEachIndexed2(block: (Int, Int, T) -> Unit) {
    forEachIndexed { i, xs ->
        xs.forEachIndexed { j, x ->
            block(i, j, x)
        }
    }
}

fun List<String>.forEachIndexedS(block: (Int, Int, Char) -> Unit) {
    forEachIndexed { i, xs ->
        xs.forEachIndexed { j, x ->
            block(i, j, x)
        }
    }
}

fun Any.println() = println(this)

suspend fun withTime(block: suspend () -> Unit) {
    measureTimeMillis {
        block()
    }.also {
        println("Execution time: $it ms")
    }
}

fun <T> List<T>.uniquePairs() = withIndex().flatMap { (index, a) ->
    drop(index + 1).map { b -> a to b }
}

fun runIfInBounds(block: () -> Unit) {
    try {
        block()
    } catch (_: IndexOutOfBoundsException) {
    }
}

infix fun Int.mod(b: Int): Int = ((this % b) + b) % b