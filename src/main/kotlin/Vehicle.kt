sealed class Vehicle (val vehicleId:Int, val vehicleName :String, val vehicleType:VehicleType, val numberPlate:String,
               val maxOccupants:Int, var vehicleDriverId: String) {}

enum class VehicleType{
    CAR,
    BIKE,
    AUTO_RICKSHAW
}