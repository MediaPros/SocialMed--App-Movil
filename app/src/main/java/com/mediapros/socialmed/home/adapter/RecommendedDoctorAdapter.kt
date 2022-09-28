package com.mediapros.socialmed.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.security.models.User

class RecommendedDoctorAdapter(val doctors: List<User>):
    RecyclerView.Adapter<RecommendedDoctorPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedDoctorPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_recommended_doctor, parent, false)

        return RecommendedDoctorPrototype(view)
    }

    override fun onBindViewHolder(holder: RecommendedDoctorPrototype, position: Int) {
        holder.bind(doctors[position])
    }

    override fun getItemCount(): Int {
        return doctors.size
    }
}

class RecommendedDoctorPrototype(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvRecDocName = itemView.findViewById<TextView>(R.id.tvRecDocName)
    val tvRecDocSpecialist = itemView.findViewById<TextView>(R.id.tvRecDocSpecialist)
    val tvRecDocWorkplace = itemView.findViewById<TextView>(R.id.tvRecDocWorkplace)
    val ivDoctorPic = itemView.findViewById<ImageView>(R.id.ivDoctorPic)

    fun bind(doctor: User) {
        tvRecDocName.text = "${doctor.name} ${doctor.lastName}"
        tvRecDocSpecialist.text = doctor.specialist
        tvRecDocWorkplace.text = doctor.workPlace
    }
}