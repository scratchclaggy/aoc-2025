import kotlin.io.path.Path
import kotlin.io.path.readText

class Day05 {
  companion object {
    private fun parseRanges(input: String) =
        input.lines().map { it.split("-").map { it.toLong() } }.map { (l, u) -> l..u }

    private fun parseItems(input: String) = input.lines().map { it.toLong() }

    private fun parseInput(input: String) =
        input.split("\n\n").let { (r, i) -> parseRanges(r) to parseItems(i) }

    private fun part1(ranges: List<LongRange>, items: List<Long>) =
        items.count { item -> ranges.any { range -> item in range } }

    private fun part2(ranges: List<LongRange>): Long =
        ranges
            .sortedBy { it.first }
            .fold(emptyList<LongRange>()) { ranges, next ->
              val prev = ranges.lastOrNull() ?: return@fold listOf(next)

              when {
                next.first > prev.last + 1 -> ranges.plusElement(next)
                next.last > prev.last -> ranges.dropLast(1).plusElement(prev.first..next.last)
                else -> ranges
              }
            }
            .sumOf { it.last - it.first + 1 }

    fun solve() {
      val testInput = Path("src/Day05_Test.txt").readText().trim()
      val (testRanges, testItems) = parseInput(testInput)
      check(part1(testRanges, testItems) == 3)
      check(part2(testRanges) == 14.toLong())

      val input = Path("src/Day05.txt").readText().trim()
      val (ranges, items) = parseInput(input)
      part1(ranges, items).println()
      part2(ranges).println()
    }
  }
}
