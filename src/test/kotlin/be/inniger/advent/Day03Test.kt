package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {

    private val input = readInputFile("03")
    private val sampleContents = listOf(
        "vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw",
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(157, Day03.solveFirst(sampleContents))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(8_252, Day03.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(70, Day03.solveSecond(sampleContents))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(2_828, Day03.solveSecond(input))
    }
}
