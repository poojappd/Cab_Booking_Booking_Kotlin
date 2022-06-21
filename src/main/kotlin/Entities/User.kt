package Entities

sealed class User(val fullName:String, val age: UInt, val userName: String, private val password: String) {

}