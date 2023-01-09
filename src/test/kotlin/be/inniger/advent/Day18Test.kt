package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day18Test {

    private val input = readInputFile("18")
    private val sampleScan = listOf(
        "2,2,2",
        "1,2,2",
        "3,2,2",
        "2,1,2",
        "2,3,2",
        "2,2,1",
        "2,2,3",
        "2,2,4",
        "2,2,6",
        "1,2,5",
        "3,2,5",
        "2,1,5",
        "2,3,5",
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(64, Day18.solveFirst(sampleScan))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(3_432, Day18.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(58, Day18.solveSecond(sampleScan))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(2_042, Day18.solveSecond(input))
    }
}
