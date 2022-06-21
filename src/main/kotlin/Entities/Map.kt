package Entities

import java.io.BufferedReader
import java.io.FileReader
import java.util.*

object Map {
    private val allLocationsUnderBaseStations : MutableMap<StationPoint, MutableList<Location>> = EnumMap(StationPoint::class.java)
    init {
        allLocationsUnderBaseStations[StationPoint.THAILAVARAM] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.GUDUVANCHERY] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.VANDALUR] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.TAMBARAM] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.SANITORIUM] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.CHROMEPET] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.PALLAVARAM] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.PAMMAL] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.PERUNGALATHUR] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.URAPPAKKAM] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.TIRUSULAM] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.MEENAMBAKKAM] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.ALANDUR] = mutableListOf()
        allLocationsUnderBaseStations[StationPoint.GUINDY] = mutableListOf()

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

    fun getLocationsFromBaseLocation(baseLocation: StationPoint) = allLocationsUnderBaseStations[baseLocation]?.toList()

    internal fun calculateDistance(fromLocation: Location, toLocation: Location) :Double {
        val x1Coordinate = fromLocation.x_coordinate
        val y1Coordinate = fromLocation.y_coordinate
        val x2Coordinate = toLocation.x_coordinate
        val y2Coordinate = toLocation.y_coordinate

        val distance = Math.sqrt((Math.pow((x2Coordinate - x1Coordinate), 2.0))
                + Math.pow((y2Coordinate - y1Coordinate),2.0)) *100
        return distance
    }

    fun getLocationByIndex(baseStationPoint: StationPoint, index:Int) =
        allLocationsUnderBaseStations.get(baseStationPoint)?.get(index - 1)

    private fun getDistanceBetweenStationPoints(fromLocation: Location, ToStationPoints : Set<StationPoint>) :MutableMap<Location, Double>{
        val cabCentreWithDistance = mutableMapOf<Location, Double>()
        val source = fromLocation
        ToStationPoints.forEach {

            if (it != fromLocation.stationPoint) {
                val destination = allLocationsUnderBaseStations.get(it)?.get(0)
                val currentDistance = destination?.let { dest -> calculateDistance(source, dest) }
                if (destination != null) {
                    if (currentDistance != null) {
                        cabCentreWithDistance.put(destination, currentDistance)
                    }
                }

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

    }


