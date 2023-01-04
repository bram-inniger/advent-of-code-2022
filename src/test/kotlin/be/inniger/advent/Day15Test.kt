package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {

    private val input = readInputFile("15")
    private val sampleSensors = listOf(
        "Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
        "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
        "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
        "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
        "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
        "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
        "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
        "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
        "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
        "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
        "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
        "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
        "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
        "Sensor at x=20, y=1: closest beacon is at x=15, y=3",
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(26, Day15.solveFirst(sampleSensors, 10))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(5_394_423, Day15.solveFirst(input, 2_000_000))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(56_000_011, Day15.solveSecond(sampleSensors, 20))
    }

    @Ignore("Ignored cause it takes about 40 seconds to run, goes green though!")
    @Test
    fun validateSecondSolution() {
        assertEquals(11_840_879_211_051, Day15.solveSecond(input, 4_000_000))
    }
}
