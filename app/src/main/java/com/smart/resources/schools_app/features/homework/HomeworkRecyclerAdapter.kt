package com.smart.resources.schools_app.features.homework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemHomeworkBinding
import kotlinx.android.synthetic.main.item_homework.view.*
import ru.rhanza.constraintexpandablelayout.State

class HomeworkRecyclerAdapter(val onItemClick: (ImageView, String)-> Unit) :
    ListAdapter<HomeworkModel, HomeworkRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK) {

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
        holder.binding.image.setOnClickListener {
            if(it is ImageView) onItemClick(it, model.attachment)
        }
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
            binding.expandLayout.onStateChangeListener =
                { _: State?, newState: State ->
                    if (newState == State.Collapsed) { /*if(binding.contentText.getLineCount()<3){
                        binding.contentText.append("\u2026");
                    }else {*/
                        binding.contentText.maxLines = 1
                        // }
                    } else if (newState == State.Expanded || newState == State.Expanding) {
                        binding.contentText.maxLines = 100
                        /* String text=binding.contentText.getText().toString();
                    binding.contentText.setText(text.replace('\u2026',' ').trim());*/
                    }
                }
            binding.expandLayout.setOnClickListener {
                binding.expandLayout.toggle(
                    true
                )
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
                    return oldItem.isContentSame(newItem)
                }
            }
    }
}