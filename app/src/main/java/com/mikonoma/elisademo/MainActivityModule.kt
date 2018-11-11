package com.mikonoma.elisademo

import dagger.Module
import dagger.Subcomponent

@Module
class MainActivityModule(val activity: MainActivity) {
}

@Subcomponent(modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}