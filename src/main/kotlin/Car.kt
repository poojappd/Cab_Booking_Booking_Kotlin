sealed class Car(vehicleId:Int, val carModel: CarModel, vehicleName :String, numberPlate:String,
          maxOccupants:Int) :Vehicle(
    vehicleId,
    vehicleName,
    VehicleType.CAR,
    numberPlate,
    maxOccupants
) {
}
class Mini(vehicleId:Int, vehicleName :String, numberPlate:String)
    :Car(vehicleId, CarModel.MINI, vehicleName, numberPlate, 3 ){

    companion object{
        const val baseFare = 52.8;
        const val ratePerKm = 14.96;
        const val waitingFeePerMin = 0.88

    }

}

class PrimeSedan(vehicleId:Int, vehicleName :String, numberPlate:String,
                 override var wifiEnabled: Boolean = false, override var moviesStreaming: Boolean = false,
                 override var musicStreaming: Boolean = false,
) :Car(vehicleId, CarModel.SEDAN, vehicleName, numberPlate, 4), PrimeCars{
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

class PrimeSUV(vehicleId:Int, vehicleName :String, numberPlate:String,
                 override var wifiEnabled: Boolean = false, override var moviesStreaming: Boolean = false,
                 override var musicStreaming: Boolean = false,
)
    :Car(vehicleId, CarModel.SUV, vehicleName, numberPlate, 6 ), PrimeCars {
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
enum class CarModel{
    SUV,
    SEDAN,
    MINI
}

