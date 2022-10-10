package com.mediapros.socialmed.errors.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
/*import com.mediapros.socialmed.EXTRA_TOKEN
import com.mediapros.socialmed.EXTRA_USER_ID*/
import com.mediapros.socialmed.R
import com.mediapros.socialmed.RetrofitBuilder
import com.mediapros.socialmed.StateManager
import com.mediapros.socialmed.errors.models.Report
import com.mediapros.socialmed.errors.models.SaveReportResource
import com.mediapros.socialmed.errors.network.ReportService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class ReportErrorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_errors)

        val btSendReport = findViewById<Button>(R.id.btSendReport)

        btSendReport.setOnClickListener {
            sendReport()
        }
    }

    private fun sendReport() {
        val etReportTitle = findViewById<EditText>(R.id.etReportTitle)
        val etReportContent = findViewById<EditText>(R.id.etReportContent)
        //val date = LocalDateTime.now()
        /*val userId = intent.getStringExtra(EXTRA_USER_ID)!!.toInt()
        val token = intent.getStringExtra(EXTRA_TOKEN)*/
        val userId = StateManager.userId
        val token = StateManager.authToken

        val retrofit = RetrofitBuilder.build()

        val reportService: ReportService = retrofit.create(ReportService::class.java)

        val dateExample = "2022-09-28T06:08:22.534Z"

        val request = reportService.createReport(
            SaveReportResource(
                etReportTitle.text.toString(),
                etReportContent.text.toString(),
                dateExample,
                userId), token!!)

        request.enqueue(object : Callback<Report>{
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ReportErrorsActivity, "Reporte enviado. Gracias.", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@ReportErrorsActivity, response.body()!!.title, Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                    Toast.makeText(this@ReportErrorsActivity, "Error al enviar reporte.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Report>, t: Throwable) {
                Toast.makeText(this@ReportErrorsActivity, "Error al enviar reporte.", Toast.LENGTH_SHORT).show()
            }

        })

    }
}