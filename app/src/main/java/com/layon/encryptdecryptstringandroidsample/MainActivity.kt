package com.layon.encryptdecryptstringandroidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.layon.encryptdecryptstringandroidsample.databinding.ActivityMainBinding

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEncrypterListener()
        setupDecrypterListener()
        setupCopyListener()
        setupPastListener()

        val number = 212

        println(number shr 1)
    }

    private fun setupEncrypterListener(){
        binding.apply {
            encrypterButton.setOnClickListener{
                if(textNormalInput.text.isEmpty()){
                    showToast("The text should not be empty")
                } else {
                    try {
                        val encrypted = AESUtilsKt.encrypt(textNormalInput.text.toString())
                        textEncripted.text = encrypted
                    } catch (e : Exception) {
                        e.printStackTrace()
                        textEncripted.text = ""
                    }
                }
            }
        }

    }

    private fun setupDecrypterListener(){
        binding.apply {
            decrypterButton.setOnClickListener{
                try {
                    val decrypted = AESUtilsKt.decrypt(textDecrypedInput.text.toString())
                    textNormal.text = decrypted
                } catch (e : Exception) {
                    e.printStackTrace()
                    textNormal.text = ""
                }
            }
        }

    }

    private fun setupCopyListener(){
        binding.apply {
            copyImageView.setOnClickListener{
                copy(textEncripted.text.toString())
                showToast("text copied")
            }
        }
    }

    private fun setupPastListener(){
        binding.apply {
            pastImageView.setOnClickListener{
                textDecrypedInput.setText(past())
                showToast("text pasted")
            }
        }
    }

    private fun copy(text: String){
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("copy", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun past() : String {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        var pasteData = ""

        // If it does contain data, decide if you can handle the data.

        // If it does contain data, decide if you can handle the data.
        if (!clipboard.hasPrimaryClip()) {
        } else if (!clipboard.primaryClipDescription!!.hasMimeType(
                MIMETYPE_TEXT_PLAIN
            )
        ) {
            // since the clipboard has data but it is not plain text
        } else {

            //since the clipboard contains plain text.
            val item = clipboard.primaryClip!!.getItemAt(0)

            // Gets the clipboard as text.
            pasteData = item.text.toString()

        }

        return pasteData
    }


    private fun showToast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}