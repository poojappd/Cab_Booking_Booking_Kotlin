class Car(vehicleId:Int,   carModel: CarModel, vehicleName :String, numberPlate:String,
          maxOccupants:Int, vehicleDriverId: String) :Vehicle(vehicleId, vehicleName, VehicleType.CAR, numberPlate, maxOccupants, vehicleDriverId){

}

enum class CarModel{
    PRIME,
    SUV,
    SEDAN,
    MINI
}