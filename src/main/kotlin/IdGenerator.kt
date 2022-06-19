import java.util.Random
import java.util.UUID

object IdGenerator {
    private var driverAppendString = "Cab_driver"
    private var driverCode:ULong = 1u

    fun generateDriverId(stationPoint: StationPoint): String {
        return driverAppendString + stationPoint.toString().substring(0, 4) + driverCode++
    }

    fun generateOtp() = Random().nextInt(1000, 9999)

    fun generateBookingId() = UUID.randomUUID().toString()
}