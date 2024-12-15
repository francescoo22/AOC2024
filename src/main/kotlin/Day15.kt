private val input = readInput()

private fun parseInput(): Pair<List<List<Char>>, List<IntInt>> {
    var parseMap = true
    val map: MutableList<List<Char>> = mutableListOf()
    var moves = mutableListOf<IntInt>()
    input.forEach { line ->
        when {
            line == "" -> parseMap = false
            parseMap -> map.add(line.toList())
            else -> {
                moves += line.toList().map(::directionFromChar)
            }
        }
    }
    return map to moves
}

private fun parseInput2(): Pair<List<List<Char>>, List<IntInt>> {
    var parseMap = true
    val map: MutableList<List<Char>> = mutableListOf()
    var moves = mutableListOf<IntInt>()
    input.forEach { line ->
        when {
            line == "" -> parseMap = false
            parseMap -> map.add(line.toList().flatMap {
                when (it) {
                    '#' -> listOf('#', '#')
                    'O' -> listOf('[', ']')
                    '.' -> listOf('.', '.')
                    '@' -> listOf('@', '.')
                    else -> throw IllegalArgumentException("Incorrect input")
                }
            })

            else -> {
                moves += line.toList().map(::directionFromChar)
            }
        }
    }
    return map to moves
}

private fun part1() {
    val (initMap, moves) = parseInput()
    val map = initMap.map { it.toMutableList() }
    lateinit var curPos: IntInt
    map.forEachIndexed2 { i, j, c ->
        if (c == '@') curPos = i to j
    }
    moves.forEach { (di, dj) ->
        val (i, j) = curPos
        var (ni, nj) = i + di to j + dj
        when (map[ni][nj]) {
            '.' -> {
                map[ni][nj] = '@'
                map[i][j] = '.'
                curPos = ni to nj
            }

            'O' -> {
                while (map[ni][nj] == 'O') {
                    ni += di
                    nj += dj
                }
                if (map[ni][nj] == '.') {
                    map[ni][nj] = 'O'
                    map[i][j] = '.'
                    map[i + di][j + dj] = '@'
                    curPos = i + di to j + dj
                }
            }
        }
    }
    var ans = 0
    map.forEachIndexed2 { i, j, c ->
        if (c == 'O') ans += 100 * i + j
    }
    println(ans)
}

private fun movable(map: List<List<Char>>, pos: IntInt, di: Int): Boolean {
    val (i, j) = pos
    return when (map[i + di][j]) {
        '#' -> false
        '.' -> true
        '[' -> movable(map, i + di to j, di) && movable(map, i + di to j + 1, di)
        ']' -> movable(map, i + di to j, di) && movable(map, i + di to j - 1, di)
        else -> throw IllegalArgumentException("Incorrect input")
    }
}

private fun involvedBlocks(map: List<List<Char>>, pos: IntInt, di: Int): Set<IntInt> {
    val (i, j) = pos
    return when (map[i + di][j]) {
        '.' -> setOf(pos)
        '[' -> setOf(pos) + involvedBlocks(map, i + di to j, di) + involvedBlocks(map, i + di to j + 1, di)
        ']' -> setOf(pos) + involvedBlocks(map, i + di to j, di) + involvedBlocks(map, i + di to j - 1, di)
        else -> throw IllegalArgumentException("Incorrect input")
    }
}

private fun part2() {
    val (initMap, moves) = parseInput2()
    val map = initMap.map { it.toMutableList() }
    lateinit var curPos: IntInt
    map.forEachIndexed2 { i, j, c ->
        if (c == '@') curPos = i to j
    }
    moves.forEach { (di, dj) ->
        val (i, j) = curPos
        var (ni, nj) = i + di to j + dj
        when {
            map[ni][nj] == '#' -> {}
            map[ni][nj] == '.' -> {
                map[ni][nj] = '@'
                map[i][j] = '.'
                curPos = ni to nj
            }

            di == 0 -> {
                while (map[ni][nj] == '[' || map[ni][nj] == ']') {
                    nj += dj
                }
                if (map[ni][nj] == '.') {
                    map[i][j] = '.'
                    while (nj != j) {
                        map[ni][nj] = map[ni][nj - dj]
                        nj -= dj
                    }
                    map[i][j + dj] = '@'
                    curPos = i to j + dj
                }
            }

            else -> {
                if (movable(map, curPos, di)) {
                    val involvedBlocks = involvedBlocks(map, curPos, di)
                    involvedBlocks
                        .sortedByDescending { (i, _) -> i * di }
                        .forEach { (bi, bj) ->
                            map[bi + di][bj] = map[bi][bj]
                            map[bi][bj] = '.'
                        }
                    curPos = i + di to j
                }
            }
        }
    }
    var ans = 0
    map.forEachIndexed2 { i, j, c ->
        if (c == '[') ans += 100 * i + j
    }
    println(ans)
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}