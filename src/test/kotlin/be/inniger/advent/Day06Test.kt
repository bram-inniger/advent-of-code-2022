package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {

    private val input = readInputFile("06")[0]

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(
            listOf(
                5,
                6,
                10,
                11
            ),
            listOf(
                "bvwbjplbgvbhsrlpgdmjqwftvncz",
                "nppdvjthqldpwncqszvftbrmjlhg",
                "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
                "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
            ).map { Day06.solveFirst(it) })
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(1640, Day06.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(
            listOf(
                19,
                23,
                23,
                29,
                26
            ),
            listOf(
                "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
                "bvwbjplbgvbhsrlpgdmjqwftvncz",
                "nppdvjthqldpwncqszvftbrmjlhg",
                "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
                "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
            ).map { Day06.solveSecond(it) })
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(3613, Day06.solveSecond(input))
    }
}
