package BookingSystem

import java.util.Random
import kotlin.reflect.jvm.internal.ReflectProperties.Val

object Garage{
    private val numberPlateIds = mutableMapOf("TN" to 0)


    private val SUVModels = mutableListOf("Toyota Innova", "Chevrolet Enjoy", "Maruti Ertiga")
    private val sedanModels = mutableListOf("Maruti Swift Dzire", "Toyota etios", "Nissan Sunny")
    private val miniModels = mutableListOf("Tata Indica", "Nissan Micra", "Maruti Ritz")
    private val autoModels = mutableListOf("Bajaj-RE")
    private val bikeModels = mutableListOf("Hero Splendor","Bajaj Avendor", "Honda Activa")


    fun manufactureVehicle(vehicleType: VehicleType, stationPoint: StationPoint): Vehicle {
        var value = numberPlateIds["TN"]!!
        var newNumberPlate:String

        run loop@{
           do {
                value++
                val appendString = "".run {
                    when (value) {
                        in 0..9 -> padEnd(3, '0')

                        in 10..99 -> padEnd(2, '0')
                        in 100..999 -> padEnd(1, '0')
                        else -> ""
                    }

                }
                newNumberPlate = "TN$appendString$value"
                val isValid = ValidatingTool.validateNumberPlate(newNumberPlate)
            } while (!isValid)

            if (!CabVehiclesRegistry.checkVehicleRegistered(newNumberPlate)) {
                CabVehiclesRegistry.registerVehicle(newNumberPlate)
            } else return@loop
        }
        numberPlateIds["TN"] = value+1
        val vehicleId = IdGenerator.generateVehicleId(stationPoint)
        val newVehicle = when(vehicleType){
            VehicleType.BIKE -> Bike(vehicleId, bikeModels[Random().nextInt(0, bikeModels.size)], newNumberPlate)
            VehicleType.AUTO_RICKSHAW -> AutoRickshaw(vehicleId, autoModels[0], newNumberPlate)
            VehicleType.CAR_MINI -> Mini(vehicleId, miniModels[Random().nextInt(0, miniModels.size)], newNumberPlate)
            VehicleType.CAR_SEDAN -> PrimeSedan(vehicleId, sedanModels[Random().nextInt(0, sedanModels.size)], newNumberPlate)
            VehicleType.CAR_SUV -> PrimeSUV(vehicleId, SUVModels[Random().nextInt(0, SUVModels.size)], newNumberPlate)
        }

        return newVehicle
    }
}
