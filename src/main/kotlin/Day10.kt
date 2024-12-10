private val input = readInput().map { line -> line.map { it.toString().toInt() } }

private fun part1() {
    val initPos = mutableListOf<IntInt>()
    input.forEachIndexed2 { i, j, c ->
        if (c == 0) initPos.add(i to j)
    }

    var ans = 0
    initPos.forEach {
        val visited = mutableSetOf<IntInt>()
        val toVisit = mutableListOf(it)
        while (!toVisit.isEmpty()) {
            val (i, j) = toVisit.removeLast()
            NorthSouthWestEast.forEach { (di, dj) ->
                val (ni, nj) = i + di to j + dj
                runIfInBounds {
                    if (input[ni][nj] == input[i][j] + 1 && ni to nj !in visited) {
                        toVisit.add(ni to nj)
                    }
                }
            }
            visited.add(i to j)
        }
        ans += visited.count { (i, j) -> input[i][j] == 9 }
    }
    println(ans)
}

private fun part2() {
    val initPos = mutableListOf<IntInt>()
    input.forEachIndexed2 { i, j, c ->
        if (c == 0) initPos.add(i to j)
    }

    var ans = 0
    initPos.forEach {
        val visited = mutableSetOf<IntInt>()
        val toVisit = mutableListOf(it)
        val comb = input.map { MutableList(it.size) { 0 } }
        comb[it.first][it.second] = 1
        while (!toVisit.isEmpty()) {
            val (i, j) = toVisit.removeFirst()
            NorthSouthWestEast.forEach { (di, dj) ->
                val (ni, nj) = i + di to j + dj
                runIfInBounds {
                    if (input[ni][nj] == input[i][j] + 1) {
                        if (ni to nj !in visited) {
                            visited.add(ni to nj)
                            toVisit.add(ni to nj)
                        }
                        comb[ni][nj] += comb[i][j]
                    }
                }
            }
        }
        comb.forEachIndexed2 { i, j, n ->
            if (input[i][j] == 9) ans += n
        }
    }
    println(ans)
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}