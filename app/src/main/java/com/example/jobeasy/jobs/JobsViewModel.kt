package com.example.jobeasy.jobs

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.jobeasy.data.db.JobDatabase
import com.example.jobeasy.data.model.Job
import com.example.jobeasy.data.repository.JobRepository
import com.example.jobeasy.domain.usecase.FetchJobsUseCase
import com.example.jobeasy.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JobsViewModel(application: Application, private val fetchJobsUseCase: FetchJobsUseCase) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val jobRepository: JobRepository = JobRepository(context)

    private val _jobs = MutableLiveData<Resource<List<Job>>>()
    val jobs: LiveData<Resource<List<Job>>> = _jobs

    private var currentPage = 1
    private var isFetching = false

    // Fetch Jobs
    fun fetchJobs() {
        if (isFetching) return
        isFetching = true
        _jobs.postValue(Resource.Loading())

        viewModelScope.launch {
            try {
                val response = fetchJobsUseCase(currentPage)
                val newJobs = response.results

//                // Filter and save only bookmarked jobs
//                val bookmarkedJobs = newJobs.filter { it.is_bookmarked }
//                Log.d("ViewModel", "Saving Bookjobs: ${bookmarkedJobs}")
//                if (bookmarkedJobs.isNotEmpty()) {
//                    jobRepository.saveJobs(bookmarkedJobs)  // Save bookmarked jobs to Room
//                }

                _jobs.postValue(Resource.Success(newJobs))
                currentPage++
            } catch (e: Exception) {
                _jobs.postValue(Resource.Error("Failed to load jobs: ${e.message}"))
            } finally {
                isFetching = false
            }
        }
    }

    fun insertJob(job: Job) {
        viewModelScope.launch {
            jobRepository.insertJob(job)
        }
    }

    fun deleteJob(jobId: Int) {
        viewModelScope.launch {
            jobRepository.deleteJob(jobId)
        }
    }

    // Get Bookmarked Jobs
    fun getBookmarkedJobs(): LiveData<List<Job>> {
        return jobRepository.getBookmarkedJobs()
    }
}
