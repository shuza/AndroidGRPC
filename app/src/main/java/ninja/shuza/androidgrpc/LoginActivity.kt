package ninja.shuza.androidgrpc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.learn.*
import io.grpc.okhttp.OkHttpChannelBuilder
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

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

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.onClick {
            requestForLogin(et_username.text.toString(), et_password.text.toString())
        }

        tv_sign_up.onClick { startSignUpActivity() }
    }

    private fun requestForLogin(username: String, password: String) {
        if (username.isEmpty() or password.isEmpty()) {
            showWarningMessage("Invalid Data")
            return
        }

        val loginService = LoginServiceGrpc.newBlockingStub(connectionChannel)

        val requestMessage = LoginRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build()

        showLoadingDialog()

        Single.fromCallable { loginService.logIn(requestMessage) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<LoginResponse> {
                    override fun onSuccess(response: LoginResponse) {
                        dismissLoadingDialog()
                        if (response.responseCode == HttpResponseCode.SUCCESS) {
                            startWelcomeActivity()
                        } else {
                            showErrorMessage(response.message)
                        }
                    }

                    override fun onSubscribe(d: Disposable) {}

                    override fun onError(e: Throwable) {
                        dismissLoadingDialog()
                        showErrorMessage("Error:  ${e.message}")
                    }
                })
    }

    private fun startWelcomeActivity() {
        startActivity(Intent(this, WelcomeActivity::class.java))
    }

    private fun startSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun getDataFromServer() {
        val channel = OkHttpChannelBuilder.forAddress("192.168.0.102", 8080)
                .usePlaintext()
                .build()
        val stub = HelloServiceGrpc.newBlockingStub(channel)

        val requestMessage = HelloRequest.newBuilder()
                .setName("Shuza")
                .setAge(23)
                .setSentiment(Sentiment.HAPPY)
                .build()

        Single.fromCallable { stub.greet(requestMessage) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<HelloResponse> {
                    override fun onSuccess(t: HelloResponse) {

                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("shuza", "on      ====/        subscribe")
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }
}
