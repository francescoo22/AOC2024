import java.math.BigInteger
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs
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

fun directionFromChar(c: Char) =
    when (c) {
        '>' -> 0 to 1
        '<' -> 0 to -1
        '^' -> -1 to 0
        'v' -> 1 to 0
        else -> throw IllegalArgumentException(
            "Expected one of the following chars to be converted into a direction: '<', '>', '^', 'v', but got $c"
        )
    }

fun charFromDirection(dir: IntInt) =
    when (dir) {
        0 to 1 -> '>'
        0 to -1 -> '<'
        -1 to 0 -> '^'
        1 to 0 -> 'v'
        else -> throw IllegalArgumentException(
            "Expected one of the following directions to be converted into a char: (0,1), (0,-1), (-1,0), (1,0), but got $dir)"
        )
    }

infix fun IntInt.plus(other: IntInt): IntInt = first + other.first to second + other.second

fun manhattan(a: IntInt, b: IntInt): Int = abs(a.first - b.first) + abs(a.second - b.second)

val factorial = mutableListOf(BigInteger.ONE)

fun factorial(n: Int): BigInteger {
    require(n >= 0) { "Factorial is not defined for negative numbers." }
    if (n < factorial.size) return factorial[n]
    var result = factorial.last()
    for (i in factorial.size..n) {
        result = result.multiply(BigInteger.valueOf(i.toLong()))
        factorial.add(result)
    }
    return result
}

fun bin(a: Int, b: Int) = factorial(a) / (factorial(b) * factorial(a - b))