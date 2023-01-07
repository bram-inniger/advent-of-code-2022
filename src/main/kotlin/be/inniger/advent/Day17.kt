package be.inniger.advent

object Day17 {

    private const val WIDTH = 7
    private const val NR_ROCKS = 2_022
    private val rockTypes =
        listOf(RockType.HORIZONTAL, RockType.PLUS, RockType.CORNER, RockType.VERTICAL, RockType.SQUARE)

    fun solveFirst(jetPattern: String) =
        jetPattern.map {
            when (it) {
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> error("Invalid direction: $it")
            }
        }
            .let { recSolve(it) }
            .height()

    private tailrec fun recSolve(
        jetPattern: List<Direction>,
        counter: Counter = Counter(),
        cave: Cave = Cave(),
        rock: Rock = Rock.new(counter, cave),
    ): Cave =
        if (counter.blocks == NR_ROCKS) cave
        else {
            // Move sideways
            val direction = jetPattern[counter.turn % jetPattern.size]
            val horizontalRock = if (rock.canMove(direction, cave)) rock.move(direction) else rock

            // Move downwards
            val canMoveDown = horizontalRock.canMove(Direction.DOWN, cave)
            val newCounter = if (canMoveDown) counter else counter.incBlocks()
            val newCave = if (canMoveDown) cave else (cave + horizontalRock.rest())
            val verticalRock = if (canMoveDown) horizontalRock.move(Direction.DOWN) else Rock.new(newCounter, newCave)

            recSolve(jetPattern, newCounter.incTurn(), newCave, verticalRock)
        }

    //    fun solveSecond(jetPattern: String) = 42

    private enum class RockType(@Suppress("UNUSED_PARAMETER") visual: String) {
        HORIZONTAL(
            """
                ####
            """
        ),
        PLUS(
            """
                .#.
                ###
                .#.
            """
        ),
        CORNER(
            """
                ..#
                ..#
                ###
            """
        ),
        VERTICAL(
            """
                #
                #
                #
                #
            """
        ),
        SQUARE(
            """
                ##
                ##
            """
        );
    }

    private enum class Direction { LEFT, RIGHT, DOWN }

    private data class Position(val x: Int, val y: Int)

    private data class Cave(val stones: Set<Position> = setOf()) {
        operator fun plus(moreStones: Set<Position>) = Cave(stones + moreStones)
        fun contains(position: Position) = stones.contains(position)
        fun height() = stones.maxOfOrNull { it.y } ?: 0
    }

    private data class Rock(val type: RockType, val position: Position) {
        fun canMove(direction: Direction, cave: Cave) = when (type) {
            RockType.HORIZONTAL -> when (direction) {
                Direction.LEFT -> position.x > 1
                        && !cave.contains(Position(position.x - 1, position.y + 0))

                Direction.RIGHT -> position.x + 3 < WIDTH
                        && !cave.contains(Position(position.x + 4, position.y + 0))

                Direction.DOWN -> position.y > 1
                        && !cave.contains(Position(position.x + 0, position.y - 1))
                        && !cave.contains(Position(position.x + 1, position.y - 1))
                        && !cave.contains(Position(position.x + 2, position.y - 1))
                        && !cave.contains(Position(position.x + 3, position.y - 1))
            }

            RockType.PLUS -> when (direction) {
                Direction.LEFT -> position.x > 1
                        && !cave.contains(Position(position.x + 0, position.y + 0))
                        && !cave.contains(Position(position.x - 1, position.y + 1))
                        && !cave.contains(Position(position.x + 0, position.y + 2))

                Direction.RIGHT -> position.x + 2 < WIDTH
                        && !cave.contains(Position(position.x + 2, position.y + 0))
                        && !cave.contains(Position(position.x + 3, position.y + 1))
                        && !cave.contains(Position(position.x + 2, position.y + 2))

                Direction.DOWN -> position.y > 1
                        && !cave.contains(Position(position.x + 0, position.y + 0))
                        && !cave.contains(Position(position.x + 1, position.y - 1))
                        && !cave.contains(Position(position.x + 2, position.y + 0))
            }

            RockType.CORNER -> when (direction) {
                Direction.LEFT -> position.x > 1
                        && !cave.contains(Position(position.x - 1, position.y + 0))
                        && !cave.contains(Position(position.x + 1, position.y + 1))
                        && !cave.contains(Position(position.x + 1, position.y + 2))

                Direction.RIGHT -> position.x + 2 < WIDTH
                        && !cave.contains(Position(position.x + 3, position.y + 0))
                        && !cave.contains(Position(position.x + 3, position.y + 1))
                        && !cave.contains(Position(position.x + 3, position.y + 2))

                Direction.DOWN -> position.y > 1
                        && !cave.contains(Position(position.x + 0, position.y - 1))
                        && !cave.contains(Position(position.x + 1, position.y - 1))
                        && !cave.contains(Position(position.x + 2, position.y - 1))
            }

            RockType.VERTICAL -> when (direction) {
                Direction.LEFT -> position.x > 1
                        && !cave.contains(Position(position.x - 1, position.y + 0))
                        && !cave.contains(Position(position.x - 1, position.y + 1))
                        && !cave.contains(Position(position.x - 1, position.y + 2))
                        && !cave.contains(Position(position.x - 1, position.y + 3))

                Direction.RIGHT -> position.x + 0 < WIDTH
                        && !cave.contains(Position(position.x + 1, position.y + 0))
                        && !cave.contains(Position(position.x + 1, position.y + 1))
                        && !cave.contains(Position(position.x + 1, position.y + 2))
                        && !cave.contains(Position(position.x + 1, position.y + 3))

                Direction.DOWN -> position.y > 1
                        && !cave.contains(Position(position.x + 0, position.y - 1))
            }

            RockType.SQUARE -> when (direction) {
                Direction.LEFT -> position.x > 1
                        && !cave.contains(Position(position.x - 1, position.y + 0))
                        && !cave.contains(Position(position.x - 1, position.y + 1))

                Direction.RIGHT -> position.x + 1 < WIDTH
                        && !cave.contains(Position(position.x + 2, position.y + 0))
                        && !cave.contains(Position(position.x + 2, position.y + 1))

                Direction.DOWN -> position.y > 1
                        && !cave.contains(Position(position.x + 0, position.y - 1))
                        && !cave.contains(Position(position.x + 1, position.y - 1))
            }
        }

