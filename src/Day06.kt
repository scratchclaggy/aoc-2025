import kotlin.io.path.Path
import kotlin.io.path.readText

class Day06 {
  companion object {
    private fun part1(input: List<String>) =
        input
            .map { it.trim().split("""\s+""".toRegex()) }
            .let {
              val cols = it[0].size
              val rows = it.size
              List(cols) { j -> List(rows) { i -> it[i][j] } }
            }
            .sumOf {
              when (it.last()) {
                "+" -> it.dropLast(1).map { it.toLong() }.sum()
                "*" -> it.dropLast(1).map { it.toLong() }.reduce { acc, n -> acc * n }
                else -> error("Unreachable")
              }
            }

    private fun iteratePart2(input: List<List<Char>>): Long {
      if (input.isEmpty()) return 0

      val emptyRow = input.indexOfFirst { it.all { it == ' ' } }
      val end = if (emptyRow == -1) input.size else emptyRow

      val set = input.take(end).map { it.dropLast(1).joinToString("").trim().toLong() }

      return when (input.first().last()) {
        '+' -> set.sum()
        '*' -> set.reduce { acc, n -> acc * n }
        else -> error("Unreachable")
      } + iteratePart2(input.drop(end + 1))
    }

    private fun part2(input: List<String>) =
        input
            .map { it.toCharArray() }
            .let {
              val cols = it[0].size
              val rows = it.size
              List(cols) { j -> List(rows) { i -> it[i][j] } }
            }
            .let { iteratePart2(it) }

    private fun readInput(name: String) = Path("src/$name.txt").readText().dropLast(1).lines()

    fun solve() {
      val testInput = readInput("Day06_Test")
      check(part1(testInput) == 4277556.toLong())
      check(part2(testInput) == 3263827.toLong())

      val input = readInput("Day06")
      part1(input).println()
      part2(input).println()
    }
  }
}
