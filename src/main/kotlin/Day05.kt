typealias DependenciesMap = MutableMap<Int, MutableList<Int>>

private fun parse1(s: String): Pair<Int, Int> =
    s.split("|")[0].toInt() to s.split("|")[1].toInt()

private fun parse2(s: String): List<Int> =
    s.split(",").map { it.toInt() }

private fun parseInput(input: List<String>): Pair<DependenciesMap, IntMatrix> {
    val dependencies: DependenciesMap = mutableMapOf()
    val sequences = mutableListOf<List<Int>>()
    var parseDependencies = true
    input.forEach { line ->
        when {
            line == "" -> parseDependencies = false
            parseDependencies -> {
                val (a, b) = parse1(line)
                dependencies.getOrPut(b) { mutableListOf() }.add(a)
            }
            else -> sequences.add(parse2(line))
        }
    }
    return dependencies to sequences
}

private fun valid(nums: List<Int>, dependencies: DependenciesMap): Boolean {
    val set = mutableListOf<Int>()
    for (n in nums) {
        set.add(n)
        if (!numValid(n, set, dependencies, nums)) return false
    }
    return true
}

private fun numValid(n: Int, set: List<Int>, dependencies: DependenciesMap, nums: List<Int>): Boolean {
    for (dep in dependencies[n] ?: listOf()) {
        if (dep !in set && dep in nums) return false
    }
    return true
}

private fun part1(input: List<String>) {
    val (dependencies, sequences) = parseInput(input)
    val ans = sequences.sumOf {
        if (valid(it, dependencies)) it[it.size / 2] else 0
    }
    println(ans)
}

private fun part2(input: List<String>) {
    val (dependencies, sequences) = parseInput(input)
    val ans = sequences.sumOf {
        if (!valid(it, dependencies)) {
            val oldNUms = it.toMutableList()
            val newNums = mutableListOf<Int>()
            while (oldNUms.isNotEmpty()) {
                for (n in oldNUms) {
                    if (numValid(n, newNums, dependencies, it)) {
                        newNums.add(n)
                        oldNUms.remove(n)
                        break
                    }
                }
            }
            newNums[newNums.size / 2]
        } else 0
    }
    println(ans)
}


fun main() {
    part1(readInput())
    part2(readInput())
}