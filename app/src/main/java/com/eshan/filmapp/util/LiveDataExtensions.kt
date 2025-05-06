package com.eshan.filmapp.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.LiveData

@Composable
fun <T> LiveData<T>.observeAsState(initial: T): State<T> {
    val state = this.observeAsState()
    return remember {
        derivedStateOf { state.value ?: initial }
    }
}