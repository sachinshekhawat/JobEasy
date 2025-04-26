package com.example.jobeasy.jobs

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobeasy.domain.usecase.FetchJobsUseCase

class JobsViewModelFactory(
    private val application: Application,
    private val fetchJobsUseCase: FetchJobsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobsViewModel::class.java)) {
            return JobsViewModel(application,fetchJobsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}