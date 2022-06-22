package BookingSystem

class CabCentre (val locatedAt: Location){
    internal val allVehiclesInfo: MutableMap<VehicleType, MutableList<VehicleInfo>> = mutableMapOf()


    private val allVehicles: MutableMap<VehicleType, MutableList<Vehicle>> = mutableMapOf()
    private val allDrivers: MutableMap<String, CabDriver> = mutableMapOf()

    init {
        VehicleType.values().forEach {
            allVehicles[it] = mutableListOf()
            allVehiclesInfo[it] = mutableListOf()
        }

    }

    internal fun addDriverWithVehicle(newVehicle: Vehicle, newDriver: CabDriver,  cabCentre: CabCentre? = null) :Response{
        if(!CabVehiclesRegistry.checkVehicleRegistered(newVehicle.numberPlate) || cabCentre!= null)
        {
            newVehicle.vehicleDriverId = newDriver.driverId
            allVehicles[newVehicle.vehicleType]!!.add(newVehicle)
            allDrivers[newDriver.driverId] = newDriver
            allVehiclesInfo[newVehicle.vehicleType]!!.add(
                VehicleInfo(
                    newVehicle.vehicleType, newVehicle.vehicleId, newVehicle.numberPlate, newDriver.driverId,
                    newDriver.fullName, newVehicle.vehicleName, newVehicle.maxOccupants
                )
            )
            if(cabCentre == null) {
                CabVehiclesRegistry.registerVehicle(newVehicle.numberPlate)
            }
            return Response.SUCCESS
        }
        else return Response.DUPLICATE_ENTRY_FOUND_FOR_VEHICLE
    }

    internal fun addDriver(newDriver: CabDriver, drivableVehicle: VehicleType): VehicleInfo {
        val newVehicle = Garage.manufactureVehicle(drivableVehicle, locatedAt.stationPoint)
        newDriver.associatedVehicle = newVehicle
        addDriverWithVehicle(newVehicle, newDriver, this)
        return VehicleInfo(newVehicle.vehicleType, newVehicle.vehicleId, newVehicle.numberPlate, newDriver.driverId,
                                newDriver.fullName, newVehicle.vehicleName,newVehicle.maxOccupants)

    }

    internal fun arrangeCab(rideInfo: RideInfo) : Response {
        val result = allDrivers.get(rideInfo.driverId)?.pickupPassenger(rideInfo)
        return if (result == Unit) Response.SUCCESS else Response.INVALID_INFO_FOR_ARRANGE_CABS

    }

    fun getDriverNameFromId(driverId: String): String? {
        return allDrivers[driverId]?.fullName
    }

    fun removeDriver(cabDriver: CabDriver){
        allDrivers.remove(cabDriver.driverId)
        allVehiclesInfo.get(cabDriver.associatedVehicle?.vehicleType)?.forEach{
            if(it.driverId == cabDriver.driverId){
                it.driverName =""
                it.driverId = ""
                it.driverAvailabilityStatus = DriverAvailabilityStatus.NOT_AVAILABLE
            }
        }
    }


}