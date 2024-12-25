private val input = readInput().filter { it != "" }.chunked(7)


private fun part1() {
    val keys = buildList {
        input.forEach {
            if (it[0] == ".....") {
                val xs = mutableListOf<Int>()
                for (i in 0..<it[0].length) {
                    xs.add(it.count { it[i] == '#' })
                }
                add(xs)
            }
        }
    }

    val locks = buildList {
        input.forEach {
            if (it[0] == "#####") {
                val xs = mutableListOf<Int>()
                for (i in 0..<it[0].length) {
                    xs.add(it.count { it[i] == '#' })
                }
                add(xs)
            }
        }
    }
    var ans = 0
    keys.forEach { key ->
        locks.forEach { lock ->
            if (key.zip(lock).all { (a, b) -> a + b <= 7 }) ans++
        }
    }
    println(ans)
}

private fun part2() {

}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}