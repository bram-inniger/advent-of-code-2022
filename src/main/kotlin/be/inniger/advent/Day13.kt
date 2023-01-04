package be.inniger.advent

import be.inniger.advent.util.head
import be.inniger.advent.util.tail

object Day13 {

    fun solveFirst(list: String) =
        list.split("\n\n")
            .map { pair ->
                pair.split("\n")
                    .map { Element.parse(it) }
            }
            .mapIndexed { index, pair -> if (compare(pair[0], pair[1]) == Result.SMALLER) (index + 1) else 0 }
            .sum()

    fun solveSecond(list: String): Int {
        val dividers = listOf("[[2]]", "[[6]]").map { Element.parse(it) }.toSet()
        val comparator = { left: Element, right: Element ->
            when (compare(left, right)) {
                Result.SMALLER -> -1
                Result.EQUAL -> 0
                Result.BIGGER -> 1
            }
        }

        return list.replace("\n\n", "\n")
            .split("\n")
            .map { Element.parse(it) }
            .let { it + dividers }
            .asSequence()
            .sortedWith(comparator)
            .mapIndexed { index, element -> element to (index + 1) }
            .filter { it.first in dividers }
            .map { it.second }
            .reduce(Int::times)
    }

    private fun compare(left: Element, right: Element): Result = when {
        left is EList && left.elements.isEmpty() -> Result.SMALLER
        right is EList && right.elements.isEmpty() -> Result.BIGGER
        left is EInt && right is EInt -> when {
            left.int < right.int -> Result.SMALLER
            left.int > right.int -> Result.BIGGER
            else -> Result.EQUAL
        }
        left is EList && right is EList -> {
            val firstCompare = compare(left.elements.head(), right.elements.head())
            if (firstCompare == Result.EQUAL) compare(EList(left.elements.tail()), EList(right.elements.tail()))
            else firstCompare
        }
        left is EList -> compare(left, EList(listOf(right)))
        right is EList -> compare(EList(listOf(left)), right)
        else -> error("Cannot compare $left and $right")
    }

    private sealed interface Element {
        companion object {
            fun parse(element: String): Element =
                if (element.startsWith('[')) EList(split(element).map { parse(it) })
                else EInt(element.toInt())

            private fun split(content: String): List<String> {
                val list = mutableListOf<String>()
                var index = 1

                while (true) {
                    when (val char = content[index]) {
                        '[', in '0'..'9' -> {
                            val end =
                                if (char == '[') findClosingParen(content.substring(index))
                                else findNumberEnd(content.substring(index))
                            list.add(content.substring(index..index + end))
                            index += end + 1
                        }

                        ']' -> return list
                        ',' -> index++
                        else -> error("Unexpected character: $char")
                    }
                }
            }

            private fun findNumberEnd(content: String): Int {
                var index = 0

                while (content[index + 1] in '0'..'9') index++

                return index
            }

            private fun findClosingParen(content: String): Int {
                var index = 0
                var openCount = 1

                while (openCount > 0) {
                    index++
                    when (content[index]) {
                        '[' -> openCount++
                        ']' -> openCount--
                    }
                }

                return index
            }
        }
    }

    private data class EList(val elements: List<Element>) : Element
    private data class EInt(val int: Int) : Element
    private enum class Result { SMALLER, EQUAL, BIGGER }
}
