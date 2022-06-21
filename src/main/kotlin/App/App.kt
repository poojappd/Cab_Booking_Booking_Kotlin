package App
import App.Database.checkUserNameExists
import App.Database.verifyUser
import App.UserInput.getIntInput
import App.UserInput.getMenuChoiceInput
import App.UserInput.getStringInput
import Entities.*
import Entities.Map
import java.time.LocalDateTime
import kotlin.collections.forEach


private data class UserInfo(val fullName: String, val age: UInt, val username: String, val password: String)

private fun viewMapBaseStation():Int{
    var bases=Map.getBaseLocations()
    for(i in 1..bases.size){
        println("$i. ${bases[i-1]}")
    }
    return bases.size

}

private fun viewMapAreaFromStation(stationPoint: StationPoint): Int {
   var locationsInStation = Map.getLocationsFromBaseLocation(stationPoint)

    for(i in 1..locationsInStation!!.size){
        println("$i. ${locationsInStation[i-1].area}")
    }

    return locationsInStation.size

}

private fun bookCab(passenger: Passenger) {
    println(
        "Select your pickup area\n" +
                "                 1. From your home\n" +
                "                 2. Choose from other locations"
    )
    var chosenMenuOption = getMenuChoiceInput(2)
    val passengerCurrentLocation: Location?
    val passengerDestination: Location?


    if (chosenMenuOption == 1) {
        passengerCurrentLocation = passenger.locatedArea
    } else {
        println("Which base area are you in right now?")
        val stationCount = viewMapBaseStation()
        val passengerCurrentStation = Map.getBaseLocations().get(getMenuChoiceInput(stationCount) - 1)
        println()
        val areaCount = viewMapAreaFromStation(passengerCurrentStation)
        chosenMenuOption = getMenuChoiceInput(areaCount)

        passengerCurrentLocation = Map.getLocationsFromBaseLocation(passengerCurrentStation)?.get(chosenMenuOption)
    }
    println(
        "Select your dropping point\n" +
                "                 1. To your home\n" +
                "                 2. Choose from other locations"
    )

    chosenMenuOption = getMenuChoiceInput(2)
    if (chosenMenuOption == 1) {
        passengerDestination = passenger.locatedArea
    } else {
        println("Select your destination area")
        val stationCount = viewMapBaseStation()
        val passengerDestinationStation = Map.getBaseLocations().get(getMenuChoiceInput(stationCount) - 1)
        println()
        val areaCount = viewMapAreaFromStation(passengerDestinationStation)
        chosenMenuOption = getMenuChoiceInput(areaCount)

        passengerDestination = Map.getLocationsFromBaseLocation(passengerDestinationStation)?.get(chosenMenuOption)
    }
    println("Searching cabs...")
    if (passengerCurrentLocation != null && passengerDestination != null) {
        val resultList = CabCentralHub.searchCabs(passengerCurrentLocation, passengerDestination)
        var i = 1
        if(resultList[0].cabCentreStationPoint != passengerCurrentLocation.stationPoint) {
            println("No cab centres found at your location..." +
                    "Contacting nearby cab centres")
            Thread.sleep(2)
        }
        println("Connected with ${resultList[0].cabCentreStationPoint} cab centre ")
        println()
        println("____________________________________________________________________________________________________________________________________________________________________")
        System.out.printf("%25s %25s %25s %25s\n", "S.No.", "Vehicle", "Max Occupants", "Fare")
        println("____________________________________________________________________________________________________________________________________________________________________")
        resultList.forEach {
            System.out.printf(
                "%25d %25s %25s %25s", i++, it.vehicleType, it.seatCount, it.fare
            )
        }
        val cancelOption: Int = i
        println(
            "Choose a vehicle to book your ride\n" +
                    "Enter " + cancelOption + " to cancel"
        )
        chosenMenuOption = getMenuChoiceInput(cancelOption)

        if (chosenMenuOption != cancelOption) {
            val bookedVehicleInfo = resultList[chosenMenuOption]
            val driverId: String = bookedVehicleInfo.driverId
            val cabCentreStationPoint = bookedVehicleInfo.cabCentreStationPoint
            val tripOtp = IdGenerator.generateOtp()
            System.out.println(
                ("Book " + bookedVehicleInfo.vehicleType +  " for Rs. " + bookedVehicleInfo.fare + " ?\n" +
                        "1. Book ride\n" +
                        "2. Cancel")
            )
            val chosenOption = getMenuChoiceInput(2)
            if (chosenOption == 1) {

                val rideInfo = CabCentralHub.checkCab(passenger.fullName, passengerCurrentLocation, passengerDestination,
                bookedVehicleInfo.vehicleType, bookedVehicleInfo.cabCentreStationPoint, driverId)
                val bookingHistory = Booking(
                    IdGenerator.generateBookingId(),
                    passenger.fullName, bookedVehicleInfo.driverName, CabServiceType.CAB_BOOKING, LocalDateTime.now(), passengerCurrentLocation,
                    passengerDestination, bookedVehicleInfo.vehicleType, bookedVehicleInfo.fare)
                rideInfo?.bookingId = bookingHistory.bookingId

                if(rideInfo != null) {
                    println(
                        "Booking Confirmed! \n" +
                                "Please note this otp for verification " + rideInfo.rideOtp+
                                "1. Confirm cab" +
                                "2. Cancel cab ")
                    chosenMenuOption = getMenuChoiceInput(2)
                    if(chosenMenuOption == 2){
                        bookingHistory.bookingStatus = CabBookingStatus.CANCELLED
                    }
                    else{
                        CabCentralHub.confirmCabBooking(bookedVehicleInfo.cabCentreStationPoint, rideInfo, bookingHistory)
                    }
                }
            }
        }
    }
}

