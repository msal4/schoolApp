package com.smart.resources.schools_app.features.homework.addHomeworkAnswer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smart.resources.schools_app.core.extentions.GET_IMAGE_REQUEST
import com.smart.resources.schools_app.core.extentions.getImage
import com.smart.resources.schools_app.core.extentions.selectImage
import com.smart.resources.schools_app.core.helpers.FileUtils
import com.smart.resources.schools_app.databinding.BottomSheetAnswerHomeworkBinding
import id.zelory.compressor.Compressor
import java.io.File

// TODO: add scrolling when page opens
class AnswerHomeworkBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAnswerHomeworkBinding

    companion object Factory {

        fun newInstance(): AnswerHomeworkBottomSheet {
            return AnswerHomeworkBottomSheet()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAnswerHomeworkBinding.inflate(inflater, container, false).apply {
            addImageIcon.setOnClickListener { selectImage() }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_IMAGE_REQUEST &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {
            data.getImage()?.let {
                saveAsCompressedFile(it)
                binding.apply {
                    imageAttachment.setImageURI(it)
                    imageCard.visibility= View.VISIBLE
                }


            }
        }
    }

    private fun saveAsCompressedFile(it: Uri) {
        val originalFile: File = FileUtils.from(context, it)
        val imageFile = Compressor(context).compressToFile(originalFile)
        // TODO: save image file
    }

}