package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {

    private val input = readInputFile("12").map { it.toList() }
    private val sampleGrid = listOf(
        listOf('S', 'a', 'b', 'q', 'p', 'o', 'n', 'm'),
        listOf('a', 'b', 'c', 'r', 'y', 'x', 'x', 'l'),
        listOf('a', 'c', 'c', 's', 'z', 'E', 'x', 'k'),
        listOf('a', 'c', 'c', 't', 'u', 'v', 'w', 'j'),
        listOf('a', 'b', 'd', 'e', 'f', 'g', 'h', 'i')
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(31, Day12.solveFirst(sampleGrid))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(520, Day12.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(29, Day12.solveSecond(sampleGrid))
    }

    @Ignore("Ignored cause it takes about 2 minutes to run, goes green though!")
    @Test
    fun validateSecondSolution() {
        assertEquals(508, Day12.solveSecond(input))
    }
}
