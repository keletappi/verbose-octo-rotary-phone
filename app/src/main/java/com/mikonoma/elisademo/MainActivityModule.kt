package com.mikonoma.elisademo

import androidx.lifecycle.ViewModelProviders
import com.mikonoma.elisademo.model.ResponseViewModel
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainActivityModule(val activity: MainActivity) {

    @Provides
    fun provideMainActivity(): MainActivity = activity

    @Provides
    fun provideResponseViewModel(): ResponseViewModel {
        return ViewModelProviders.of(activity)
            .get(ResponseViewModel::class.java)
    }

}

@Subcomponent(modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}