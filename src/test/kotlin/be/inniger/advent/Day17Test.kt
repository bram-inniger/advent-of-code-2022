package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {

    private val input = readInputFile("17").single()
    private val sampleJetPattern = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(3_068, Day17.solveFirst(sampleJetPattern))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(3_069, Day17.solveFirst(input))
    }

//    @Test
//    fun validateSecondSampleInputs() {
//        assertEquals(0, Day17.solveSecond(sampleJetPattern))
//    }
//
//    @Test
//    fun validateSecondSolution() {
//        assertEquals(0, Day17.solveSecond(input))
//    }
}
