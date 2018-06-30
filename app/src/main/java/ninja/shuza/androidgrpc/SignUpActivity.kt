package ninja.shuza.androidgrpc

import android.os.Bundle
import com.example.learn.SignUpRequest
import com.example.learn.SignUpResponse
import com.example.learn.SignUpServiceGrpc
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * :=  created by:  Shuza
 * :=  create date:  28-Jun-18
 * :=  (C) CopyRight Shuza
 * :=  www.shuza.ninja
 * :=  shuza.sa@gmail.com
 * :=  Fun  :  Coffee  :  Code
 *
 **/
class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_sign_up.onClick {
            requestForSignUp(et_name.text.toString(),
                    et_username.text.toString(),
                    et_passwrod.text.toString())
        }
    }

    private fun requestForSignUp(name: String, username: String, password: String) {
        if (name.isEmpty() or username.isEmpty() or password.isEmpty()) {
            showWarningMessage("Invalid Data")
            return
        }

        val signUpService = SignUpServiceGrpc.newBlockingStub(connectionChannel)

        val requestMessage = SignUpRequest.newBuilder()
                .setName(name)
                .setUsername(username)
                .setPassword(password)
                .build()

        showLoadingDialog()

        Single.fromCallable { signUpService.signUp(requestMessage) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<SignUpResponse> {
                    override fun onSuccess(response: SignUpResponse) {
                        dismissLoadingDialog()
                        if (response.responseCode == HttpResponseCode.SUCCESS) {
                            showMessage("Sign Up Successfully")
                            finish()
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
}