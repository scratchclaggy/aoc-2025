class Day12 {
  companion object {
    private fun part1(input: List<String>) =
        input
            .filter { it.contains("x") }
            .let {
              it.map {
                it.split(": ").let {
                  it.first().split("x").map { it.toInt() }.reduce { acc, n -> acc * n } to
                      it.last().split(" ").map { it.toInt() }.sum() * 7
                }
              }
            }
            .count { (bins, boxes) -> bins >= boxes }

    fun solve() {
      // Aproximation doesn't work with test input ¯\_(ツ)_/¯
      //
      val input = readInput("Day12")
      part1(input).println()
    }
  }
}
