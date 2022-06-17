class Passenger (userName :String, password:Array<Char>, fullName:String, age:UInt, val locatedArea:Location)
            :User(fullName, age, userName, password) {
       val bookingHistory:MutableList<Booking> = mutableListOf()
    get() = field.toMutableList()

    fun addToBookingHistory( booking:Booking){
        bookingHistory.add(booking)
    }

}