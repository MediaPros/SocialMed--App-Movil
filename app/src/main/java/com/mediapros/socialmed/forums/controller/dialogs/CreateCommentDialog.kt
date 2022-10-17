package com.mediapros.socialmed.forums.controller.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.mediapros.socialmed.databinding.DialogCreateCommentBinding

class CreateCommentDialog(
    private val onSendClickListener: (String) -> Unit
): DialogFragment() {
    private lateinit var binding: DialogCreateCommentBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogCreateCommentBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.btSendComment.setOnClickListener {
            onSendClickListener.invoke(binding.etCreateComment.text.toString())
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}