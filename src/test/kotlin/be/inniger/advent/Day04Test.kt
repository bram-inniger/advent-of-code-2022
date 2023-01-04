package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {

    private val input = readInputFile("04")
    private val samplePairs = listOf(
        "2-4,6-8",
        "2-3,4-5",
        "5-7,7-9",
        "2-8,3-7",
        "6-6,4-6",
        "2-6,4-8",
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(2, Day04.solveFirst(samplePairs))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(540, Day04.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(4, Day04.solveSecond(samplePairs))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(872, Day04.solveSecond(input))
    }
}
