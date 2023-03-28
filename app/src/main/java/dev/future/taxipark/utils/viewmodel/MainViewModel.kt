package dev.future.taxipark.utils.viewmodel


import dev.future.taxipark.base.BaseViewModel
import dev.future.taxipark.main.MainApplication
import dev.future.taxipark.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class MainViewModel @Inject constructor(application: MainApplication,
                                        private val repository: Repository,
                                        @Named("IO") private val io: CoroutineDispatcher,
                                        @Named("MAIN") private val main: CoroutineDispatcher): BaseViewModel(application) {



}