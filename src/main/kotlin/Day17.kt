import java.math.BigInteger

private val input = readInput()

private var A = input[0].split(" ").last().toBigInteger()
private var B = input[1].split(" ").last().toBigInteger()
private var C = input[2].split(" ").last().toBigInteger()
private var IP = 0
private var res = mutableListOf<BigInteger>()
private val program = input[4].split(" ")[1].split(",").map { it.toInt() }

fun resetRegisters() {
    A = input[0].split(" ").last().toBigInteger()
    B = input[1].split(" ").last().toBigInteger()
    C = input[2].split(" ").last().toBigInteger()
    IP = 0
    res = mutableListOf<BigInteger>()
}

private val operations = listOf<(BigInteger) -> Unit>(
    { A = A / BigInteger("2").pow(combo(it).toInt()) },
    { B = B xor it },
    { B = combo(it) % BigInteger("8") },
    { if (A != BigInteger("0")) IP = (it - BigInteger("2")).toInt() },
    { B = B xor C },
    { res.add(combo(it) % BigInteger("8")) },
    { B = A / BigInteger("2").pow(combo(it).toInt()) },
    { C = A / BigInteger("2").pow(combo(it).toInt()) },
)

fun combo(op: BigInteger) = when (op) {
    BigInteger("4") -> A
    BigInteger("5") -> B
    BigInteger("6") -> C
    else -> op
}

private fun part1() {
    resetRegisters()
    val program = input[4].split(" ")[1].split(",").map { it.toInt() }
    while (IP + 1 < program.size) {
        operations[program[IP]](program[IP + 1].toBigInteger())
        IP += 2
    }
    res.joinToString(",").println()
    check(res.joinToString(",") == "1,4,6,1,6,4,3,0,3")
}

private val pattern = buildList {
    for (i in 0..1023) {
        resetRegisters()
        A = i.toBigInteger()
        while (IP + 1 < program.size) {
            operations[program[IP]](program[IP + 1].toBigInteger())
            IP += 2
        }
        add(res[0].toInt())
    }
}

private fun findAnswer17(programIndex: Int, patternIndex: Int): BigInteger? {
    if(programIndex<0) return BigInteger.ZERO
    for (i in 0..7) {
        if (pattern[(patternIndex + i) % pattern.size] != program[programIndex]) continue
        val nextAns = findAnswer17(programIndex - 1, ((patternIndex + i) * 8) % pattern.size)
        if (nextAns != null) {
            return nextAns + BigInteger.TWO.pow(programIndex * 3) * i.toBigInteger()
        }
    }
    return null
}

private fun part2() {
    findAnswer17(program.size - 1, 0)?.println()
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}
