private val input = readInput().map { it.toList() }
private fun inBounds(i: Int, j: Int) = i >= 0 && j >= 0 && i < input.size && j < input[0].size

private fun part1() {
    var ans = 0
    val visited = input.map { it.map { false }.toMutableList() }
    input.forEachIndexed2 { x, y, c ->
        if (visited[x][y]) return@forEachIndexed2
        val toVisit = mutableListOf(x to y)
        var curA = 1
        var curP = 0
        while (toVisit.isNotEmpty()) {
            val (ci, cj) = toVisit.removeLast()
            visited[ci][cj] = true
            NorthSouthWestEast.forEach { (di, dj) ->
                val ni = ci + di
                val nj = cj + dj
                if (!inBounds(ni, nj) || input[ni][nj] != input[ci][cj]) {
                    curP++
                } else if (!visited[ni][nj]) {
                    curA++
                    toVisit.add(ni to nj)
                    visited[ni][nj] = true
                }
            }
        }
        ans += curP * curA
    }
    println(ans)
}

private fun part2() {
    var ans = 0
    val visited = input.map { it.map { false }.toMutableList() }
    input.forEachIndexed2 { i, j, c ->
        if (!visited[i][j]) {
            visited[i][j] = true
            var curP = 0
            var curA = 1
            val toVisit = mutableListOf(i to j)
            while (toVisit.isNotEmpty()) {
                val i = toVisit.last().first
                val j = toVisit.last().second
                toVisit.removeLast()
                NorthSouthWestEast.forEach { (di, dj) ->
                    val ni = di + i
                    val nj = dj + j
                    if (inBounds(ni, nj) && !visited[ni][nj] && input[i][j] == input[ni][nj]) {
                        curA++
                        toVisit.add(ni to nj)
                        visited[ni][nj] = true
                    } else if (!inBounds(ni, nj) || input[i][j] != input[ni][nj]) {
                        if (di != 0) {
                            if (!inBounds(i, j - 1) || input[i][j - 1] != input[i][j] || (inBounds(
                                    i + di,
                                    j - 1
                                ) && input[i + di][j - 1] == input[i][j])
                            ) {
                                curP++
                            }
                        } else {
                            if (!inBounds(i - 1, j) || input[i - 1][j] != input[i][j] || (inBounds(
                                    i - 1,
                                    j + dj
                                ) && input[i - 1][j + dj] == input[i][j])
                            ) {
                                curP++
                            }
                        }
                    }
                }
            }
            ans += curP * curA
        }
    }
    println(ans)
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}