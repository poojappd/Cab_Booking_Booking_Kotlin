
object ValidatingTool {

    fun validatePassword(password: String): Boolean {
        return password.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))
    }

    fun validateUserAge(age: Int): Boolean {
        return age in 18..150
    }

    fun validateNumberPlate(numberPlate: String): Boolean {
        return numberPlate.matches(Regex("^[A-Za-z]{2}[0-9]{4}\\z"))
    }
}