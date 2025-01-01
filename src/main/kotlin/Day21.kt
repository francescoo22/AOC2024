import java.math.BigInteger

private val input = readInput()
private var dp: MutableMap<Triple<Char, Char, Int>, BigInteger> = mutableMapOf()
private var stages = 3
private val numPad = listOf(
    "#####",
    "#789#",
    "#456#",
    "#123#",
    "##0A#",
    "#####",
)
private val keyPad = listOf(
    "#####",
    "##^A#",
    "#<v>#",
    "#####",
)

private fun posOf(button: Char, pad: List<String>): IntInt {
    for (i in pad.indices)
        for (j in pad[0].indices)
            if (pad[i][j] == button) return i to j
    throw IllegalStateException("Button not found")
}

private fun path(from: Char, to: Char, pad: List<String>): String {
    val (x1, y1) = posOf(from, pad)
    val (x2, y2) = posOf(to, pad)
    val dx = x1 - x2
    val dy = y1 - y2
    var res = ""
    if (dx > 0) repeat(dx) { res += '^' }
    else repeat(-dx) { res += 'v' }
    if (dy > 0) repeat(dy) { res += '<' }
    else repeat(-dy) { res += '>' }
    return res
}

private fun isValid(path: String, c1: Char, pad: List<String>): Boolean {
    val realPath = path.filter { it != 'A' }
    var (i, j) = posOf(c1, pad)
    for (c in realPath) {
        val (di, dj) = directionFromChar(c)
        i += di
        j += dj
        if (pad[i][j] == '#') return false
    }
    return true
}

fun shortestPath(c1: Char, c2: Char, stage: Int): BigInteger {
    if (c1 == c2) return BigInteger.ONE

    dp[Triple(c1, c2, stage)]?.let { return it }

    val curPad = if (stage == stages) numPad else keyPad

    val p1 = "A" + path(c1, c2, curPad) + 'A'
    val p2 = "A" + path(c1, c2, curPad).reversed() + 'A'
    if (stage == 1) return (p1.length - 1).toBigInteger()

    var sp1 = BigInteger.ZERO
    var sp2 = BigInteger.ZERO
    for (i in 0..p1.length - 2) {
        if (isValid(p1, c1, curPad)) sp1 += shortestPath(p1[i], p1[i + 1], stage - 1)
        if (isValid(p2, c1, curPad)) sp2 += shortestPath(p2[i], p2[i + 1], stage - 1)
    }
    val res = when {
        !isValid(p1, c1, curPad) -> sp2
        !isValid(p2, c1, curPad) -> sp1
        else -> {
            if (sp1 < sp2) sp1 else sp2
        }
    }
    dp[Triple(c1, c2, stage)] = res
    return res
}

private fun getAns(stagesNumber: Int): BigInteger {
    stages = stagesNumber
    dp = mutableMapOf()
    var ans = BigInteger.ZERO
    input.forEach {
        val s = "A$it"
        var tmpAns = BigInteger.ZERO
        for (i in 0..s.length - 2) {
            tmpAns += shortestPath(s[i], s[i + 1], stages)
        }
        ans += tmpAns * it.dropLast(1).toBigInteger()
    }
    return ans
}

private fun part1() {
    var ans = getAns(3)
    println(ans)
    check(ans == "205160".toBigInteger())
}

private fun part2() {
    var ans = getAns(26)
    println(ans)
    check(ans == "252473394928452".toBigInteger())
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}
