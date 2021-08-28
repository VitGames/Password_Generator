package com.vitgames.passworkd_gen

import android.content.Context
import com.vitgames.passworkd_gen.utils.Logger
import com.vitgames.passworkd_gen.utils.LoggerImpl
import dagger.Component
import dagger.Module
import dagger.Provides


@Component(modules = [MainModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)
}

@Module
class MainModule(private val view: MainView, private val context: Context) {

    @Provides
    fun providePresenter(presenterImpl: MainPresenterImpl): MainPresenter = presenterImpl

    @Provides
    fun provideView(): MainView = view

    @Provides
    fun provideLogger(loggerImpl: LoggerImpl): Logger = loggerImpl

    @Provides
    fun provideContext(): Context = context
}