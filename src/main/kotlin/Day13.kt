import java.math.BigInteger
import kotlin.plus

private val input = readInput().map { "$it," }

private data class Configuration(
    val x1: BigInteger,
    val x2: BigInteger,
    val x3: BigInteger,
    val y1: BigInteger,
    val y2: BigInteger,
    val y3: BigInteger,
)

private val reg1 = Regex("X\\+\\d{1,10},")
private val reg2 = Regex("Y\\+\\d{1,10},")
private val reg3 = Regex("X=\\d{1,10},")
private val reg4 = Regex("Y=\\d{1,10},")

private fun getConfiguration(i: Int): Configuration {
    val x1 = reg1.find(input[i])!!.value.drop(2).dropLast(1).toBigInteger()
    val y1 = reg2.find(input[i])!!.value.drop(2).dropLast(1).toBigInteger()
    val x2 = reg1.find(input[i + 1])!!.value.drop(2).dropLast(1).toBigInteger()
    val y2 = reg2.find(input[i + 1])!!.value.drop(2).dropLast(1).toBigInteger()
    val x3 = reg3.find(input[i + 2])!!.value.drop(2).dropLast(1).toBigInteger()
    val y3 = reg4.find(input[i + 2])!!.value.drop(2).dropLast(1).toBigInteger()
    return Configuration(x1, x2, x3, y1, y2, y3)
}

private fun cost(config: Configuration): BigInteger {
    val (x1, x2, x3, y1, y2, y3) = config
    val m = (x1 * y3 - y1 * x3) / (-y1 * x2 + x1 * y2)
    val n = (x3 - m * x2) / x1
    return if (x1 * n + x2 * m == x3 && y1 * n + y2 * m == y3) {
        "3".toBigInteger() * n + m
    } else "0".toBigInteger()
}

private fun part1() {
    var i = 0
    var ans = "0".toBigInteger()
    while (i < input.size) {
        val config = getConfiguration(i)
        ans += cost(config)
        i += 4
    }
    println(ans)
}

private fun part2() {
    var i = 0
    var ans = "0".toBigInteger()
    while (i < input.size) {
        val config = getConfiguration(i)
        ans += cost(
            config.copy(
                x3 = config.x3 + "10000000000000".toBigInteger(),
                y3 = config.y3 + "10000000000000".toBigInteger()
            )
        )
        i += 4
    }
    println(ans)
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}