        fun move(direction: Direction) = Rock(
            type,
            when (direction) {
                Direction.LEFT -> Position(position.x - 1, position.y + 0)
                Direction.RIGHT -> Position(position.x + 1, position.y + 0)
                Direction.DOWN -> Position(position.x + 0, position.y - 1)
            }
        )

        fun rest() = when (type) {
            RockType.HORIZONTAL -> setOf(
                Position(position.x + 0, position.y + 0),
                Position(position.x + 1, position.y + 0),
                Position(position.x + 2, position.y + 0),
                Position(position.x + 3, position.y + 0),
            )

            RockType.PLUS -> setOf(
                Position(position.x + 1, position.y + 0),
                Position(position.x + 0, position.y + 1),
                Position(position.x + 1, position.y + 1),
                Position(position.x + 2, position.y + 1),
                Position(position.x + 1, position.y + 2),
            )

            RockType.CORNER -> setOf(
                Position(position.x + 0, position.y + 0),
                Position(position.x + 1, position.y + 0),
                Position(position.x + 2, position.y + 0),
                Position(position.x + 2, position.y + 1),
                Position(position.x + 2, position.y + 2),
            )

            RockType.VERTICAL -> setOf(
                Position(position.x + 0, position.y + 0),
                Position(position.x + 0, position.y + 1),
                Position(position.x + 0, position.y + 2),
                Position(position.x + 0, position.y + 3),
            )

            RockType.SQUARE -> setOf(
                Position(position.x + 0, position.y + 0),
                Position(position.x + 1, position.y + 0),
                Position(position.x + 0, position.y + 1),
                Position(position.x + 1, position.y + 1),
            )
        }

        companion object {
            private const val X_OFFSET = 3
            private const val Y_OFFSET = 4

            fun new(counter: Counter, cave: Cave) =
                Rock(rockTypes[counter.blocks % rockTypes.size], Position(X_OFFSET, cave.height() + Y_OFFSET))
        }
    }

    private data class Counter(val turn: Int = 0, val blocks: Int = 0) {
        fun incTurn() = Counter(turn + 1, blocks)
        fun incBlocks() = Counter(turn, blocks + 1)
    }

    @Suppress("unused")
    private fun print(cave: Set<Position>, rock: Rock) {
        val maxY = rock.rest().maxOf { it.y }

        println((maxY downTo 0).joinToString("\n") { y ->
            (0..(WIDTH + 1)).map { x ->
                when {
                    Position(x, y) in rock.rest() -> '@'
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
