package com.raul.themovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.imageLoader
import com.raul.themovieapp.data.network.KtorNetworkService
import com.raul.themovieapp.presentation.PopularMoviesViewModel
import com.raul.themovieapp.presentation.viewModelFactory
import com.raul.themovieapp.screens.PopularMoviesScreen
import com.raul.themovieapp.ui.theme.TheMovieAppTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.headers
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity() {

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .addHeader(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzOTYwYjAwZTFkYzgyODliZWQ3ZTIwMTRlNzFjM2Q4OSIsIm5iZiI6MTczNzQ1MzMyNS42OTEsInN1YiI6IjY3OGY2ZjBkMDFhNzFhY2E1NGYwODA1NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.oxCXkutKzO5EAjEct02uTDyoet45GSRdHnOxFmXpcoE"
                ).build()
            chain.proceed(newRequest)
        }
    }.build()

    private val networkService = KtorNetworkService(
        client = HttpClient(OkHttp) {
            install(createClientPlugin("auth") {
                onRequest { request, content ->
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
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheMovieAppTheme {
                val imageLoader =
                    LocalContext.current.imageLoader.newBuilder().okHttpClient(okHttpClient).build()
                val viewModel =
                    viewModel<PopularMoviesViewModel>(factory = viewModelFactory {
                        PopularMoviesViewModel(
                            networkService = networkService
                        )
                    })
                val viewState = viewModel.viewState.collectAsState().value
                PopularMoviesScreen(viewState, imageLoader)
            }
        }
    }
}
