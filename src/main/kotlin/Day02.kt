import kotlin.math.abs

private fun safe(xs: List<Int>) : Boolean {
    var increasing = false
    var decreasing = false
    for (i in 0..xs.size - 2) {
        val d = xs[i] - xs[i+1]
        if (d == 0 || abs(d) > 3) return false
        if (d > 0) decreasing = true
        else increasing = true
    }
    return increasing xor decreasing
}

private fun safe2(xs: List<Int>): Boolean {
    for (i in 0..<xs.size){
        val ys = xs.toMutableList()
        ys.removeAt(i)
        if (safe(ys)) return true
    }
    return false
}

private fun part1(input: List<String>) {
    val x = input.map { it.split(" ").map { n -> n.toInt() } }
    val res = x.count { safe(it) }
    println(res)
}

private fun part2(input: List<String>) {
    val x = input.map { it.split(" ").map { n -> n.toInt() } }
    val res = x.count { safe2(it) }
    println(res)
}

fun main() {
    part1(readInput())
    part2(readInput())
}