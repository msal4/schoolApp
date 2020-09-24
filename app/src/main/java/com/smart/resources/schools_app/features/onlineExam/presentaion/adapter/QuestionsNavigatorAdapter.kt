package com.smart.resources.schools_app.features.onlineExam.presentaion.adapter

import android.content.Context
import android.graphics.Color
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.toColor
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


class QuestionsNavigatorAdapter : CommonNavigatorAdapter() {
    var onItemPressed:((index: Int)->Unit)?=null
    private var answeredQuestions: List<Boolean> = emptyList()

    fun submitList(answeredQuestions: List<Boolean>){
        this.answeredQuestions= answeredQuestions
        notifyDataSetChanged()
    }

    override fun getCount() = answeredQuestions.size
    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        return ColorTransitionPagerTitleView(context).apply {
            text= "%02d".format(index + 1)
            val color=    if (answeredQuestions[index]) R.color.green_a200.toColor(context) else Color.WHITE
            selectedColor= color
            normalColor= color
            setOnClickListener {
                onItemPressed?.invoke(index)
            }
        }
    }


    override fun getIndicator(context: Context): IPagerIndicator {
        val indicator = WrapPagerIndicator(context)
        indicator.fillColor= R.color.colorAccent87.toColor(context)
        return indicator
    }

}