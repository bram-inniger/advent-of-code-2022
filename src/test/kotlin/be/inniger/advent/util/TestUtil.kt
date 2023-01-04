package be.inniger.advent.util

import java.io.File

fun readInputFile(day: String) = File("src/main/resources/inputs", "$day.txt").readLines()
