package ninja.shuza.androidgrpc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dmax.dialog.SpotsDialog
import io.grpc.ManagedChannel
import io.grpc.okhttp.OkHttpChannelBuilder
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

/**
 *
 * :=  created by:  Shuza
 * :=  create date:  27-Jun-18
 * :=  (C) CopyRight Shuza
 * :=  www.shuza.ninja
 * :=  shuza.sa@gmail.com
 * :=  Fun  :  Coffee  :  Code
 *
 **/
open class BaseActivity : AppCompatActivity() {
    private var dialog: SpotsDialog by Delegates.notNull()

    val connectionChannel: ManagedChannel by lazy {
        OkHttpChannelBuilder.forAddress("192.168.0.102", 8080)
                .usePlaintext()
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog = SpotsDialog(this, R.style.LoadingDialog)
        dialog.setCancelable(false)
    }

    fun showLoadingDialog() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    fun dismissLoadingDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    fun showErrorMessage(message: String) {
        toast(message)
    }

    fun showMessage(message: String) {
        toast(message)
    }

    fun showWarningMessage(message: String) {
        toast(message)
    }
}