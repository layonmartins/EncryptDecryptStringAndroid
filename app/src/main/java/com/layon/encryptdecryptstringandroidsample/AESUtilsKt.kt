package com.layon.encryptdecryptstringandroidsample

import java.lang.Exception
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class AESUtilsKt {

    companion object {
        private val HEX = "0123456789ABCDEF"

        private val keyValue = byteArrayOf(
            'c'.toByte(),
            'o'.toByte(),
            'd'.toByte(),
            'i'.toByte(),
            'n'.toByte(),
            'g'.toByte(),
            'a'.toByte(),
            'f'.toByte(),
            'f'.toByte(),
            'a'.toByte(),
            'i'.toByte(),
            'r'.toByte(),
            's'.toByte(),
            'c'.toByte(),
            'o'.toByte(),
            'm'.toByte()
        )


        @Throws(Exception::class)
        fun encrypt(cleartext: String): String? {
            val rawKey = getRawKey()
            val result = encrypt(rawKey, cleartext.toByteArray())
            return toHex(result)
        }

        @Throws(Exception::class)
        fun decrypt(encrypted: String): String? {
            val enc = toByte(encrypted)
            val result = decrypt(enc)
            return String(result)
        }

        @Throws(Exception::class)
        private fun getRawKey(): ByteArray {
            val key: SecretKey = SecretKeySpec(keyValue, "AES")
            return key.encoded
        }

        @Throws(Exception::class)
        private fun encrypt(raw: ByteArray, clear: ByteArray): ByteArray {
            val skeySpec: SecretKey = SecretKeySpec(raw, "AES")
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
            return cipher.doFinal(clear)
        }

        @Throws(Exception::class)
        private fun decrypt(encrypted: ByteArray): ByteArray {
            val skeySpec: SecretKey = SecretKeySpec(keyValue, "AES")
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec)
            return cipher.doFinal(encrypted)
        }

        fun toByte(hexString: String): ByteArray {
            val len = hexString.length / 2
            val result = ByteArray(len)
            for (i in 0 until len) result[i] = Integer.valueOf(
                hexString.substring(2 * i, 2 * i + 2),
                16
            ).toByte()
            return result
        }

        fun toHex(buf: ByteArray?): String? {
            if (buf == null) return ""
            val result = StringBuffer(2 * buf.size)
            for (i in buf.indices) {
                appendHex(result, buf[i])
            }
            return result.toString()
        }

        private fun appendHex(sb: StringBuffer, b: Byte) {
            sb.append(HEX[(b.toInt() shr 4) and 0x0f]).append(HEX[b.toInt() and 0x0f])
        }
    }

}