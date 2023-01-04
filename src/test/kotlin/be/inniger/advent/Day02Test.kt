package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {

    private val input = readInputFile("02")
    private val sampleGuide = listOf(
        "A Y",
        "B X",
        "C Z",
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(15, Day02.solveFirst(sampleGuide))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(10_624, Day02.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(12, Day02.solveSecond(sampleGuide))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(14_060, Day02.solveSecond(input))
    }
}
