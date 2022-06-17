data class RideInfo(val passengerName:String, val currentLocation : Location, val passengerDestination : Location,
                    var rideStatus: RideStatus = RideStatus.RIDEBOOKED,
) {
    enum class RideStatus {
        RIDEBOOKED,
        RIDECANCELLED,
        RIDECOMPLETE
    }
}

