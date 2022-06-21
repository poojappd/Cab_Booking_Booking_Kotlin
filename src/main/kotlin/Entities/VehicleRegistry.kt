package Entities

object VehicleRegistry {
    private val registeredVehicles : MutableList<String> = mutableListOf()

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