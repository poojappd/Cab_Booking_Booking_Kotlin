package App

object Encryptor {
    fun encrypt(s: String): CharArray {
        val temp = s.toCharArray()
        for (i in temp.indices) {
            temp[i] = ((temp[i].code + 1) * 10).toChar()
        }
        return temp
    }

    fun decrypt(s: CharArray): CharArray {
        for (i in s.indices) {
            s[i] = (s[i].code / 10 - 1).toChar()
        }
        return s
    }
}