package com.vitgames.passworkd_gen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vitgames.passworkd_gen.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    lateinit var appComponent: MainComponent

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = viewBinding.root
        setContentView(view)

        appComponent = DaggerMainComponent
            .builder()
            .mainModule(MainModule(this, this))
            .build()
        appComponent.inject(this)

        viewBinding.buttonGenerate.setOnClickListener {
            val length = viewBinding.editLengthPass.text
            if (length.isNotEmpty()) {
                presenter.onGenerateClicked(
                    hasNumbers = viewBinding.switchNumbers.isChecked,
                    hasChars = viewBinding.switchChars.isChecked,
                    hasCaps = viewBinding.switchCaps.isChecked,
                    length = length.toString().toInt()
                )
            } else {
                showToast(getString(R.string.activity_toast_error_string_empty))
            }
        }
    }

    override fun onStart() {
        presenter.onEnterScreen()
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onExitScreen()
    }

    override fun showPassword(password: String) {
        viewBinding.textGeneratedPass.text = password
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showNetworkErrorAlertDialog() {
        val networkErrorAlert = AlertDialog.Builder(this)
        networkErrorAlert.setTitle(getString(R.string.activity_alert_network_title))
            .setMessage(getString(R.string.activity_alert_network_msg))
            .setIcon(R.drawable.ic_wifi_off)
            .setPositiveButton(getString(R.string.activity_alert_network_settings)) { _, _ ->
                presenter.onOpenSettingsClicked()
            }
            .setNegativeButton(getString(R.string.activity_alert_network_close)) { dialog, _ ->
                dialog.cancel()
            }
        networkErrorAlert
            .create()
            .show()
    }
}