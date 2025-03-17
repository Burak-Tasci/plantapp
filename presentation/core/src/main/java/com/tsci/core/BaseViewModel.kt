package com.tsci.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsci.domain.NetworkError
import com.tsci.domain.NetworkResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _showLoading = MutableStateFlow(false)
    val showLoading = _showLoading.asStateFlow()

    private val _showError = MutableSharedFlow<NetworkError>()
    val showError = _showError.asSharedFlow()

    suspend fun showError(error: NetworkError) {
        _showError.emit(error)
    }

    fun <T> request(
        request: suspend () -> NetworkResult<T>,
        onSuccess: suspend (T) -> Unit,
        onError: (suspend (NetworkError) -> Unit)? = null,
        showProgress: Boolean = true
    ) {
        viewModelScope.launch {
            if (showProgress) {
                //show progress
                _showLoading.emit(true)
            }
            val response = request()
            if (showProgress) {
                _showLoading.emit(false)
            }
            when (response) {
                is NetworkResult.Error -> {
                    onError?.invoke(response.error)
                }

                is NetworkResult.Success -> {
                    onSuccess(response.data)
                }
            }

        }
    }
}