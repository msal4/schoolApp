package com.smart.resources.schools_app.features.schools

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ItemSchoolBinding
import jp.wasabeef.blurry.Blurry

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
        val model = filterListSchools[position]
        holder.bind(model)

        //val rootLayout= holder.binding.circleImageView

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
                com.orhanobut.logger.Logger.d("mySearch $str")
                if(str.isEmpty()){
                    filterListSchools=dataList
                }else{
                    val result=ArrayList<SchoolModel>()
                    for(row in dataList){

                        if(row.name.toLowerCase().contains(str.toLowerCase())){

                            result.add(row)
                        }
                        filterListSchools=result
                    }
                }
                val filterResults=FilterResults()
                filterResults.values=filterListSchools
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            }

        }
    }

}