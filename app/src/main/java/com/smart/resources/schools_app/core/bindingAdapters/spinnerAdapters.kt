package com.smart.resources.schools_app.core.bindingAdapters

import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.tiper.MaterialSpinner

private const val SPINNER_SET_LIST_ATTRIBUTE= "android:setList"
private const val SPINNER_SELECT_ITEM_ATTRIBUTE= "android:selectedItem"

@BindingAdapter(SPINNER_SET_LIST_ATTRIBUTE)
fun <T>MaterialSpinner.setSpinnerList(list:List<T>?) {
    if(list.isNullOrEmpty()) return

    ArrayAdapter(
        context,
        android.R.layout.simple_spinner_item, list
    ).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter= this
    }
}


@BindingAdapter(SPINNER_SELECT_ITEM_ATTRIBUTE)
fun MaterialSpinner.setSelectedItem(index:Int?){
    selection= index?:-1
}

@InverseBindingAdapter(attribute = SPINNER_SELECT_ITEM_ATTRIBUTE)
fun MaterialSpinner.getSelectedItem():Int{
    return selection
}

@BindingAdapter("${SPINNER_SELECT_ITEM_ATTRIBUTE}AttrChanged")
fun MaterialSpinner.setSelectedItemChangedListener(
    attrChange: InverseBindingListener
) {
    var oldPosition:Int= selection
   onItemSelectedListener= object: MaterialSpinner.OnItemSelectedListener {

       override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
           if(oldPosition!=position){
               oldPosition= position
               attrChange.onChange()
           }
       }

       override fun onNothingSelected(parent: MaterialSpinner) {
       }

   }
}

