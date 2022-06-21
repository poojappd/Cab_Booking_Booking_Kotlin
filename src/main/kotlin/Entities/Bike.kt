package Entities

class Bike (vehicleId:UInt, vehicleName :String, numberPlate:String)
    : Vehicle(vehicleId, vehicleName, VehicleType.BIKE, numberPlate, 1){
    companion object{
        const val bikeSpeedPerKmInMinutes = 2.6f
        const val bikeRatePerKm = 3.4
        const val bikeBaseFare = 17.0
    }



}