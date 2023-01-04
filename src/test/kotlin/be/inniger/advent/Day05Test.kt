package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {

    private val input = readInputFile("05").joinToString("\n")
    private val sampleInput =
        """
                [D]
            [N] [C]
            [Z] [M] [P]
             1   2   3
            
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent()

    @Test
    fun validateFirstSampleInputs() {
        assertEquals("CMZ", Day05.solveFirst(sampleInput))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals("JCMHLVGMG", Day05.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals("MCD", Day05.solveSecond(sampleInput))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals("LVMRWSSPZ", Day05.solveSecond(input))
    }
}
