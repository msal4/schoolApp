package com.smart.resources.schools_app.features.onlineExam.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.haytham.coder.extensions.toString
import com.kaopiz.kprogresshud.KProgressHUD
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.callbacks.SwipeAdapter
import com.smart.resources.schools_app.core.extentions.showConfirmationDialog
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.menuSheet.MenuBottomSheet
import com.smart.resources.schools_app.features.menuSheet.MenuItemData
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.OnlineExamViewModel
import com.smart.resources.schools_app.features.onlineExam.presentation.adapter.OnlineExamAdapter
import com.smart.resources.schools_app.features.onlineExam.presentation.bottomSheets.ExamKeyBottomSheet
import dagger.hilt.android.AndroidEntryPoint

// TODO: add confirmation dialogs
@AndroidEntryPoint
class OnlineExamsFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var adapter: OnlineExamAdapter
    private val viewModel: OnlineExamViewModel by viewModels()
    private val progressHud: KProgressHUD by lazy {
        KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setDimAmount(0.5f)
    }

    companion object {
        fun newInstance(fm: FragmentManager) {
            val fragment = OnlineExamsFragment()
            fm.beginTransaction().apply {
                add(R.id.fragmentContainer, fragment)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@OnlineExamsFragment
            listState = viewModel.listState
            setupRecycler()
        }

        observeViewModel()
        setupToolbar()
        return binding.root
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        (activity as SectionActivity).setCustomTitle(R.string.online_exam)
    }

    private fun observeViewModel() {
        viewModel.apply {
            onlineExams.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
            deleteFailedEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { pair ->
                    adapter.notifyItemChanged(pair.first)
                    showErrorSnackbar(pair.second)
                }
            }
            errorEvent.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { errorMsgId ->
                    showErrorSnackbar(errorMsgId)
                }
            }

            menuActionInProgress.observe(viewLifecycleOwner) {
                it.let { isLoading ->
                    if (isLoading) progressHud.show()
                    else progressHud.dismiss()
                }
            }
        }
    }

    private fun showErrorSnackbar(errorMsgId: Int) {
        val errorMsg = errorMsgId.toString(requireContext())
        binding.layout.showSnackBar(errorMsg)
    }

    private fun FragmentRecyclerLoaderBinding.setupRecycler() {
        adapter = OnlineExamAdapter(true)
        adapter.onItemPressed = ::onOnlineExamPressed

        if (viewModel.isTeacher) {
            // add swipe functionality
            val swipeAdapter = SwipeAdapter(onSwiped = ::onItemSwiped, swipedRightForOptions = true)
            ItemTouchHelper(swipeAdapter).attachToRecyclerView(recyclerView)
            adapter.onItemLongPressed = ::showOptionsMenu
        }
        recyclerView.adapter = adapter
    }

    private fun onItemSwiped(swipeDirection: Int, viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.adapterPosition
        if (swipeDirection == ItemTouchHelper.RIGHT) {
            showConfirmationDialog(
                R.string.delete_confirmation,
                okAction = {
                    viewModel.removeExam(position)
                },
                dismissAction = {
                    adapter.notifyItemChanged(position)
                },
            )
        } else {
            adapter.notifyItemChanged(position)
            val onlineExam = viewModel.onlineExams.value?.getOrNull(position)
            if (onlineExam != null) showOptionsMenu(onlineExam)
        }
    }

    private fun onOnlineExamPressed(onlineExam: OnlineExam) {
        if(!isAdded) return

        if (viewModel.isTeacher) {
            if (onlineExam.examStatus == OnlineExamStatus.INACTIVE) {
                ExamPaperFragment.newInstance(parentFragmentManager, onlineExam)
            } else {
                OnlineExamAnswersFragment.newInstance(parentFragmentManager, onlineExam)
            }
        } else {
            if(onlineExam.examStatus == OnlineExamStatus.ACTIVE) {
                ExamKeyBottomSheet.newInstance(onlineExam.id).apply {
                    onExamKeyMatch = {
                        ExamPaperFragment.newInstance(parentFragmentManager, onlineExam)
                    }
                }.show(parentFragmentManager, "")
            }else{
                ExamPaperFragment.newInstance(parentFragmentManager, onlineExam)
            }
        }
    }

    private fun showOptionsMenu(onlineExam: OnlineExam) {
        MenuBottomSheet.newOnlineExamsInstance(onlineExam.examStatus).apply {
            onMenuItemPressed = {
                onSheetMenuItemPressed(it, onlineExam)
            }
        }.show(parentFragmentManager, "")
    }

    private fun onSheetMenuItemPressed(menuItemData: MenuItemData, onlineExam: OnlineExam) {
        when (menuItemData.label) {
            R.string.the_answers -> {
                OnlineExamAnswersFragment.newInstance(parentFragmentManager, onlineExam)
            }
            R.string.the_questions -> {
                ExamPaperFragment.newInstance(parentFragmentManager, onlineExam)
            }
            R.string.activate -> {
                showConfirmationDialog(R.string.exam_activation_confirmation) {
                    viewModel.activateExam(onlineExam.id)
                }
            }
            R.string.finish -> {
                showConfirmationDialog(R.string.exam_finish_confirmation) {
                    viewModel.finishExam(onlineExam.id)
                }
            }
            R.string.delete -> {
                showConfirmationDialog(R.string.delete_confirmation) {
                    viewModel.removeExam(onlineExam.id)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (viewModel.isTeacher) {
            inflater.inflate(R.menu.menu_add_btn, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addMenuItem -> if (isAdded) {
                AddOnlineExamFragment.newInstance(parentFragmentManager)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}