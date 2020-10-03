package com.smart.resources.schools_app.features.onlineExam.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.abstractTypes.LoadableActionMenuItemFragment
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.core.extentions.toString
import com.smart.resources.schools_app.databinding.FragmentAddOnlineExamBinding
import com.smart.resources.schools_app.features.filter.FilterBottomSheet
import com.smart.resources.schools_app.features.filter.FilterItem
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.AddOnlineExamViewModel
import com.smart.resources.schools_app.features.onlineExam.presentation.adapter.QuestionsQuickAdapter
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

@AndroidEntryPoint
class AddOnlineExamFragment : LoadableActionMenuItemFragment() {
    private lateinit var binding: FragmentAddOnlineExamBinding
    private val viewModel: AddOnlineExamViewModel by viewModels()
    private lateinit var adapter: QuestionsQuickAdapter
    private val addQuestionFragment: AddQuestionFragment? by lazy {
        findAddQuestionFragment()?.also {
            it.onQuestionAdded = ::onQuestionAdded
        }
    }

    override val loadableItemId: Int
        get() = R.id.saveMenuItem
    override val menuResourceId: Int
        get() = R.menu.menu_save_btn

    companion object {
        private const val TAG = "AddOnlineExamFragment"

        fun newInstance(fm: FragmentManager) {
            val fragment = AddOnlineExamFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right,
                )
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(TAG)
                commit()
            }
        }
    }

    private fun findAddQuestionFragment(): AddQuestionFragment? {
        val fragmentTag = R.string.tag_add_question_fragment
            .toString(requireContext())
        val fragment = childFragmentManager.findFragmentByTag(fragmentTag)
        return if (fragment is AddQuestionFragment) {
            fragment
        } else {
            Logger.e("$TAG: couldn't find AddQuestionFragment, class type = ${fragment?.javaClass}")
            null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddOnlineExamBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@AddOnlineExamFragment
            model = viewModel

            adapter = QuestionsQuickAdapter()
            simpleQuestionsRecycler.adapter = adapter

            classesField.setOnClickListener { showClassesFilter() }
            addQuestionFab.setOnClickListener { showAddQuestionFragment() }
            setupObservers()
        }

        registerKeyboardListener()
        (activity as SectionActivity).setCustomTitle(R.string.add_exam)
        return binding.root
    }

    private fun setupObservers() {
        viewModel.apply {
            questions.observe(viewLifecycleOwner) {
                adapter.setDiffNewData(it.toMutableList())
            }

            isLoading.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { isLoading ->
                    setToolbarLoading(isLoading)
                }
            }

            errorMsgEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { errorMsgId ->
                    val errorMsg = errorMsgId.toString(requireContext())
                    binding.coordinatorLayout.showSnackBar(errorMsg)
                }
            }

            examAddedEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { examAdded ->
                    if (examAdded && isAdded) {
                        parentFragmentManager.popBackStack()
                    }
                }
            }
        }
    }

    private fun onQuestionAdded(question: Question) {
        viewModel.addQuestion(question)
    }

    private fun showClassesFilter() {
        val classes = viewModel.classes
        if (classes.isNullOrEmpty()) return

        if (isAdded) {
            // map classes to FilterItems
            val filterItems= classes.mapIndexed {index, item ->
                FilterItem(
                    selected = viewModel.selectedClassesPositions.value.orEmpty().contains(index),
                    item = item,
                )
            }
            
            // show filter bottom sheet
            FilterBottomSheet
                .newInstance(filterItems)
                .also {
                    it.onSelectionChanged = {
                        viewModel.selectedClassesPositions.value = it
                    }
                    it.show(parentFragmentManager, "")
                }
        }
    }


    private fun showAddQuestionFragment() {
        binding.addQuestionFab.hide(
            object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onHidden(fab: FloatingActionButton) {
                    addQuestionFragment?.show()
                }
            },
        )
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveMenuItem -> {
                viewModel.addOnlineExam()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun registerKeyboardListener() {
        KeyboardVisibilityEvent.setEventListener(
            requireActivity(),
            viewLifecycleOwner,
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    if (isOpen) {
                        binding.addQuestionFab.hide()
                    } else {
                        addQuestionFragment?.hide()
                        binding.addQuestionFab.show()
                    }
                }
            },
        )
    }

}

