package com.example.jobeasy.bookmarks

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobeasy.R
import com.example.jobeasy.data.model.Job
import com.example.jobeasy.data.repository.JobRepository
import com.example.jobeasy.domain.usecase.FetchJobsUseCase
import com.example.jobeasy.jobdetail.JobDetailBottomSheet
import com.example.jobeasy.jobs.JobAdapter
import com.example.jobeasy.jobs.JobsViewModel
import com.example.jobeasy.jobs.JobsViewModelFactory

class BookmarksFragment : Fragment() {

    private lateinit var viewModel: JobsViewModel
    private lateinit var jobAdapter: JobAdapter
    private val bookmarkedJobs = mutableListOf<Job>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel using the ViewModelFactory
        viewModel = ViewModelProvider(
            requireActivity(),
            JobsViewModelFactory(requireActivity().application, FetchJobsUseCase(JobRepository(requireContext())))
        ).get(JobsViewModel::class.java)

        // Setup RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvBookmarkedJobs)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Handle item click to show Job details in Bottom Sheet
        jobAdapter = JobAdapter(bookmarkedJobs) { job ->
            val bottomSheet = JobDetailBottomSheet(job)
            bottomSheet.show(requireActivity().supportFragmentManager, "JobDetailBottomSheet")
        }

        recyclerView.adapter = jobAdapter

        val noBookmarksTextView = view.findViewById<TextView>(R.id.tvNoBookmarks)
        // Observe Bookmarked Jobs
        viewModel.getBookmarkedJobs().observe(viewLifecycleOwner) { jobs ->
            Log.d("BookmarksFragment", "Bookmarked jobs size: ${jobs.size}")
            bookmarkedJobs.clear()
            bookmarkedJobs.addAll(jobs)
            jobAdapter.notifyDataSetChanged()

            // Show the "No Jobs Bookmarked Yet" message if no jobs are bookmarked
            if (bookmarkedJobs.isEmpty()) {
                noBookmarksTextView.visibility = View.VISIBLE
            } else {
                noBookmarksTextView.visibility = View.GONE
            }
        }

        // Fetch Bookmarked Jobs
        viewModel.getBookmarkedJobs()
        Log.d("BookmarksFragment", "Saving jobs: ${bookmarkedJobs}")



    }
}
