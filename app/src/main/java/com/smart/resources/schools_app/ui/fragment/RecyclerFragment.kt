package com.smart.resources.schools_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.adapter.sections.*
import com.smart.resources.schools_app.database.dao.HomeworkDao
import com.smart.resources.schools_app.database.model.HomeworkModel
import com.smart.resources.schools_app.databinding.FragmentRecyclerBinding
import com.smart.resources.schools_app.ui.activity.SectionActivity
import com.smart.resources.schools_app.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerBinding

    companion object {
        private const val EXTRA_SECTION = "extraSection"

        fun newInstance(section: Section): RecyclerFragment {
            val fragment = RecyclerFragment()
            Bundle().apply {
                putSerializable(EXTRA_SECTION, section)
                fragment.arguments = this
            }

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecyclerBinding.inflate(inflater, container, false)


        mapSectionToRecycler()
        return binding.root
    }

    private fun mapSectionToRecycler() {
        binding.progressIndicator.show()

        var stringId= 0
        val adapter:RecyclerView.Adapter<out RecyclerView.ViewHolder>?

        when (arguments?.getSerializable(EXTRA_SECTION)) {
            Section.HOMEWORK -> {
                setupHomeworkRecycler()
                stringId= R.string.homework
            }
            Section.EXAM -> {
                adapter = ExamRecyclerAdapter()
                binding.recyclerView.adapter= adapter
                stringId= R.string.exams
            }

            Section.LIBRARY ->{
                createGridLayout(LibraryRecyclerAdapter())
                stringId= R.string.library
            }

            Section.NOTIFICATION ->{
                adapter = NotificationRecyclerAdapter()
                binding.recyclerView.adapter= adapter
                stringId= R.string.notifications
            }
            Section.SCHEDULE -> {
                createGridLayout(ScheduleRecyclerAdapter())
                stringId= R.string.schedule
            }
            Section.ABSENCE -> {
                adapter= AbsenceRecyclerAdapter()
                binding.recyclerView.adapter= adapter
                stringId= R.string.absence
            }
        }
            (activity as SectionActivity).setCustomTitle(stringId)
    }

    private fun setupHomeworkRecycler() {
        val homeworkDao = BackendHelper.retrofit.create(HomeworkDao::class.java)
        homeworkDao.fetchHomework().enqueue(
            object : BackendCallBack<List<HomeworkModel>>() {
                override fun onFailure(call: Call<List<HomeworkModel>>, t: Throwable) {
                    super.onFailure(call, t)
                    this@RecyclerFragment.context?.toast(t.message.toString())
                    binding.errorText.text= getString(R.string.connection_error)
                }

                override fun onSuccess(result: List<HomeworkModel>?) {
                    if(result.isNullOrEmpty()){
                        binding.errorText.text= getString(R.string.no_homework)
                        return
                    }
                    binding.recyclerView.adapter = HomeworkRecyclerAdapter(result)
                }

                override fun onError(code: Int, msg: String?) {
                    binding.errorText.text= msg
                }

                override fun onFinish() {
                    binding.progressIndicator.hide()
                }
            }
        )
    }


    private fun createGridLayout(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        binding.recyclerView.apply {

            val itemMargin = resources.getDimension(R.dimen.item_margin).toInt()
            setPadding(itemMargin, itemMargin, 0, itemMargin)
            layoutManager = GridLayoutManager(context, 2)
            this.adapter = adapter
        }
    }

}
