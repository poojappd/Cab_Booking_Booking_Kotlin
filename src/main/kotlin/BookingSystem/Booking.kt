package BookingSystem

import java.time.LocalDateTime

data class Booking(
    val bookingId: String, val passengerName:String, val driverName:String, val cabServiceType: CabServiceType,
    val cabBookedTime: LocalDateTime, val fromLocation: Location, val toLocation: Location,
    val vehicleType: VehicleType, val fare:Double,
    var bookingStatus: CabBookingStatus = CabBookingStatus.WAITING_FOR_CONFIRMATION
) {
}