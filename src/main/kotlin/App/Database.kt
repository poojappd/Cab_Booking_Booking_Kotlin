package App

import BookingSystem.User


object Database {
    private val allUsers: MutableMap<String, User> = mutableMapOf()
    private val userCredentials = mutableMapOf<String, String>()


    fun verifyUser(userName: String, password: String): User? {

        val actualUser = allUsers.get(userName)
        val actualUserEncryptedPassword = userCredentials.get(actualUser?.userName)
        val actualUserPassword = actualUserEncryptedPassword?.let { Encryptor.decrypt(it) }

        if (actualUserPassword.contentEquals(password)) {
            return actualUser

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