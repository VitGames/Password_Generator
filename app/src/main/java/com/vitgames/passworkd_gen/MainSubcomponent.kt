package com.vitgames.passworkd_gen

import android.content.Context
import com.vitgames.passworkd_gen.utils.*
import dagger.Component
import dagger.Module
import dagger.Provides
import rx.subscriptions.CompositeSubscription


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

    @Provides
    fun provideCompositeSubscription(): CompositeSubscription = CompositeSubscription()

    @Provides
    fun provideNetworkCheckManager(impl: NetworkCheckManagerImpl): NetworkCheckManager = impl

    @Provides
    fun provideNavigator(impl: AppNavigatorImpl): AppNavigator = impl
}