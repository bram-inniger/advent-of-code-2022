package be.inniger.advent

object Day02 {

    fun solveFirst(guide: List<String>) = guide.sumOf { simpleStrategy(it) }
    fun solveSecond(guide: List<String>) = guide.sumOf { complexStrategy(it) }

    private fun simpleStrategy(instruction: String) =
        when (instruction) {
            "A X" -> 3 + 1
            "A Y" -> 6 + 2
            "A Z" -> 0 + 3
            "B X" -> 0 + 1
            "B Y" -> 3 + 2
            "B Z" -> 6 + 3
            "C X" -> 6 + 1
            "C Y" -> 0 + 2
            "C Z" -> 3 + 3
            else -> error("Invalid instruction: $instruction")
        }

    private fun complexStrategy(instruction: String) =
        when (instruction) {
            "A X" -> 0 + 3
            "A Y" -> 3 + 1
            "A Z" -> 6 + 2
            "B X" -> 0 + 1
            "B Y" -> 3 + 2
            "B Z" -> 6 + 3
            "C X" -> 0 + 2
            "C Y" -> 3 + 3
            "C Z" -> 6 + 1
            else -> error("Invalid instruction: $instruction")
        }
}
