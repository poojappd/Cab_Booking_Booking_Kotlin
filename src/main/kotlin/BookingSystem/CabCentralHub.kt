package BookingSystem

internal object CabCentralHub {
   private val allCabCentres:MutableMap<StationPoint, CabCentre> = mutableMapOf()
   private val allPassengerBookingHistory: MutableMap<String, Booking> = mutableMapOf()

   internal fun addCabCentre(cabCentre: CabCentre, stationPoint: StationPoint) {
      allCabCentres[stationPoint] = cabCentre
   }

   fun addDriverWithVehicle(newDriver: CabDriver, newVehicle: Vehicle): Response {
      val response: Response
      var cabCentre = allCabCentres[newDriver.CabCentre]
      response = if (cabCentre != null) {
         cabCentre.addDriverWithVehicle(newVehicle, newDriver)
         Response.SUCCESS
      }
         else{
         Response.NO_SUCH_CAB_CENTRES
         }

         return response




   }

   fun addDriverOnly(newDriver: CabDriver, drivableVehicle: VehicleType):VehicleInfo? {
      val response: Response
      val cabCentre = allCabCentres[newDriver.CabCentre]
      if (cabCentre != null) {
         return cabCentre.addDriver(newDriver, drivableVehicle)
      }
      return null
   }


   fun viewFunctioningStationPoints(): MutableSet<StationPoint> {
      return allCabCentres.keys
   }


   fun getVehicleInfo(chosenCabCentre: CabCentre, fromLocation: Location, toLocation: Location): MutableList<CabSearchResult> {
      val searchResult:MutableList<CabSearchResult> = mutableListOf()
      chosenCabCentre.allVehiclesInfo.forEach{
         it.value.forEach { vehicleInfo ->
            if (vehicleInfo.driverAvailabilityStatus == DriverAvailabilityStatus.AVAILABLE) {
               val cabSearchResult = CabSearchResult(chosenCabCentre.locatedAt.stationPoint, vehicleInfo.vehicleType, vehicleInfo.vehicleName, vehicleInfo.seatCount,
                  FareCalculator.calculateFare(vehicleInfo.vehicleType, Map.calculateDistance(fromLocation, toLocation)),
                  vehicleInfo.driverId, chosenCabCentre.getDriverNameFromId(vehicleInfo.driverId)!!)

               searchResult.add( cabSearchResult)

            }

         }
      }
      return searchResult
   }


   fun searchCabs(fromLocation: Location, toLocation: Location):List<CabSearchResult>{
      var searchResults:MutableList<CabSearchResult> = mutableListOf()
      var stationPoint = fromLocation.stationPoint
      val nearestCabCentreList = Map.getNearestStationPoints(fromLocation, allCabCentres.keys)
         for(i in nearestCabCentreList.size-1 downTo 0) {
            val nearestCabCentre = nearestCabCentreList[i]
            searchResults = getVehicleInfo(allCabCentres.get(nearestCabCentre)!!, fromLocation, toLocation)
            if (searchResults.size > 0) {
              break
            }
         }

      return searchResults
    }


   fun arrangeCab(passengerName:String, currentLocation: Location, destination: Location, vehicleType: VehicleType,
                  chosenCabCentrePoint: StationPoint, driverId: String, booking: Booking, rideOtp:Int): RideInfo?{

      val chosenCabCentre = allCabCentres.get(chosenCabCentrePoint)
      if(chosenCabCentre != null){
         allPassengerBookingHistory.put(booking.bookingId, booking)
         val driverName = chosenCabCentre.getDriverNameFromId(driverId)!!
         val rideInfo = RideInfo(passengerName, driverName, driverId, currentLocation, destination, rideOtp)
         chosenCabCentre.arrangeCab(rideInfo)
         rideInfo.bookingId = booking.bookingId
         return rideInfo
      }
      return null

   }


