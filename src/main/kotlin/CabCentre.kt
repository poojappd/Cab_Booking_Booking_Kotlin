class CabCentre (val locatedStationPoint: StationPoint){

    private val activeVehiclesInfo: MutableMap<VehicleType, MutableList<VehicleInfo>> = mutableMapOf()
    private val allAvailableVehicles: MutableMap<VehicleType, MutableList<Vehicle>> = mutableMapOf()
    private val availableDrivers: MutableMap<String, CabDriver> = mutableMapOf()

    init {

        allAvailableVehicles[VehicleType.CAR_SUV] = mutableListOf()
        allAvailableVehicles[VehicleType.CAR_MINI] = mutableListOf()
        allAvailableVehicles[VehicleType.CAR_SEDAN] = mutableListOf()
        allAvailableVehicles[VehicleType.AUTO_RICKSHAW] = mutableListOf()
        allAvailableVehicles[VehicleType.BIKE] = mutableListOf()
        activeVehiclesInfo[VehicleType.CAR_SEDAN] = mutableListOf()
        activeVehiclesInfo[VehicleType.CAR_SUV] = mutableListOf()
        activeVehiclesInfo[VehicleType.CAR_MINI] = mutableListOf()
        activeVehiclesInfo[VehicleType.AUTO_RICKSHAW] = mutableListOf()
        activeVehiclesInfo[VehicleType.BIKE] = mutableListOf()
    }

    fun addDriverWithVehicle(newVehicle: Vehicle, newDriver: CabDriver) {
        //pre-assigned driver with vehicle shud only be passed
        newVehicle.vehicleDriverId = newDriver.driverId
        allAvailableVehicles[newVehicle.vehicleType]?.add(newVehicle)
        availableDrivers[newDriver.driverId] = newDriver

        activeVehiclesInfo[newVehicle.vehicleType]?.add(
            VehicleInfo(
                newVehicle.vehicleType, newVehicle.vehicleId, newVehicle.numberPlate, newDriver.driverId,
                newDriver.fullName, newVehicle.vehicleName)
            )

    }

    fun addDriver(newDriver: CabDriver, drivableVehicle:VehicleType){
        when(drivableVehicle){
            VehicleType.CAR_SUV->
            VehicleType.CAR_MINI ->
            VehicleType.CAR_SEDAN ->
            VehicleType.AUTO_RICKSHAW ->
            VehicleType.BIKE ->
        }

    }

}