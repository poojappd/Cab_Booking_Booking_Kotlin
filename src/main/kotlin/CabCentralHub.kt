internal object CabCentralHub {
   private val allCabCentres:MutableMap<StationPoint, CabCentre> = mutableMapOf()
   private val allPassengerBookingHistory: MutableMap<String, Booking> = mutableMapOf()

   internal fun addCabCentre(cabCentre: CabCentre, stationPoint: StationPoint) {
      allCabCentres[stationPoint] = cabCentre
   }

   fun addDriverWithVehicle(newDriver:CabDriver, newVehicle:Vehicle): Response {
      val response:Response
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

   fun addDriverOnly(newDriver: CabDriver, drivableVehicle: VehicleType): Response {
      val response:Response
      var cabCentre = allCabCentres[newDriver.CabCentre]
      response = if (cabCentre != null) {
         cabCentre.addDriver(newDriver, drivableVehicle)
         Response.SUCCESS
      }

   else {
      Response.NO_SUCH_CAB_CENTRES
      }

   return response
   }


   fun viewFunctioningStationPoints(): MutableSet<StationPoint> {
      return allCabCentres.keys
   }


   fun getVehicleInfo(chosenCabCentre: CabCentre, fromLocation: Location, toLocation: Location): MutableList<CabSearchResult> {
      val searchResult:MutableList<CabSearchResult> = mutableListOf()
      chosenCabCentre.allVehiclesInfo.forEach{
         it.value.forEach { vehicleInfo ->

            if (vehicleInfo.availabilityStatus == AvailabilityStatus.AVAILABLE) {
               val cabSearchResult = CabSearchResult(chosenCabCentre.locatedAt.stationPoint, vehicleInfo.vehicleType, vehicleInfo.vehicleName, vehicleInfo.seatCount,
                  FareCalculator.calculateFare(vehicleInfo.vehicleType, Map.calculateDistance(fromLocation, toLocation)),
                  vehicleInfo.driverId)

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
         for(nearestCabCentre in nearestCabCentreList) {
            searchResults = getVehicleInfo(allCabCentres.get(nearestCabCentre)!!, fromLocation, toLocation)
            if (searchResults.size > 0) {
              break
            }
         }

      return searchResults
    }



   fun arrangeCab(passengerName:String, currentLocation: Location, destination: Location, vehicleType: VehicleType,
                  chosenCabCentrePoint: StationPoint, driverId: String):RideInfo{
      var response = Response.NO_SUCH_CAB_CENTRES
      val chosenCabCentre = allCabCentres.get(chosenCabCentrePoint)
      if(chosenCabCentre != null){
         val driverName = chosenCabCentre.getDriverNameFromId(driverId)!!
         val rideInfo = RideInfo(passengerName, driverName, driverId, currentLocation,destination, IdGenerator.generateOtp() )
         response = chosenCabCentre.arrangeCab(rideInfo)
         return rideInfo
      }
      else{
         throw Exception("d")
      }

   }

}

data class CabSearchResult(val cabCentreStationPoint:StationPoint, val vehicleType: VehicleType, val vehicleName:String, val seatCount:Int, val fare:Double, val driverId:String){

}