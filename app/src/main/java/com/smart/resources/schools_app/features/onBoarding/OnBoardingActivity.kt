package com.smart.resources.schools_app.features.onBoarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import com.haytham.coder.extensions.hideSystemUi
import com.haytham.coder.extensions.toColor
import com.haytham.coder.extensions.toString
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.BaseActivity
import com.smart.resources.schools_app.core.activity.HomeActivity
import com.smart.resources.schools_app.core.extentions.toInt
import com.smart.resources.schools_app.core.myTypes.UserType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_on_boarding.*

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity() {
    private val viewModel: OnBoardingViewModel by viewModels()

    companion object Factory {
        fun newInstance(context: Context) {
            Intent(context, OnBoardingActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        createOnBoardingFragment()

        viewModel.onBoardingFinishedEvent.observe(this) {
            it?.getContentIfNotHandled()?.let { onBoardingFinished ->
                if (onBoardingFinished) onOnBoardingFinished()
            }
        }
    }

    override fun onResume() {
        hideSystemUi()
        super.onResume()
    }

    private fun getOnBoardingPages(userType: UserType): ArrayList<PaperOnboardingPage> {
        return arrayListOf(
            PaperOnboardingPage(
                run {
                    if (userType == UserType.STUDENT) R.string.on_boarding_title_student_1
                    else R.string.on_boarding_title_teacher_1
                }.toString(this),
                run {
                    if (userType == UserType.STUDENT) R.string.on_boarding_description_student_1
                    else R.string.on_boarding_description_teacher_1
                }.toString(this),
                R.color.colorOnBoarding1.toColor(this),
                R.drawable.ic_on_boarding_1,
                R.drawable.ic_person_reading
            ),
            PaperOnboardingPage(
                run {
                    if (userType == UserType.STUDENT) R.string.on_boarding_title_student_2
                    else R.string.on_boarding_title_teacher_2
                }.toString(this),
                run {
                    if (userType == UserType.STUDENT) R.string.on_boarding_description_student_2
                    else R.string.on_boarding_description_teacher_2
                }.toString(this),
                R.color.colorOnBoarding2.toColor(this),
                R.drawable.ic_on_boarding_2,
                R.drawable.ic_school_bell
            ),
            PaperOnboardingPage(
                R.string.on_boarding_title_3.toString(this),
                run {
                    if (userType == UserType.STUDENT) R.string.on_boarding_description_student_3
                    else R.string.on_boarding_description_teacher_3
                }.toString(this),
                R.color.colorOnBoarding3.toColor(this),
                R.drawable.ic_on_boarding_3,
                R.drawable.ic_exam_on_boarding
            ),
        )
    }


    private fun createOnBoardingFragment() {
        viewModel.userType.observe(this){
            supportFragmentManager.commit {
                val onBoardingFragment = PaperOnboardingFragment
                    .newInstance(getOnBoardingPages(it))

                add(R.id.containerLayout, onBoardingFragment)
                onBoardingFragment.setOnChangeListener(::onPageChanged)
                onBoardingFragment.setOnRightOutListener(::onLastPageSwiped)
            }
        }
    }

    private fun onPageChanged(prev: Int, current: Int) {
        val showStart = current == 2

        skipButton
            .animate()
            .alpha((!showStart).toInt().toFloat())
            .setDuration(viewModel.btnAnimationDuration)
            .withEndAction {
                skipButton.isVisible = !showStart
            }


        startButton
            .animate()
            .alpha(showStart.toInt().toFloat())
            .setDuration(viewModel.btnAnimationDuration)
            .withEndAction {
                startButton.isVisible = showStart
            }
    }

    private fun onLastPageSwiped() {
        viewModel.finishOnBoarding()
    }

    fun onSkipClicked(view: View) {
        viewModel.finishOnBoarding()
    }

    fun onStartClicked(view: View) {
        viewModel.finishOnBoarding()
    }

    private fun onOnBoardingFinished() {
        HomeActivity.newInstance(this)
    }
}


