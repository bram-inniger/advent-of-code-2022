package be.inniger.advent

object Day21 {

    private const val ROOT = "root"

    fun solveFirst(monkeyDescriptions: List<String>): Long {
        val monkeys = monkeyDescriptions.map { Monkey.of(it) }
        val numbers = yell(monkeys)

        return numbers.filterKeys { it == ROOT }
            .values
            .single()
    }
//    fun solveSecond(monkeyDescriptions: List<String>) = 42

    private fun yell(monkeys: List<Monkey>): Map<String, Long> {
        val numberMonkeys = monkeys.filterIsInstance<NumberMonkey>()
        val mathMonkeys = monkeys.filterIsInstance<MathMonkey>()

        return yell(mathMonkeys, numberMonkeys.associate { it.name to it.number })
    }

    private tailrec fun yell(monkeys: List<MathMonkey>, numbers: Map<String, Long>): Map<String, Long> =
        if (monkeys.isEmpty()) numbers
        else {
            val solvedMonkeys = monkeys.filter { it.waitOne in numbers && it.waitTwo in numbers }
            val solvedNumbers = solvedMonkeys.associate {
                it.name to it.operation(numbers[it.waitOne]!!, numbers[it.waitTwo]!!)
            }

            yell(monkeys.filter { it !in solvedMonkeys }, numbers + solvedNumbers)
        }

    private sealed interface Monkey {
        companion object {
            private val numberRegex = """^([a-z]{4}): (\d+)$""".toRegex()
            private val mathRegex = """^([a-z]{4}): ([a-z]{4}) ([-+/*]) ([a-z]{4})$""".toRegex()

            fun of(monkey: String) = when {
                numberRegex.matches(monkey) -> {
                    val (name, number) = numberRegex.find(monkey)!!.destructured

                    NumberMonkey(name, number.toLong())
                }

                mathRegex.matches(monkey) -> {
                    val (name, waitOne, operator, waitTwo) = mathRegex.find(monkey)!!.destructured

                    val operation: (Long, Long) -> Long = when (operator) {
                        "+" -> { one, two -> one + two }
                        "-" -> { one, two -> one - two }
                        "*" -> { one, two -> one * two }
                        "/" -> { one, two -> one / two }
                        else -> error("Invalid operator: $operator")
                    }
                    MathMonkey(name, waitOne, waitTwo, operation)
                }

                else -> error("Invalid monkey: $monkey")
            }
        }
    }

    private data class NumberMonkey(val name: String, val number: Long) : Monkey

    private data class MathMonkey(
        val name: String,
        val waitOne: String,
        val waitTwo: String,
        val operation: (Long, Long) -> Long
    ) : Monkey
}
