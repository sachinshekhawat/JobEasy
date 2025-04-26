package com.example.jobeasy.domain.usecase

import com.example.jobeasy.data.repository.JobRepository

class GetBookmarksUseCase(private val repository: JobRepository) {
    operator fun invoke() = repository.getBookmarkedJobs()
}