package com.smart.resources.schools_app.features.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemAdvertisingBinding
import com.smart.resources.schools_app.databinding.ItemSchoolBinding
import ru.rhanza.constraintexpandablelayout.State

class SchoolsRecyclerAdapter(private val dataList: List<SchoolModel>) :
    RecyclerView.Adapter<SchoolsRecyclerAdapter.MyViewHolder>(),Filterable {

    lateinit var filterListSchools:List<SchoolModel>
    init {
        this.filterListSchools=dataList
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            ItemSchoolBinding.inflate(inflater, parent, false)
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

    }

    override fun getItemCount(): Int {
        return filterListSchools.size
    }

    class MyViewHolder(val binding: ItemSchoolBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: SchoolModel) {
            binding.itemModel = model
        }

    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val str=constraint.toString()
                if(str.isEmpty()){
                    filterListSchools=dataList
                }else{
                    val result=ArrayList<SchoolModel>()
                    for(row in dataList){
                        if(row.title.toLowerCase().contains(str.toLowerCase())){
                            result.add(row)
                        }
                        filterListSchools=result
                    }
                }
                val filterResults=Filter.FilterResults()
                filterResults.values=filterListSchools
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            }

        }
    }

}