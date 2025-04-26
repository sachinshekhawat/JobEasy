package com.example.jobeasy.data.repository

import android.content.Context
import com.example.jobeasy.data.db.JobDao
import com.example.jobeasy.data.db.JobDatabase
import com.example.jobeasy.data.model.Job
import com.example.jobeasy.data.network.JobApiService
import com.example.jobeasy.data.network.RetrofitInstance.api


class JobRepository(
    private val context: Context
) {

    private val jobDao: JobDao = JobDatabase.getDatabase(context).jobDao()
    suspend fun fetchJobs(page: Int) = api.getJobs(page)

    suspend fun saveJobs(jobs: List<Job>) {
        if (jobs.isNotEmpty()) {
            jobDao.insertAll(jobs)
        }
    }

    suspend fun insertJob(job: Job) {
        jobDao.insertJob(job)
    }

    suspend fun deleteJob(jobId: Int) {
        jobDao.deleteJob(jobId)
    }

    suspend fun bookmarkJob(job: Job) = jobDao.bookmarkJob(job)
    suspend fun deleteBookmark(job: Job) = jobDao.deleteBookmark(job)
    fun getBookmarkedJobs() = jobDao.getAllBookmarks()
}