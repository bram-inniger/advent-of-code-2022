package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day12 {

    fun solveFirst(heightmap: List<List<Char>>): Int {
        val grid = Grid.of(heightmap)

        return distanceToEnd(grid)
    }

    fun solveSecond(heightmap: List<List<Char>>): Int {
        val grid = Grid.of(heightmap)
        val allGrids = grid.grid.filterValues { it == 0 } // Find all 'a' on the heightmap
            .keys
            .map { grid.copy(start = it) }

        return allGrids.minOf { distanceToEnd(it) }
    }

    private tailrec fun distanceToEnd(
        grid: Grid,
        toVisit: List<Position> = listOf(grid.start),
        distances: Map<Position, Int> = mapOf(grid.start to 0)
    ): Int =
        when {
            distances.contains(grid.end) -> distances[grid.end]!!
            toVisit.isEmpty() -> Int.MAX_VALUE
            else -> {
                val current = toVisit.head()
                val distance = distances[current]!!

                val unvisitedNeighbours = neighbours(grid, current)
                    .filter { !distances.keys.contains(it) }

                val newToVisit = toVisit.tail() + unvisitedNeighbours
                val newDistances = distances + unvisitedNeighbours.associateWith { distance + 1 }

                distanceToEnd(grid, newToVisit, newDistances)
            }
        }

    private fun neighbours(grid: Grid, position: Position) =
        listOf(
            Position(position.row - 1, position.col),
            Position(position.row, position.col + 1),
            Position(position.row + 1, position.col),
            Position(position.row, position.col - 1),
        ).filter { (grid.grid[it] ?: Int.MAX_VALUE) <= (grid.grid[position]!! + 1) }

    private data class Position(val row: Int, val col: Int)

    private data class Grid(
        val grid: Map<Position, Int>,
        val maxRow: Int,
        val maxCol: Int,
        val start: Position,
        val end: Position
    ) {
        companion object {
            fun of(heightmap: List<List<Char>>): Grid {
                val maxRow = heightmap.lastIndex
                val maxCol = heightmap.first().lastIndex

                val start = find(heightmap, 'S')
                val end = find(heightmap, 'E')

                val grid = heightmap.indices.flatMap { row ->
                    heightmap.first().indices.map { col ->
                        Position(row, col)
                    }
                }.associateWith {
                    when (val char = heightmap[it.row][it.col]) {
                        'S' -> 'a' - 'a'
                        'E' -> 'z' - 'a'
                        in 'a'..'z' -> char - 'a'
                        else -> error("Invalid height: $char")
                    }
                }

                return Grid(grid, maxRow, maxCol, start, end)
            }

            private fun find(grid: List<List<Char>>, point: Char) =
                grid.indices.flatMap { row ->
                    grid.first().indices.filter { col ->
                        grid[row][col] == point
                    }.map { col -> Position(row, col) }
                }.single()
        }
    }
}
