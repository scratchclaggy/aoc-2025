import java.math.BigInteger

class Day11 {
  companion object {
    private fun part1(input: List<String>): Int =
        input
            .map { it.split(" ") }
            .map { it.first().dropLast(1) to it.drop(1).toList() }
            .toMap()
            .let {
              fun traverse(next: String, visited: Set<String>): Int {
                return if (next == "out") 1
                else if (next in visited) return 0
                else it[next]?.sumOf { traverse(it, visited + next) } ?: 0
              }

              traverse("you", emptySet())
            }

    private fun part2(input: List<String>): BigInteger {
      val graph =
          input
              .map { it.split(" ") }
              .map { it.first().dropLast(1) to it.drop(1).toList() }
              .plus("out" to emptyList())
              .toMap()

      val indegree = graph.keys.map { it to 0 }.toMap().toMutableMap()
      graph.values.forEach { it.forEach { indegree[it] = indegree[it]!! + 1 } }

      val queue = ArrayDeque(indegree.filter { it.value == 0 }.keys)
      val toposort = mutableListOf<String>()

      while (queue.isNotEmpty()) {
        val next = queue.removeFirst()
        toposort.add(next)

        graph.get(next)!!.forEach {
          indegree[it] = indegree[it]!! - 1
          if (indegree[it] == 0) queue.add(it)
        }
      }

      val paths = { from: String, to: String ->
        val pathways = graph.keys.map { it to 0.toBigInteger() }.toMap().toMutableMap()
        pathways.set(from, 1.toBigInteger())

        toposort.forEach { parent ->
          graph[parent]?.forEach { child ->
            pathways[child] = pathways[child]!! + pathways[parent]!!
          }
        }

        pathways[to]!!
      }

      return (paths("svr", "dac") * paths("dac", "fft") * paths("fft", "out")) +
          (paths("svr", "fft") * paths("fft", "dac") * paths("dac", "out"))
    }

    fun solve() {
      val testInput1 = readInput("Day11_Test01")
      val testInput2 = readInput("Day11_Test02")
      check(part1(testInput1) == 5)
      check(part2(testInput2) == 2.toBigInteger())

      val input = readInput("Day11")
      part1(input).println()
      part2(input).println()
    }
  }
}
