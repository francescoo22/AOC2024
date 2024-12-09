private val input = readInput()

private val map = buildMap <Char, MutableList<IntInt>> {
    input.forEachIndexedS { i, j, c ->
        if (c != '.') getOrPut(c) { mutableListOf() }.add(i to j)
    }
}

private fun inBounds(i: Int, j: Int) = i >= 0 && j >= 0 && i < input[0].length && j < input.size

private fun part1() {
    val set: MutableSet<IntInt> = mutableSetOf()
    map.forEach { (_, nodes) ->
        nodes.uniquePairs().forEach { (p1, p2) ->
            val (x1, y1) = p1
            val (x2, y2) = p2
            set.add(2 * x1 - x2 to 2 * y1 - y2)
            set.add(2 * x2 - x1 to 2 * y2 - y1)
        }
    }
    val ans = set.count { (x, y) -> inBounds(x, y) }
    println(ans)
}

private fun part2() {
    val set: MutableSet<IntInt> = mutableSetOf()
    map.forEach { (_, nodes) ->
        nodes.uniquePairs().forEach { (p1, p2) ->
            var (x1, y1) = p1
            var (x2, y2) = p2
            var dx = x1 - x2
            var dy = y1 - y2
            while (inBounds(x1, y1) || inBounds(x2, y2)) {
                set.add(x1 to y1)
                set.add(x2 to y2)
                x1 += dx
                x2 -= dx
                y1 += dy
                y2 -= dy
            }
        }
    }
    val ans = set.count { (x, y) -> inBounds(x, y) }
    println(ans)
}


suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}