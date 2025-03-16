package com.tsci.sample

import com.tsci.core.BaseViewModel
import com.tsci.domain.sample.usecase.GetSampleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val getSampleUseCase: GetSampleUseCase
) : BaseViewModel() {

    private val _sampleData = MutableStateFlow(SampleUiModel())
    val sampleData = _sampleData.asStateFlow()

    fun getSample() {

        request(
            request = {
                getSampleUseCase.getName()
            },
            onSuccess = { domainModel ->
                //success
                _sampleData.update {
                    it.copy(name = domainModel.name)
                }
            },
            onError = {
                //error
                showError(it)
            }
        )

    }
}