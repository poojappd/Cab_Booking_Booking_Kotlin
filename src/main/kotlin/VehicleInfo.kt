enum class AvailabilityStatus{
    AVAILABLE,
    BOOKED
}
data class VehicleInfo(val vehicleType: VehicleType, val vehicleId:Int, val numberplate:String, val driverId:String,
                       val driverName:String, val vehicleName:String, var availabilityStatus: AvailabilityStatus = AvailabilityStatus.AVAILABLE   ) {
    val carModel:CarModel? = null

}