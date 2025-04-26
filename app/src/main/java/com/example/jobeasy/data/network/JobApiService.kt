package com.example.jobeasy.data.network
import com.example.jobeasy.data.model.JobResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface JobApiService {
    @GET("common/jobs")
    suspend fun getJobs(@Query("page") page: Int): JobResponse //Response<List<Job>>
}