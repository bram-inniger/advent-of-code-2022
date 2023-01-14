package be.inniger.advent

object Day20 {

    private val groveNumbers = listOf(1_000, 2_000, 3_000)

    fun solveFirst(file: List<Long>) = mix(file, nrRounds = 1, decryptionKey = 1)
    fun solveSecond(file: List<Long>) = mix(file, nrRounds = 10, decryptionKey = 811_589_153)

    private fun mix(file: List<Long>, nrRounds: Int, decryptionKey: Long): Long {
        // Set up and link Nodes in a bidirectional linked list
        val nodes = file.map { Node(it * decryptionKey) }
        nodes.forEachIndexed { index, node ->
            node.prev = nodes[(index - 1).mod(nodes.size)]
            node.next = nodes[(index + 1).mod(nodes.size)]
        }

        repeat(nrRounds) {
            // Take every node in the order of original input
            for (node in nodes) {
                // Remove node from the circular list
                node.prev!!.next = node.next
                node.next!!.prev = node.prev

                // Move the node to the new position
                var newPosition = node
                (node.value % (nodes.size - 1)).toInt().let { nrMoves ->
                    if (nrMoves > 0) repeat(nrMoves) { newPosition = newPosition.next!! }
                    else repeat(-nrMoves + 1) { newPosition = newPosition.prev!! }
                }

                // Re-insert it
                node.prev = newPosition
                node.next = newPosition.next
                node.prev!!.next = node
                node.next!!.prev = node
            }
        }

        // Find the 1000th, 2000th and 3000th element after 0 and sum them
        return groveNumbers.sumOf { findNthElement(nodes, it) }
    }

    private fun findNthElement(nodes: List<Node>, n: Int): Long {
        val zero = nodes.single { it.value == 0L }

        var node = zero
        repeat(n % nodes.size) { node = node.next!! }

        return node.value
    }

    @Suppress("unused")
    private fun List<Node>.print() {
        val zero = single { it.value == 0L }
        val buffer = StringBuilder()

        var current = zero
        do {
            buffer.append(current.value).append(", ")
            current = current.next!!
        } while (current.next != zero.next)

        println(buffer)
    }

    private data class Node(val value: Long, var prev: Node? = null, var next: Node? = null)
}