private fun createUserName(): String {
    var username = ""
    do{
    var userNameExists = true


    println("Enter a new userName")
    username = getStringInput()
    userNameExists = checkUserNameExists(username)
    if (userNameExists) {
            println("Username already exists! please enter a new username")
    }
    }while (userNameExists)
    return username
}


private fun getUserInfo(): UserInfo {
    println("Enter your age")
    var userAge = getIntInput()
    var isOldEnough: Boolean = ValidatingTool.validateUserAge(userAge)
    while (!isOldEnough) {
        println("Accepted age values are from 18 - 160")
        userAge = getIntInput()
        isOldEnough = ValidatingTool.validateUserAge(userAge)
    }
    println("Enter your full name")
    val name = getStringInput()
    val username: String = createUserName()
    println(
        """
            Enter a password of length more than 8 characters, 
            with atleast one uppercase and lowercase alphabet, and a symbol
            """.trimIndent()
    )
    var password = getStringInput()
    var passwordIsValid: Boolean = ValidatingTool.validatePassword(password)
    while (!passwordIsValid) {
        println("Password doesn't match the conditions\n Kindly enter a new password")
        password = getStringInput()
        passwordIsValid = ValidatingTool.validatePassword(password)
    }

    return UserInfo ( name, userAge.toUInt(), username, password)
}



private fun createPassengerAccount() {
    val userInfo: UserInfo = getUserInfo()
    println("Choose your Home address base location")
    var bases=Map.getBaseLocations()
    for(i in 1..bases.size){
        println("$i. ${bases[i-1]}")
    }
    val homeStation = bases[getMenuChoiceInput(bases.size)-1]
    var locationsInStation = Map.getLocationsFromBaseLocation(homeStation)
    println("Now pick your locality")
    for(i in 1..locationsInStation!!.size){
        println("$i. ${locationsInStation[i-1].area}")
    }

    val choice = getMenuChoiceInput(locationsInStation.size)
    val homeLocation = locationsInStation[choice - 1]

    val newPassenger = Passenger(
        userInfo.username, userInfo.password, userInfo.fullName, userInfo.age, homeLocation)
    Database.addUser(newPassenger, userInfo.password)
    println("Account created successfuly! \n Please login to use the application!")
}


private fun chooseAccountForNewUser() {
    println(
        "Choose your Account type\n" +
                "                1. User\n" +
                "                2. Driver"
    )
    val chosenMenuOption = getMenuChoiceInput(2)
    when (chosenMenuOption) {
        1 -> createPassengerAccount()
       // 2 -> createDriverAccount()
    }
}

fun viewBookingHistory(passenger: Passenger) {
    val bookingHistory = passenger.bookingHistory
    println("---------------------         Booking History         ---------------------\n")
    for (booking in bookingHistory) {
        println("Booking time:     $booking.cabBookedTime")
        println("Booking Id:" + booking.bookingId)
        println(
            "From :            $booking.fromLocation.stationPoint"
                 + " - $booking.fromLocation.area"
                + "\nTo :            $booking.toLocation.stationPoint"
                + " -    $booking.toLocation.area"
        )
        println(booking.bookingStatus)
        println("Driver name :     " + booking.driverName)
        println(
            "Vehicle details:  " + booking.vehicleType
        )
        println("\n Total Fare: -----> " + booking.fare)
        println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    }
}
fun login() {
    println("Enter your username")
    var userName = getStringInput()
    println("And your password...")
    var password = getStringInput().toCharArray()
    var user: User?= verifyUser(userName, password)
    var toExit = false
    while (user == null) {
        println("\nInvalid username or password!\nPlease re-enter your account username")
        userName = getStringInput()
        println("And your password...")
        password = getStringInput().toCharArray()
        user = verifyUser(userName, password)
    }
    System.out.println(
        "Welcome ${user.fullName} !"
    )
    if (user is Passenger) {
        passengerServices(user)
    } else if (user is CabDriver) {
        //employeeServices(user);
    }
}


fun passengerServices(passenger: Passenger) {
    do{
    var logout = false

        println("How may we help you?")
        println("1. Book cab\n" +
                "2. Schedule a cab ride\n" +
                "3. Rent a cab\n" +
                "4. Share a cab with others (Discount on Fare!)\n" +
                "5. View Booking History\n" +
                "6. Logout of account"

        )
        val chosenMenuOption = getMenuChoiceInput(6)
        when (chosenMenuOption) {
            1 -> bookCab(passenger)
            5 -> viewBookingHistory(passenger)
            6 -> logout = true
        }
    }while (!logout)

}


fun viewWelcomeScreen() {
    println(
        "**********************************************************\n" +
                "            Cab Booking System\n" +
        "**********************************************************\n" +
                "Choose the following option to continue\n" +
                "                \n" +
                "1.  New User\n" +
                "2.  Existing User\n" +
                "\n" +
                "Enter your option"
    )
}

fun main() {
    do {
        var chosenMenuOption = 0
        viewWelcomeScreen()
        chosenMenuOption = getMenuChoiceInput(2, -1)
        println(chosenMenuOption)
        when (chosenMenuOption) {
            1 -> chooseAccountForNewUser()
            2 -> login()
        }
    }
    while (chosenMenuOption != -1)
}