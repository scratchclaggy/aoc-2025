class Day07 {
  companion object {
    private fun part1(input: List<String>) =
        input
            .drop(1)
            .scan(input.first().toList()) { prev, next ->
              val beams = prev.mapIndexed { i, c -> if (c == 'S') i else null }.filterNotNull()
              val activeSplitters =
                  next
                      .mapIndexed { i, c -> if (c == '^' && i in beams) i else null }
                      .filterNotNull()
              next.mapIndexed { i, _ ->
                when {
                  i + 1 in activeSplitters || i - 1 in activeSplitters -> 'S'
                  i in activeSplitters -> '^'
                  i in beams -> 'S'
                  else -> '.'
                }
              }
            }
            .flatten()
            .count { it == '^' }

    private fun part2(input: List<String>) =
        input
            .drop(1)
            .fold(input.first().map { if (it == 'S') 1.toLong() else 0 }) { prev, next ->
              val activeSplitters =
                  next
                      .mapIndexed { i, c -> if (c == '^' && prev[i] > 0) i else null }
                      .filterNotNull()
              next.mapIndexed { i, _ ->
                when {
                  i - 1 in activeSplitters && i + 1 in activeSplitters ->
                      prev[i - 1] + prev[i] + prev[i + 1]
                  i - 1 in activeSplitters -> prev[i - 1] + prev[i]
                  i + 1 in activeSplitters -> prev[i + 1] + prev[i]
                  i in activeSplitters -> 0
                  else -> prev[i]
                }
              }
            }
            .sum()

    fun solve() {
      val testInput = readInput("Day07_Test")
      check(part1(testInput) == 21)
      check(part2(testInput) == 40.toLong())

      val input = readInput("Day07")
      part1(input).println()
      part2(input).println()
    }
  }
}
