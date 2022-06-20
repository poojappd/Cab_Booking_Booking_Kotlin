class PrimeSedan(vehicleId:UInt, vehicleName :String, numberPlate:String,
                 override var wifiEnabled: Boolean = false, override var moviesStreaming: Boolean = false,
                 override var musicStreaming: Boolean = false,
) :Vehicle(vehicleId, vehicleName, VehicleType.CAR_SEDAN, numberPlate, 4 ), PrimeCars{
    override fun activatePrime() {
        wifiEnabled = true
        moviesStreaming = true
        musicStreaming = true

        println("Prime has been activated! Enjoy unlimited wifi with movies and music")

    }

    companion object{
        const val baseFare = 57.4;
        const val ratePerKm = 16.81;
        const val waitingFeePerMin = 3

    }
}