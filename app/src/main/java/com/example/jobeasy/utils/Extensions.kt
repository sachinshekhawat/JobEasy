package com.example.jobeasy.utils


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobeasy.data.model.ContactPreference
import com.example.jobeasy.data.model.ContentField
import com.example.jobeasy.data.model.ContentV3
import com.example.jobeasy.data.model.Job
import com.example.jobeasy.data.model.PrimaryDetails

fun RecyclerView.setupInfiniteScroll(onLoadMore: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(rv, dx, dy)
            val layoutManager = rv.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 4) {
                onLoadMore()
            }
        }
    })
}

fun Job.safeCopy(): Job {
    return Job(
        id = this.id,
        title = this.title ?: "",
        type = this.type,
        company_name = this.company_name ?: "",
        place = this.place ?: "",
        salary_min = this.salary_min,
        salary_max = this.salary_max,
        job_type = this.job_type,
        job_category_id = this.job_category_id,
        qualification = this.qualification,
        experience = this.experience,
        shift_timing = this.shift_timing,
        job_role_id = this.job_role_id,
        city_location = this.city_location,
        locality = this.locality,
        premium_till = this.premium_till ?: "",
        content = this.content ?: "",
        advertiser = this.advertiser,
        button_text = this.button_text ?: "",
        custom_link = this.custom_link ?: "",
        amount = this.amount ?: "",
        views = this.views,
        shares = this.shares,
        fb_shares = this.fb_shares,
        is_bookmarked = this.is_bookmarked,
        is_applied = this.is_applied,
        is_owner = this.is_owner,
        updated_on = this.updated_on ?: "",
        whatsapp_no = this.whatsapp_no ?: "",
        created_on = this.created_on ?: "",
        is_premium = this.is_premium,
        status = this.status,
        expire_on = this.expire_on ?: "",
        job_hours = this.job_hours ?: "",
        openings_count = this.openings_count,
        job_role = this.job_role ?: "",
        other_details = this.other_details ?: "",
        job_category = this.job_category ?: "",
        num_applications = this.num_applications,
        enable_lead_collection = this.enable_lead_collection,
        is_job_seeker_profile_mandatory = this.is_job_seeker_profile_mandatory,
        job_location_slug = this.job_location_slug ?: "",
        fees_text = this.fees_text ?: "",
        fees_charged = this.fees_charged,
        should_show_last_contacted = this.should_show_last_contacted,

        primary_details = this.primary_details ?: PrimaryDetails(),
        contactPreference = this.contactPreference ?: ContactPreference(),
        job_tags = this.job_tags ?: emptyList(),
        creatives = this.creatives ?: emptyList(),
        locations = this.locations ?: emptyList(),
        contentV3 = this.contentV3 ?: ContentV3(listOf(ContentField())),
        translated_content = this.translated_content ?: hashMapOf()
    )
}
