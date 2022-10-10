package com.mediapros.socialmed.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.security.models.User
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class RecommendedDoctorAdapter(val doctors: List<User>, private val context: Context):
    RecyclerView.Adapter<RecommendedDoctorAdapter.Prototype>() {
    class Prototype(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvRecDocName = itemView.findViewById<TextView>(R.id.tvRecDocName)
        private val tvRecDocSpecialist = itemView.findViewById<TextView>(R.id.tvRecDocSpecialist)
        private val tvRecDocWorkplace = itemView.findViewById<TextView>(R.id.tvRecDocWorkplace)
        private val ivDoctorPic = itemView.findViewById<ImageView>(R.id.ivDoctorPic)

        fun bind(doctor: User, context: Context) {
            tvRecDocName.text = "${doctor.name} ${doctor.lastName}"
            tvRecDocSpecialist.text = doctor.specialist
            tvRecDocWorkplace.text = doctor.workPlace
            val picBuilder = Picasso.Builder(context)
            picBuilder.downloader(OkHttp3Downloader(context))
            picBuilder.build().load(doctor.image).into(ivDoctorPic)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Prototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_recommended_doctor, parent, false)

        return Prototype(view)
    }

    override fun onBindViewHolder(holder: Prototype, position: Int) {
        holder.bind(doctors[position], context)
    }

    override fun getItemCount(): Int {
        return doctors.size
    }
}