import kotlin.io.path.Path
import kotlin.io.path.readText

fun readInput() = Path("src/input.txt").readText().trim().lines()