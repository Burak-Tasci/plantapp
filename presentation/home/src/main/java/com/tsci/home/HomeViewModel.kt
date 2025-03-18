package com.tsci.home

import com.tsci.core.BaseViewModel
import com.tsci.domain.plants.usecase.IGetCategoriesUseCase
import com.tsci.domain.plants.usecase.IGetQuestionsUseCase
import com.tsci.home.model.CategoryUiModel
import com.tsci.home.model.QuestionUiModel
import com.tsci.home.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getQuestionsUseCase: IGetQuestionsUseCase,
    private val getCategoriesUseCase: IGetCategoriesUseCase
) : BaseViewModel() {


    private val _questions = MutableStateFlow<List<QuestionUiModel>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryUiModel>>(emptyList())
    val categories = _categories.asStateFlow()


    init {
        getQuestions()
        getCategories()
    }

    private fun getQuestions() {
        request(
            request = { getQuestionsUseCase.get() },
            onSuccess = { questionDomainList ->
                _questions.value = questionDomainList.map {
                    it.toUiModel()
                }
            },
            onError = {
                showError(it)
            }
        )
    }

    private fun getCategories() {
        request(
            request = { getCategoriesUseCase.get() },
            onSuccess = { categoryDomainList ->
                _categories.value = categoryDomainList.map {
                    it.toUiModel()
                }
            },
            onError = {
                showError(it)
            }
        )
    }
}