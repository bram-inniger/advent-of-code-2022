package be.inniger.advent

object Day20 {

    private val groveNumbers = listOf(1_000, 2_000, 3_000)

    fun solveFirst(file: List<Int>): Int {
        // Set up and link Nodes in a bidirectional linked list
        val nodes = file.map { Node(it) }
        nodes.forEachIndexed { index, node ->
            node.prev = nodes[(index - 1).mod(nodes.size)]
            node.next = nodes[(index + 1).mod(nodes.size)]
        }

        // Take every node in the order of original input
        for (node in nodes) {
            // Remove node from the circular list
            node.prev!!.next = node.next
            node.next!!.prev = node.prev

            // Move the node to the new position
            var newPosition = node
            when {
                node.value > 0 -> repeat(node.value) { newPosition = newPosition.next!! }
                node.value <= 0 -> repeat(-node.value + 1) { newPosition = newPosition.prev!! }
            }

            // Re-insert it
            node.prev = newPosition
            node.next = newPosition.next
            node.prev!!.next = node
            node.next!!.prev = node
        }

        // Find the 1000th, 2000th and 3000th element after 0 and sum them
        return groveNumbers.sumOf { findNthElement(nodes, it) }
    }

//    fun solveSecond(file: List<Int>) = 42

    private fun findNthElement(nodes: List<Node>, n: Int): Int {
        var node = nodes.single { it.value == 0 }
        repeat(n % nodes.size) { node = node.next!! }

        return node.value
    }

    @Suppress("unused")
    private fun List<Node>.print() {
        val zero = single { it.value == 0 }
        val buffer = StringBuilder()

        var current = zero
        do {
            buffer.append(current.value).append(", ")
            current = current.next!!
        } while (current.next != zero.next)

        println(buffer)
    }

    private data class Node(val value: Int, var prev: Node? = null, var next: Node? = null)
}
