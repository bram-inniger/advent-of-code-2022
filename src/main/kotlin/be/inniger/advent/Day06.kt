package be.inniger.advent

object Day06 {

    fun solveFirst(signal: String) = findMarker(signal, 4)
    fun solveSecond(signal: String) = findMarker(signal, 14)

    private fun findMarker(signal: String, markerSize: Int) =
        (0..(signal.length - markerSize))
            .asSequence()
            .map { signal.substring(it, it + markerSize) }
            .map { it.toSet() }
            .indexOfFirst { it.size == markerSize }
            .let { it + markerSize }
}
