package com.example.nativeandroidtask.di

import com.example.nativeandroidtask.MyApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        FragmentModule::class,
        AppModule::class

    ]
)
interface AppComponent {
    fun inject(application: MyApplication)
}