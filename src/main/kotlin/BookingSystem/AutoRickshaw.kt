package BookingSystem

class AutoRickshaw (vehicleId:UInt, vehicleName :String, numberPlate:String)
    : Vehicle(vehicleId, vehicleName, VehicleType.AUTO_RICKSHAW, numberPlate, 3){

    companion object{
        //waiting charge
        const val AutoRatePerKm = 14.0
        const val AutoBaseFare = 30.0
    }
}