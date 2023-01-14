package be.inniger.advent

import be.inniger.advent.util.readInputFile
import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {

    private val input = readInputFile("21")
    private val sampleFile = listOf(
        "root: pppw + sjmn",
        "dbpl: 5",
        "cczh: sllz + lgvd",
        "zczc: 2",
        "ptdq: humn - dvpt",
        "dvpt: 3",
        "lfqf: 4",
        "humn: 5",
        "ljgn: 2",
        "sjmn: drzm * dbpl",
        "sllz: 4",
        "pppw: cczh / lfqf",
        "lgvd: ljgn * ptdq",
        "drzm: hmdt - zczc",
        "hmdt: 32"
    )

    @Test
    fun validateFirstSampleInputs() {
        assertEquals(152, Day21.solveFirst(sampleFile))
    }

    @Test
    fun validateFirstSolution() {
        assertEquals(168_502_451_381_566, Day21.solveFirst(input))
    }

//    @Test
//    fun validateSecondSampleInputs() {
//        assertEquals(0, Day21.solveSecond(sampleFile))
//    }
//
//    @Test
//    fun validateSecondSolution() {
//        assertEquals(0, Day21.solveSecond(input))
//    }
}
