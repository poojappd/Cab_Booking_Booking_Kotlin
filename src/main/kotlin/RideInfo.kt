data class RideInfo(val passengerName:String, val currentLocation : Location, val passengerDestination : Location,
                    var rideStatus: RideStatus = RideStatus.RIDE_BOOKED, val rideOtp:Int, ) {

}
