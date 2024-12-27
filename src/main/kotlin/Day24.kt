private val input = readInput()
private val wires: MutableMap<String, Boolean> = mutableMapOf()
private val gates: MutableMap<String, Gate> = mutableMapOf()

private data class Gate(
    val l: String,
    val op: String,
    val r: String,
)

private fun resolve(wire: String): Boolean {
    wires[wire]?.let { return it }

    val (l, op, r) = gates[wire]!!
    val resL = resolve(l)
    val resR = resolve(r)
    return when (op) {
        "AND" -> resL && resR
        "OR" -> resL || resR
        "XOR" -> resL xor resR
        else -> throw IllegalStateException("Unexpected operation '$op'")
    }
}

private fun parseInput() {
    var parsePart1 = true
    input.forEach {
        when {
            it == "" -> parsePart1 = false
            parsePart1 -> {
                val (key, value) = it.split(": ")
                wires[key] = value == "1"
            }

            else -> {
                val (a, b, c, _, d) = it.split(" ")
                gates[d] = Gate(a, b, c)
            }
        }
    }
}

private fun part1() {
    gates.keys
        .filter { it.startsWith('z') }
        .sortedDescending()
        .map { if (resolve(it)) 1 else 0 }
        .joinToString("")
        .toLong(2)
        .println()
}


private fun part2() {
    val lastZ = gates.keys.filter { it.startsWith('z') }.max()
    val wrongOutZ = gates.filter { (k, v) -> k.startsWith('z') && k != lastZ && v.op != "XOR" }.keys
    val wrongXor1 = gates.filter { (k, v) ->
        v.op == "XOR" && !(listOf(v.l, v.r).all { it.startsWith('x') || it.startsWith('y') } || k.startsWith('z'))
    }.keys
    val wrongXor2 = gates.filter { (k, v) ->
        v.op == "XOR" && v.l != "x00" && gates.any { (_, v2) -> (k == v2.l || k == v2.r) && v2.op == "OR" }
    }.keys
    val wrongAnd = gates.filter { (k, v) ->
        v.op == "AND" && v.l != "x00" && gates.any { (_, v2) -> (k == v2.l || k == v2.r) && v2.op != "OR" }
    }.keys

    val ans = buildSet {
        addAll(wrongOutZ)
        addAll(wrongXor1)
        addAll(wrongXor2)
        addAll(wrongAnd)
    }.sorted().joinToString(",")
    println(ans)
}

suspend fun main() {
    parseInput()
    withTime { part1() }
    withTime { part2() }
}