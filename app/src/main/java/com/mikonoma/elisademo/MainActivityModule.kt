package com.mikonoma.elisademo

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainActivityModule(val activity: MainActivity) {

    @Provides
    fun provideMainActivity(): MainActivity = activity

}

@Subcomponent(modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}