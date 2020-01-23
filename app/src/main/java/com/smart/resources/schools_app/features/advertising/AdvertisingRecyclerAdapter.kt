package com.smart.resources.schools_app.features.advertising

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAdvertisingBinding
import ru.rhanza.constraintexpandablelayout.State

class AdvertisingRecyclerAdapter(private val dataList: List<AdvertisingModel>, val onItemClick: (ImageView, String)-> Unit) :
    RecyclerView.Adapter<AdvertisingRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            ItemAdvertisingBinding.inflate(inflater, parent, false)
        return MyViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val model = dataList[position]
        holder.bind(model)

        holder.binding.image.setOnClickListener {
            if (it is ImageView) onItemClick(it, model.attachment)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(val binding: ItemAdvertisingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: AdvertisingModel) {
            binding.itemModel = model
        }

        init {
            binding.expandLayout.onStateChangeListener =
                { _: State?, newState: State ->
                    if (newState == State.Collapsed) { /*if(binding.contentText.getLineCount()<3){
                        binding.contentText.append("\u2026");
                    }else {*/
                        binding.contentText.maxLines = 2
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

}