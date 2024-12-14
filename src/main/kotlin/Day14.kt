private val regex = Regex("p=|,| v=")
private val input = readInput().map { it.split(regex).drop(1).map { s -> s.toInt() } }
private const val width = 101
private const val height = 103

private fun getQuadrant(i: Int, j: Int): Int {
    val mi = i mod height
    val mj = j mod width
    if (mi == height / 2 || mj == width / 2) return -1

    val x = if (mi < height / 2) 0 else 1
    val y = if (mj < width / 2) 0 else 2
    return x + y
}

private fun centralRobots(seconds: Int): Int {
    val grid = MutableList(height) { MutableList(width) { 0 } }
    input.forEach { (j, i, y, x) ->
        grid[(i + seconds * x) mod height][(j + seconds * y) mod width]++
    }
    val h4 = height / 4
    val w4 = width / 4
    var central = 0
    for (i in h4..<3 * h4)
        for (j in w4..<3 * w4)
            central += grid[i][j]
    return central
}

private fun printTree(seconds: Int) {
    val grid = MutableList(height) { MutableList(width) { 0 } }
    input.forEach { (j, i, y, x) ->
        grid[(i + seconds * x) mod height][(j + seconds * y) mod width]++
    }
    grid.forEach { t ->
        t.forEach { if (it > 0) print("*") else print(" ") }
        println()
    }
}

private fun part1() {
    val seconds = 100
    val quadrants = MutableList(4) { 0 }
    input.forEach { (j, i, y, x) ->
        val q = getQuadrant(i + seconds * x, j + seconds * y)
        if (q != -1) quadrants[q]++
    }
    val ans = quadrants.fold(1) { x, y -> x * y }
    println(ans)
}

private fun part2() {
    var curMax = 0
    var ans = 0
    for (n in 0..10000) {
        val centralRobots = centralRobots(n)
        if (centralRobots > curMax) {
            curMax = centralRobots
            ans = n
        }
    }
    println(ans)
    printTree(ans)
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}