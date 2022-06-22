package BookingSystem

class PrimeSedan(vehicleId:UInt, vehicleName :String, numberPlate:String,
                 override var wifiEnabled: Boolean = false, override var moviesStreaming: Boolean = false,
                 override var musicStreaming: Boolean = false,
) : Vehicle(vehicleId, vehicleName, VehicleType.CAR_SEDAN, numberPlate, 4 ) , PrimeCars {


    companion object{
        const val baseFare = 57.4;
        const val ratePerKm = 16.81;
        const val waitingFeePerMin = 3

    }
}