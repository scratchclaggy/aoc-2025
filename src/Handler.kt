fun main(args: Array<String>) {
	when (args.getOrNull(0)) {
		"01" -> Day01.solve()
		"02" -> Day02.solve()
		"03" -> Day03.solve()
		"04" -> Day04.solve()
		"05" -> Day05.solve()
		"06" -> Day06.solve()
		"07" -> Day07.solve()
		"08" -> Day08.solve()
		"09" -> Day09.solve()
		"10" -> Day10.solve()
		"11" -> Day11.solve()
		"12" -> Day12.solve()
		else -> throw Error("[MISSING ARG: DAY]")
	}
}
