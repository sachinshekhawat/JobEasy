package com.example.jobeasy.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "jobs")
@Parcelize
data class Job(
    @PrimaryKey val id: Int,
    val title: String = "",
    val type: Int,
    val company_name: String = "",
    val place: String = "",  // From primary_details.Place
    val salary_min: Int,
    val salary_max: Int,
    val job_type: Int,
    val job_category_id: Int,
    val qualification: Int,
    val experience: Int,
    val shift_timing: Int,
    val job_role_id: Int,
    val city_location: Int,
    val locality: Int,
    val premium_till: String?,
    val content: String  = "",
    val advertiser: Int,
    val button_text: String = "",
    val custom_link: String = "",
    val amount: String = "",
    val views: Int,
    val shares: Int,
    val fb_shares: Int,
    var is_bookmarked: Boolean,
    val is_applied: Boolean,
    val is_owner: Boolean,
    val updated_on: String,
    val whatsapp_no: String = "",
    val created_on: String = "",
    val is_premium: Boolean,
    val status: Int,
    val expire_on: String = "",
    val job_hours: String = "",
    val openings_count: Int,
    val job_role: String = "",
    val other_details: String = "",
    val job_category: String = "",
    val num_applications: Int,
    val enable_lead_collection: Boolean,
    val is_job_seeker_profile_mandatory: Boolean,
    val job_location_slug: String = "",
    val fees_text: String = "",
    val fees_charged: Int,
    val should_show_last_contacted: Boolean,

    val primary_details: PrimaryDetails? = PrimaryDetails(),  // Default to an empty object
    val contactPreference: ContactPreference = ContactPreference(),
    val job_tags: List<JobTag> = listOf(),  // Default to an empty list
    val creatives: List<Creative> = listOf(),  // Default to an empty list
    val locations: List<Location> = listOf(),  // Default to an empty list
    val contentV3: ContentV3 = ContentV3(listOf(ContentField())),  // Default to an empty object
    val translated_content: HashMap<String, String> = hashMapOf()  // Default to an empty map
) : Parcelable
