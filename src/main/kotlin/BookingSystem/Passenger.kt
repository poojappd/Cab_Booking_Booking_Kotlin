package BookingSystem

class Passenger(userName:String, password: String, fullName:String, age:UInt, val locatedArea: Location)
            : User(fullName, age, userName, password) {
       val bookingHistory:MutableList<Booking> = mutableListOf()
    //get() = field.toMutableList()

    fun addToBookingHistory( booking: Booking){
        bookingHistory.add(booking)
    }

}
