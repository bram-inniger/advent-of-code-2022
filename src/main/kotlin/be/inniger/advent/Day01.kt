package be.inniger.advent

object Day01 {

    fun solveFirst(calories: String) = countTopKCalories(calories, 1)
    fun solveSecond(calories: String) = countTopKCalories(calories, 3)

    private fun countTopKCalories(calories: String, nrElves: Int) =
        calories.split("\n\n")
            .map { inventory ->
                inventory.split("\n")
                    .sumOf { calorie -> calorie.toInt() }
            }
            .sortedDescending()
            .subList(0, nrElves)
            .sum()
}
