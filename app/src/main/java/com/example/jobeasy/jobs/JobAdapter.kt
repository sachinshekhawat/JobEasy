package com.example.jobeasy.jobs

import android.annotation.SuppressLint
import com.example.jobeasy.R
import com.example.jobeasy.data.model.Job

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jobeasy.jobdetail.JobDetailBottomSheet

class JobAdapter(
    private var jobs: MutableList<Job> = mutableListOf(),
    private val onItemClick: (Job) -> Unit
) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newJobs: List<Job>) {
        jobs.clear()
        jobs.addAll(newJobs)
        notifyDataSetChanged()
    }

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val company: TextView = itemView.findViewById(R.id.tvCompany)
        private val location: TextView = itemView.findViewById(R.id.tvLocation)
        private val salary: TextView = itemView.findViewById(R.id.tvSalary)
        private val phone: TextView = itemView.findViewById(R.id.tvPhone)

        fun bind(job: Job) {
            title.text = job.title
            ("Company : " + job.company_name).also { company.text = it }
            ("Location : " + job.primary_details?.Place.orEmpty()).also { location.text = it }
            ("Salary : " + job.primary_details?.Salary.orEmpty()).also { salary.text = it }
            ("Phone : " + job.whatsapp_no).also { phone.text = it }
            itemView.setOnClickListener { onItemClick(job) }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job_card, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(jobs[position])
    }

    override fun getItemCount(): Int = jobs.size
}
