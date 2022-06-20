import VehicleType.*

object FareCalculator {
    private const val miniRatePerKm = Mini.ratePerKm
    private const val sedanRatePerKm = PrimeSedan.ratePerKm
    private const val suvRatePerKm = PrimeSUV.ratePerKm
    private const val autoRatePerKm: Double = AutoRickshaw.AutoRatePerKm
    private const val bikeRatePerKm: Double = Bike.bikeRatePerKm

    private const val miniBaseFare = Mini.baseFare
    private const val sedanBaseFare = PrimeSedan.baseFare
    private const val suvBaseFare = PrimeSUV.baseFare
    private const val autoBaseFare: Double = AutoRickshaw.AutoBaseFare
    private const val bikeBaseFare: Double = Bike.bikeBaseFare

    private fun calculateAutoFare(km: Double): Double = autoBaseFare + autoRatePerKm * km


    private fun calculateMiniFare(km: Double): Double = miniBaseFare + miniRatePerKm * km


    private fun calculateBikeFare(km: Double): Double = bikeBaseFare + bikeRatePerKm * km


    private fun calculateSUVFare(km: Double): Double = suvBaseFare + suvRatePerKm * km


    private fun calculateSEDANFare(km: Double): Double = sedanBaseFare + sedanRatePerKm * km

    fun calculateFare(vehicleType: VehicleType, km:Double): Double {
        return when(vehicleType){
            CAR_SUV -> calculateSUVFare(km)
            BIKE -> calculateBikeFare(km)
            AUTO_RICKSHAW -> calculateAutoFare(km)
            CAR_MINI -> calculateMiniFare(km)
            CAR_SEDAN -> calculateSEDANFare(km)
        }
    }

}