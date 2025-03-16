package com.tsci.sample

import app.cash.turbine.test
import com.tsci.MainDispatcherRule
import com.tsci.domain.NetworkError
import com.tsci.domain.NetworkErrorType
import com.tsci.domain.NetworkResult
import com.tsci.domain.sample.model.SampleDomainModel
import com.tsci.domain.sample.usecase.GetSampleUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SampleViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule() // Rule for TestCoroutineDispatcher

    private lateinit var viewModel: SampleViewModel

    @MockK
    private lateinit var getSampleUseCase: GetSampleUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SampleViewModel(getSampleUseCase)
    }

    @Test
    fun `getSample() updates sampleData on success`() = runTest {
        // Given
        val expectedName = "Test Name"
        val domainModel = NetworkResult.Success(SampleDomainModel(name = expectedName))
        coEvery { getSampleUseCase.getName() } returns domainModel

        // When
        viewModel.getSample()

        // Then
        viewModel.sampleData.test {
            assertEquals(expectedName, awaitItem().name)
        }
    }

    @Test
    fun `getSample() calls showError on failure`() = runTest {
        // Given
        val networkResult =
            NetworkResult.Error(NetworkError(type = NetworkErrorType.UNKNOWN)) // Mock NetworkError
        coEvery { getSampleUseCase.getName() } returns networkResult

        viewModel.showError.test {
            // When
            viewModel.getSample()
            // Then
            assertEquals(networkResult.error, awaitItem())
        }
    }
}
