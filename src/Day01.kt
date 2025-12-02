import kotlin.math.abs

class Day01 {
  companion object {
    private val parseMoves = { line: String ->
      """([L|R])(\d+)""".toRegex().matchEntire(line)?.destructured?.let { (direction, distance) ->
        if (direction == "L") -distance.toInt() else distance.toInt()
      } ?: throw Error("Bad input")
    }

    private fun part1(input: List<String>): Int =
        input.map(parseMoves).scan(50, Int::plus).count { it % 100 == 0 }

    private fun part2(input: List<String>) =
        input
            .map(parseMoves)
            .scan(50 to 0) { (lastLocation, _), move -> (lastLocation + move).mod(100) to move }
            .zipWithNext { (lastLocation, _), (_, nextMove) -> lastLocation to nextMove }
            .sumOf { (location, move) ->
              // Revolutions in the move
              abs(move) / 100 +
                  // Whether the move crosses the threshold (aside from full revolutions)
                  if (location != 0 && location + (move % 100) !in 1..99) 1 else 0
            }

    fun solve() {
      val testInput = readInput("Day01_Test")
      check(part1(testInput) == 3)
      check(part2(testInput) == 6)

      val input = readInput("Day01")
      part1(input).println()
      part2(input).println()
    }
  }
}
