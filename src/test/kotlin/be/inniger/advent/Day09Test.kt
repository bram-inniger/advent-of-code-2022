package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {

    private val input = readInputFile("09")

    @Test
    fun validateFirstSampleInputs() {
        val sampleMotions = listOf(
            "R 4",
            "U 4",
            "L 3",
            "D 1",
            "R 4",
            "D 1",
            "L 5",
            "R 2",
        )

        assertEquals(13, Day09.solveFirst(sampleMotions))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(5_960, Day09.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        val shortSampleMotions = listOf(
            "R 4",
            "U 4",
            "L 3",
            "D 1",
            "R 4",
            "D 1",
            "L 5",
            "R 2",
        )
        val longSampleMotions = listOf(
            "R 5",
            "U 8",
            "L 8",
            "D 3",
            "R 17",
            "D 10",
            "L 25",
            "U 20"
        )

        assertEquals(1, Day09.solveSecond(shortSampleMotions))
        assertEquals(36, Day09.solveSecond(longSampleMotions))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(2_327, Day09.solveSecond(input))
    }
}
