class Day03 {
  companion object {
    private fun part1(input: List<String>) =
        input
            .map { it.map { it.toString().toInt() } }
            .sumOf { batteries ->
              val leftCursor = batteries.dropLast(1).indices.maxBy { batteries[it] }
              val r = batteries.drop(leftCursor + 1).max()
              batteries[leftCursor] * 10 + r
            }

    private fun part2(input: List<String>) =
        input
            .map { it.map { it.toString().toLong() } }
            .sumOf { batteries ->
              (11 downTo 0)
                  .scan(-1) { l, r ->
                    batteries.indices.drop(l + 1).dropLast(r).maxBy { batteries[it] }
                  }
                  .drop(1)
                  .map { batteries[it] }
                  .fold(0.toLong()) { acc, i -> acc * 10 + i }
            }

    fun solve() {
      val testInput = readInput("Day03_Test")
      check(part1(testInput) == 357)
      check(part2(testInput) == 3121910778619)

      val input = readInput("Day03")
      part1(input).println()
      part2(input).println()
    }
  }
}
