import java.util.LinkedList
import java.util.Queue

private val input = readInput()
private val gridSize = 70

private fun inBounds(x: Int, y: Int) = x >= 0 && y >= 0 && x <= gridSize && y <= gridSize

private data class Node18(val pos: IntInt, val dist: Int)

private fun shortestPath(bytes: Int): Int {
    val bytes = input
        .take(bytes)
        .map { it.split(",").let { (x, y) -> x.toInt() to y.toInt() } }
        .toMutableSet()
    val queue: Queue<Node18> = LinkedList<Node18>()
    queue.add(Node18(0 to 0, 0))
    while (queue.isNotEmpty() && queue.first().pos != gridSize to gridSize) {
        val (pos, dist) = queue.poll()
        val (x, y) = pos
        NorthSouthWestEast.forEach { (dx, dy) ->
            val nx = x + dx
            val ny = y + dy
            if (inBounds(nx, ny) && nx to ny !in bytes) {
                bytes.add(nx to ny)
                queue.add(Node18(nx to ny, dist + 1))
            }
        }
    }
    return if (queue.isEmpty()) -1 else queue.first().dist
}

private fun part1() {
    shortestPath(1024).println()
}

private fun part2() {
    (1025..input.size).first { shortestPath(it) == -1 }.also {
        println(input[it-1])
    }
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}