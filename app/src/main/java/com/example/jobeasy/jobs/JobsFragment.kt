package com.example.jobeasy.jobs

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobeasy.R
import com.example.jobeasy.data.db.JobDatabase
import com.example.jobeasy.data.model.Job
import com.example.jobeasy.data.network.RetrofitInstance
import com.example.jobeasy.data.repository.JobRepository
import com.example.jobeasy.domain.usecase.FetchJobsUseCase
import com.example.jobeasy.jobdetail.JobDetailBottomSheet
import com.example.jobeasy.utils.Resource
import com.example.jobeasy.utils.setupInfiniteScroll
import java.util.Locale

class JobsFragment : Fragment() {

    private lateinit var viewModel: JobsViewModel
    private lateinit var jobAdapter: JobAdapter
    private val allJobs = mutableListOf<Job>() // This stores all jobs permanently
    private var jobs = mutableListOf<Job>()    // This is what you show in RecyclerView

    //private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }

    @SuppressLint("NotifyDataSetChanged", "RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val fetchJobsUseCase = FetchJobsUseCase(JobRepository(requireContext()))
        // Initialize ViewModel using ViewModelFactory
        viewModel = ViewModelProvider(
            this,
            JobsViewModelFactory(requireActivity().application, fetchJobsUseCase)
        ).get(JobsViewModel::class.java)

        // Setup RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvJobs)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val searchView = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)
        val emptyState = view.findViewById<TextView>(R.id.tvEmptyState)
        val loadingText = view.findViewById<TextView>(R.id.loadingText)

        jobAdapter = JobAdapter(jobs) { job ->
            val bottomSheet = JobDetailBottomSheet(job)
            bottomSheet.show((context as AppCompatActivity).supportFragmentManager, "JobDetailBottomSheet")
        }

        recyclerView.apply {
            adapter = jobAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            setupInfiniteScroll {
                viewModel.fetchJobs()
            }
        }


        viewModel.jobs.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
//                    if (currentPage == 1) {
//
//                    }
                    progressBar.visibility = View.VISIBLE
                    loadingText.visibility = View.VISIBLE
                    loadingText.text = "Loading jobs, please wait..."
                    emptyState.visibility = View.GONE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    loadingText.visibility = View.GONE
                    resource.data?.let { newJobs ->
//                        if (currentPage == 1) {
////                            jobs.clear()
//                        }
                        jobs.addAll(newJobs)
                        if (jobs.isNotEmpty()) {
                             // Always store the full list
                            recyclerView.post {  // 💥 This line is the magic
                                jobAdapter.updateList(jobs)
                            }
                            recyclerView.visibility = View.VISIBLE
                            emptyState.visibility = View.GONE
                        } else {
                            recyclerView.visibility = View.GONE
                            emptyState.visibility = View.VISIBLE
                            emptyState.text = "No Jobs Found!"
                        }
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    loadingText.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message ?: "Error loading jobs", Toast.LENGTH_SHORT).show()

                    if (jobs.isEmpty()) {
                        recyclerView.visibility = View.GONE
                        emptyState.visibility = View.VISIBLE
                        emptyState.text = "No Jobs Found!"
                    }
                }
            }
        }

        viewModel.fetchJobs()


        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText?.lowercase(Locale.ROOT)?.trim() ?: ""

                val filteredList = if (searchText.isEmpty()) {
                    jobs
                } else {
                    jobs.filter { job ->
                        val title = job.title?.lowercase(Locale.ROOT) ?: ""
                        val company = job.company_name?.lowercase(Locale.ROOT) ?: ""
                        title.contains(searchText) || company.contains(searchText)
                    }
                }

                recyclerView.post {
                    jobAdapter.updateList(filteredList)
                }

                if (filteredList.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    emptyState.visibility = View.VISIBLE
                    emptyState.text = "No Jobs Found!"
                } else {
                    recyclerView.visibility = View.VISIBLE
                    emptyState.visibility = View.GONE
                }
                return true
            }
        })

    }

}