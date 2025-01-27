package com.raul.themovieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun viewModelFactory(create: () -> ViewModel): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = create() as T
    }
}
