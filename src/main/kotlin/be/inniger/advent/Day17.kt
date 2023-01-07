package be.inniger.advent

object Day17 {

    private const val WIDTH = 7
    private const val NR_ROCKS = 2_022
    private val rockTypes =
        listOf(RockType.HORIZONTAL, RockType.PLUS, RockType.CORNER, RockType.VERTICAL, RockType.SQUARE)

    fun solveFirst(jetPattern: String): Int {
        val pattern = jetPattern.map {
            when (it) {
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> error("Invalid direction: $it")
            }
        }

        val cave = mutableSetOf<Position>()

        var turnCounter = 0
        var blockCounter = 0

        var maxHeight = 0

        var rockType = rockTypes[blockCounter]
        var rockPosition = Position(3, 4)

        while (blockCounter < NR_ROCKS) {
//            print(cave, rockType, rockPosition)

            val direction = pattern[turnCounter % pattern.size]

            rockPosition = if (rockType.canMove(rockPosition, cave, direction)) {
                when (direction) {
                    Direction.LEFT -> Position(rockPosition.x - 1, rockPosition.y + 0)
                    Direction.RIGHT -> Position(rockPosition.x + 1, rockPosition.y + 0)
                    Direction.DOWN -> error("Pattern cannot contain direction down")
                }
            } else rockPosition

            if (rockType.canMove(rockPosition, cave, Direction.DOWN)) {
                rockPosition = Position(rockPosition.x + 0, rockPosition.y - 1)
            } else {
                blockCounter++
                cave.addAll(rockType.rest(rockPosition))
                maxHeight = cave.maxOf { it.y }
                rockType = rockTypes[blockCounter % rockTypes.size]
                rockPosition = Position(3, maxHeight + 4)
            }

            turnCounter++
        }

        return maxHeight
    }

//    fun solveSecond(jetPattern: String) = 42

