class Day04 {
  companion object {
    private fun parseInput(input: List<String>) =
        input
            .flatMapIndexed { y, row -> row.mapIndexed { x, c -> if (c == '@') x to y else null } }
            .filterNotNull()
            .toSet()

    private fun part1(locations: Set<Pair<Int, Int>>) =
        locations
            .map { (x, y) ->
              (-1..1)
                  .flatMap { n -> (-1..1).map { n to it } }
                  .count { (xOffset, yOffset) -> x + xOffset to y + yOffset in locations }
            }
            .count { it < 5 }

    private fun removeRolls(rolls: Set<Pair<Int, Int>>): Int {
      val next =
          rolls
              .filter { (x, y) ->
                (-1..1)
                    .flatMap { n -> (-1..1).map { n to it } }
                    .count { (xOffset, yOffset) -> x + xOffset to y + yOffset in rolls } >= 5
              }
              .toSet()
      val diff = rolls.size - next.size

      return if (diff == 0) 0 else diff + removeRolls(next)
    }

    private fun part2(locations: Set<Pair<Int, Int>>) = removeRolls(locations)

    fun solve() {
      val testInput = parseInput(readInput("Day04_Test"))
      check(part1(testInput) == 13)
      check(part2(testInput) == 43)

      val input = parseInput(readInput("Day04"))
      part1(input).println()
      part2(input).println()
    }
  }
}
