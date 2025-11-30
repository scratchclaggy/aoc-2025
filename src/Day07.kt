class Day07 {
    companion object {
        private fun part1(input: List<String>): Int = input.size

        private fun part2(input: List<String>): Int = input.size

        fun solve() {
            check(part1(listOf("test_input")) == 1)
            check(part2(listOf("test_input")) == 1)

            val input = readInput("Day07")
            part1(input).println()
            part2(input).println()
        }
    }
}

