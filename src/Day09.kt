import kotlin.math.abs

fun areaOf(points: Pair<Pair<Long, Long>, Pair<Long, Long>>): Long =
    (1 + abs(points.first.first - points.second.first)) *
        (1 + abs(points.first.second - points.second.second))

class Day09 {
  companion object {
    private fun part1(input: List<String>): Long =
        input
            .map { it.split(",").map { it.toLong() } }
            .map { (x, y) -> x to y }
            .let { points ->
              points.flatMapIndexed { i, a -> points.drop(i + 1).map { b -> a to b } }
            }
            .maxOfOrNull { areaOf(it) }!!

    private fun part2(input: List<String>): Long {
      val points = input.map { it.split(",").map { it.toLong() } }.map { (x, y) -> x to y }

      val boundarySegments = points.let { it.plus(it.first()) }.zipWithNext()

      val rectangles =
          points
              .let { points ->
                points.flatMapIndexed { i, a -> points.drop(i + 1).map { b -> a to b } }
              }
              .sortedByDescending { areaOf(it) }

      /**
       * CREDIT: @bdlepla
       * http://github.com/bdlepla/AdventOfCode2025/blob/880aad212f1c24c6a4ef2d810949bdf9d12043c5/src/Day09.kt
       *
       * I initially tried to filter rectangles by checking whether the top-right and bottom-left
       * corners were contained within the polygon formed by all points. But it was really
       * non-performant and didn't quite work. Turns out it's much simpler to sort by greatest area
       * and then find the first rectangle that does not exist outside the limits of any boundary
       * segment.
       */
      return rectangles
          .first { (r1, r2) ->
            val l = minOf(r1.first, r2.first)
            val r = maxOf(r1.first, r2.first)
            val t = minOf(r1.second, r2.second)
            val b = maxOf(r1.second, r2.second)

            boundarySegments.none { (l1, l2) ->
              val w = minOf(l1.first, l2.first)
              val e = maxOf(l1.first, l2.first)
              val n = minOf(l1.second, l2.second)
              val s = maxOf(l1.second, l2.second)

              w < r && e > l && n < b && s > t
            }
          }
          .let { areaOf(it) }
    }

    fun solve() {
      val testInput = readInput("Day09_Test")
      check(part1(testInput) == 50.toLong())
      check(part2(testInput) == 24.toLong())

      val input = readInput("Day09")
      part1(input).println()
      part2(input).println()
    }
  }
}
