package com.example.jobeasy.domain.usecase

import com.example.jobeasy.data.model.Job
import com.example.jobeasy.data.model.JobResponse
import com.example.jobeasy.data.repository.JobRepository


class FetchJobsUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(page: Int): JobResponse {
        return repository.fetchJobs(page)
    }
}