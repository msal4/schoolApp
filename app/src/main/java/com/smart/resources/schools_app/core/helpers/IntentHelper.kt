package com.smart.resources.schools_app.core.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import java.util.*

object IntentHelper {
    var GET_IMAGE_REQUEST = 0
    var GET_MULTI_IMAGES_REQUEST = 1

    fun selectImage(
        activity: Activity?= null,
        fragment: Fragment?= null,
        multiSelect: Boolean= false,
        neededForLaterUsage: Boolean= false
    ) {
        if((activity != null && fragment != null) || (activity == null && fragment == null)) return

        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiSelect)
        intent.action =
            if (neededForLaterUsage) Intent.ACTION_OPEN_DOCUMENT else Intent.ACTION_GET_CONTENT

        activity?.startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            if (multiSelect) GET_MULTI_IMAGES_REQUEST else GET_IMAGE_REQUEST)

            ?: // if null

        fragment?.startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            if (multiSelect) GET_MULTI_IMAGES_REQUEST else GET_IMAGE_REQUEST
        )
    }

    fun getImage(data: Intent): Uri? {
        return getImages(data)[0]
    }

    // NOTE: there is no reliable way to convert Uri into real Path
    fun getImages(data: Intent): List<Uri?> {
        val images: MutableList<Uri?> =
            ArrayList()
        if (data.clipData != null) { //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
            val count = data.clipData!!.itemCount
            for (i in 0 until count) {
                val imageUri = data.clipData!!.getItemAt(i).uri
                images.add(imageUri)
            }
        } else if (data.data != null) {
            val imageUri = data.data
            images.add(imageUri)
        }
        return images
    }



}

fun Context.openPdfViewer(url:String){
    val pdfIntent = Intent(Intent.ACTION_VIEW)
    pdfIntent.setDataAndType(Uri.parse(url) , "application/pdf")
    startActivity(pdfIntent)
}