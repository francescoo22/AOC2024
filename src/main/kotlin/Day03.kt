private fun part1(input: List<String>) {
    val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
    var ans = 0
    input.forEach { line ->
        regex.findAll(line).forEach {
            val x = it.value.split(",")[0].drop(4).toInt()
            val y = it.value.split(",")[1].dropLast(1).toInt()
            ans += x * y
        }
    }
    println(ans)
}

private fun part2(input: List<String>) {
    val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)")
    var ans = 0
    var enabled = true
    input.forEach { line ->
        regex.findAll(line).forEach {
            when (it.value) {
                "do()" -> enabled = true
                "don't()" -> enabled = false
                else -> {
                    if (enabled) {
                        val x = it.value.split(",")[0].drop(4).toInt()
                        val y = it.value.split(",")[1].dropLast(1).toInt()
                        ans += x * y
                    }
                }
            }
        }
    }
    println(ans)
}

fun main() {
    part1(readInput())
    part2(readInput())
}