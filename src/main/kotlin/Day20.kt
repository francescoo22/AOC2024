private val input = readInput()
private lateinit var S: IntInt
private lateinit var E: IntInt
private const val toSave = 100
private val path = mutableListOf<IntInt>()

private fun initPath() {
    var cur = S
    while (cur != E) {
        NorthSouthWestEast.forEach {
            val next = cur plus it
            if (path.isEmpty() || next != path.last()) {
                if (input[next.first][next.second] != '#') {
                    path.add(cur)
                    cur = next
                    return@forEach
                }
            }
        }
    }
    path.add(E)
}

private fun part1() {
    var ans = 0
    path.forEachIndexed { i, cur ->
        for (j in i + toSave + 1..<path.size) {
            val next = path[j]
            if ((j - i) - manhattan(cur, next) >= toSave && manhattan(cur, next) == 2) {
                ans++
            }
        }
    }
    println(ans)
}

private fun part2() {
    var ans = 0
    path.forEachIndexed { i, cur ->
        for (j in i + toSave + 1..<path.size) {
            val next = path[j]
            if ((j - i) - manhattan(cur, next) >= toSave && manhattan(cur, next) <= 20) {
                ans++
            }
        }
    }
    println(ans)
}

suspend fun main() {
    input.forEachIndexedS { i, j, c ->
        if (c == 'S') S = i to j
        if (c == 'E') E = i to j
    }
    initPath()
    withTime { part1() }
    withTime { part2() }
}