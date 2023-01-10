package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day10 {

    private const val WIDTH = 40
    private const val HEIGHT = 6

    fun solveFirst(program: List<String>): Int {
        val interestingCycles = (20..(WIDTH * HEIGHT) step WIDTH).toSet()
        val plan = derivePlan(program.map { Instruction.of(it) })
        val xOverTime = runPlan(plan)

        return xOverTime.filter { it.key in interestingCycles }
            .map { it.key * it.value }
            .sum()
    }

    fun solveSecond(program: List<String>): String {
        val plan = derivePlan(program.map { Instruction.of(it) })
        val xOverTime = runPlan(plan)

        val pixels = (1..(WIDTH * HEIGHT)).map { cycle ->
            val pixelX = (cycle - 1) % WIDTH
            val regX = xOverTime[cycle] ?: error("No regX value known at cycle $cycle")

            if (pixelX in (regX - 1..regX + 1)) '#'
            else '.'
        }

        return pixels.chunked(WIDTH).joinToString("\n") { it.joinToString("") }
    }

    private tailrec fun derivePlan(
        program: List<Instruction>,
        cycle: Int = 1,
        plan: Map<Int, Instruction> = mapOf()
    ): Map<Int, Instruction> =
        if (program.isEmpty()) plan
        else {
            val instruction = program.head()
            val newCycle = cycle + instruction.cycles()
            val newPlan = plan + (newCycle - 1 to instruction)

            derivePlan(program.tail(), newCycle, newPlan)
        }

    private tailrec fun runPlan(
        program: Map<Int, Instruction>,
        cycle: Int = 1,
        x: Int = 1,
        xOverTime: Map<Int, Int> = mapOf()
    ): Map<Int, Int> =
        if (cycle > WIDTH * HEIGHT) xOverTime
        else {
            val instruction = program[cycle] ?: NoOp
            val newXOverTime = xOverTime + (cycle to x)
            val newX = x + when (instruction) {
                is AddX -> instruction.x
                is NoOp -> 0
            }

            runPlan(program, cycle + 1, newX, newXOverTime)
        }

    private sealed interface Instruction {
        fun cycles(): Int

        companion object {
            val addRegex = """^addx (-?\d+)$""".toRegex()

            fun of(instruction: String): Instruction =
                when {
                    instruction == "noop" -> NoOp
                    addRegex.matches(instruction) -> AddX(addRegex.find(instruction)!!.groupValues[1].toInt())
                    else -> error("Invalid instruction: $instruction")
                }
        }
    }

    private data class AddX(val x: Int) : Instruction {
        override fun cycles() = 2
    }

    private object NoOp : Instruction {
        override fun cycles() = 1
        override fun toString(): String = "NoOp()"
    }
}
