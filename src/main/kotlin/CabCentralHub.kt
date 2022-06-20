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
               searchResult.add(CabSearchResult(vehicleInfo.vehicleType, vehicleInfo.vehicleName, vehicleInfo.seatCount,
                  FareCalculator.calculateFare(vehicleInfo.vehicleType, Map.calculateDistance(fromLocation, toLocation)))
               )

            }

         }
      }
      return searchResult
   }


   fun searchCabs(fromLocation: Location, toLocation: Location):CabSearchResult{
      var searchResults:MutableList<CabSearchResult>
      var stationPoint = fromLocation.stationPoint
      var toRemove :StationPoint? = null
      var flag = false
      if(allCabCentres.containsKey(stationPoint)){
         val chosenCabCentre = allCabCentres.get(stationPoint)!!
         searchResults = getVehicleInfo(chosenCabCentre, fromLocation, toLocation)
         if (searchResults.size == 0 ) {
            flag = true
            toRemove = chosenCabCentre.locatedAt.stationPoint
         }



      }
      //.remove(StationPoint.THAILAVARAM)

      if(!allCabCentres.containsKey(stationPoint) || flag){
         flag = false
         val otherCabCentrePoints = allCabCentres.keys
         otherCabCentrePoints.remove(toRemove)
         stationPoint = Map.getNearestStationPoints(fromLocation.stationPoint, otherCabCentrePoints )
         val chosenCabCentre = allCabCentres.get(stationPoint)!!
         searchResults = getVehicleInfo(chosenCabCentre, fromLocation, toLocation)



      }


      val nearestCabCentre:CabCentre
      if(allCabCentres.containsKey(stationPoint))
         nearestCabCentre = allCabCentres.get(stationPoint)!!

         val nearestCabCentreList = Map.getNearestStationPoints(fromLocation, allCabCentres.keys)
         for(i in nearestCabCentreList) {
            searchResults = getVehicleInfo(allCabCentres.get(i)!!, fromLocation, toLocation)
            if (searchResults.size > 0) {
               break
            }
         }


      }

   }

   fun arrangeCab(passengerName:String, sourceLocation: Location, vehicleType: VehicleType):RideInfo{

   }

}

data class CabSearchResult(val vehicleType: VehicleType, val vehicleName:String, val seatCount:Int, val fare:Double, val response: Response = Response.SUCCESS  )