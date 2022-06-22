package BookingSystem

enum class DriverAvailabilityStatus{
    AVAILABLE,
    BOOKED,
    NOT_AVAILABLE
}
data class VehicleInfo(val vehicleType: VehicleType, val vehicleId:UInt, val numberplate:String, var driverId:String,
                       var driverName:String, val vehicleName:String, val seatCount:Int, var driverAvailabilityStatus: DriverAvailabilityStatus = DriverAvailabilityStatus.AVAILABLE,  )

