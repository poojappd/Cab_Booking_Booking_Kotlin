package BookingSystem

import java.io.BufferedReader
import java.io.FileReader

object Map {
    private val allLocationsUnderBaseStations = mutableMapOf<StationPoint, MutableList<Location>>()

    init {
        StationPoint.values().forEach {
            allLocationsUnderBaseStations[it] = mutableListOf()
        }

        val fileName = "src/main/Data_Resources/MapData.CSV"
        val br = BufferedReader(FileReader(fileName))
        val linesInCSV = br.readLines()
        linesInCSV.forEach {
            val contentList = it.split(',')
            val stationPoint = StationPoint.valueOf(contentList[0])
            val area = contentList[1]
            val x = contentList[2].toDouble()
            val y = contentList[3].toDouble()
            val location = Location(x, y, stationPoint, area)
            allLocationsUnderBaseStations[stationPoint]?.add(location)
        }

    }

    internal fun addToMap(location: Location){
        allLocationsUnderBaseStations[location.stationPoint]?.add(location).let{
           allLocationsUnderBaseStations.put(location.stationPoint, mutableListOf( location))
        }
    }

    fun getBaseLocations() = StationPoint.values()

    fun getLocationsFromBaseLocation(baseLocation: StationPoint) = allLocationsUnderBaseStations[baseLocation]!!.toList()

    internal fun calculateDistance(fromLocation: Location, toLocation: Location): Double {
        val x1Coordinate = fromLocation.x_coordinate
        val y1Coordinate = fromLocation.y_coordinate
        val x2Coordinate = toLocation.x_coordinate
        val y2Coordinate = toLocation.y_coordinate

        return Math.sqrt(
            (Math.pow((x2Coordinate - x1Coordinate), 2.0))
                    + Math.pow((y2Coordinate - y1Coordinate), 2.0)
        ) * 100
    }

    fun getLocationByIndex(baseStationPoint: StationPoint, index:Int) =
        allLocationsUnderBaseStations.get(baseStationPoint)!!.get(index - 1)

    private fun getDistanceBetweenStationPoints(fromLocation: Location, ToStationPoints : Set<StationPoint>) :MutableMap<Location, Double>{
        val cabCentreWithDistance = mutableMapOf<Location, Double>()

        ToStationPoints.forEach {
            if (it != fromLocation.stationPoint) {
                val destination = allLocationsUnderBaseStations.get(it)!!.get(0)
                val currentDistance = calculateDistance(fromLocation, destination)
                cabCentreWithDistance.put(destination, currentDistance)

            }
        }
    return cabCentreWithDistance
    }

    fun getNearestStationPoints(fromLocation: Location, ToStationPoints : Set<StationPoint>) : List<StationPoint> {

        val stationPointWithDistance = getDistanceBetweenStationPoints( fromLocation, ToStationPoints).toList()
        val sortedStationPoint:MutableList<StationPoint> = mutableListOf()
        stationPointWithDistance.toList().sortedByDescending { it.second }.forEach { sortedStationPoint.add(it.first.stationPoint) }
        return sortedStationPoint.toList()

    }

    fun getBaseStationCount() = allLocationsUnderBaseStations.size

    fun getAreaCount(stationPoint: StationPoint) = allLocationsUnderBaseStations[stationPoint]!!.size

    }


