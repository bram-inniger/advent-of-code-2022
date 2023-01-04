package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    private val input = readInputFile("01").joinToString("\n")
    private val sampleCalories = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent()

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(24_000, Day01.solveFirst(sampleCalories))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(75_501, Day01.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(45_000, Day01.solveSecond(sampleCalories))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(215_594, Day01.solveSecond(input))
    }
}
