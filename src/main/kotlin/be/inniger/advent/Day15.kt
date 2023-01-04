package be.inniger.advent

import kotlin.math.abs
import kotlin.math.max

object Day15 {

    private const val TUNING_COEFFICIENT = 4_000_000L

    fun solveFirst(sensorsInput: List<String>, row: Int) =
        sensorsInput.map { Sensor.of(it) }
            .let { sensors ->
                sensors
                    .flatMap { intersect(it, row) }
                    .toSet()
                    .let { excludedBeacons ->
                        excludedBeacons - sensors.filter { sensor -> sensor.beaconY == row }
                            .map { sensor -> sensor.beaconX }
                            .toSet()
                    }
                    .size
            }

    fun solveSecond(sensors: List<String>, bound: Int) =
        (0..bound).associateWith { row ->
            sensors.map { Sensor.of(it) }.map { sensor -> intersect(sensor, row) }
                .filter { range -> range.first <= range.last }
                .sortedBy { range -> range.first }
                .let { ranges -> mergeIntervals(ranges, bound) }
        }
            .filter { it.value.size > 1 }
            .map { it }
            .single()
            .let { it.key + TUNING_COEFFICIENT * (it.value.first().last + 1) }

    private fun intersect(sensor: Sensor, row: Int): IntRange {
        val distanceToRow = abs(sensor.sensorY - row)
        return ((distanceToRow - sensor.distance + sensor.sensorX)..(sensor.distance - distanceToRow + sensor.sensorX))
    }

    private fun mergeIntervals(intervals: List<IntRange>, bound: Int): List<IntRange> {
        val merged = mutableListOf<IntRange>()
        var index = 1
        var interval = intervals.first()

        while (index <= intervals.lastIndex) {
            val next = intervals[index]

            interval = if (next.first in (interval.first..(interval.last + 1))) {
                interval.first..max(interval.last, next.last)
            } else {
                merged.add(interval)
                next
            }

            index++
        }

        merged.add(interval)

        return merged.filter { it.first < bound && it.last > 0 }
    }

    private data class Sensor(val sensorX: Int, val sensorY: Int, val beaconX: Int, val beaconY: Int) {
        val distance = abs(sensorX - beaconX) + abs(sensorY - beaconY)

        companion object {
            val regex = """^Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)$""".toRegex()

            fun of(sensor: String): Sensor {
                val (sensorX, sensorY, beaconX, beaconY) = regex.find(sensor)!!.destructured

                return Sensor(sensorX.toInt(), sensorY.toInt(), beaconX.toInt(), beaconY.toInt())
            }
        }
    }
}
