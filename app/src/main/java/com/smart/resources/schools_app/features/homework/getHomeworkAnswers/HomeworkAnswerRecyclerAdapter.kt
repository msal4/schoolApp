package com.smart.resources.schools_app.features.homework.getHomeworkAnswers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemHomeworkAnswerBinding
import ru.rhanza.constraintexpandablelayout.State

class HomeworkAnswerRecyclerAdapter :
    ListAdapter<HomeworkAnswerModel, HomeworkAnswerRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeworkAnswerBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val model = getItem(position)
        holder.bind(model)

    }

    override fun submitList(list: List<HomeworkAnswerModel>?) {
        super.submitList(list?.toList())
    }

    class MyViewHolder(var binding: ItemHomeworkAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HomeworkAnswerModel) {
            binding.itemModel = model
        }

        init {
            binding.expandLayout.onStateChangeListener =
                { _: State?, newState: State ->
                    binding.contentText.maxLines =
                        if (newState == State.Expanded || newState == State.Expanding) 500 else 1

                }
            binding.expandLayout.setOnClickListener {
                binding.expandLayout.toggle(
                    true
                )
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<HomeworkAnswerModel> =
            object : DiffUtil.ItemCallback<HomeworkAnswerModel>() {
                override fun areItemsTheSame(
                    oldItem: HomeworkAnswerModel,
                    newItem: HomeworkAnswerModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: HomeworkAnswerModel,
                    newItem: HomeworkAnswerModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}