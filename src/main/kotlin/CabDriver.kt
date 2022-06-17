class CabDriver(fullName:String, age:UInt, username: String, password: Array<Char>, val driverId : String, val defaultStationPoint: StationPoint) : User(fullName, age, username, password)
{
    var associatedVehicle :Vehicle? = null


    fun pickupPassenger(rideInfo: RideInfo ){}
}