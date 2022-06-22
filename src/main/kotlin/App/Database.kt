package App

import BookingSystem.User


object Database {
    private val allUsers: MutableMap<String, User> = mutableMapOf()
    private val userCredentials = HashMap<String, CharArray>()

    //driverId
    fun verifyUser(userName: String, password: CharArray): User? {
        val actualUserEncryptedPassword: CharArray
        val actualUserPassword: CharArray
        val actualUser = allUsers.get(userName)
        if (actualUser != null) {
            actualUserEncryptedPassword = userCredentials.get(actualUser.userName)!!
            actualUserPassword = Encryptor.decrypt(actualUserEncryptedPassword)
            if (actualUserPassword.contentEquals(password)) {
                return actualUser
            }
        }
        return null
    }

    fun addUser(user: User, password: String) {
        if(!checkUserNameExists(user.userName)) {
            val encryptedPassword = Encryptor.encrypt(password)
            allUsers[user.userName] = user
            userCredentials[user.userName] = encryptedPassword
        }
    }

    fun checkUserNameExists(userName: String): Boolean {
        return userCredentials.keys.contains(userName)
    }
}