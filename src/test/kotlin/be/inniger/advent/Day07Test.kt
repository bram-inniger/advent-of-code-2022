package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test {

    private val input = readInputFile("07")
    private val sampleCommands = listOf(
        "$ cd /",
        "$ ls",
        "dir a",
        "14848514 b.txt",
        "8504156 c.dat",
        "dir d",
        "$ cd a",
        "$ ls",
        "dir e",
        "29116 f",
        "2557 g",
        "62596 h.lst",
        "$ cd e",
        "$ ls",
        "584 i",
        "$ cd ..",
        "$ cd ..",
        "$ cd d",
        "$ ls",
        "4060174 j",
        "8033020 d.log",
        "5626152 d.ext",
        "7214296 k"
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(95_437, Day07.solveFirst(sampleCommands))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(1_642_503, Day07.solveFirst(input))
    }

    @Test
    fun validateSecondSampleInputs() {
        assertEquals(24_933_642, Day07.solveSecond(sampleCommands))
    }

    @Test
    fun validateSecondSolution() {
        assertEquals(6_999_588, Day07.solveSecond(input))
    }
}
