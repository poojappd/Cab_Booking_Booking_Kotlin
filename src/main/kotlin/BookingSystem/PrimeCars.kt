package BookingSystem

sealed interface PrimeCars {
    var wifiEnabled: Boolean
    var moviesStreaming:Boolean
    var musicStreaming:Boolean

    fun activatePrime() {
        wifiEnabled = true
        moviesStreaming = true
        musicStreaming = true

        println("Prime has been activated! " +
                "\nEnjoy unlimited wifi " +
                "\n      unlimited movies and music " +
                "for free!")


    }
}