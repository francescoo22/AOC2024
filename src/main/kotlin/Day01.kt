import kotlin.math.abs

private fun part1(input: List<String>) {
    val x = input.map { it.split("   ").map { n -> n.toInt() } }
    val xs = x.map { it[0] }.sorted()
    val ys = x.map { it[1] }.sorted()
    var ans = 0
    for ((a, b) in xs zip ys) {
        ans += abs(a - b)
    }
    println(ans)
}

private fun part2(input: List<String>) {
    val x = input.map { it.split("   ").map { n -> n.toInt() } }
    val xs = x.map { it[0] }
    val ys = x.map { it[1] }
    val map = mutableMapOf<Int, Int>()
    ys.forEach {
        if (map[it] == null) {
            map[it] = 1
        } else {
            map[it] = map[it]!! + 1
        }
    }
    var ans = 0
    xs.forEach {
        val n = map[it] ?: 0
        ans += n * it
    }
    println(ans)
}

fun main() {
    part1(readInput())
    part2(readInput())
}