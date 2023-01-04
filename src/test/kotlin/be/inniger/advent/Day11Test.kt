package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {

    private val input = readInputFile("11").joinToString("\n")
    private val sampleMonkeys =
        """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
            
            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
            
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
            
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.trimIndent()

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(10_605, Day11.solveFirst(sampleMonkeys))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(110_220, Day11.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(2_713_310_158, Day11.solveSecond(sampleMonkeys))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(19_457_438_264, Day11.solveSecond(input))
    }
}
