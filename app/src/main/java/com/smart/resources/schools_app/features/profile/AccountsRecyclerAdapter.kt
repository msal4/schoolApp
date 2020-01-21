package com.smart.resources.schools_app.features.profile

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.databinding.ItemAccountBinding

class AccountsRecyclerAdapter(private val users:MutableList<User>, private val listener: AccountsRecyclerAdapter.OnItemClickListener) : RecyclerView.Adapter<AccountsRecyclerAdapter.MyViewHolder>(){



    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

    inner class MyViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(accountModel: User) {

            binding.itemModel = accountModel

        }
    }


    fun removeItem(position: Int){
        users.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemAccountBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener{
            listener.onItemClick(users[position])
        }
        holder.bind(users[position])

        if(users[position].uid== SharedPrefHelper.instance?.currentUser){
            holder.binding.profileImage.borderColor= holder.itemView.context.getColor(R.color.green)
            holder.binding.profileImage.borderWidth=10
        }


    }

}