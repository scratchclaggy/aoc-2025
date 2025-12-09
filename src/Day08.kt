import kotlin.math.pow
import kotlin.math.sqrt

typealias Point = Triple<Double, Double, Double>

infix fun Point.distanceTo(that: Point): Double =
    sqrt(
        (that.first - this.first).pow(2) +
            (that.second - this.second).pow(2) +
            (that.third - this.third).pow(2))

class Day08 {
  companion object {
    private fun parseInput(input: List<String>) =
        input.map { it.split(",").map { it.toDouble() } }.map { (x, y, z) -> Triple(x, y, z) }

    private fun toPairs(input: List<Point>) =
        input
            .let { points ->
              points.flatMapIndexed { i, a -> points.drop(i + 1).map { b -> a to b } }
            }
            .sortedBy { (a, b) -> a distanceTo b }

    private fun part1(input: List<Pair<Point, Point>>) =
        input
            .fold(mutableListOf<MutableSet<Point>>()) { circuits, (a, b) ->
              val aCircuit = circuits.find { a in it }
              val bCircuit = circuits.find { b in it }

              when {
                aCircuit != null && bCircuit != null && aCircuit == bCircuit -> {}
                aCircuit != null && bCircuit != null -> {
                  aCircuit.addAll(bCircuit)
                  circuits.remove(bCircuit)
                }
                aCircuit != null -> aCircuit.add(b)
                bCircuit != null -> bCircuit.add(a)
                else -> circuits.add(mutableSetOf(a, b))
              }

              circuits
            }
            .map { it.size }
            .sortedDescending()
            .take(3)
            .reduce { acc, n -> acc * n }

    private fun part2(points: List<Point>, pairs: List<Pair<Point, Point>>): Int {
      val circuits = points.map { mutableSetOf(it) }.toMutableSet()

      for ((a, b) in pairs) {
        val aCircuit = circuits.find { a in it }
        val bCircuit = circuits.find { b in it }

        if (aCircuit != bCircuit) {
          aCircuit?.addAll(bCircuit!!)
          circuits.removeIf { b in it && a !in it }
        }

        if (circuits.size == 1) {
          return (a.first * b.first).toInt()
        }
      }

      return error("Unreachable")
    }

    fun solve() {
      val testPoints = parseInput(readInput("Day08_Test"))
      val testPairs = toPairs(testPoints)
      check(part1(testPairs.take(10)) == 40)
      check(part2(testPoints, testPairs) == 25272)

      val points = parseInput(readInput("Day08"))
      val pairs = toPairs(points)
      part1(pairs.take(1000)).println()
      part2(points, pairs).println()
    }
  }
}
