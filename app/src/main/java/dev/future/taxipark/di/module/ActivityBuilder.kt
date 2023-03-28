package dev.future.taxipark.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.future.taxipark.main.LanguageActivity
import dev.future.taxipark.ui.splash.SplashActivity


@Module
abstract class ActivityBuilder {

//    @ContributesAndroidInjector
//    abstract fun contributesMainActivity(): MainActivity

//    @ContributesAndroidInjector
//    abstract fun contributesUserActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun contributesLanguageActivity(): LanguageActivity

    @ContributesAndroidInjector
    abstract fun contributesSplashActivity(): SplashActivity



}