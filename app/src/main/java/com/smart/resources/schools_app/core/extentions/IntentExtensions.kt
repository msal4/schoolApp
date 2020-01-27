package com.smart.resources.schools_app.core.extentions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import java.util.*

var GET_IMAGE_REQUEST = 0
var GET_MULTI_IMAGES_REQUEST = 1


fun Activity.selectImage(
    multiSelect: Boolean= false,
    neededForLaterUsage: Boolean= false
) {

    val intent = getImageIntent(
        multiSelect,
        neededForLaterUsage
    )
    startActivityForResult(
        Intent.createChooser(intent, "Select Picture"),
        if (multiSelect) GET_MULTI_IMAGES_REQUEST else GET_IMAGE_REQUEST
    )
}

fun Fragment.selectImage(
    multiSelect: Boolean= false,
    neededForLaterUsage: Boolean= false
) {
    val intent = getImageIntent(
        multiSelect,
        neededForLaterUsage
    )

    startActivityForResult(
        Intent.createChooser(intent, "Select Picture"),
        if (multiSelect) GET_MULTI_IMAGES_REQUEST else GET_IMAGE_REQUEST
    )
}


private fun getImageIntent(
    multiSelect: Boolean,
    neededForLaterUsage: Boolean
): Intent {
    val intent = Intent()
    intent.type = "image/*"
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiSelect)
    intent.action =
        if (neededForLaterUsage) Intent.ACTION_OPEN_DOCUMENT else Intent.ACTION_GET_CONTENT
    return intent
}


fun Intent.getImage(): Uri? {
    return getImages()[0]
}

// NOTE: there is no reliable way to convert Uri into real Path
fun Intent.getImages(): List<Uri?> {
    val images: MutableList<Uri?> =
        ArrayList()
    if (clipData != null) {
        //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
        val count = clipData!!.itemCount
        for (i in 0 until count) {
            val imageUri = clipData!!.getItemAt(i).uri
            images.add(imageUri)
        }
    } else if (data != null) {
        val imageUri = data
        images.add(imageUri)
    }
    return images
}

fun Context.openPdfViewer(url:String){
    val pdfIntent = Intent(Intent.ACTION_VIEW)
    pdfIntent.setDataAndType(Uri.parse(url) , "application/pdf")
    startActivity(pdfIntent)
}