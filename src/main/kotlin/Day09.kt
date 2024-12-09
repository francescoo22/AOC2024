private val input = readInput()

private fun part1() {
    val x = input.first().split("").drop(1).dropLast(1).map { it.toInt() }.toMutableList()

    val res = mutableListOf<Int>()
    var j = x.size - 1
    if (j % 2 == 1) j--
    for (i in x.indices) {
        if (i == j) {
            repeat(x[i]) {
                res.add((i + 1) / 2)
            }
            break
        }
        if (i > j) break
        if (i % 2 == 0) {
            repeat(x[i]) {
                res.add((i + 1) / 2)
            }
        } else {
            var k = x[i]
            while (x[j] > 0 && k > 0) {
                res.add((j + 1) / 2)
                x[j] -= 1
                k--
                if (x[j] == 0) {
                    j -= 2
                }
            }
        }
    }
    var ans = "0".toBigInteger()
    res.forEachIndexed { i, x ->
        ans = ans + (i * x).toBigInteger()
    }
    println(ans)
}

private fun part2() {
    val x = input.first().split("").drop(1).dropLast(1).map { it.toInt() }.toMutableList()
    val original = x.toList()
    val added = List<MutableList<IntInt>>(x.size) { mutableListOf() }
    var j = x.size - 1
    if (j % 2 == 1) j--

    while (j >= 0) {
        var i = 1
        while (j > i) {
            if (x[j] <= x[i]) {
                x[i] -= x[j]
                added[i].add(j to x[j])
                x[j] = -1
                break
            }
            i += 2
        }
        j -= 2
    }

    val res = mutableListOf<Int>()
    for (i in x.indices) {
        if (i % 2 == 0) {
            if (x[i] == -1) {
                repeat(original[i]) {
                    res.add(0)
                }
            } else {
                repeat(x[i]) {
                    res.add((i + 1) / 2)
                }
            }
        } else {
            added[i].forEach { (j, nj) ->
                repeat(nj) { res.add((j + 1) / 2) }
            }
            repeat(x[i]) {
                res.add(0)
            }
        }
    }

    var ans = "0".toBigInteger()
    res.forEachIndexed { i, x ->
        ans = ans + (i * x).toBigInteger()
    }
    println(ans)
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}