package com.raul.themovieapp

import android.app.Application
import com.raul.themovieapp.di.AppComponent
import com.raul.themovieapp.di.AppModule
import com.raul.themovieapp.di.DaggerAppComponent

class TheMovieApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
