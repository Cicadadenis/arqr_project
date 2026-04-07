package com.example.arqr

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView
import com.google.zxing.Result

class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var scannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun handleResult(rawResult: Result?) {
        val data = rawResult?.text ?: return
        val intent = Intent(this, ARActivity::class.java).apply {
            putExtra("marker_data", data)
        }
        startActivity(intent)
    }
}
