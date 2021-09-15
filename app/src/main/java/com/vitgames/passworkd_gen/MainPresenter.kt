package com.vitgames.passworkd_gen

import android.content.Context
import retrofit2.Call
import com.vitgames.passworkd_gen.api.ApiInteractor
import com.vitgames.passworkd_gen.utils.NetworkCheckManager
import com.vitgames.passworkd_gen.api.PasswordModel
import com.vitgames.passworkd_gen.utils.AppNavigationInput
import com.vitgames.passworkd_gen.utils.AppNavigator
import com.vitgames.passworkd_gen.utils.Logger
import retrofit2.Callback
import retrofit2.Response
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

interface MainView {
    fun showPassword(password: String)
    fun showToast(message: String)
    fun showNetworkErrorAlertDialog()
}

interface MainPresenter {
    fun onEnterScreen()
    fun onExitScreen()
    fun onGenerateClicked(hasNumbers: Boolean, hasChars: Boolean, hasCaps: Boolean, length: Int)
    fun onOpenSettingsClicked()
}

class MainPresenterImpl @Inject constructor(
    private val context: Context,
    private val view: MainView,
    private val initViewSubscription: CompositeSubscription,
    private val networkCheckManager: NetworkCheckManager,
    private val navigator: AppNavigator,
    private val logger: Logger
) : MainPresenter {
    // TODO check length pass
    override fun onEnterScreen() {
        if (!networkCheckManager.isNetworkEnabled()) {
            view.showNetworkErrorAlertDialog()
        }
    }

    override fun onExitScreen() {
        initViewSubscription.clear()
    }

    override fun onGenerateClicked(
        hasNumbers: Boolean,
        hasChars: Boolean,
        hasCaps: Boolean,
        length: Int
    ) {
        if (!networkCheckManager.isNetworkEnabled()) {
            view.showNetworkErrorAlertDialog()
        } else {
            generatePassword(hasNumbers, hasChars, hasCaps, length)
        }
    }

    override fun onOpenSettingsClicked() {
        navigator.navigateTo(AppNavigationInput.Settings)
    }

    private fun generatePassword(
        hasNumbers: Boolean,
        hasChars: Boolean,
        hasCaps: Boolean,
        length: Int
    ) {
        val api = ApiInteractor().getClient().create(ApiInteractor.ApiInterface::class.java)
        val callback: Call<PasswordModel> = api.getPassword(
            hasNum = hasNumbers,
            hasChar = hasChars,
            hasCaps = hasCaps,
            passLength = length
        )
        callback.enqueue(object : Callback<PasswordModel> {
            override fun onResponse(call: Call<PasswordModel>, response: Response<PasswordModel>
            ) {
                val password = response.body().getPassword()
                view.showPassword(password)
                logger.printMessage("Response is:" + response.isSuccessful.toString() + password)
            }

            override fun onFailure(call: Call<PasswordModel>, t: Throwable?) {
                view.showToast(context.getString(R.string.activity_toast_error_string_network))
                logger.printError("ERROR to callback response: $t")
            }
        }
        )
    }
}