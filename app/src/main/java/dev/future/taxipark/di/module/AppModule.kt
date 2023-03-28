package dev.future.taxipark.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dev.future.taxipark.main.MainApplication
import dev.future.taxipark.network.ApiService
import dev.future.taxipark.repository.Repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: MainApplication):Context =app

    @Provides
    @Singleton
    fun provideApplication(app: MainApplication): Application = app


    @Provides
    @Singleton
    @Named("IO")
    fun provideBackgroundDispatchers(): CoroutineDispatcher =
        Dispatchers.IO

    @Provides
    @Singleton
    @Named("MAIN")
    fun provideMainDispatchers(): CoroutineDispatcher =
        Dispatchers.Main

    @Provides
    @Singleton
    fun provideApiService(@Named("APISERVICE") retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService) = Repository(apiService)

}