    private enum class RockType(
        @Suppress("UNUSED_PARAMETER") visual: String,
        val canMove: (rock: Position, cave: Set<Position>, direction: Direction) -> Boolean,
        val rest: (rock: Position) -> Set<Position>
    ) {
        HORIZONTAL(
            """
                ####
            """,
            { rock, cave, direction ->
                when (direction) {
                    Direction.LEFT -> rock.x > 1 &&
                            !cave.contains(Position(rock.x - 1, rock.y + 0))

                    Direction.RIGHT -> rock.x + 3 < WIDTH &&
                            !cave.contains(Position(rock.x + 4, rock.y + 0))

                    Direction.DOWN -> rock.y > 1 &&
                            !cave.contains(Position(rock.x + 0, rock.y - 1)) &&
                            !cave.contains(Position(rock.x + 1, rock.y - 1)) &&
                            !cave.contains(Position(rock.x + 2, rock.y - 1)) &&
                            !cave.contains(Position(rock.x + 3, rock.y - 1))
                }
            },
            { rock ->
                setOf(
                    Position(rock.x + 0, rock.y + 0),
                    Position(rock.x + 1, rock.y + 0),
                    Position(rock.x + 2, rock.y + 0),
                    Position(rock.x + 3, rock.y + 0),
                )
            }
        ),
        PLUS(
            """
                .#.
                ###
                .#.
            """,
            { rock, cave, direction ->
                when (direction) {
                    Direction.LEFT -> rock.x > 1 &&
                            !cave.contains(Position(rock.x + 0, rock.y + 0)) &&
                            !cave.contains(Position(rock.x - 1, rock.y + 1)) &&
                            !cave.contains(Position(rock.x + 0, rock.y + 2))

                    Direction.RIGHT -> rock.x + 2 < WIDTH &&
                            !cave.contains(Position(rock.x + 2, rock.y + 0)) &&
                            !cave.contains(Position(rock.x + 3, rock.y + 1)) &&
                            !cave.contains(Position(rock.x + 2, rock.y + 2))

                    Direction.DOWN -> rock.y > 1 &&
                            !cave.contains(Position(rock.x + 0, rock.y + 0)) &&
                            !cave.contains(Position(rock.x + 1, rock.y - 1)) &&
                            !cave.contains(Position(rock.x + 2, rock.y + 0))
                }
            },
            { rock ->
                setOf(
                    Position(rock.x + 1, rock.y + 0),
                    Position(rock.x + 0, rock.y + 1),
                    Position(rock.x + 1, rock.y + 1),
                    Position(rock.x + 2, rock.y + 1),
                    Position(rock.x + 1, rock.y + 2),
                )
            }
        ),
        CORNER(
            """
                ..#
                ..#
                ###
            """,
            { rock, cave, direction ->
                when (direction) {
                    Direction.LEFT -> rock.x > 1 &&
                            !cave.contains(Position(rock.x - 1, rock.y + 0)) &&
                            !cave.contains(Position(rock.x + 1, rock.y + 1)) &&
                            !cave.contains(Position(rock.x + 1, rock.y + 2))

                    Direction.RIGHT -> rock.x + 2 < WIDTH &&
                            !cave.contains(Position(rock.x + 3, rock.y + 0)) &&
                            !cave.contains(Position(rock.x + 3, rock.y + 1)) &&
                            !cave.contains(Position(rock.x + 3, rock.y + 2))

                    Direction.DOWN -> rock.y > 1 &&
                            !cave.contains(Position(rock.x + 0, rock.y - 1)) &&
                            !cave.contains(Position(rock.x + 1, rock.y - 1)) &&
                            !cave.contains(Position(rock.x + 2, rock.y - 1))
                }
            },
            { rock ->
                setOf(
                    Position(rock.x + 0, rock.y + 0),
                    Position(rock.x + 1, rock.y + 0),
                    Position(rock.x + 2, rock.y + 0),
                    Position(rock.x + 2, rock.y + 1),
                    Position(rock.x + 2, rock.y + 2),
                )
            }
        ),
        VERTICAL(
            """
                #
                #
                #
                #
            """,
            { rock, cave, direction ->
                when (direction) {
                    Direction.LEFT -> rock.x > 1 &&
                            !cave.contains(Position(rock.x - 1, rock.y + 0)) &&
                            !cave.contains(Position(rock.x - 1, rock.y + 1)) &&
                            !cave.contains(Position(rock.x - 1, rock.y + 2)) &&
                            !cave.contains(Position(rock.x - 1, rock.y + 3))

                    Direction.RIGHT -> rock.x + 0 < WIDTH &&
                            !cave.contains(Position(rock.x + 1, rock.y + 0)) &&
                            !cave.contains(Position(rock.x + 1, rock.y + 1)) &&
                            !cave.contains(Position(rock.x + 1, rock.y + 2)) &&
                            !cave.contains(Position(rock.x + 1, rock.y + 3))

                    Direction.DOWN -> rock.y > 1 &&
                            !cave.contains(Position(rock.x + 0, rock.y - 1))
                }
            },
            { rock ->
                setOf(
                    Position(rock.x + 0, rock.y + 0),
                    Position(rock.x + 0, rock.y + 1),
                    Position(rock.x + 0, rock.y + 2),
                    Position(rock.x + 0, rock.y + 3),
                )
            }
        ),
        SQUARE(
            """
                ##
                ##
            """,
            { rock, cave, direction ->
                when (direction) {
                    Direction.LEFT -> rock.x > 1 &&
                            !cave.contains(Position(rock.x - 1, rock.y + 0)) &&
                            !cave.contains(Position(rock.x - 1, rock.y + 1))

                    Direction.RIGHT -> rock.x + 1 < WIDTH &&
                            !cave.contains(Position(rock.x + 2, rock.y + 0)) &&
                            !cave.contains(Position(rock.x + 2, rock.y + 1))

                    Direction.DOWN -> rock.y > 1 &&
                            !cave.contains(Position(rock.x + 0, rock.y - 1)) &&
                            !cave.contains(Position(rock.x + 1, rock.y - 1))
                }
            },
            { rock ->
                setOf(
                    Position(rock.x + 0, rock.y + 0),
                    Position(rock.x + 1, rock.y + 0),
                    Position(rock.x + 0, rock.y + 1),
                    Position(rock.x + 1, rock.y + 1),
                )
            }
        );
    }

    private data class Position(val x: Int, val y: Int)
    private enum class Direction { LEFT, RIGHT, DOWN }

    @Suppress("unused")
    private fun print(cave: Set<Position>, rockType: RockType, rockPosition: Position) {
        val rock = rockType.rest(rockPosition)
        val maxY = rock.maxOf { it.y }

        println((maxY downTo 0).joinToString("\n") { y ->
            (0..(WIDTH + 1)).map { x ->
                when {
                    Position(x, y) in rock -> '@'
                    Position(x, y) in cave -> '#'
                    y == 0 && (x == 0 || x == WIDTH + 1) -> '+'
                    x == 0 || x == WIDTH + 1 -> '|'
                    y == 0 -> '-'
                    else -> '.'
                }
            }.joinToString("")
        })
        println("\n")
    }
}
