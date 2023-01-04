package be.inniger.advent

object Day04 {

    fun solveFirst(pairs: List<String>) = pairs.map { SectionPair.from(it) }.count { it.haveFullOverlap() }
    fun solveSecond(pairs: List<String>) = pairs.map { SectionPair.from(it) }.count { it.havePartialOverlap() }

    private fun SectionPair.haveFullOverlap() =
        (first.first <= second.first && first.last >= second.last) ||
                (second.first <= first.first && second.last >= first.last)

    private fun SectionPair.havePartialOverlap() =
        (first.first <= second.first && first.last >= second.first) ||
                (second.first <= first.first && second.last >= first.first)

    private data class SectionPair(val first: IntRange, val second: IntRange) {
        companion object {
            private val regex = """^(\d+)-(\d+),(\d+)-(\d+)$""".toRegex()
            fun from(line: String): SectionPair {
                val (firstFrom, firstTo, secondFrom, secondTo) = regex.find(line)!!.destructured

                return SectionPair(
                    (firstFrom.toInt())..(firstTo.toInt()),
                    (secondFrom.toInt())..(secondTo.toInt())
                )
            }
        }
    }
}
