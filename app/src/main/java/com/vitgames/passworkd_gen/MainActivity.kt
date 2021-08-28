package com.vitgames.passworkd_gen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.vitgames.passworkd_gen.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    lateinit var appComponent: MainComponent

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        appComponent = DaggerMainComponent
            .builder()
            .mainModule(MainModule(this, this))
            .build()
        appComponent.inject(this)


        viewBinding.buttonGenerate.setOnClickListener {
            presenter.onGenerateClicked(
                hasNumbers = viewBinding.switchNumbers.isActivated,
                hasChars = viewBinding.switchChars.isActivated,
                hasCaps = viewBinding.switchCaps.isActivated,
                length = viewBinding.editLengthPass.text.toString().toInt()
            )
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

    override fun showProgress(visible: Boolean) {
        if (visible) {
            viewBinding.progressBar.visibility = View.VISIBLE
        } else {
            viewBinding.progressBar.visibility = View.GONE
        }
    }

    override fun showPassword(password: String) {
        viewBinding.textGeneratedPass.text = password
    }
}