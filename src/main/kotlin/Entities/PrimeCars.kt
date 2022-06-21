package Entities

sealed interface PrimeCars {
    var wifiEnabled: Boolean
    var moviesStreaming:Boolean
    var musicStreaming:Boolean

    fun activatePrime() {
        wifiEnabled = true
        moviesStreaming = true
        musicStreaming = true
        println("Prime subscription is available for this vehicle!")
        println("Prime has been activated! Enjoy unlimited wifi with movies and music")

    }
}