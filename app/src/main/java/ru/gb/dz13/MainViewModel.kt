package ru.gb.dz13

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class MainViewModel() : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private val _credentials = MutableStateFlow(Credentials())
    val credentials = _credentials.asStateFlow()

    var job = viewModelScope

    @SuppressLint("SetTextI18n")
    fun onSignInClick() {
        job.launch {
            yield()
            _state.value = State.Loading
            delay(5000)
            _state.value = State.Success
            val request = credentials.value.request
            if (request.length > 3) _state.value =
                State.Error("По запросу " + request + " ничего не найдено!")
            credentials.value.streams = true
        }
    }

    fun stopClick() {
//        job.cancel()              //Отменить текущий поток
        _state.value = State.Success
        _state.value = State.Error("")
        if (credentials.value.streams) onSignInClick()
    }
}