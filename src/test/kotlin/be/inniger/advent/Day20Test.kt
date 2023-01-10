package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day20Test {

    private val input = readInputFile("20").map { it.toInt() }
    private val sampleFile = listOf(1, 2, -3, 3, -2, 0, 4)

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(3, Day20.solveFirst(sampleFile))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(8_721, Day20.solveFirst(input))
    }

//    @Test
//    fun validateSecondSampleInputs() {
//        assertEquals(0, Day20.solveSecond(sampleFile))
//    }
//
//    @Test
//    fun validateSecondSolution() {
//        assertEquals(0, Day20.solveSecond(input))
//    }
}
