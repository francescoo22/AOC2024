import java.math.BigInteger
import kotlin.collections.mutableListOf
import kotlin.math.max

private val input = readInput().map { it.toBigInteger() }
private val MOD = 16777216.toBigInteger()
private const val REPS = 2000

private fun evolve(n: BigInteger): BigInteger {
    val phase1 = ((n * 64.toBigInteger()) xor n) % MOD
    val phase2 = ((phase1 / 32.toBigInteger()) xor phase1) % MOD
    return ((phase2 * 2048.toBigInteger()) xor phase2) % MOD
}

private fun multipleEvolve(n: BigInteger, t: Int): BigInteger {
    var x = n
    repeat(t.toInt()) {
        x = evolve(x)
    }
    return x
}

private fun part1() {
    input.sumOf { multipleEvolve(it, REPS) }.println()
}

private fun part2() {
    val seq = mutableListOf<Int>()
    val map = List(input.size) {
        mutableMapOf<List<Int>, Int>()
    }
    val keys = mutableSetOf<List<Int>>()
    input.forEachIndexed { i, n ->
        seq.clear()
        var x = n
        var prev = n % 10.toBigInteger()
        repeat(REPS) {
            x = evolve(x)
            val cur = x % 10.toBigInteger()
            if (seq.size == 4) seq.removeAt(0)
            seq.add((cur - prev).toInt())
            if (seq.size == 4 && map[i][seq.toList()] == null) {
                map[i][seq.toList()] = cur.toInt()
                keys.add(seq.toList())
            }
            prev = cur
        }
    }

    var ans = 0
    keys.forEach { key ->
        var cur = 0
        input.indices.forEach {
            cur += map[it][key] ?: 0
        }
        ans = max(ans, cur)
    }
    println(ans)
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}