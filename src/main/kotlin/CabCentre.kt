class CabCentre (val locatedAt: Location){
    internal val allVehiclesInfo: MutableMap<VehicleType, MutableList<VehicleInfo>> = mutableMapOf()


    private val allVehicles: MutableMap<VehicleType, MutableList<Vehicle>> = mutableMapOf()
    private val allDrivers: MutableMap<String, CabDriver> = mutableMapOf()

    init {

        allVehicles[VehicleType.CAR_SUV] = mutableListOf()
        allVehicles[VehicleType.CAR_MINI] = mutableListOf()
        allVehicles[VehicleType.CAR_SEDAN] = mutableListOf()
        allVehicles[VehicleType.AUTO_RICKSHAW] = mutableListOf()
        allVehicles[VehicleType.BIKE] = mutableListOf()
        allVehiclesInfo[VehicleType.CAR_SEDAN] = mutableListOf()
        allVehiclesInfo[VehicleType.CAR_SUV] = mutableListOf()
        allVehiclesInfo[VehicleType.CAR_MINI] = mutableListOf()
        allVehiclesInfo[VehicleType.AUTO_RICKSHAW] = mutableListOf()
        allVehiclesInfo[VehicleType.BIKE] = mutableListOf()
    }

    internal fun addDriverWithVehicle(newVehicle: Vehicle, newDriver: CabDriver) {
        //pre-assigned driver with vehicle shud only be passed
        newVehicle.vehicleDriverId = newDriver.driverId
        allVehicles[newVehicle.vehicleType]?.add(newVehicle)
        allDrivers[newDriver.driverId] = newDriver

        allVehiclesInfo[newVehicle.vehicleType]?.add(
            VehicleInfo(
                newVehicle.vehicleType, newVehicle.vehicleId, newVehicle.numberPlate, newDriver.driverId,
                newDriver.fullName, newVehicle.vehicleName, newVehicle.maxOccupants)
            )

    }

    internal fun addDriver(newDriver: CabDriver, drivableVehicle:VehicleType){
        val newVehicle = Garage.manufactureVehicle(drivableVehicle, locatedAt.stationPoint)
        addDriverWithVehicle(newVehicle, newDriver)

    }

    internal fun arrangeCab(rideInfo: RideInfo) :Response{
        val result = allDrivers.get(rideInfo.driverId)?.pickupPassenger(rideInfo)
        return if (result == Unit) Response.SUCCESS else Response.INVALID_INFO_FOR_ARRANGE_CAB

    }

    fun getDriverNameFromId(driverId: String): String? {
        return allDrivers[driverId]?.fullName
    }



}