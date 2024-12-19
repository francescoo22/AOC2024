import java.math.BigInteger

private val input = readInput()
private val patterns = input[0].split(", ").toSet()
private val designs = input.drop(2)
private val dp: MutableMap<String, Boolean> = mutableMapOf()
private val dp2: MutableMap<String, BigInteger> = mutableMapOf()

private fun possible(design: String): Boolean {
    if (design.isEmpty()) return true
    if (dp[design] != null) return dp[design]!!
    patterns.forEach {
        if (design.take(it.length) == it && possible(design.drop(it.length))) {
            dp[design] = true
            return true
        }
    }
    dp[design] = false
    return false
}

private fun arrangements(design: String): BigInteger {
    if (design.isEmpty()) return "1".toBigInteger()
    if (dp2[design] != null) return dp2[design]!!
    var tot = "0".toBigInteger()
    patterns.forEach {
        if (design.take(it.length) == it) {
            tot += arrangements(design.drop(it.length))
        }
    }
    dp2[design] = tot
    return tot
}

private fun part1() {
    designs.count { possible(it) }.println()
}

private fun part2() {
    designs.sumOf { arrangements(it) }.println()
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}