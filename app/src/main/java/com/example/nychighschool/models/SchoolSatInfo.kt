package com.example.nychighschool.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SchoolSatInfo(@SerializedName("sat_critical_reading_avg_score") val reading: String?,
                         @SerializedName("sat_writing_avg_score") val writing: String?,
                         @SerializedName("sat_math_avg_score") val math: String?) : Parcelable
