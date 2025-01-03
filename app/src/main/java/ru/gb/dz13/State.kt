package ru.gb.dz13

sealed class State(
    val isLoading: Boolean = false,
    open val requestError: String? = null
) {
    object Loading : State(isLoading = true)
    object Success : State()
    data class Error(
        override val requestError: String?
    ) : State(
        requestError = requestError
    )
}