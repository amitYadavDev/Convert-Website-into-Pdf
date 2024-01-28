package amitapps.media.webviewtopdf

import amitapps.media.webviewtopdf.databinding.ActivityMainBinding
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding get() = _binding
    var printWeb: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding?.webViewMain?.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                // initializing the printWeb Object
                printWeb = view
            }
        })

        // loading the URL

        binding?.webViewMain?.loadUrl("https://www.google.com")

        savePDF()
    }

    private fun savePDF() {
        binding?.savePdfBtn?.setOnClickListener {
            
        }
    }
}