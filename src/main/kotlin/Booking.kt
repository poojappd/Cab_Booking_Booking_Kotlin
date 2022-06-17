import java.time.LocalDateTime

data class Booking (val passengerName:String, val driverName:String, val cabServiceType: CabServiceType,
                    val cabBookedTime: LocalDateTime, val fromLocation: Location, val toLocation: Location, val vehicleInfo: VehicleInfo) {
}