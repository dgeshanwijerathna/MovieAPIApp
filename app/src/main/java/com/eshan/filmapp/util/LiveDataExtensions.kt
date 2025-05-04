package com.eshan.filmapp.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.LiveData

/**
 * Extension function that allows observing LiveData with an initial value in Compose.
 * This is useful when you want to ensure the state always has a value, even before
 * the LiveData emits its first value.
 *
 * @param initial The initial value to use if the LiveData hasn't emitted anything yet
 * @return A State object that updates when the LiveData changes
 */
@Composable
fun <T> LiveData<T>.observeAsState(initial: T): State<T> {
    val state = this.observeAsState()
    return remember {
        derivedStateOf { state.value ?: initial }
    }
}