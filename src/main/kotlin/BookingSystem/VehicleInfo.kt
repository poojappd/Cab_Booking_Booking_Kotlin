package BookingSystem

enum class DriverAvailabilityStatus{
    AVAILABLE,
    BOOKED
}
data class VehicleInfo(val vehicleType: VehicleType, val vehicleId:UInt, val numberplate:String, val driverId:String,
                       val driverName:String, val vehicleName:String, val seatCount:Int, var driverAvailabilityStatus: DriverAvailabilityStatus = DriverAvailabilityStatus.AVAILABLE,  )

