class Bike (vehicleId:Int, vehicleName :String, numberPlate:String, vehicleDriverId: String)
    :Vehicle(vehicleId, vehicleName, VehicleType.BIKE, numberPlate, 1,  vehicleDriverId){
    companion object{
        val bikeSpeedPerKmInMinutes = 2.6f
        val bikeRatePerKm = 3.4
        val bikeBaseFare = 17.0
    }


}