package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test {

    private val input = readInputFile("08").map { line -> line.map { it.digitToInt() } }
    private val sampleGrid = listOf(
        listOf(3, 0, 3, 7, 3),
        listOf(2, 5, 5, 1, 2),
        listOf(6, 5, 3, 3, 2),
        listOf(3, 3, 5, 4, 9),
        listOf(3, 5, 3, 9, 0)
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(21, Day08.solveFirst(sampleGrid))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(1_823, Day08.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(8, Day08.solveSecond(sampleGrid))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(211_680, Day08.solveSecond(input))
    }
}
