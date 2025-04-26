package com.example.jobeasy.jobdetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.jobeasy.R
import com.example.jobeasy.data.model.ContactPreference
import com.example.jobeasy.data.model.Job
import com.example.jobeasy.data.repository.JobRepository
import com.example.jobeasy.domain.usecase.FetchJobsUseCase
import com.example.jobeasy.jobs.JobsViewModel
import com.example.jobeasy.jobs.JobsViewModelFactory
import com.example.jobeasy.utils.safeCopy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONException
import org.json.JSONObject
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class JobDetailBottomSheet(private val job: Job) : BottomSheetDialogFragment() {

    private lateinit var viewModel: JobsViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_job_detail_bottom_sheet, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.tvJobTitle).text = job.title
        view.findViewById<TextView>(R.id.tvCompanyName).text = job.company_name
        "Location: ${job.primary_details?.Place.orEmpty()}".also { view.findViewById<TextView>(R.id.tvPlace).text = it }

        "Salary: ₹${job.salary_min ?: "N/A"} - ₹${job.salary_max ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvSalary).text = it }

        "Category: ${job.job_category ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvJobCategory).text = it }

        "Experience: ${job.primary_details?.Experience ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvExperience).text = it }

        "Qualification: ${job.primary_details?.Qualification ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvQualification).text = it }

        "Job Type: ${job.primary_details?.Job_Type ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvJobType).text = it }

        "Fees: ${job.primary_details?.Fees_Charged ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvFees).text = it }

        "Role: ${job.job_role ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvJobRole).text = it }

//        view.findViewById<TextView>(R.id.tvContent).text = decodeUnicodeJson(job.content.toString()) ?: "No description available"
        val contentText = job.content?.toString()
        val decoded = decodeUnicodeJson(contentText)
        (decoded ?: "No description available").also { view.findViewById<TextView>(R.id.tvContent).text = it }


        "Working Hours: ${job.job_hours ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvJobHours).text = it }

        "Openings: ${job.openings_count ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvOpenings).text = it }

        "Posted on: ${job.created_on?.toIndianDateTime() ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvPostedDate).text = it }

        "Expires on: ${job.expire_on?.toIndianDateTime() ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvExpiryDate).text = it }

        "Last updated: ${job.updated_on?.toIndianDateTime() ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvUpdatedOn).text = it }


        "WhatsApp: ${job.whatsapp_no ?: "N/A"}".also { view.findViewById<TextView>(R.id.tvWhatsapp).text = it }

        val apBtn = view.findViewById<Button>(R.id.btApplyNow)
        var lastClickTime = 0L

        apBtn.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 1500) { // 1.5 second gap
                Toast.makeText(context, "Applied! Cheers 🎉", Toast.LENGTH_SHORT).show()
                lastClickTime = currentTime
            }
        }


        val ivBookmark = view.findViewById<ImageView>(R.id.ivBookmark)

        viewModel = ViewModelProvider(
            requireActivity(),
            JobsViewModelFactory(requireActivity().application, FetchJobsUseCase(JobRepository(requireContext())))
        ).get(JobsViewModel::class.java)



        // Load initial icon
        var isBookmarked = job.is_bookmarked
        ivBookmark.setImageResource(if (isBookmarked) R.drawable.bookmarkfill else R.drawable.bookic)


        ivBookmark.setOnClickListener {
            isBookmarked = !isBookmarked
            job.is_bookmarked = isBookmarked

            if(isBookmarked){
                Toast.makeText(context, "BookMark Saved 🎉", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "BookMark Removed !! ", Toast.LENGTH_SHORT).show()
            }
            ivBookmark.setImageResource(if (isBookmarked) R.drawable.bookmarkfill else R.drawable.bookic)

            if (isBookmarked) {
                val safeJob = job.safeCopy() // <-- use your bulletproof safe copy
                viewModel.insertJob(safeJob) // Save to DB
            } else {
                viewModel.deleteJob(job.id) // Remove from DB
            }
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun String.toIndianDateTime(): String {
        return try {
            val dateTime = OffsetDateTime.parse(this)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
            dateTime.format(formatter)
        } catch (e: Exception) {
            "N/A"
        }
    }

    fun decodeUnicodeJson(input: String?): String? {
        // Check for null or empty input string
        if (input.isNullOrBlank() || input == "null") return null

        return try {
            val json = JSONObject(input)
            val sb = StringBuilder()

            // Loop through all keys in the JSONObject
            for (key in json.keys()) {
                // Safely fetch each value, handling possible nulls
                val rawValue = json.optString(key, null) // optString returns null if the key doesn't exist

                // If the rawValue is not null, decode it
                if (!rawValue.isNullOrBlank()) {
                    val decoded = rawValue.replace("\\\\u", "\\u") // Fix escaped unicode
                        .let { decodeUnicode(it) } // Assuming decodeUnicode() safely handles the decoding
                    sb.append(decoded).append("\n")
                }
            }

            // Return the decoded content, trimming any extra spaces or newlines
            sb.toString().trim()

        } catch (e: JSONException) {
            // Log the error and return null if the JSON is malformed
            e.printStackTrace()
            null
        }
    }


    // Helper function to decode Unicode escape sequences
    fun decodeUnicode(unicodeStr: String): String {
        val out = StringBuilder()
        var i = 0
        while (i < unicodeStr.length) {
            if (unicodeStr[i] == '\\' && i + 5 < unicodeStr.length && unicodeStr[i + 1] == 'u') {
                val hex = unicodeStr.substring(i + 2, i + 6)
                out.append(hex.toInt(16).toChar())
                i += 6
            } else {
                out.append(unicodeStr[i])
                i++
            }
        }
        return out.toString()
    }


}