   fun updateDriverStatus(CabDriver: CabDriver, driverAvailabilityStatus: DriverAvailabilityStatus?) {
      val driverCabCentre = allCabCentres[CabDriver.CabCentre]!!
      val activeVehiclesInfo = driverCabCentre.allVehiclesInfo
      activeVehiclesInfo.forEach { (k: VehicleType, v: MutableList<VehicleInfo>) ->
         for (vehicleInfo in v) {
            if (vehicleInfo.driverId == CabDriver.driverId) {
               vehicleInfo.driverAvailabilityStatus = DriverAvailabilityStatus.BOOKED
            }
         }
      }
   }


   fun updateCancelledBookings(passenger: Passenger, booking: Booking){
      allPassengerBookingHistory.put(booking.bookingId, booking)
   }

   fun removeDriver(cabCentreStationPoint: StationPoint, cabDriver: CabDriver){
      allCabCentres.get(cabCentreStationPoint)?.removeDriver(cabDriver)
   }

   init {
   val cabCentreLocation = Map.getLocationByIndex(StationPoint.ALANDUR,1)
   val cabCentre1 = CabCentre(cabCentreLocation)
   val newDriver1 = CabDriver(
      "Perumal",
      35u,
      "peru",
      "peru@35Cabs",
      StationPoint.ALANDUR,
      IdGenerator.generateDriverId(StationPoint.ALANDUR)
   )
   val vehicle1 = PrimeSedan(IdGenerator.generateVehicleId(newDriver1.CabCentre),"Maruti Swift", "TN4567")
   newDriver1.associatedVehicle = vehicle1

   val newDriver2 = CabDriver(
      "Karthick",
      30u,
      "Karthick33",
      "Karthick@30Cabs",
      StationPoint.ALANDUR,
      IdGenerator.generateDriverId(StationPoint.ALANDUR)
   )
   val vehicle2 = AutoRickshaw(IdGenerator.generateVehicleId(newDriver2.CabCentre),"Bajaj-RE", "TN3844")
   newDriver2.associatedVehicle = vehicle2

   val newDriver3 = CabDriver(
      "Daniel",
      28u,
      "Dannyguy",
      "theDanny@28Cabs",
      StationPoint.ALANDUR,
      IdGenerator.generateDriverId(StationPoint.ALANDUR)
   )
   val vehicle3 = Bike(IdGenerator.generateVehicleId(newDriver3.CabCentre),"Honda Splendor", "AP7623")
   newDriver3.associatedVehicle = vehicle3

      cabCentre1.addDriverWithVehicle(vehicle1, newDriver1)
      cabCentre1.addDriverWithVehicle(vehicle2, newDriver2)
      cabCentre1.addDriverWithVehicle(vehicle3, newDriver3)
      addCabCentre(cabCentre1, StationPoint.ALANDUR)




   val cabCentreLocation2 = Map.getLocationByIndex(StationPoint.TAMBARAM,1)
   val cabCentre2 = CabCentre(cabCentreLocation2)
   val newDriver12 = CabDriver(
      "PerumalSami",
      35u,
      "perusami",
      "perusami@35Cabs",
      StationPoint.TAMBARAM,
      IdGenerator.generateDriverId(StationPoint.TAMBARAM)
   )
   val vehicle12 = PrimeSUV(IdGenerator.generateVehicleId(newDriver12.CabCentre),"Toyota Innova", "AP4567")
   newDriver12.associatedVehicle = vehicle12

   val newDriver22 = CabDriver(
      "Karthick Subburaj",
      30u,
      "KarthickSubu33",
      "Karthicksubu@30Cabs",
      StationPoint.TAMBARAM,
      IdGenerator.generateDriverId(StationPoint.TAMBARAM)
   )
   val vehicle22 = AutoRickshaw(IdGenerator.generateVehicleId(newDriver22.CabCentre),"Bajaj-RE", "TN7844")
   newDriver22.associatedVehicle = vehicle22

   val newDriver32 = CabDriver(
      "Daniel",
      28u,
      "Dannyguy",
      "theDanny@28Cabs",
      StationPoint.TAMBARAM,
      IdGenerator.generateDriverId(StationPoint.TAMBARAM)
   )
   val vehicle32 = Bike(IdGenerator.generateVehicleId(newDriver32.CabCentre),"Honda Splendor", "AP7625")
   newDriver32.associatedVehicle = vehicle32

   val newDriver42 = CabDriver(
      "Jack",
      25u,
      "JackieChan",
      "theJacky@28Cabs",
      StationPoint.TAMBARAM,
      IdGenerator.generateDriverId(StationPoint.TAMBARAM)
   )
   val vehicle42 = Mini(IdGenerator.generateVehicleId(newDriver42.CabCentre),"Tata Indica", "KN2341")
   newDriver42.associatedVehicle = vehicle42

      cabCentre2.addDriverWithVehicle(vehicle12, newDriver12)
      cabCentre2.addDriverWithVehicle(vehicle22, newDriver22)
      cabCentre2.addDriverWithVehicle(vehicle32, newDriver32)
      cabCentre2.addDriverWithVehicle(vehicle42, newDriver42)
      addCabCentre(cabCentre2, StationPoint.TAMBARAM)



   val cabCentreLocation3 = Map.getLocationByIndex(StationPoint.GUDUVANCHERY,1)
   val cabCentre3 = CabCentre(cabCentreLocation3)
   val newDriver13 = CabDriver(
      "Ashok",
      39u,
      "ashokashok",
      "perusami@35Cabs",
      StationPoint.GUDUVANCHERY,
      IdGenerator.generateDriverId(StationPoint.GUDUVANCHERY)
   )
   val vehicle13 = PrimeSedan(IdGenerator.generateVehicleId(newDriver13.CabCentre),"Toyota etios", "TN5692")
   newDriver13.associatedVehicle = vehicle13

   val newDriver23 = CabDriver(
      "Maddy",
      23u,
      "MaddyMaddy1254",
      "Maddy*344",
      StationPoint.GUDUVANCHERY,
      IdGenerator.generateDriverId(StationPoint.GUDUVANCHERY)
   )
   val vehicle23 = AutoRickshaw(IdGenerator.generateVehicleId(newDriver23.CabCentre),"Bajaj-RE", "TN2211")
   newDriver23.associatedVehicle = vehicle23

   val newDriver33 = CabDriver(
      "Chad",
      28u,
      "chad654",
      "Chad@23Cabs",
      StationPoint.GUDUVANCHERY,
      IdGenerator.generateDriverId(StationPoint.GUDUVANCHERY)
   )
   val vehicle33 = Bike(IdGenerator.generateVehicleId(newDriver23.CabCentre),"Honda Splendor", "AP9623")
   newDriver33.associatedVehicle = vehicle33

   val newDriver43 = CabDriver(
      "Yash",
      25u,
      "Yash234",
      "RockyBhai@28Cabs",
      StationPoint.GUDUVANCHERY,
      IdGenerator.generateDriverId(StationPoint.GUDUVANCHERY)
   )
   val vehicle43 = Mini(IdGenerator.generateVehicleId(newDriver23.CabCentre),"Tata Indica", "KN7771")
   newDriver43.associatedVehicle = vehicle43

      cabCentre3.addDriverWithVehicle(vehicle13, newDriver13)
      cabCentre3.addDriverWithVehicle(vehicle23, newDriver23)
      cabCentre3.addDriverWithVehicle(vehicle33, newDriver33)
      cabCentre3.addDriverWithVehicle(vehicle43, newDriver43)
      addCabCentre(cabCentre3, StationPoint.GUDUVANCHERY)
   }
}

data class CabSearchResult(val cabCentreStationPoint: StationPoint, val vehicleType: VehicleType, val vehicleName:String, val seatCount:Int, val fare:Double, val driverId:String, val driverName:String){

}