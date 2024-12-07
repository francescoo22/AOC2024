import java.math.BigInteger

private fun tryAll1(n: BigInteger, curRes: BigInteger, xs: List<BigInteger>): Boolean =
    if (xs.isEmpty() || curRes > n) n == curRes
    else tryAll1(n, curRes + xs.first(), xs.drop(1)) || tryAll1(n, curRes * xs.first(), xs.drop(1))

private fun tryAll2(n: BigInteger, curRes: BigInteger, xs: List<BigInteger>): Boolean =
    if (xs.isEmpty() || curRes > n) n == curRes
    else {
        tryAll2(n, curRes + xs.first(), xs.drop(1)) ||
                tryAll2(n, curRes * xs.first(), xs.drop(1)) ||
                tryAll2(n, (curRes.toString() + xs.first().toString()).toBigInteger(), xs.drop(1))
    }

private fun getAns(input: List<String>, tryAll: (BigInteger, BigInteger, List<BigInteger>) -> Boolean): BigInteger {
    val parsedInput = input.map { line ->
        line.split(":").let { it[0].toBigInteger() to it[1].trim().split(" ").map { it.toBigInteger() } }
    }
    return parsedInput.sumOf { (res, nums) ->
        if (tryAll(res, nums[0], nums.drop(1))) res else "0".toBigInteger()
    }
}

private fun part1(input: List<String>) {
    val ans = getAns(input, ::tryAll1)
    println(ans)
}

private fun part2(input: List<String>) {
    val ans = getAns(input, ::tryAll2)
    println(ans)
}


suspend fun main() {
    withTime { part1(readInput()) }
    withTime { part2(readInput()) }
}