package com.mediapros.socialmed.errors.network

import com.mediapros.socialmed.errors.models.Report
import com.mediapros.socialmed.errors.models.SaveReportResource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ReportService {
    @POST("api/v1/reports")
    fun createReport(@Body resource: SaveReportResource, @Header("Authorization") token: String): Call<Report>
}