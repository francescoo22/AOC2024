private fun isXmas(input: List<String>, x: Int, y: Int, nx: Int, ny: Int): Boolean {
    val xmas = "XMAS"
    try {
        for (i in 0..3)
            if (input[x + nx * i][y + ny * i] != xmas[i]) return false
        return true
    } catch (_: IndexOutOfBoundsException) {
        return false
    }
}

private fun isXmas2(input: List<String>, x: Int, y: Int): Boolean {
    try {
        when {
            input[x][y] != 'A' -> return false
            !((input[x + 1][y + 1] == 'M' && input[x - 1][y - 1] == 'S') || (input[x + 1][y + 1] == 'S' && input[x - 1][y - 1] == 'M')) -> return false
            !((input[x - 1][y + 1] == 'M' && input[x + 1][y - 1] == 'S') || (input[x - 1][y + 1] == 'S' && input[x + 1][y - 1] == 'M')) -> return false
        }
        return true
    } catch (_: IndexOutOfBoundsException) {
        return false
    }
}

private fun part1(input: List<String>) {
    var ans = 0
    for (x in 0..<input.size)
        for (y in 0..<input[x].length)
            for (nx in -1..1)
                for (ny in -1..1)
                    if (isXmas(input, x, y, nx, ny)) ans++
    println(ans)
}

private fun part2(input: List<String>) {
    var ans = 0
    for (x in 0..<input.size)
        for (y in 0..<input[x].length)
            if (isXmas2(input, x, y)) ans++
    println(ans)
}

fun main() {
    part1(readInput())
    part2(readInput())
}