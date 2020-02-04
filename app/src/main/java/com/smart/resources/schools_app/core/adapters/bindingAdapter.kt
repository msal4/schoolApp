package com.smart.resources.schools_app.core.adapters

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.textfield.TextInputLayout
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.features.users.UsersRepository
import com.tiper.MaterialSpinner
import jp.wasabeef.blurry.Blurry
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.lang.Exception

@BindingAdapter("mine:errorMsg")
fun setEditTextError(editText: EditText, errorMsg:String?){
    editText.error= errorMsg
}

@BindingAdapter("mine:errorMsg")
fun setInputLayoutError(inputLayout: TextInputLayout, errorMsg:String?){
    inputLayout.error= errorMsg
}

@BindingAdapter("mine:errorMsg")
fun setSpinnerError(spinner: MaterialSpinner, errorMsg:String?){
    spinner.error= errorMsg
}

@BindingAdapter("mine:setStars")
fun setStars(ratingBar:  RatingBar, stars:Int?){
    ratingBar.rating= (stars?:0).toFloat()
}

@BindingAdapter("mine:setDate")
fun setTextFromDate(textView: TextView, date: LocalDateTime?){
    try {
        textView.text = date?.format(dateDisFormatter)
    }catch (e: Exception){
        Logger.e("binding error: ${e.message}")
    }

}
@InverseBindingAdapter(attribute = "mine:setDate")
fun setDateFomText(textView: TextView): LocalDateTime{
    return LocalDateTime.of(LocalDate.parse(textView.text, dateDisFormatter), LocalTime.of(0, 0))
}

@BindingAdapter("mine:setDateAttrChanged")
fun setListeners(
    view: TextView,
    attrChange: InverseBindingListener
) {
    view.addTextChangedListener(object :TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            attrChange.onChange()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}


@BindingAdapter("android:srcUrl")
fun loadImageUrl(iv: ImageView, url: String?) {
    //iv.context.toast(url.toString())

    loadUrl(url, iv)
}
@BindingAdapter("android:blurrySrc")
fun loadBlurryImageUrl(iv: ImageView, url: String?) {
    loadUrl(url, iv, true)
}

private fun loadUrl(url: String?, iv: ImageView, blurry:Boolean= false) {
    if (url == "") {
        Glide
            .with(iv.context)
            .clear(iv)
    } else {
        Glide
            .with(iv.context)
            .load(url)
            .apply {
                if(blurry)
                    addListener(object: RequestListener<Drawable>{
                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Blurry
                                .with(iv.context)
                                .radius(10)
                                .sampling(2)
                                .color(ResourcesCompat.getColor(iv.context.resources, R.color.shadowColor, null))
                                .from(resource?.toBitmap())
                                .into(iv)

                            return true
                        }

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return true
                        }

                    })
            }
            .into(iv)
    }
}

@BindingAdapter("android:accountUrl")
fun setAccountImage(iv: ImageView, url: String?) {
    //iv.context.toast(url.toString())

    if(url==""){
        Glide
            .with(iv.context)
            .load(R.drawable.ic_profile_place_holder)
            .into(iv)
    }else {

        Glide
            .with(iv.context)
            .load(url)
            .into(iv)
    }
}

@BindingAdapter("mine:setImage")
fun loadImageDrawable(iv: ImageView, drawable: Drawable) {
    Glide
        .with(iv.context)
        .load(drawable)
        .into(iv)
}

@BindingAdapter("android:setMark")
fun setMark(tv: TextView, mark:Int?) {
    val isStudent= UsersRepository.instance.getCurrentUser()?.userType==0
        tv.text = mark?.toString() ?: if(isStudent)"- " else ""
}


@BindingAdapter("android:setList")
fun <T>setSpinnerList(spinner: MaterialSpinner, list:List<T>) {
     ArrayAdapter<T>(
        spinner.context,
        android.R.layout.simple_spinner_item, list
    ).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter= this
    }
}

