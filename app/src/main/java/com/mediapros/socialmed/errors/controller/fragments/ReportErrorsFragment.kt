package com.mediapros.socialmed.errors.controller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mediapros.socialmed.R
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.shared.StateManager
import com.mediapros.socialmed.errors.models.Report
import com.mediapros.socialmed.errors.models.SaveReportResource
import com.mediapros.socialmed.errors.network.ReportService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ReportErrorsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_errors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btSendReport = view.findViewById<Button>(R.id.btSendReport)
        btSendReport.setOnClickListener {
            sendReport(view)
        }
    }

    private fun sendReport(view: View) {
        val etReportTitle = view.findViewById<EditText>(R.id.etReportTitle)
        val etReportContent = view.findViewById<EditText>(R.id.etReportContent)
        val userId = StateManager.loggedUserId
        val token = StateManager.authToken

        val retrofit = RetrofitBuilder.build()

        val reportService: ReportService = retrofit.create(ReportService::class.java)

        val date = StateManager.getJSDate(Date())

        val request = reportService.createReport(
            SaveReportResource(
                etReportTitle.text.toString(),
                etReportContent.text.toString(),
                date,
                userId), token)

        request.enqueue(object : Callback<Report> {
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Reporte enviado. Gracias.", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, response.body()!!.title, Toast.LENGTH_SHORT).show()
                    etReportTitle.text.clear()
                    etReportContent.text.clear()
                }
                else
                    Toast.makeText(context, "Error al enviar reporte.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Report>, t: Throwable) {
                Toast.makeText(context, "Error al enviar reporte: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })

    }
}