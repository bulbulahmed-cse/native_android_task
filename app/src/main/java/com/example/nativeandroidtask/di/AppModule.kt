package com.example.nativeandroidtask.di

import android.content.Context
import android.content.SharedPreferences
import com.example.nativeandroidtask.network.ApiClient
import com.example.nativeandroidtask.network.ApiInterface
import com.example.nativeandroidtask.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule (

    private var application: MyApplication
        ){

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }


    @Singleton
    @Provides
    fun provideApiInterface(): ApiInterface {
        return ApiClient.getClient()
    }

//
//    @Singleton
//    @Provides
//    fun provideSharedPreferences(): SharedPreferences {
//        return application.getSharedPreferences("BTP", Context.MODE_PRIVATE)
//    }
}