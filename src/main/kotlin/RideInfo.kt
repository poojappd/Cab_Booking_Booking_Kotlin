data class RideInfo(
    val passengerName: String,
    val driverName: String?,
    val driverId: String,
    val currentLocation: Location,
    val passengerDestination: Location,
    val rideOtp: Int, ) {

}
