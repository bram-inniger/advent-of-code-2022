package be.inniger.advent

import kotlin.math.max
import kotlin.math.min

object Day14 {

    private val source = Coordinate(500, 0)

    fun solveFirst(traces: List<String>) = pourSand(scanToRocks(traces), hasInfiniteBottom = true).size
    fun solveSecond(traces: List<String>) = pourSand(scanToRocks(traces), hasInfiniteBottom = false).size

    private fun scanToRocks(scan: List<String>) =
        scan.flatMap { trace ->
            trace.split(" -> ")
                .map { Coordinate.of(it) }
                .let { traceToRocks(it) }
        }.toSet()

    private fun traceToRocks(trace: List<Coordinate>) =
        (0 until trace.lastIndex).flatMap {
            lineToRocks(trace[it], trace[it + 1])
        }

    private fun lineToRocks(start: Coordinate, end: Coordinate) =
        when {
            start.x == end.x -> (if (start.y < end.y) start.y..end.y else end.y..start.y)
                .map { y -> Coordinate(start.x, y) }

            start.y == end.y -> (if (start.x < end.x) start.x..end.x else end.x..start.x)
                .map { x -> Coordinate(x, start.y) }

            else -> error("Cannot draw line between $start and $end")
        }

    private tailrec fun pourSand(
        rocks: Set<Coordinate>,
        maxDepth: Int = rocks.maxOf { it.y },
        unit: Coordinate = source,
        sand: Set<Coordinate> = setOf(),
        hasInfiniteBottom: Boolean
    ): Set<Coordinate> = when {
        hasInfiniteBottom && unit.y > maxDepth -> sand
        sand.contains(source) -> sand
        else -> {
            val canMove = { coordinate: Coordinate ->
                !rocks.contains(coordinate) && !sand.contains(coordinate) && unit.y <= maxDepth
            }
            val center = Coordinate(unit.x, unit.y + 1)
            val left = Coordinate(unit.x - 1, unit.y + 1)
            val right = Coordinate(unit.x + 1, unit.y + 1)

            val newUnit = when {
                canMove(center) -> center
                canMove(left) -> left
                canMove(right) -> right
                else -> unit
            }

            if (newUnit == unit) pourSand(rocks, maxDepth, source, sand + unit, hasInfiniteBottom)
            else pourSand(rocks, maxDepth, newUnit, sand, hasInfiniteBottom)
        }
    }

    @Suppress("unused")
    private fun printCave(rocks: Set<Coordinate>, sand: Set<Coordinate>, unit: Coordinate) {
        val minX = min(rocks.minOf { it.x }, sand.minOfOrNull { it.x } ?: source.x )
        val minY = source.y
        val maxX = max(rocks.maxOf { it.x }, sand.maxOfOrNull { it.x } ?: source.x)
        val maxY = max(rocks.maxOf { it.y }, sand.maxOfOrNull { it.y } ?: source.y)

        println((minY..maxY).joinToString("\n") { y ->
            (minX..maxX).map { x -> Coordinate(x, y) }
                .map {
                    when (it) {
                        in sand -> 'o'
                        unit -> '*'
                        source -> '+'
                        in rocks -> '#'
                        else -> '.'
                    }
                }
                .joinToString("")
        })
        println("\n\n")
    }

    private data class Coordinate(val x: Int, val y: Int) {
        companion object {
            private val regex = """^(\d+),(\d+)$""".toRegex()

            fun of(coordinate: String): Coordinate {
                val (x, y) = regex.find(coordinate)!!.destructured
                return Coordinate(x.toInt(), y.toInt())
            }
        }
    }
}
