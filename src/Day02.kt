class Day02 {
  companion object {
    private fun part1(input: String) =
        input.split(",").sumOf {
          """(\d+)-(\d+)"""
              .toRegex()
              .matchEntire(it)
              ?.destructured
              ?.let { (a, b) -> a.toLong()..b.toLong() }
              ?.filter { """(\d+)\1""".toRegex().matches(it.toString()) }
              ?.sum() ?: throw Error()
        }

    private fun part2(input: String) =
        input.split(",").sumOf {
          """(\d+)-(\d+)"""
              .toRegex()
              .matchEntire(it)
              ?.destructured
              ?.let { (a, b) -> a.toLong()..b.toLong() }
              ?.filter { """(\d+)\1+""".toRegex().matches(it.toString()) }
              ?.sum() ?: throw Error()
        }

    fun solve() {
      val testInput = readInput("Day02_Test").first()
      check(part1(testInput) == 1227775554.toLong())
      check(part2(testInput) == 4174379265.toLong())

      val input = readInput("Day02").first()
      part1(input).println()
      part2(input).println()
    }
  }
}
