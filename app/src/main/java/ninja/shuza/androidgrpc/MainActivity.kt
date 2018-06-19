package ninja.shuza.androidgrpc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_grpc_call.onClick { getDataFromServer() }
    }

    private fun getDataFromServer() {
        tv_result.text = "calling..."
    }
}
