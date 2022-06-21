package Entities

import java.util.Random

object Garage{
    private val numberPlateIds = mutableMapOf("TN" to 1000)


    private val SUVModels = mutableListOf("Toyota Innova", "Chevrolet Enjoy", "Maruti Ertiga")
    private val sedanModels = mutableListOf("Maruti Swift Dzire", "Toyota etios", "Nissan Sunny")
    private val miniModels = mutableListOf("Tata Indica", "Nissan Micra", "Maruti Ritz")
    private val autoModels = mutableListOf("Bajaj-RE")
    private val bikeModels = mutableListOf("Hero Splendor","Bajaj Avendor", "Honda Activa")


    fun manufactureVehicle(vehicleType: VehicleType, stationPoint: StationPoint): Vehicle {
        var value = numberPlateIds["TN"]!!
        var newNumberPlate:String
        do {
            value++
            newNumberPlate = "TN$value"
        } while (!VehicleRegistry.checkVehicleRegistered(newNumberPlate))

        numberPlateIds["TN"] = value+1
        val vehicleId = IdGenerator.generateVehicleId(stationPoint)

        val newVehicle = when(vehicleType){
            VehicleType.BIKE -> Bike(vehicleId, bikeModels[Random().nextInt(0, bikeModels.size)], newNumberPlate)
            VehicleType.AUTO_RICKSHAW -> AutoRickshaw(vehicleId, autoModels[0], newNumberPlate)
            VehicleType.CAR_MINI -> Mini(vehicleId, miniModels[Random().nextInt(0, miniModels.size)], newNumberPlate)
            VehicleType.CAR_SEDAN -> Bike(vehicleId, sedanModels[Random().nextInt(0, sedanModels.size)], newNumberPlate)
            VehicleType.CAR_SUV -> Bike(vehicleId, SUVModels[Random().nextInt(0, SUVModels.size)], newNumberPlate)
        }

        return newVehicle
    }
}

//suv - toyot