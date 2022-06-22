package BookingSystem

enum class StationPoint{
    THAILAVARAM,
    GUDUVANCHERY,
    VANDALUR,
    TAMBARAM,
    TAMBARAM_SANATORIUM,
    CHROMEPET,
    PALLAVARAM,
    PAMMAL,
    PERUNGALATHUR,
    URAPPAKKAM,
    TIRUSULAM,
    MEENAMBAKKAM,
    ALANDUR,
    GUINDY,


}
data class Location(val x_coordinate: Double, val y_coordinate: Double, val stationPoint: StationPoint, val area:String)