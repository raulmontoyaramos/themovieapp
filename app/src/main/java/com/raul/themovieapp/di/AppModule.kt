package com.raul.themovieapp.di

import android.app.Application
import androidx.room.Room
import com.raul.themovieapp.data.network.KtorNetworkService
import com.raul.themovieapp.domain.NetworkService
import com.raul.themovieapp.domain.database.TheMovieAppDatabase
import com.raul.themovieapp.presentation.PopularMoviesViewModelFactory
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.headers
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    fun providesHttpClient(): HttpClient =
        HttpClient(OkHttp) {
            install(createClientPlugin("auth") {
                onRequest { request, _ ->
                    request.headers {
                        append("accept", "application/json")
                        append(
                            "Authorization",
                            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzOTYwYjAwZTFkYzgyODliZWQ3ZTIwMTRlNzFjM2Q4OSIsIm5iZiI6MTczNzQ1MzMyNS42OTEsInN1YiI6IjY3OGY2ZjBkMDFhNzFhY2E1NGYwODA1NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.oxCXkutKzO5EAjEct02uTDyoet45GSRdHnOxFmXpcoE"
                        )
                    }.build()
                }
            })
        }

    @Provides
    @Singleton
    fun providesStarWarsDatabase(): TheMovieAppDatabase =
        Room.databaseBuilder(application, TheMovieAppDatabase::class.java, "movies_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesNetworkService(
        client: HttpClient
    ): NetworkService = KtorNetworkService(client)

    @Provides
    fun providesPopularMoviesViewModelFactory(
        networkService: NetworkService
    ): PopularMoviesViewModelFactory = PopularMoviesViewModelFactory(networkService)
}
