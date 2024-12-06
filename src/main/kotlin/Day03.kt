private fun computeMul(mul: String): Int {
    val x = mul.split(",")[0].drop(4).toInt()
    val y = mul.split(",")[1].dropLast(1).toInt()
    return x * y
}

private fun part1(input: List<String>) {
    val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
    var ans = 0
    input.forEach { line ->
        regex.findAll(line).forEach {
            ans += computeMul(it.value)
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
            when {
                it.value == "do()" -> enabled = true
                it.value == "don't()" -> enabled = false
                enabled -> ans += computeMul(it.value)
            }
        }
    }
    println(ans)
}

fun main() {
    part1(readInput())
    part2(readInput())
}