import kotlin.system.measureTimeMillis
import kotlinx.coroutines.*

private val directions = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)

private fun part1(input: List<MutableList<Char>>) {
    var curDir = 0
    var curPos = 0 to 0
    input.forEachIndexed2 { i, j, c ->
        if (c == '^') {
            curPos = i to j
            return@forEachIndexed2
        }
    }
    while (true) {
        try {
            val (i, j) = curPos
            input[i][j] = 'X'
            val (ni, nj) = directions[curDir].first + i to directions[curDir].second + j
            if (input[ni][nj] == '#') {
                curDir = (curDir + 1) % 4
            } else {
                curPos = ni to nj
            }
        } catch (_: IndexOutOfBoundsException) {
            break
        }
    }
    val ans = input.sumOf { line -> line.count { it == 'X' } }
    check(ans == 4722)
    println(ans)
}

private fun isLoop(input: List<MutableList<Char>>, initPos: IntInt, extraBlock: IntInt): Boolean {
    var curPos = initPos
    var curDir = 0
    val visited: List<List<MutableList<IntInt>>> = List(input.size) { List(input[0].size) { mutableListOf() } }
    while (true) {
        try {
            val (i, j) = curPos
            if (directions[curDir] in visited[i][j]) return true
            else visited[i][j].add(directions[curDir])
            val (ni, nj) = directions[curDir].first + i to directions[curDir].second + j
            if (input[ni][nj] == '#' || ni to nj == extraBlock) {
                curDir = (curDir + 1) % 4
            } else {
                curPos = ni to nj
            }
        } catch (_: IndexOutOfBoundsException) {
            return false
        }
    }
}

private suspend fun part2(input: List<MutableList<Char>>) {
    var curPos = 0 to 0
    input.forEachIndexed2 { i, j, c ->
        if (c == '^') {
            curPos = i to j
            return@forEachIndexed2
        }
    }
    val ans = coroutineScope {
        val deferredResults = mutableListOf<Deferred<Boolean>>()
        input.forEachIndexed2 { i, j, c ->
            if (c == '.') {
                deferredResults.add(async { isLoop(input, curPos, i to j) })
            }
        }
        deferredResults.awaitAll().count { it }
    }
    check(ans == 1602)
    println(ans)
}


suspend fun main() {
    measureTimeMillis {
        part1(readInput().map { it.toMutableList() })
    }.also {
        println("Execution time: $it")
    }
    measureTimeMillis {
        part2(readInput().map { it.toMutableList() })
    }.also {
        println("Execution time: $it")
    }
}