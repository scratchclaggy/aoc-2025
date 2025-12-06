import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.max
import kotlin.math.min

class Day05 {
  companion object {
    private fun parseRanges(input: String) =
        input.lines().map { it.split("-").map { it.toLong() } }.map { (l, u) -> l to u }

    private fun parseItems(input: String) = input.lines().map { it.toLong() }

    private fun parseInput(input: String) =
        input.split("\n\n").let { (r, i) -> parseRanges(r) to parseItems(i) }

    private fun part1(ranges: List<Pair<Long, Long>>, items: List<Long>) =
        items.count { i -> ranges.any { (l, u) -> l <= i && i <= u } }

    private fun part2(ranges: List<Pair<Long, Long>>) =
        ranges
            .fold(mutableListOf<Pair<Long, Long>>()) { ranges, current ->
              var (low, high) = current

              ranges.removeAll { low <= it.first && high >= it.second }

              val lowIdx = ranges.indexOfFirst { it.first <= low && low <= it.second }
              if (lowIdx != -1) {
                low = ranges[lowIdx].first
                high = max(ranges[lowIdx].second, high)
                ranges.removeAt(lowIdx)
              }

              val highIdx = ranges.indexOfFirst { it.first <= high && high <= it.second }
              if (highIdx != -1) {
                low = min(ranges[highIdx].first, low)
                high = ranges[highIdx].second
                ranges.removeAt(highIdx)
              }

              ranges.add(low to high)

              ranges
            }
            .sumOf { (l, u) -> u - l + 1 }

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
