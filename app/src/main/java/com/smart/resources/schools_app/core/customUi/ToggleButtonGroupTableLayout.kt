package com.smart.resources.schools_app.core.customUi

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TableLayout
import android.widget.TableRow


/**
 * @author diego
 */
class ToggleButtonGroupTableLayout : TableLayout, View.OnClickListener {
    private var activeRadioButton: RadioButton? = null
    var onActiveBtnChanged: (()->Unit)? = null

    /**
     * @param context
     */
    constructor(context: Context?) : super(context)

    /**
     * @param context
     * @param attrs
     */
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    override fun onClick(v: View) {
        setChecked(v as RadioButton)
    }

    fun setChecked(radioButton: RadioButton) {
        if(activeRadioButton== radioButton) return

        if (activeRadioButton != null) {
            activeRadioButton!!.isChecked = false
        }
        radioButton.isChecked = true
        activeRadioButton = radioButton
        onActiveBtnChanged?.invoke()
    }

    /* (non-Javadoc)
     * @see android.widget.TableLayout#addView(android.view.View, int, android.view.ViewGroup.LayoutParams)
     */
    override fun addView(
        child: View, index: Int,
        params: ViewGroup.LayoutParams
    ) {
        super.addView(child, index, params)
        setChildrenOnClickListener(child as TableRow)
    }

    /* (non-Javadoc)
     * @see android.widget.TableLayout#addView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    override fun addView(
        child: View,
        params: ViewGroup.LayoutParams
    ) {
        super.addView(child, params)
        setChildrenOnClickListener(child as TableRow)
    }

    private fun setChildrenOnClickListener(tr: TableRow) {
        val c = tr.childCount
        for (i in 0 until c) {
            val v = tr.getChildAt(i)
            (v as? RadioButton)?.setOnClickListener(this)
        }
    }

    val checkedRadioButtonId: Int
        get() = if (activeRadioButton != null) {
            activeRadioButton!!.id
        } else -1

    companion object {
        private const val TAG = "ToggleButtonGroupTableLayout"
    }
}