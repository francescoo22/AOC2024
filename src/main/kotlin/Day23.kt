private val input = readInput().map {
    val (a, b) = it.split("-")
    a to b
}

private val graph = buildMap {
    input.forEach { (a, b) ->
        put(a, getOrPut(a) { emptyList<String>() } + b)
        put(b, getOrPut(b) { emptyList<String>() } + a)
    }
}

private fun part1() {
    var ans = 0
    graph.forEach { (c, value) ->
        for (i in 0..<value.size)
            for (j in i + 1..<value.size) {
                val a = value[i]
                val b = value[j]
                if (a in graph[b]!! && listOf(a, b, c).any { it[0] == 't' }) ans++
            }
    }
    ans /= 3
    println(ans)
}

private fun part2() {
    val connected3 = mutableSetOf<Set<String>>()
    graph.forEach { (c, value) ->
        for (i in 0..<value.size)
            for (j in i + 1..<value.size) {
                val a = value[i]
                val b = value[j]
                if (a in graph[b]!!) connected3.add(setOf(a, b, c))
            }
    }
    var fullyConnected = connected3.toSet()
    while (true) {
        val curSet = mutableSetOf<Set<String>>()
        fullyConnected.forEach { set ->
            graph.keys.forEach { newNode ->
                if (newNode !in set && set.all { it in graph[newNode]!! }) {
                    curSet += set + newNode
                }
            }
        }
        if (curSet.isEmpty()) break
        else fullyConnected = curSet
    }
    val ans = fullyConnected.single().sorted().joinToString(",")
    println(ans)
}

suspend fun main() {
    withTime { part1() }
    withTime { part2() }
}