package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {

    private val input = readInputFile("14")
    private val sampleTraces = listOf(
        "498,4 -> 498,6 -> 496,6",
        "503,4 -> 502,4 -> 502,9 -> 494,9"
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(24, Day14.solveFirst(sampleTraces))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(1_133, Day14.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(93, Day14.solveSecond(sampleTraces))
    }

    @Ignore("Ignored cause it takes about 8 seconds to run, goes green though!")
    @Test
    fun validateSecondSolution() {
        assertEquals(27_566, Day14.solveSecond(input))
    }
}
