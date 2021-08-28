package com.vitgames.passworkd_gen

import android.content.Context
import retrofit2.Call
import com.vitgames.passworkd_gen.api.ApiInteractor
import com.vitgames.passworkd_gen.api.PasswordModel
import com.vitgames.passworkd_gen.utils.Logger
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface MainView {
    fun showProgress(visible: Boolean)
    fun showPassword(password: String)
}

interface MainPresenter {
    fun onEnterScreen()
    fun onExitScreen()
    fun onGenerateClicked(hasNumbers: Boolean, hasChars: Boolean, hasCaps: Boolean, length: Int)
}

class MainPresenterImpl @Inject constructor(
    private val context: Context,
    private val view: MainView,
    private val logger: Logger
) : MainPresenter {
    override fun onEnterScreen() {

    }

    override fun onExitScreen() {

    }

    override fun onGenerateClicked(
        hasNumbers: Boolean,
        hasChars: Boolean,
        hasCaps: Boolean,
        length: Int
    ) {
        val api = ApiInteractor().getClient().create(ApiInteractor.ApiInterface::class.java)
        val callback: Call<PasswordModel> = api.getPassword(
            hasNum = false,
            hasChar = false,
            hasCaps = false,
            passLenght = 14
        )
        callback.enqueue(object : Callback<PasswordModel> {
            override fun onResponse(
                call: Call<PasswordModel>,
                response: Response<PasswordModel>
            ) {
                val data: PasswordModel = response.body()
                logger.printMessage(response.isSuccessful.toString())
                logger.printMessage(data.getPassword())
            }

            override fun onFailure(call: Call<PasswordModel>, t: Throwable?) {

            }
        }
        )
    }


}