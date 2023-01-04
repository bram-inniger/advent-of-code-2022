package be.inniger.advent

object Day08 {

    fun solveFirst(grid: List<List<Int>>) =
        grid.indices.sumOf { row ->
            grid[row].indices.count { col ->
                isVisible(grid, row, col)
            }
        }

    fun solveSecond(grid: List<List<Int>>) =
        grid.indices.flatMap { row ->
            grid[row].indices.map { col ->
                countVisible(grid, row, col)
            }
        }.maxOf { it }

    private fun isVisible(grid: List<List<Int>>, row: Int, col: Int): Boolean {
        val maxRow = grid.lastIndex
        val maxCol = grid[0].lastIndex

        val tree = grid[row][col]

        val north = (0 until row).map { grid[it][col] }
        val east = (col + 1..maxCol).map { grid[row][it] }
        val south = (row + 1..maxRow).map { grid[it][col] }
        val west = (0 until col).map { grid[row][it] }

        return listOf(north, east, south, west)
            .any { it.isEmpty() || it.maxOf { el -> el } < tree }
    }

    private fun countVisible(grid: List<List<Int>>, row: Int, col: Int): Int {
        val maxRow = grid.lastIndex
        val maxCol = grid[0].lastIndex

        val tree = grid[row][col]

        val north = (0 until row).map { grid[it][col] }.reversed()
        val east = (col + 1..maxCol).map { grid[row][it] }
        val south = (row + 1..maxRow).map { grid[it][col] }
        val west = (0 until col).map { grid[row][it] }.reversed()

        return listOf(north, east, south, west)
            .map { countVisible(it, tree) }
            .reduce(Int::times)
    }

    private fun countVisible(trees: List<Int>, current: Int): Int {
        var count = 0

        for (tree in trees) {
            count++
            if (tree >= current) break
        }

        return count
    }
}
