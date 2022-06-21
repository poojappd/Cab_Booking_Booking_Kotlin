package Entities

sealed class Vehicle(val vehicleId:UInt, val vehicleName :String, val vehicleType: VehicleType, val numberPlate:String,
                     val maxOccupants:Int) {

    var vehicleDriverId: String = ""


}
