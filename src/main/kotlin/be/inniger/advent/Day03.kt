package be.inniger.advent

object Day03 {

    fun solveFirst(contents: List<String>) = solve(contents, ::splitHalf)
    fun solveSecond(contents: List<String>) = solve(contents, ::groupByThree)

    private fun solve(contents: List<String>, grouper: (List<String>) -> List<List<String>>) =
        grouper(contents)
            .map { it.map { chars -> chars.toSet() } }
            .map { it.reduce { acc, chars -> acc.intersect(chars) } }
            .map { it.single() }
            .sumOf { priority(it) }

    private fun splitHalf(contents: List<String>) = contents.map { it.chunked(it.length  / 2) }
    private fun groupByThree(contents: List<String>) = contents.chunked(3)

    private fun priority(item: Char) =
        when (item) {
            in 'a'..'z' -> item - 'a' + 1
            in 'A'..'Z' -> item - 'A' + 27
            else -> error("Invalid item: $item")
        }
}
