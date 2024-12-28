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

class MainViewModel() : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private val _credentials = MutableStateFlow(Credentials())
    val credentials = _credentials.asStateFlow()

    @SuppressLint("SetTextI18n")
    fun onSignInClick() {
        viewModelScope.launch {
            val request = credentials.value.request
//            var isRequestEmpty = request.isBlank()
//            if (request.length < 3) {
//                isRequestEmpty = false
//            } else {
//                isRequestEmpty = true
//            }
//            if (isRequestEmpty) {
//                _error.send("Request not valid")

//                _state.value = State.Stopping
//            } else {
                _state.value = State.Loading
                delay(2000)
                _state.value = State.Success
                _state.value = State.Error("По запросу " + request + " ничего не найдено!")
//            }
        }
    }
    fun stopClick(){
        viewModelScope.cancel()
    }
}