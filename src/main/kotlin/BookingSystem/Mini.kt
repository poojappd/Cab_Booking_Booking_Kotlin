package BookingSystem
class Mini(vehicleId:UInt, vehicleName :String,  numberPlate:String)
    : Vehicle(vehicleId, vehicleName, VehicleType.CAR_MINI, numberPlate, 3 ){

    companion object{
        const val baseFare = 52.8;
        const val ratePerKm = 14.96;
        const val waitingFeePerMin = 0.88

    }

}