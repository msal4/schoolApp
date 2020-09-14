package com.smart.resources.schools_app.features.homeworkSolution.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemHomeworkAnswerBinding
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import ru.rhanza.constraintexpandablelayout.State

class HomeworkAnswerRecyclerAdapter :
    ListAdapter<HomeworkSolutionModel, HomeworkAnswerRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onImageClicked: ((ImageView, String) -> Unit)? = null

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
        holder.binding.image.setOnClickListener {
            if (it is ImageView) onImageClicked?.invoke(it, model.imageUrl.toString())
        }
    }

    override fun submitList(list: List<HomeworkSolutionModel>?) {
        super.submitList(list?.toList())
    }

    class MyViewHolder(var binding: ItemHomeworkAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HomeworkSolutionModel) {
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
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<HomeworkSolutionModel> =
            object : DiffUtil.ItemCallback<HomeworkSolutionModel>() {
                override fun areItemsTheSame(
                    oldItem: HomeworkSolutionModel,
                    newItem: HomeworkSolutionModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: HomeworkSolutionModel,
                    newItem: HomeworkSolutionModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}