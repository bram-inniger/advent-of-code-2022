package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {

    private val input = readInputFile("13").joinToString("\n")
    private val sampleList =
        """
            [1,1,3,1,1]
            [1,1,5,1,1]
            
            [[1],[2,3,4]]
            [[1],4]
            
            [9]
            [[8,7,6]]
            
            [[4,4],4,4]
            [[4,4],4,4,4]
            
            [7,7,7,7]
            [7,7,7]
            
            []
            [3]
            
            [[[]]]
            [[]]
            
            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
        """.trimIndent()

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(13, Day13.solveFirst(sampleList))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(6_568, Day13.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(140, Day13.solveSecond(sampleList))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(19_493, Day13.solveSecond(input))
    }
}
