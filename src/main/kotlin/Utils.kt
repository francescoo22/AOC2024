import kotlin.io.path.Path
import kotlin.io.path.readText

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