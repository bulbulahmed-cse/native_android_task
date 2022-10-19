package com.example.nativeandroidtask

import android.app.Application
import android.content.Context
import com.example.nativeandroidtask.di.AppModule
import com.example.nativeandroidtask.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class MyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var mInject: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        mInstance = this

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> {
        return mInject
    }

    companion object {
        lateinit var mInstance: MyApplication
        fun getContext(): Context {
            return mInstance.applicationContext
        }
    }


}