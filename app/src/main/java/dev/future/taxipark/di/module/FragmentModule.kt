package dev.future.taxipark.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.future.taxipark.ui.SplashFragment
import dev.future.taxipark.ui.detailes.balanse.BalansYechFragment
import dev.future.taxipark.ui.detailes.bonus.BonusDriversFragment
import dev.future.taxipark.ui.detailes.bonus.BonusYechFragment
import dev.future.taxipark.ui.detailes.brigada.AddBrigadaFragment
import dev.future.taxipark.ui.detailes.history.OrderHistoryDetailesFragment
import dev.future.taxipark.ui.detailes.rekvizit.AddCardFragment
import dev.future.taxipark.ui.drawer.*
import dev.future.taxipark.ui.registeration.ui.PinView.PinActiveFragment
import dev.future.taxipark.ui.registeration.ui.PinView.SavePinFragment
import dev.future.taxipark.ui.registeration.ui.access.AccessPhoneFragment
import dev.future.taxipark.ui.registeration.ui.kantakt.KantaktFragment
import dev.future.taxipark.ui.registeration.ui.register.filtterRegister.RegisterFilterFragment
import dev.future.taxipark.ui.registeration.ui.register.registerCode.RegisterCodeFragment


import dev.future.taxipark.ui.registeration.ui.register.registerPhone.RegisterPhoneFragment
import dev.future.taxipark.ui.registeration.ui.successAndError.ErrorFragment
import dev.future.taxipark.ui.registeration.ui.successAndError.SecessMoneyFragment
import dev.future.taxipark.ui.registeration.ui.successAndError.SuccessFragment


@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeRegisterPhoneFragment(): RegisterPhoneFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterCodeFragment(): RegisterCodeFragment

//    @ContributesAndroidInjector
//    abstract fun contributeRegisterFullFragment(): RegisterFullFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterFilterFragment(): RegisterFilterFragment

    @ContributesAndroidInjector
    abstract fun contributeErrorFragment(): ErrorFragment

    @ContributesAndroidInjector
    abstract fun contributeSuccessFragment(): SuccessFragment

    @ContributesAndroidInjector
    abstract fun contributePinActiveFragment(): PinActiveFragment

    @ContributesAndroidInjector
    abstract fun contributeSavePinFragment(): SavePinFragment

    @ContributesAndroidInjector
    abstract fun contributeKantaktFragment(): KantaktFragment

    @ContributesAndroidInjector
    abstract fun contributeAccessPhoneFragment(): AccessPhoneFragment

    @ContributesAndroidInjector
    abstract fun contributeSalansFragment(): BalansFragment

    @ContributesAndroidInjector
    abstract fun contributeBonusesFragment(): BonusesFragment

    @ContributesAndroidInjector
    abstract fun contributeKantaktiFragment(): KantaktiFragment

    @ContributesAndroidInjector
    abstract fun contributeMoyDannixFragment(): MoyDannixFragment

    @ContributesAndroidInjector
    abstract fun contributeMyBrigadeFragment(): MyBrigadeFragment

    @ContributesAndroidInjector
    abstract fun contributeNavigationFragment(): NavigationFragment

    @ContributesAndroidInjector
    abstract fun contributeRekvizitlarFragment(): RekvizitlarFragment

    @ContributesAndroidInjector
    abstract fun contributeRequisiteFragment(): RequisiteFragment

    @ContributesAndroidInjector
    abstract fun contributeWithdrawalRequestsFragment():WithdrawalRequestsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddCardFragment():AddCardFragment

    @ContributesAndroidInjector
    abstract fun contractBalansYechFragment():BalansYechFragment

    @ContributesAndroidInjector
    abstract fun contractSecessMoneyFragment():SecessMoneyFragment

    @ContributesAndroidInjector
    abstract fun orderBonusDriversFragment(): BonusDriversFragment

    @ContributesAndroidInjector
    abstract fun contributeBonusYechFragment():BonusYechFragment

    @ContributesAndroidInjector
    abstract fun contributeHitoryTravelFragment(): HitoryTravelFragment

    @ContributesAndroidInjector
    abstract fun contributeOrderHistoryDetailesFragment(): OrderHistoryDetailesFragment

    @ContributesAndroidInjector
    abstract fun AddBrigadaFragment(): AddBrigadaFragment

    @ContributesAndroidInjector
    abstract fun historySpashFragment(): SplashFragment

//
//    @ContributesAndroidInjector
//    abstract fun odersDetailesFragment(): OdersDetailesFragment
//
////    @ContributesAndroidInjector
////    abstract fun onMyWayFragment(): OnMyWayFragment
//
//    @ContributesAndroidInjector
//    abstract fun filterFragment(): FilterFragment
//
//    @ContributesAndroidInjector
//    abstract fun unFilterFragment(): UnFilterFragment

/*

    @ContributesAndroidInjector
    abstract fun contributesConfirmFragment(): ConfirmNumberFragment

    @ContributesAndroidInjector
    abstract fun contributesCarInfoAboutFragment(): CarAboutInfoFragment

    @ContributesAndroidInjector
    abstract fun contributesDirectionFragment(): DirectionFragment

    @ContributesAndroidInjector
    abstract fun contributesSetOrderFragment(): SetOrderFragment

    @ContributesAndroidInjector
    abstract fun contributerSetMapFragment():SearchDirectionFragment

    @ContributesAndroidInjector
    abstract fun  contributerSetProfilEditFragment():ProfilEditFragment

    @ContributesAndroidInjector
    abstract fun  contributerSetOrderEditFragment():OrderHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributerMessageFragment(): MessageFragment

    @ContributesAndroidInjector
    abstract  fun contributerOrderMapYandex():OrderYandexMapFragment*/
}