package amitapps.media.webviewtopdf

import amitapps.media.webviewtopdf.databinding.ActivityMainBinding
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintJob
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding get() = _binding
    var printWeb: WebView? = null
    var printBtnPressed = false
    var printJob: PrintJob? = null
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
            if (printWeb != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    PrintTheWebPage()
                } else {
                    Toast.makeText(
                        this,
                        "Not available for device below Android LOLLIPOP",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            } else {
                Toast.makeText(this, "WebPage not fully loaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun PrintTheWebPage() {
        // set printBtnPressed true
        printBtnPressed = true

        // Creating  PrintManager instance
        var printManager = this.getSystemService(Context.PRINT_SERVICE) as PrintManager

        // setting the name of job
        var jobName = getString(R.string.app_name) + " webpage" + printWeb?.getUrl();

        // Creating  PrintDocumentAdapter instance
        var printAdapter: PrintDocumentAdapter = printWeb!!.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        printJob = printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())

    }

    override fun onResume() {
        super.onResume()
        if (printJob != null && printBtnPressed) {
            if (printJob!!.isCompleted()) {
                // Showing Toast Message
                Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show()
            } else if (printJob!!.isStarted()) {
                // Showing Toast Message
                Toast.makeText(this, "isStarted", Toast.LENGTH_SHORT).show()
            } else if (printJob!!.isBlocked()) {
                // Showing Toast Message
                Toast.makeText(this, "isBlocked", Toast.LENGTH_SHORT).show()
            } else if (printJob!!.isCancelled()) {
                // Showing Toast Message
                Toast.makeText(this, "isCancelled", Toast.LENGTH_SHORT).show()
            } else if (printJob!!.isFailed()) {
                // Showing Toast Message
                Toast.makeText(this, "isFailed", Toast.LENGTH_SHORT).show()
            } else if (printJob!!.isQueued()) {
                // Showing Toast Message
                Toast.makeText(this, "isQueued", Toast.LENGTH_SHORT).show()
            }
            // set printBtnPressed false
            printBtnPressed = false
        }
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
    }
}
