package com.example.jobeasy.domain.usecase

import com.example.jobeasy.data.model.Job
import com.example.jobeasy.data.repository.JobRepository


class BookmarkJobUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(job: Job) {
        repository.bookmarkJob(job)
    }
}