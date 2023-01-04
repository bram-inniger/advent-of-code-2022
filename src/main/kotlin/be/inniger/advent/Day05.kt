package be.inniger.advent

object Day05 {

    private const val CHAR_LENGTH = 4
    private const val CHAR_POS = 1

    fun solveFirst(input: String) = solve(input, this::moveSingle)
    fun solveSecond(input: String) = solve(input, this::moveMultiple)

    private fun solve(input: String, mover: (Instruction, MutableList<MutableList<Char>>) -> Unit): String {
        val (positions, instructions) = input.split("\n\n").map { it.split("\n") }

        val nrStacks = readNrStacks(positions)
        val stacks = positions.map { it.padEnd(nrStacks * CHAR_LENGTH, ' ') }
            .map { line -> line.chunked(CHAR_LENGTH).map { it[CHAR_POS] } }
            .dropLast(1)
            .asStacks(nrStacks)

        instructions.map { Instruction.from(it) }
            .forEach { mover(it, stacks) }

        return stacks.map { it.last() }.joinToString("")
    }

    private fun moveSingle(instruction: Instruction, stacks: MutableList<MutableList<Char>>) {
        repeat(instruction.amount) {
            val toMove = stacks[instruction.source].removeLast()
            stacks[instruction.destination].add(toMove)
        }
    }

    private fun moveMultiple(instruction: Instruction, stacks: MutableList<MutableList<Char>>) {
        stacks[instruction.destination] += stacks[instruction.source].subList(
            stacks[instruction.source].size - instruction.amount,
            stacks[instruction.source].size
        )
        stacks[instruction.source] = stacks[instruction.source].subList(
            0,
            stacks[instruction.source].size - instruction.amount
        )
    }

    private fun readNrStacks(positions: List<String>): Int {
        val lastStackRegex = """^.*(\d) *$""".toRegex()
        return lastStackRegex.find(positions.last())!!.destructured.component1().toInt()
    }

    private fun List<List<Char>>.asStacks(nrStacks: Int): MutableList<MutableList<Char>> {
        val stacks = (0 until nrStacks).map { mutableListOf<Char>() }.toMutableList()

        (this.lastIndex downTo 0).map { this[it] }
            .forEach { row ->
                row.forEachIndexed { index, crate ->
                    if (crate != ' ') stacks[index].add(crate)
                }
            }

        return stacks
    }

    private data class Instruction(val amount: Int, val source: Int, val destination: Int) {
        companion object {
            val regex = """^move (\d+) from (\d) to (\d)$""".toRegex()

            fun from(line: String): Instruction {
                val (amount, source, destination) = regex.find(line)!!.destructured

                return Instruction(
                    amount.toInt(),
                    source.toInt() - 1,        // turn to 0-indexed
                    destination.toInt() - 1 // turn to 0-indexed
                )
            }
        }
    }
}
