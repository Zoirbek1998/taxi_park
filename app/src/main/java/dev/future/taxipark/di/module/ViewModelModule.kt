package dev.future.taxipark.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.future.taxipark.di.ViewModelKey

import dev.future.taxipark.di.factory.ViewModelFactory
import dev.future.taxipark.ui.auth.viewmodel.AuthViewmodel
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.viewmodel.MainViewModel
import dev.future.taxipark.utils.viewmodel.SharedViewModel

/*
* created by Asrorjon
*/
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun providesPlayerViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SharedViewModel::class)
    internal abstract fun providesSharedViewModel(viewModel: SharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    internal abstract fun providesOrderViewModel(viewModel: OrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewmodel::class)
    internal abstract fun providesAuthViewModel(viewModel: AuthViewmodel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(CabViewModel::class)
//    internal abstract fun providescabViewModel(viewModel: CabViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(StatisticViewModel::class)
//    internal abstract fun providesStatisticViewModel(viewModel: StatisticViewModel): ViewModel




    /*@Binds
    @IntoMap
    @ViewModelKey(LocationUpdateViewModel::class)
    internal abstract fun providesLocationUpdateViewModel(viewModel: LocationUpdateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    internal abstract fun providesauthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    internal abstract fun providesuserViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchDriverViewModel::class)
    internal abstract fun providessearchDriverModel(viewModel: SearchDriverViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    internal abstract fun providechatViewmodel(viewModel: ChatViewModel): ViewModel
*/



}