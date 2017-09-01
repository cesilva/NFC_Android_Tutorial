package com.multimeleon.welcome.peter_john.nfcapp

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mNfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        mNfcAdapter?.let {
            NFCUtil.enableNFCInForeground(it, this,javaClass)
        }
    }

    override fun onPause() {
        super.onPause()
        mNfcAdapter?.let {
            NFCUtil.disableNFCInForeground(it,this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val messageWrittenSuccessfully = NFCUtil.createNFCMessage("anything", intent)
        resultTextView.text = messageDisplayed(messageWrittenSuccessfully)
        imageView.visibility = View.INVISIBLE
        imageView.setImageResource(imageDisplayed(messageWrittenSuccessfully))
        imageView.visibility = View.VISIBLE
    }

    fun messageDisplayed(messageWrittenInTag: Boolean ): String =
            when (messageWrittenInTag) {
                true -> getString(R.string.success_message)
                false -> getString(R.string.error_message)
            }

    fun imageDisplayed(messageWrittenInTag: Boolean) =
            when (messageWrittenInTag){
                true -> R.drawable.ic_thumb_up_black_48dp
                false -> R.drawable.ic_thumb_down_black_48dp
            }
}
