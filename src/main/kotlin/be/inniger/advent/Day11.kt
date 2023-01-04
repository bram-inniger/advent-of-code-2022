package be.inniger.advent

object Day11 {

    fun solveFirst(monkeys: String) = monkeyBusiness(monkeys, 20, worryReduce = 3)
    fun solveSecond(monkeys: String) = monkeyBusiness(monkeys, 10_000)

    private fun monkeyBusiness(monkeysInput: String, nrRounds: Int, worryReduce: Int = 1): Long {
        val monkeys = monkeysInput.split("\n\n").map { Monkey.of(it) }
        val chineseRemainder = monkeys.map { it.test }.reduce(Long::times)

        repeat(nrRounds) {
            for (monkey in monkeys) {
                for (item in monkey.items) {
                    val worry = monkey.operation(item) % chineseRemainder / worryReduce
                    val passTo = if (worry % monkey.test == 0L) monkey.trueId else monkey.falseId

                    monkeys[passTo].items.add(worry)
                }

                monkey.inspected += monkey.items.size
                monkey.items.clear()
            }
        }

        return monkeys.map { it.inspected }.sortedDescending().take(2).reduce(Long::times)
    }

    private data class Monkey(
        val id: Int,
        val operation: (Long) -> Long,
        val test: Long,
        val trueId: Int,
        val falseId: Int,
        val items: MutableList<Long>,
        var inspected: Long = 0
    ) {
        companion object {
            private val idRegex = """^ *Monkey (\d):$""".toRegex()
            private val itemsRegex = """^ *Starting items: ((:?\d+(:?, )?+)+)$""".toRegex()
            private val operationRegex = """^ *Operation: new = old ([+*] (?:(old)|(\d+)))$""".toRegex()
            private val testRegex = """^ *Test: divisible by (\d+)$""".toRegex()
            private val trueIdRegex = """^ *If true: throw to monkey (\d)$""".toRegex()
            private val falseIdRegex = """^ *If false: throw to monkey (\d)$""".toRegex()

            fun of(monkey: String): Monkey {
                val lines = monkey.split("\n")
                fun findGroup(regex: Regex, line: Int) = regex.find(lines[line])!!.groupValues[1]

                val id = findGroup(idRegex, 0).toInt()
                val operation = findGroup(operationRegex, 2)
                    .let { part ->
                        when {
                            part == "* old" -> { old: Long -> old * old }
                            part.startsWith("* ") -> { old: Long -> old * part.substring(2).toInt() }
                            part.startsWith("+ ") -> { old: Long -> old + part.substring(2).toInt() }
                            else -> error("Invalid operation part: $part")
                        }
                    }
                val test = findGroup(testRegex, 3).toLong()
                val trueId = findGroup(trueIdRegex, 4).toInt()
                val falseId = findGroup(falseIdRegex, 5).toInt()
                val items = findGroup(itemsRegex, 1).split(", ").map { it.toLong() }.toMutableList()

                return Monkey(
                    id,
                    operation,
                    test,
                    trueId,
                    falseId,
                    items
                )
            }
        }
    }
}
