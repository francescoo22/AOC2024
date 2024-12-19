import java.util.PriorityQueue
import kotlin.math.min

private val input = readInput()
lateinit var initPos: IntInt
lateinit var endPos: IntInt

private fun part1() {
    val visited = List(input.size) { MutableList(input[0].length) { MutableList(3) { MutableList(3) { false } } } }
    val queue = PriorityQueue<Triple<Int, IntInt, IntInt>>(compareBy { it.first })
    queue.add(Triple(0, initPos, 0 to 1))
    var curPos = queue.poll()
    while (curPos.second != endPos) {
        val (i, j) = curPos.second
        val (di, dj) = curPos.third
        if (visited[i][j][di + 1][dj + 1]) {
            curPos = queue.poll()
            continue
        } else visited[i][j][di + 1][dj + 1] = true
        NorthSouthWestEast.forEach { (ndi, ndj) ->
            val rotationCost = when {
                ndi == di && ndj == dj -> 0
                ndi == di || ndj == dj -> 2000
                else -> 1000
            }
            if (input[i + ndi][j + ndj] != '#' && !visited[i + ndi][j + ndj][ndi + 1][ndj + 1]) {
                queue.add(
                    Triple(
                        curPos.first + rotationCost + 1,
                        i + ndi to j + ndj,
                        ndi to ndj
                    )
                )
            }
        }
        curPos = queue.poll()
    }
    println(curPos.first)
}

data class Node16(
    val pos: IntInt,
    val dir: IntInt,
)

data class NodeWithCost16(
    val pos: IntInt,
    val dir: IntInt,
    val cost: Int,
) {
    fun node() = Node16(pos, dir)
}

private fun getAdjacentNodes(node: Node16): List<NodeWithCost16> {
    val (i, j) = node.pos
    val (di, dj) = node.dir
    val res = mutableListOf<NodeWithCost16>()
    NorthSouthWestEast.forEach { (ndi, ndj) ->
        val ni = i + ndi
        val nj = j + ndj
        if (input[ni][nj] != '#') {
            val cost = when {
                ndi == di && ndj == dj -> 1
                ndi != di && ndj != dj -> 1001
                else -> return@forEach
            }
            res.add(
                NodeWithCost16(
                    pos = ni to nj,
                    dir = ndi to ndj,
                    cost = cost
                )
            )
        }
    }
    return res
}

private fun part2() {
    val graph: Map<Node16, List<NodeWithCost16>> = buildMap {
        input.forEachIndexedS { i, j, c ->
            if (c != '#') {
                NorthSouthWestEast.forEach { dir ->
                    val node = Node16(i to j, dir)
                    put(node, getAdjacentNodes(node))
                }
            }
        }
    }
    val visited: MutableMap<Node16, Pair<Int, Set<Node16>>> = mutableMapOf()
    val queue = PriorityQueue<Pair<NodeWithCost16, Set<Node16>>>(compareBy { it.first.cost })
    queue.add(NodeWithCost16(initPos, 0 to 1, 0) to setOf())
    while (queue.isNotEmpty()) {
        val (curNodeWithCost, curPath) = queue.poll()
        val curNode = curNodeWithCost.node()
        if (visited[curNode] == null) {
            visited[curNode] = curNodeWithCost.cost to curPath
            graph[curNode]!!.forEach {
                queue.add(it.copy(cost = it.cost + curNodeWithCost.cost) to curPath + curNode)
            }
        } else {
            val (visCost, visPath) = visited[curNode]!!
            if (visCost == curNodeWithCost.cost) {
                visited[curNode] = visCost to visPath + curPath
                graph[curNode]!!.forEach {
                    queue.add(it.copy(cost = it.cost + curNodeWithCost.cost) to curPath + curNode)
                }
            }
        }
    }

    var minCost = Int.MAX_VALUE
    NorthSouthWestEast.forEach {
        visited[Node16(endPos, it)]?.let {
            minCost = min(it.first, minCost)
        }
    }
    val ans = mutableSetOf<IntInt>()
    NorthSouthWestEast.forEach {
        visited[Node16(endPos, it)]?.let {
            if (it.first == minCost) ans += it.second.map { node16 -> node16.pos }
        }
    }
    println(ans.size + 1)
}

suspend fun main() {
    input.forEachIndexedS { i, j, c ->
        if (c == 'S') initPos = i to j
        if (c == 'E') endPos = i to j
    }
    withTime { part1() }
    withTime { part2() }
}