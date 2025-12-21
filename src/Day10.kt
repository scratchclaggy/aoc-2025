import com.google.ortools.*
import com.google.ortools.linearsolver.*

class Day10 {
  companion object {

    private fun <T> Collection<T>.powerset(): List<List<T>> =
        if (isEmpty()) {
          listOf(emptyList())
        } else {
          val head = first()
          val tailPowerset = drop(1).powerset()
          tailPowerset + tailPowerset.map { it + head }
        }

    private fun part1(input: List<String>) =
        input
            .map { it.split(" ") }
            .map {
              it.first().drop(1).dropLast(1) to
                  it.drop(1).dropLast(1).map {
                    it.drop(1).dropLast(1).split(",").map { it.toInt() }
                  }
            }
            .map { (target, btns) ->
              target.fold(0) { acc, c ->
                when (c) {
                  '#' -> acc.shl(1) + 1
                  else -> acc.shl(1)
                }
              } to btns.map { it.fold(0) { acc, i -> acc.or(1.shl(target.length - 1 - i)) } }
            }
            .sumOf { (target, btns) ->
              btns
                  .powerset()
                  .sortedBy { it.size }
                  .drop(1)
                  .map { it.size to it.reduce { acc, i -> acc.xor(i) } }
                  .find { target == it.second }!!
                  .first
            }

    private fun part2(input: List<String>) =
        input
            .map { it.split(" ") }
            .map {
              it.last().drop(1).dropLast(1).split(",").map { it.toInt() } to
                  it.drop(1)
                      .dropLast(1)
                      .map { it.drop(1).dropLast(1).split(",").map { it.toInt() } }
                      .sortedByDescending { it.size }
            }
            .sumOf { (target, btns) ->
              val solver = MPSolver.createSolver("SCIP")

              val vars =
                  btns.indices.map { solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "x$it") }

              target.forEachIndexed { i, count ->
                val constraint = solver.makeConstraint(count.toDouble(), count.toDouble(), "c$i")
                btns.forEachIndexed { j, btn ->
                  constraint.setCoefficient(vars[j], if (i in btn) 1.toDouble() else 0.toDouble())
                }
              }

              val objective = solver.objective()
              vars.forEach { objective.setCoefficient(it, 1.toDouble()) }

              objective.setMinimization()
              solver.solve()

              objective.value()
            }

    fun solve() {
      Loader.loadNativeLibraries()
      val testInput = readInput("Day10_Test")
      check(part1(testInput) == 7)
      check(part2(testInput) == 33.toDouble())

      val input = readInput("Day10")
      part1(input).println()
      part2(input).println()
    }
  }
}
