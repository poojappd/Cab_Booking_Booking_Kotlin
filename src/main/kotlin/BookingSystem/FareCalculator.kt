package BookingSystem

import BookingSystem.VehicleType.*

object FareCalculator {
    private const val accessFee = 7.35
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

    private fun calculateAutoFare(km: Double): Double = autoBaseFare + autoRatePerKm + accessFee * km


    private fun calculateMiniFare(km: Double): Double = miniBaseFare + miniRatePerKm + accessFee* km


    private fun calculateBikeFare(km: Double): Double = bikeBaseFare + bikeRatePerKm + accessFee * km


    private fun calculateSUVFare(km: Double): Double = suvBaseFare + suvRatePerKm + accessFee* km


    private fun calculateSEDANFare(km: Double): Double = sedanBaseFare + sedanRatePerKm + accessFee * km

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