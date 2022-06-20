

class PrimeSUV(vehicleId:UInt, vehicleName :String, numberPlate:String,
               override var wifiEnabled: Boolean = false, override var moviesStreaming: Boolean = false,
               override var musicStreaming: Boolean = false,
)
    :Vehicle(vehicleId, vehicleName, VehicleType.CAR_SUV, numberPlate, 6 ), PrimeCars {
    override fun activatePrime() {
        wifiEnabled = true
        moviesStreaming = true
        musicStreaming = true

        println("Prime has been activated! Enjoy unlimited wifi with movies and music")

    }

    companion object {
        const val baseFare = 65.7;
        const val ratePerKm = 20.0;
    }

}
