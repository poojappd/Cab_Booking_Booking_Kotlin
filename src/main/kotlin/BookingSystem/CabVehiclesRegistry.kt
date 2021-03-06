package BookingSystem

object CabVehiclesRegistry {
    private val registeredVehicles : MutableSet<String> = mutableSetOf()

    fun registerVehicle(numberPlateId:String): Boolean {
        if(ValidatingTool.validateNumberPlate(numberPlateId)){
            registeredVehicles.add(numberPlateId)
            return true
        }
        return false
    }

    fun checkVehicleRegistered(numberPlateId: String):Boolean{
        return registeredVehicles.contains(numberPlateId)
    }
}