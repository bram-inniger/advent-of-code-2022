package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day18 {

    private const val CUBE_SIDES = 6

    fun solveFirst(scan: List<String>) = countExposedSides(scan, includeAirPockets = true)
    fun solveSecond(scan: List<String>) = countExposedSides(scan, includeAirPockets = false)

    private fun countExposedSides(scan: List<String>, includeAirPockets: Boolean): Int {
        val lava = scan.map { Cube.of(it) }.toSet()
        val allLava =
            if (includeAirPockets) lava
            else {
                val space = Space.of(lava)
                val reachable = floodFill(space, lava)
                space.cubes - reachable
            }

        return allLava.sumOf { cube ->
            CUBE_SIDES - neighbours(cube).count { neighbour -> neighbour in allLava }
        }
    }

    private fun neighbours(cube: Cube) = listOf(
        cube.copy(x = cube.x - 1),
        cube.copy(x = cube.x + 1),
        cube.copy(y = cube.y - 1),
        cube.copy(y = cube.y + 1),
        cube.copy(z = cube.z - 1),
        cube.copy(z = cube.z + 1),
    )

    private tailrec fun floodFill(
        space: Space,
        occupied: Set<Cube>,
        toVisit: List<Cube> = listOf(Cube(space.minX, space.minY, space.minZ)),
        visited: Set<Cube> = setOf(),
    ): Set<Cube> =
        when {
            toVisit.isEmpty() -> visited
            toVisit.head() in visited -> floodFill(space, occupied, toVisit.tail(), visited)
            else -> {
                val current = toVisit.head()
                val reachableNeighbours = neighbours(current)
                    .filter { it !in occupied }
                    .filter { it in space.cubes }

                floodFill(space, occupied, toVisit.tail() + reachableNeighbours, visited + current)
            }
        }

    private data class Cube(val x: Int, val y: Int, val z: Int) {
        companion object {
            private val regex = """^(\d+),(\d+),(\d+)$""".toRegex()

            fun of(cube: String): Cube {
                val (x, y, z) = regex.find(cube)!!.destructured

                return Cube(x.toInt(), y.toInt(), z.toInt())
            }
        }
    }

    private data class Space(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int, val minZ: Int, val maxZ: Int) {
        companion object {
            fun of(cubes: Set<Cube>) = Space(
                cubes.minOf { it.x } - 1,
                cubes.maxOf { it.x } + 1,
                cubes.minOf { it.y } - 1,
                cubes.maxOf { it.y } + 1,
                cubes.minOf { it.z } - 1,
                cubes.maxOf { it.z } + 1,
            )
        }

        val cubes =
            (minX..maxX).flatMap { x ->
                (minY..maxY).flatMap { y ->
                    (minZ..maxZ).map { z -> Cube(x, y, z) }
                }
            }
    }
}
