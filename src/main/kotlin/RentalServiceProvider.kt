@JvmInline
value class RentalServiceProvider(val hour: Int) {
        val miniRentalPackagePerHour: Int
        get() {
           return 320 * hour
        }
        val sedanRentalPackagePerHour: Int
        get() = 360 * hour

        val suvRentalPackagePerHour: Int
        get() = 448 * hour

    }

