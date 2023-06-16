package com.example.nychighschool.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class HighSchool(val dbn: String?,
                      @SerializedName("school_name") val schoolName: String?,
                      @SerializedName("primary_address_line_1") val schoolAddress: String?,
                      @SerializedName("state_code") val State: String?,
                      val city: String?,
                      val zip: String?,
                      @SerializedName("overview_paragraph") val overview: String?,
                      @SerializedName("school_email") val email: String?,
                      @SerializedName("phone_number") val phone: String?,
                      val website: String?) : Parcelable

