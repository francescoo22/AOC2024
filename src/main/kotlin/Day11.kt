import java.math.BigInteger

private val input = readInput()[0].split(" ").map { it.toBigInteger() }

fun process(x: BigInteger): List<BigInteger> {
    if (x == "0".toBigInteger()) return listOf("1".toBigInteger())
    if (x.toString().length % 2 == 0) {
        val sz = x.toString().length
        val x1 = x.toString().take(sz / 2).toBigInteger()
        val x2 = x.toString().drop(sz / 2).toBigInteger()
        return listOf(x1, x2)
    } else return listOf(x * "2024".toBigInteger())
}

private fun part1() {
    var x = input.toMutableList()
    repeat(25) {
        var nx = mutableListOf<BigInteger>()
        for (n in x)
            nx += process(n)
        x = nx
    }
    val ans = x.size
    check(ans == 217443)
    println(ans)
}

private fun part2() {
    var x = input.toMutableList()
    var map = mutableMapOf<BigInteger, BigInteger>()
    for (n in x) {
        map[n] = map.getOrPut(n) { "0".toBigInteger() } + ("1".toBigInteger())
    }
    repeat(75) {
        val nx = mutableMapOf<BigInteger, BigInteger>()
        for ((k, v) in map)
            for (proc in process(k)) {
                nx[proc] = nx.getOrPut(proc) { "0".toBigInteger() } + v
            }
        map = nx
    }
    var ans = map.values.sumOf { it }
    check(ans == "257246536026785".toBigInteger())
    println(ans)
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}