package BookingSystem

import App.UserInput

class CabDriver(fullName:String, age:UInt, username: String, password: String, val CabCentre: StationPoint, val driverId : String) : User(fullName, age, username, password) {
    var associatedVehicle: Vehicle? = null


    fun pickupPassenger(rideInfo: RideInfo) {

        println("Please enter the otp: ")
        var passengerOtp: Int = UserInput.getIntInput()
        var otpMatched = rideInfo.rideOtp == passengerOtp
        val otpMatchedMessage = "Otp verified! Enjoy your ride\n\n"
        val otpNotMatchedMessage = "Sorry, your otp didn't match!\n\n"
        if (!otpMatched) {
            val tries = 3
            for (i in 3 downTo 1) {
                val tryWord = if (i == 1) " try" else " tries"
                println(
                    """Invalid otp!!! Re-enter your otp - $i$tryWord  left"""
                )
                passengerOtp = UserInput.getIntInput()
                if (passengerOtp == rideInfo.rideOtp) {
                    otpMatched = true
                    break
                }
            }
        }
        if (otpMatched) {
            println(otpMatchedMessage)
            CabCentralHub.updateDriverStatus(this, DriverAvailabilityStatus.BOOKED)
            if (associatedVehicle is PrimeCars) {
                if(App.activatePrime(this, rideInfo.bookingId)) {
                    (associatedVehicle as PrimeCars).activatePrime()
                }
            }

            if (associatedVehicle is Bike) {
                val speed: Float = Bike.bikeSpeedPerKmInMinutes
                System.out.println(
                    "Reached in " + (Map.calculateDistance(
                        rideInfo.currentLocation, rideInfo.passengerDestination
                    ) * speed).toString() + "minutes"
                )
            }

            else {
                println("Nearing your destination in")

                for (i in 5 downTo 1) {
                    println("$i minutes...")
                    Thread.sleep(2000)
                }
                println("Your destination has arrived. Thank you for using our services!\n")
            }

            CabCentralHub.updateDriverStatus(this, DriverAvailabilityStatus.AVAILABLE)
        } else {
            println(otpNotMatchedMessage)
//            CabCentralHub.setBookingStatus(this, rideInfo.bookingId, CabBookingStatus.CANCELLED)
//            throw InterruptedException()
        }


    }
}