package App

object Encryptor {
    fun encrypt(s: String): String {
        val temp = s.toCharArray()
        for (i in temp.indices) {
            temp[i] = (1 + (temp[i].code * 10)).toChar()
        }
        return temp.joinToString("")
    }

    fun decrypt(st: String): String {
        val s = st.toCharArray()
        for (i in st.indices) {
            s[i] = ((-1 + s[i].code) / 10 ).toChar()
        }
        return s.joinToString("")
    }
}