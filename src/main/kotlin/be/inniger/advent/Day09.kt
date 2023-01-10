package be.inniger.advent

object Day09 {

    fun solveFirst(motions: List<String>) = countTailPositions(motions, 2)
    fun solveSecond(motions: List<String>) = countTailPositions(motions, 10)

    private fun countTailPositions(motionsInput: List<String>, nrKnots: Int): Int {
        val motions = motionsInput.map { Motion.of(it) }
        val rope = List(nrKnots) { Position(0, 0) }.toMutableList()
        val visited = mutableSetOf(rope.last())

        for (motion in motions) {
            repeat(motion.steps) {
                rope[0] = rope.first().moveHead(motion.direction)

                (1..rope.lastIndex).forEach {
                    rope[it] = rope[it].moveToward(rope[it - 1])
                }

                visited.add(rope.last())
            }
        }

        return visited.size
    }

    private data class Position(val row: Int, val col: Int) {
        fun moveHead(direction: Direction) = when (direction) {
            Direction.R -> Position(row + 1, col)
            Direction.U -> Position(row, col + 1)
            Direction.L -> Position(row - 1, col)
            Direction.D -> Position(row, col - 1)
        }

        fun moveToward(that: Position) = when {
            this.row - that.row in (-1..1) && this.col - that.col in (-1..1) -> this
            that.row == this.row && that.col > this.col -> Position(this.row, this.col + 1)
            that.row < this.row && that.col > this.col -> Position(this.row - 1, this.col + 1)
            that.row < this.row && that.col == this.col -> Position(this.row - 1, this.col)
            that.row < this.row && that.col < this.col -> Position(this.row - 1, this.col - 1)
            that.row == this.row && that.col < this.col -> Position(this.row, this.col - 1)
            that.row > this.row && that.col < this.col -> Position(this.row + 1, this.col - 1)
            that.row > this.row && that.col == this.col -> Position(this.row + 1, this.col)
            that.row > this.row && that.col > this.col -> Position(this.row + 1, this.col + 1)
            else -> error("Invalid head and tail: $that vs $this")
        }
    }

    private enum class Direction { R, U, L, D }

    private data class Motion(val direction: Direction, val steps: Int) {
        companion object {
            private val regex = """^([RULD]) (\d+)$""".toRegex()

            fun of(motion: String): Motion {
                val (direction, steps) = regex.find(motion)!!.destructured

                return Motion(
                    when (direction) {
                        "R" -> Direction.R
                        "U" -> Direction.U
                        "L" -> Direction.L
                        "D" -> Direction.D
                        else -> error("Invalid direction: $direction")
                    },
                    steps.toInt()
                )
            }
        }
    }
}
