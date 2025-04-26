package com.example.jobeasy.data.db


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jobeasy.data.model.Job

@Dao
interface JobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: Job)

    @Query("DELETE FROM jobs WHERE id = :jobId")
    suspend fun deleteJob(jobId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookmarkJob(job: Job)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(jobs: List<Job>)

    @Delete
    suspend fun deleteBookmark(job: Job)

    @Query("SELECT * FROM jobs WHERE is_bookmarked = 1")
    fun getAllBookmarks(): LiveData<List<Job>>
}