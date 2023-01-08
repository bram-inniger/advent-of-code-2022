package be.inniger.advent

object Day18 {

    private const val CUBE_SIDES = 6

    fun solveFirst(scan: List<String>) =
        scan.map { Cube.of(it) }
            .toSet()
            .let { cubes ->
                cubes.sumOf { cube ->
                    CUBE_SIDES - neighbours(cube).count { neighbour -> cubes.contains(neighbour) }
                }
            }

//    fun solveSecond(scan: List<String>) = 42

    private fun neighbours(cube: Cube) = listOf(
        cube.copy(x = cube.x - 1),
        cube.copy(x = cube.x + 1),
        cube.copy(y = cube.y - 1),
        cube.copy(y = cube.y + 1),
        cube.copy(z = cube.z - 1),
        cube.copy(z = cube.z + 1),
    )

    private data class Cube(val x: Int, val y: Int, val z: Int) {
        companion object {
            val regex = """^(\d+),(\d+),(\d+)$""".toRegex()

            fun of(cube: String): Cube {
                val (x, y, z) = regex.find(cube)!!.destructured

                return Cube(x.toInt(), y.toInt(), z.toInt())
            }
        }
    }
}
