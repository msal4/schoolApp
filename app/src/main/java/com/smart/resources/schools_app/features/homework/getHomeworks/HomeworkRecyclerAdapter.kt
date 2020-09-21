package com.smart.resources.schools_app.features.homework.getHomeworks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ItemHomeworkBinding
import com.smart.resources.schools_app.features.homework.HomeworkModel
import ru.rhanza.constraintexpandablelayout.State

class HomeworkRecyclerAdapter(private val isStudent:Boolean) :
    ListAdapter<HomeworkModel, HomeworkRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onImageClicked: ((ImageView, String) -> Unit)? = null
    var onAnswerClicked: ((homeworkModel: HomeworkModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeworkBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val model = getItem(position)
        holder.bind(model)
        holder.binding.apply {
            image.setOnClickListener {
                if (it is ImageView) onImageClicked?.invoke(it, model.attachment)
            }

            answerButton.apply {
                setOnClickListener { onAnswerClicked?.invoke(model) }
                text = context.getString(getBtnText(model))
            }
        }
    }

    private fun getBtnText(model: HomeworkModel) =
        if (isStudent) {
            if (model.solution == null) R.string.answer_action else R.string.show_answer
        } else {
            R.string.answers
        }

    override fun submitList(list: List<HomeworkModel>?) {
        super.submitList(list?.toList())
    }

    class MyViewHolder(var binding: ItemHomeworkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HomeworkModel?) {
            binding.itemModel = model
        }

        init {
            binding.expandLayout.apply {
                onStateChangeListener =
                    { _: State?, newState: State ->
                        binding.contentText.maxLines =
                            if (newState == State.Expanded || newState == State.Expanding) 500 else 1
                    }
                setOnClickListener {
                    toggle(true)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<HomeworkModel> =
            object : DiffUtil.ItemCallback<HomeworkModel>() {
                override fun areItemsTheSame(
                    oldItem: HomeworkModel,
                    newItem: HomeworkModel
                ): Boolean {
                    return oldItem.idHomework == newItem.idHomework
                }

                override fun areContentsTheSame(
                    oldItem: HomeworkModel,
                    newItem: HomeworkModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}